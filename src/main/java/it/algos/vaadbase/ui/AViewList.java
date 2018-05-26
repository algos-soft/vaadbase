package it.algos.vaadbase.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcons;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import it.algos.vaadbase.backend.entity.AEntity;
import it.algos.vaadbase.backend.service.IAService;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.service.AAnnotationService;
import it.algos.vaadbase.service.ATextService;
import it.algos.vaadbase.ui.dialog.AForm;
import it.algos.vaadbase.ui.dialog.AViewDialog;
import it.algos.vaadbase.ui.dialog.IADialog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: sab, 05-mag-2018
 * Time: 18:49
 * Classe astratta per visualizzare la Grid e il Form/Dialog <br>
 * <p>
 * Le sottoclassi concrete NON hanno le annotation @SpringComponent, @SpringView e @Scope
 * NON annotated with @SpringComponent - Sbagliato perché va in conflitto con la @Route
 * NON annotated with @SpringView - Sbagliato perché usa la Route di VaadinFlow
 * NON annotated with @Scope - Viene ricreata ogni volta
 * Annotated with @Route (obbligatorio) per la selezione della vista.
 * <p>
 * Annotation @Route(value = "") per la vista iniziale - Ce ne pouò essere solo una per applicazione
 * ATTENZIONE: se rimangono due (o più) classi con @Route(value = ""), in fase di compilazione appare l'errore:
 * -'org.springframework.context.ApplicationContextException:
 * -Unable to start web server;
 * -nested exception is org.springframework.boot.web.server.WebServerException:
 * -Unable to start embedded Tomcat'
 * <p>
 * Annotated with @Qualifier (obbligatorio) nel costruttore per permettere a Spring di istanziare la sottoclasse specifica
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 */
@Slf4j
public abstract class AViewList extends VerticalLayout implements IAView, BeforeEnterObserver {

    protected final TextField searchField = new TextField("", "Search");
    /**
     * Service iniettato da Spring (@Scope = 'singleton'). Unica per tutta l'applicazione. Usata come libreria.
     */
    @Autowired
    public AAnnotationService annotation;
    /**
     * Service iniettato da Spring (@Scope = 'singleton'). Unica per tutta l'applicazione. Usata come libreria.
     */
    @Autowired
    protected ATextService text;
    /**
     * Il presenter viene iniettato dal costruttore della sottoclasse concreta
     */
    protected IAPresenter presenter;

    /**
     * Il service viene recuperato dal presenter,
     * La repository è gestita direttamente dal service
     */
    protected IAService service;

    /**
     * Il modello-dati specifico viene recuperato dal presenter
     */
    protected Class<? extends AEntity> entityClazz;


    protected Grid<AEntity> grid;

    /**
     * Caption sovrastante il body della view
     * Valore che può essere regolato nella classe specifica
     * Componente grafico facoltativo. Normalmente presente (Grid), ma non obbligatorio.
     */
    protected String caption;

    protected IADialog dialog;
    protected AForm form;


    @Deprecated
    public AViewList(IAPresenter presenter) {
        this.presenter = presenter;
        this.service = presenter.getService();
        this.entityClazz = presenter.getEntityClazz();
        initView();
    }// end of constructor

    public AViewList(IAPresenter presenter, IADialog dialog) {
        this.presenter = presenter;
        this.presenter.setView(this);
        this.dialog = dialog;
        this.service = presenter.getService();
        this.entityClazz = presenter.getEntityClazz();
        initView();
    }// end of constructor

    protected void initView() {
//        addClassName("categories-list");
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);

        addDeveloperAlert();
        addSearchBar();
        addGrid();

        updateView();
    }// end of method


    protected void addDeveloperAlert() {
    }// end of method

    protected void addSearchBar() {
        Div viewToolbar = new Div();
        viewToolbar.addClassName("view-toolbar");

        searchField.setPrefixComponent(new Icon("lumo", "search"));
        searchField.addClassName("view-toolbar__search-field");
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> updateView());

        Button clearFilterTextBtn = new Button(new Icon(VaadinIcons.CLOSE_CIRCLE));
        clearFilterTextBtn.addClickListener(e -> searchField.clear());

        Button newButton = new Button("New entity", new Icon("lumo", "plus"));
        newButton.getElement().setAttribute("theme", "primary");
        newButton.addClassName("view-toolbar__button");
        newButton.addClickListener(e -> dialog.open(service.newEntity(), AViewDialog.Operation.ADD));

        viewToolbar.add(searchField, clearFilterTextBtn, newButton);
        add(viewToolbar);
    }// end of method


    /**
     * Crea il corpo centrale della view
     * Componente grafico obbligatorio
     * Sovrascritto nella sottoclasse della view specifica (AList, AForm, ...)
     */
    protected void addGrid() {
        List<String> gridPropertiesName = service.getGridPropertiesName();

        if (AEntity.class.isAssignableFrom(entityClazz)) {
            try { // prova ad eseguire il codice
                grid = new Grid(entityClazz);
            } catch (Exception unErrore) { // intercetta l'errore
                log.error(unErrore.toString());
                return;
            }// fine del blocco try-catch
        }// end of if cycle

        for (Grid.Column column : grid.getColumns()) {
            grid.removeColumn(column);
        }// end of for cycle
        for (String property : gridPropertiesName) {
            grid.addColumn(property);
        }// end of for cycle

        grid.setWidth("50em");
        grid.setHeightByRows(true);
        grid.addClassName("pippoz");
        grid.getElement().setAttribute("theme", "row-dividers");
        add(grid);
    }// end of method


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        this.updateView();
    }// end of method


    protected void saveUpdate(AEntity entityBean, AViewDialog.Operation operation) {
        service.save(entityBean);
        updateView();
        Notification.show(entityBean + " successfully " + operation.getNameInText() + "ed.", 3000, Notification.Position.BOTTOM_START);
    }// end of method


    protected void deleteUpdate(AEntity entityBean) {
        service.delete(entityBean);
        Notification.show(entityBean + " successfully deleted.", 3000, Notification.Position.BOTTOM_START);
        updateView();
    }// end of method


    public void updateView() {
        List items = service.findFilter(searchField.getValue());
        grid.setItems(items);
    }// end of method


    @Override
    public String getName() {
        return annotation.getViewName(this.getClass());
    }// end of method

}// end of class
