package it.algos.vaadbase.ui;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.backend.entity.AEntity;
import it.algos.vaadbase.backend.service.IAService;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.service.ATextService;
import it.algos.vaadbase.ui.dialog.AForm;
import it.algos.vaadbase.ui.menu.AMenu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: sab, 24-mar-2018
 * Time: 08:40
 */
@Slf4j
@SpringComponent
//@Theme(Lumo.class)
@HtmlImport("frontend://styles/shared-styles.html")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AView extends VerticalLayout implements IAView, BeforeEnterObserver {


    protected final TextField searchField = new TextField("", "Search");
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
     * Il modello-dati specifico viene recuperato dal presenter
     */
    protected Class<? extends AEntity> entityClazz;
    /**
     * Il service viene recuperato dal presenter,
     * La repository è gestita direttamente dal service
     */
    protected IAService service;
    /**
     * Contenitore grafico per la barra di menu principale e per il menu/bottone del Login
     * Un eventuale menuBar specifica può essere iniettata dalla sottoclasse concreta
     * Le sottoclassi possono aggiungere/modificare i menu che verranno ripristinati all'uscita della view
     * Componente grafico obbligatorio
     */
    @Autowired
    protected AMenu menu;
    protected Grid<AEntity> grid;
    /**
     * Caption sovrastante il body della view
     * Valore che può essere regolato nella classe specifica
     * Componente grafico facoltativo. Normalmente presente (Grid), ma non obbligatorio.
     */
    protected String caption;
    protected AForm form;

    /**
     * Costruttore vuoto di default
     */
    public AView() {
    }// end of Spring constructor


    /**
     * Costruttore @Autowired (nella sottoclasse concreta)
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation.
     * L' @Autowired (esplicito o implicito) funziona SOLO per UN costruttore
     * Se ci sono DUE o più costruttori, va in errore
     * Se ci sono DUE costruttori, di cui uno senza parametri, inietta quello senza parametri
     *
     * @param presenter iniettato da Spring come sottoclasse concreta specificata dal @Qualifier
     */
    public AView(IAPresenter presenter) {
        this.presenter = presenter;
        this.entityClazz = presenter.getEntityClazz();
        this.service = presenter.getService();
        initView();
    }// end of Spring constructor


    protected void initView() {
//        this.setSpacing(true);
//        this.setMargin(true);
//        this.setWidth("100%");
//        this.setHeight("100%");

//        addClassName("categories-list");
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);

        addSearchBar();
        addGrid();

        updateView();
    }// end of method


    protected void addSearchBar() {
        Div viewToolbar = new Div();
        viewToolbar.addClassName("view-toolbar");

        searchField.setPrefixComponent(new Icon("lumo", "search"));
        searchField.addClassName("view-toolbar__search-field");
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> updateView());

        Button clearFilterTextBtn = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
        clearFilterTextBtn.addClickListener(e -> searchField.clear());

        Button newButton = new Button("New entity", new Icon("lumo", "plus"));
        newButton.getElement().setAttribute("theme", "primary");
        newButton.addClassName("view-toolbar__button");
        newButton.addClickListener(e -> form.open(service.newEntity(), AForm.Operation.ADD));

        viewToolbar.add(searchField,clearFilterTextBtn, newButton);
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

//        ComponentRenderer renderer = new ComponentRenderer<>(this::createEditButton);
//        grid.addColumn(renderer);
//        this.setFlexGrow(0);

        grid.setWidth("50em");
        grid.setHeightByRows(true);
        grid.addClassName("pippoz");
        grid.getElement().setAttribute("theme", "row-dividers");
        add(grid);
    }// end of method


//    protected Button createEditButton(AEntity entityBean) {
//        Button edit = new Button("Edit", event -> form.open(entityBean, AForm.Operation.EDIT));
//        edit.setIcon(new Icon("lumo", "edit"));
//        edit.addClassName("review__edit");
//        edit.getElement().setAttribute("theme", "tertiary");
//        return edit;
//        return null;
//    }// end of method

    protected void saveUpdate(AEntity entityBean, AForm.Operation operation) {
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
    public void beforeEnter(BeforeEnterEvent event) {
//        this.removeAll();
        List items = service.findAll();

        //--componente grafico facoltativo
//        this.regolaMenu();
//        this.add(menu);
//

        //--componente grafico facoltativo
        VerticalLayout topLayout = creaTop(items);
        if (topLayout != null) {
            this.add(topLayout);
        }// end of if cycle


        //--componente grafico obbligatorio
//        VerticalLayout bodyLayout = creaBody(items);
//        this.add(bodyLayout);

    }// end of method


    /**
     * Contenitore grafico per la barra di menu principale e per il menu/bottone del Login
     * Un eventuale menuBar specifica può essere iniettata dalla sottoclasse concreta
     * Le sottoclassi possono aggiungere/modificare i menu che verranno ripristinati all'uscita della view
     * Componente grafico obbligatorio
     *
     * @return MenuLayout
     */
    protected void regolaMenu() {
        menu.start();
    }// end of method


    /**
     * Caption sovrastante il body della view
     * Valore che può essere regolato nella classe specifica
     * Componente grafico facoltativo. Normalmente presente (AList e AForm), ma non obbligatorio.
     *
     * @param items da visualizzare nella Grid
     */
    protected VerticalLayout creaTop(List items) {
        VerticalLayout topLayout = null;
        Label labelRosso = new Label();

        //--gestione delle scritte in rosso sopra il body
        this.fixCaption(entityClazz, items);
        if (text.isValid(caption)) {
            labelRosso.setText(caption);
            topLayout = new VerticalLayout();
            topLayout.setMargin(false);
            topLayout.add(labelRosso);

            //@todo RIMETTERE
//            if (LibParams.usaAvvisiColorati()) {
//                topLayout.addComponent(new LabelRosso(caption));
//            } else {
//                topLayout.addComponent(new Label(caption));
//            }// end of if/else cycle
        }// end of if cycle

        return topLayout;
    }// end of method


//    /**
//     * Crea il corpo centrale della view
//     * Componente grafico obbligatorio
//     * Sovrascritto nella sottoclasse della view specifica (AList, AForm, ...)
//     *
//     * @param items da visualizzare nella Grid
//     */
//    protected VerticalLayout creaBody(List items) {
//        VerticalLayout bodyLayout = new VerticalLayout();
//        List<String> gridPropertiesName = service.getGridPropertiesName();
//
//        if (AEntity.class.isAssignableFrom(entityClazz)) {
//            try { // prova ad eseguire il codice
//                grid = new Grid(entityClazz);
//            } catch (Exception unErrore) { // intercetta l'errore
//                log.error(unErrore.toString());
//            }// fine del blocco try-catch
//        }// end of if cycle
//
//
//        grid.setItems(items);
//        for (Grid.Column column : grid.getColumns()) {
//            grid.removeColumn(column);
//        }// end of for cycle
//        for (String property : gridPropertiesName) {
//            grid.addColumn(property);
//        }// end of for cycle
//        ComponentRenderer renderer = new ComponentRenderer<>(this::createEditButton);
//        grid.addColumn(renderer);
//        this.setFlexGrow(0);
//
//        grid.setWidth("50em");
//        grid.setHeightByRows(true);
//        grid.addClassName("pippoz");
//        grid.getElement().setAttribute("theme", "row-dividers");
//
//        bodyLayout.add(grid);
//        return bodyLayout;
//    }// end of method

    /**
     * Crea la scritta esplicativa
     * Può essere sovrascritto per un'intestazione specifica (caption) della grid
     */
    protected void fixCaption(Class<? extends AEntity> entityClazz, List items) {
        String className = entityClazz != null ? entityClazz.getSimpleName() : null;

        caption = className != null ? className + " - " : "";

        if (items.size() == 1) {
            caption += "Elenco di 1 sola scheda ";
        } else {
            caption += "Elenco di " + items.size() + " schede ";
        }// end of if/else cycle
    }// end of method

    @Override
    public String getName() {
        return null;
    }
}// end of class
