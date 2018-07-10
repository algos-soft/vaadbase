package it.algos.vaadbase.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import it.algos.vaadbase.application.StaticContextAccessor;
import it.algos.vaadbase.backend.entity.AEntity;
import it.algos.vaadbase.backend.service.IAService;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.service.*;
import it.algos.vaadbase.ui.dialog.AForm;
import it.algos.vaadbase.ui.dialog.AViewDialog;
import it.algos.vaadbase.ui.dialog.IADialog;
import it.algos.vaadbase.ui.enumeration.EAFieldType;
import it.algos.vaadbase.ui.footer.AFooter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
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

    protected final static String EDIT_NAME = "Edit";
    protected final TextField searchField = new TextField("", "Search");

    /**
     * Questa classe viene costruita partendo da @Route e non da SprinBoot <br>
     * Service iniettato nel metodo initInjection() <br>
     */
    public AAnnotationService annotation;

    /**
     * Questa classe viene costruita partendo da @Route e non da SprinBoot <br>
     * Service iniettato nel metodo initInjection() <br>
     */
    public AReflectionService reflection;

    /**
     * Questa classe viene costruita partendo da @Route e non da SprinBoot <br>
     * Service iniettato nel metodo initInjection() <br>
     */
    public AArrayService array;

    /**
     * Questa classe viene costruita partendo da @Route e non da SprinBoot <br>
     * Service iniettato nel metodo initInjection() <br>
     */
    protected ATextService text;

    /**
     * Il presenter viene iniettato dal costruttore della sottoclasse concreta
     */
    protected IAPresenter presenter;

    /**
     * Il dialog viene iniettato dal costruttore della sottoclasse concreta
     */
    protected IADialog dialog;

    /**
     * Il service viene recuperato dal presenter,
     * La repository è gestita direttamente dal service
     */
    protected IAService service;

    /**
     * Layout iniettato da Spring
     */
    @Autowired
    protected AFooter footer;

    protected HorizontalLayout headerPlaceHolder = new HorizontalLayout();
    protected Div viewToolbar;
    protected Button newButton;

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

    protected AForm form;


//    @Deprecated
//    public AViewList(IAPresenter presenter) {
//        this.presenter = presenter;
//        this.service = presenter.getService();
//        this.entityClazz = presenter.getEntityClazz();
//        initView();
//    }// end of constructor


    /**
     * Costruttore @Autowired (nella sottoclasse concreta) <br>
     * La sottoclasse usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * La sottoclasse usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     */
    public AViewList(IAPresenter presenter, IADialog dialog) {
        this.presenter = presenter;
        this.presenter.setView(this);
        this.dialog = dialog;
        this.service = presenter.getService();
        this.entityClazz = presenter.getEntityClazz();
    }// end of Spring constructor


    /**
     * Questa classe viene costruita partendo da @Route e non da SprinBoot <br>
     * La injection può essere fatta SOLO DOPO il metodo init() <br>
     */
    @PostConstruct
    private void initView() {
        addClassName("categories-list");
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);

        addSearchBar();
        addDeveloperAlert();
        addHeader();
        addGrid();
        addFooter();

        updateView();
    }// end of method

//    /**
//     * Questa classe viene costruita partendo da @Route e non da SprinBoot <br>
//     * La injection può essere fatta o nel costruttore (sconsigliato), o usando il StaticContextAccessor.getBean() <br>
//     */
//    private void initInjection() {
//        this.annotation = StaticContextAccessor.getBean(AAnnotationService.class);
//        this.reflection = StaticContextAccessor.getBean(AReflectionService.class);
//        this.array = StaticContextAccessor.getBean(AArrayService.class);
//        this.text = StaticContextAccessor.getBean(ATextService.class);
//    }// end of method
//    protected void initView() {
//    }// end of method


    protected void addDeveloperAlert() {
    }// end of method


    protected void addSearchBar() {
        viewToolbar = new Div();
        viewToolbar.addClassName("view-toolbar");

        searchField.setPrefixComponent(new Icon("lumo", "search"));
        searchField.addClassName("view-toolbar__search-field");
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> updateView());

        Button clearFilterTextBtn = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
        clearFilterTextBtn.addClickListener(e -> searchField.clear());

        newButton = new Button("New entity", new Icon("lumo", "plus"));
        newButton.getElement().setAttribute("theme", "primary");
        newButton.addClassName("view-toolbar__button");
        newButton.addClickListener(e -> dialog.open(service.newEntity(), AViewDialog.Operation.ADD));

        viewToolbar.add(searchField, clearFilterTextBtn, newButton);
        add(viewToolbar);
    }// end of method


    protected void addHeader() {
        this.add(headerPlaceHolder);
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
            addColonna(property);
        }// end of for cycle

        //--Aggiunge eventuali colonne calcolate
        addSpecificColumns();

        this.addEditButton();

        grid.setWidth("50em");
        grid.setHeightByRows(true);
        grid.addClassName("pippoz");
        grid.getElement().setAttribute("theme", "row-dividers");
        add(grid);
    }// end of method


    protected void addColonna(String property) {
        Grid.Column<AEntity> colonna = null;
        EAFieldType type = annotation.getFormType(entityClazz, property);
        String header = annotation.getColumnName(entityClazz, property);

        switch (type) {
            case text:
                colonna = grid.addColumn(property);
                break;
            case integer:
                colonna = grid.addColumn(property);
                break;
            case checkbox:
                colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                    Boolean status = false;
                    Field field = reflection.getField(entityClazz, property);
                    try { // prova ad eseguire il codice
                        status = field.getBoolean(entity);
                    } catch (Exception unErrore) { // intercetta l'errore
                        log.error(unErrore.toString());
                    }// fine del blocco try-catch
                    return new Checkbox(status);
                }));
                break;
            default:
                log.warn("Switch - caso non definito");
                break;
        } // end of switch statement

        if (colonna != null) {
            colonna.setHeader(text.isValid(header) ? header : property);
            colonna.setSortProperty(property);
        }// end of if cycle

    }// end of method

    /**
     * Aggiunge eventuali colonne calcolate
     */
    protected void addSpecificColumns() {
    }// end of method


    private void addEditButton() {
        ComponentRenderer renderer = new ComponentRenderer<>(this::createEditButton);
        grid.addColumn(renderer);
        this.setFlexGrow(0);
    }// end of method

    private Button createEditButton(AEntity entityBean) {
        Button edit = new Button(EDIT_NAME, event -> dialog.open(entityBean, AViewDialog.Operation.EDIT));
        edit.setIcon(new Icon("lumo", "edit"));
        edit.addClassName("review__edit");
        edit.getElement().setAttribute("theme", "tertiary");
        return edit;
    }// end of method


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        this.updateView();
    }// end of method


    protected void save(AEntity entityBean, AViewDialog.Operation operation) {
        switch (operation) {
            case ADD:
                if (service.isEsisteEntityKeyUnica(entityBean)) {
                    Notification.show(entityBean + " non è stata registrata, perché esisteva già con lo stesso code ", 3000, Notification.Position.BOTTOM_START);
                } else {
                    service.save(entityBean);
                    updateView();
                    Notification.show(entityBean + " successfully " + operation.getNameInText() + "ed.", 3000, Notification.Position.BOTTOM_START);
                }// end of if/else cycle
                break;
            case EDIT:
                service.save(entityBean);
                updateView();
                Notification.show(entityBean + " successfully " + operation.getNameInText() + "ed.", 3000, Notification.Position.BOTTOM_START);
                break;
            default:
                log.warn("Switch - caso non definito");
                break;
        } // end of switch statement

    }// end of method


    protected void delete(AEntity entityBean) {
        service.delete(entityBean);
        Notification.show(entityBean + " successfully deleted.", 3000, Notification.Position.BOTTOM_START);
        updateView();
    }// end of method


    public void updateView() {
        List items = service.findFilter(searchField.getValue());
        if (items != null) {
            grid.setItems(items);
        }// end of if cycle
        addFooter();
    }// end of method

    protected void addFooter() {
        if (footer != null) {
            footer.setAppMessage("");
            add(footer);
        }// end of if cycle
    }// end of method

    @Override
    public String getName() {
        return annotation.getViewName(this.getClass());
    }// end of method

}// end of class
