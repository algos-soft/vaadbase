package it.algos.vaadbase.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.selection.SingleSelectionEvent;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import it.algos.vaadbase.backend.entity.AEntity;
import it.algos.vaadbase.backend.service.IAService;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.service.*;
import it.algos.vaadbase.ui.dialog.AViewDialog;
import it.algos.vaadbase.ui.dialog.IADialog;
import it.algos.vaadbase.ui.enumeration.EAFieldType;
import it.algos.vaadbase.ui.footer.AFooter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
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
 * Graficamente abbiamo:
 * una SearchBar (eventuale, presente di default); con o senza bottone New, regolato da preferenza o da parametro
 * un avviso (eventuale) per il developer
 * un layout top (eventuale) con bottoni aggiuntivi
 * una Grid (obbligatoria); alcune regolazioni da preferenza o da parametro (bottone Edit, ad esempio)
 * un layout bottom (eventuale) con bottoni aggiuntivi
 * un footer (obbligatorio) con informazioni generali
 * <p>
 * Le injections vengono fatta da SpringBoot nel metodo @PostConstruct DOPO init() automatico
 * Le preferenze vengono (eventualmente) lette da Mongo e (eventualmente) sovrascritte nella sottoclasse
 * <p>
 * Annotation @Route(value = "") per la vista iniziale - Ce ne pouò essere solo una per applicazione
 * ATTENZIONE: se rimangono due (o più) classi con @Route(value = ""), in fase di compilazione appare l'errore:
 * -'org.springframework.context.ApplicationContextException:
 * -Unable to start web server;
 * -nested exception is org.springframework.boot.web.server.WebServerException:
 * -Unable to start embedded Tomcat'
 * <p>
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 */
@Slf4j
public abstract class AViewList extends VerticalLayout implements IAView, BeforeEnterObserver {

    protected final static String EDIT_NAME = "Edit";
    protected final TextField searchField = new TextField("", "Search");

    /**
     * Questa classe viene costruita partendo da @Route e non da SprinBoot <br>
     * La injection viene fatta da SpringBoot nel metodo @PostConstruct DOPO init() automatico <br>
     */
    @Autowired
    public AAnnotationService annotation;

    /**
     * Questa classe viene costruita partendo da @Route e non da SprinBoot <br>
     * La injection viene fatta da SpringBoot nel metodo @PostConstruct DOPO init() automatico <br>
     */
    @Autowired
    public AReflectionService reflection;

    /**
     * Questa classe viene costruita partendo da @Route e non da SprinBoot <br>
     * La injection viene fatta da SpringBoot nel metodo @PostConstruct DOPO init() automatico <br>
     */
    @Autowired
    public AArrayService array;

    /**
     * Questa classe viene costruita partendo da @Route e non da SprinBoot <br>
     * La injection viene fatta da SpringBoot nel metodo @PostConstruct DOPO init() automatico <br>
     */
    @Autowired
    protected ATextService text;

    /**
     * Questa classe viene costruita partendo da @Route e non da SprinBoot <br>
     * La injection viene fatta da SpringBoot nel metodo @PostConstruct DOPO init() automatico <br>
     */
    @Autowired
    protected APreferenzaService pref;

    /**
     * Questa classe viene costruita partendo da @Route e non da SprinBoot <br>
     * La injection viene fatta da SpringBoot nel metodo @PostConstruct DOPO init() automatico <br>
     */
    @Autowired
    protected ADateService date;

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
     * Il modello-dati specifico viene recuperato dal presenter
     */
    protected Class<? extends AEntity> entityClazz;

    /**
     * Questa classe viene costruita partendo da @Route e non da SprinBoot <br>
     * La injection viene fatta da SpringBoot nel metodo @PostConstruct DOPO init() automatico <br>
     */
    @Autowired
    protected AFooter footer;

    /**
     * Placeholder per (eventuali) bottoni SOPRA della Grid <br>
     */
    protected HorizontalLayout topLayout = new HorizontalLayout();

    /**
     * Placeholder per (eventuali) bottoni SOTTO la Grid <br>
     */
    protected HorizontalLayout bottomLayout = new HorizontalLayout();


    /**
     * Griglia principale <br>
     */
    protected Grid<AEntity> grid;

    /**
     * Flag di preferenza per usare la searchBar. Normalmente true.
     */
    protected boolean usaSearchBar;


    /**
     * Flag di preferenza per usare il bottone new. Normalmente true.
     */
    protected boolean usaBottoneNew;


    /**
     * Flag di preferenza per mostrare una caption sopra la grid. Normalmente true.
     */
    protected boolean usaCaption;


    /**
     * Flag di preferenza per modificare la entity. Normalmente true.
     */
    protected boolean isEntityModificabile;


    /**
     * Flag di preferenza per aprire il dialog di detail con un bottone Edit. Normalmente false.
     */
    protected boolean usaBottoneEdit;


    /**
     * Flag di preferenza per il testo del bottone Edit. Normalmente 'Edit'.
     */
    protected String testoBottoneEdit;


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
     * La injection viene fatta da SpringBoot SOLO DOPO il metodo init() automatico <br>
     * Le preferenze vengono (eventualmente) lette da Mongo e (eventualmente) sovrascritte nella sottoclasse
     */
    @PostConstruct
    private void initView() {
        addClassName("categories-list");
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);

        fixPreferenze();
        creaSearchBar();
        creaDeveloperAlert();
        creaTopLayout();
        creaCaption();
        creaGrid();
        creaBottomLayout();
        creaFooter();

        updateView();
    }// end of method


    /**
     * Le preferenze vengono (eventualmente) lette da Mongo e (eventualmente) sovrascritte nella sottoclasse
     */
    private void fixPreferenze() {
        //--Flag di preferenza per usare la searchBar. Normalmente true.
        usaSearchBar = true;

        //--Flag di preferenza per usare il bottone new. Normalmente true.
        usaBottoneNew = true;

        //--Flag di preferenza per mostrare una caption sopra la grid. Normalmente true.
        usaCaption = true;

        //--Flag di preferenza per modificare la entity. Normalmente true.
        isEntityModificabile = true;

        //--Flag di preferenza per aprire il dialog di detail con un bottone Edit. Normalmente true.
        usaBottoneEdit = false;

        //--Flag di preferenza per il testo del bottone Edit. Normalmente 'Edit'.
        testoBottoneEdit = EDIT_NAME;

        //--Le preferenze sovrascritte nella sottoclasse
        fixPreferenzeSpecifiche();
    }// end of method

    /**
     * Le preferenze sovrascritte nella sottoclasse
     */
    protected void fixPreferenzeSpecifiche() {
    }// end of method


    /**
     * Costruisce la searchBar
     * CSS specifico
     * Sempre presente il campo edit di ricerca/selezione
     * Sempre presente il bottone di reset del valore del campo di ricerca/selezione
     * Facoltativo (presente di default) il bottone New (flag da Mongo eventualmente sovrascritto)
     */
    protected void creaSearchBar() {
        Div viewToolbar = new Div();
        Button newButton = null;
        viewToolbar.addClassName("view-toolbar");

        if (!usaSearchBar) {
            return;
        }// end of if cycle

        searchField.setPrefixComponent(new Icon("lumo", "search"));
        searchField.addClassName("view-toolbar__search-field");
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> updateView());

        Button clearFilterTextBtn = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
        clearFilterTextBtn.addClickListener(e -> searchField.clear());

        viewToolbar.add(searchField, clearFilterTextBtn);

        if (usaBottoneNew) {
            newButton = new Button("New entity", new Icon("lumo", "plus"));
            newButton.getElement().setAttribute("theme", "primary");
            newButton.addClassName("view-toolbar__button");
            newButton.addClickListener(e -> dialog.open(service.newEntity(), AViewDialog.Operation.ADD));
            viewToolbar.add(newButton);
        }// end of if cycle

        add(viewToolbar);
    }// end of method


    /**
     * Costruisce un (eventuale) layout per informazioni ad uso esclusivo del developer
     */
    protected void creaDeveloperAlert() {
    }// end of method


    /**
     * Costruisce un (eventuale) layout con bottoni aggiuntivi
     * Facoltativo (assente di default); eventualmente sovrascritto
     */
    protected void creaTopLayout() {
        topLayout = new HorizontalLayout();
        this.add(topLayout);
    }// end of method

    /**
     * Crea il corpo centrale della view
     * Componente grafico obbligatorio
     * Alcune regolazioni vengono (eventualmente) lette da Mongo e (eventualmente) sovrascritte nella sottoclasse
     * Facoltativo (presente di default) il bottone Edit (flag da Mongo eventualmente sovrascritto)
     */
    protected void creaGrid() {
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

        //--Apre il dialog di detail
        this.addDetailDialog();

        grid.setWidth("50em");
        grid.setHeightByRows(true);
        grid.addClassName("pippoz");
        grid.getElement().setAttribute("theme", "row-dividers");
        add(grid);
    }// end of method

    /**
     * Eventuale caption sopra la grid
     */
    protected void creaCaption() {
        String testo;
        int count = 0;
        String tag = "";

        if (usaCaption) {
            count = service.count();

            switch (count) {
                case 0:
                    tag = "Al momento non ci sono elementi in questa collezione";
                    break;
                case 1:
                    tag = "Collezione con un solo elemento";
                    break;
                default:
                    tag = "Collezione con " + count + " elementi";
                    break;
            } // end of switch statement

            testo = entityClazz.getSimpleName() + " - " + tag;
            this.add(new Label(testo));
        }// end of if cycle

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
            case localdatetime:
                colonna = grid.addColumn(new ComponentRenderer<>(entity -> {
                    LocalDateTime timeStamp;
                    String testo = "X";
                    Field field = reflection.getField(entityClazz, property);
                    try { // prova ad eseguire il codice
                        timeStamp = (LocalDateTime) field.get(entity);
                        testo = date.getTime(timeStamp);
                    } catch (Exception unErrore) { // intercetta l'errore
                        log.error(unErrore.toString());
                    }// fine del blocco try-catch
                    return new Label(testo);
                }));
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

    /**
     * Apre il dialog di detail
     */
    private void addDetailDialog() {
        //--Flag di preferenza per aprire il dialog di detail con un bottone Edit. Normalmente true.
        if (usaBottoneEdit) {
            ComponentRenderer renderer = new ComponentRenderer<>(this::createEditButton);
            grid.addColumn(renderer);
            this.setFlexGrow(0);
        } else {
            grid.setSelectionMode(Grid.SelectionMode.SINGLE);
            AViewDialog.Operation operation = isEntityModificabile ? AViewDialog.Operation.EDIT : AViewDialog.Operation.SHOW;
            grid.addSelectionListener(evento -> apreDialogo((SingleSelectionEvent) evento, operation));
        }// end of if/else cycle
    }// end of method


    private Button createEditButton(AEntity entityBean) {
        Button edit = new Button(testoBottoneEdit, event -> dialog.open(entityBean, AViewDialog.Operation.EDIT));
        edit.setIcon(new Icon("lumo", "edit"));
        edit.addClassName("review__edit");
        edit.getElement().setAttribute("theme", "tertiary");
        return edit;
    }// end of method

    private void apreDialogo(SingleSelectionEvent evento, AViewDialog.Operation operation) {
        if (evento != null && evento.getOldValue() != evento.getValue()) {
            if (evento.getValue().getClass().getName().equals(entityClazz.getName())) {
                dialog.open((AEntity) evento.getValue(), operation);
            }// end of if cycle
        }// end of if cycle
    }// end of method

    /**
     * Costruisce un (eventuale) layout con bottoni aggiuntivi
     * Facoltativo (assente di default); eventualmente sovrascritto
     */
    protected void creaBottomLayout() {
        bottomLayout = new HorizontalLayout();
        this.add(bottomLayout);
    }// end of method


    protected void creaFooter() {
        if (footer != null) {
            footer.setAppMessage("");
            this.add(footer);
        }// end of if cycle
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
            try { // prova ad eseguire il codice
                grid.deselectAll();
                grid.setItems(items);
            } catch (Exception unErrore) { // intercetta l'errore
                log.error(unErrore.toString());
            }// fine del blocco try-catch
        }// end of if cycle
        creaFooter();
    }// end of method


    @Override
    public String getName() {
        return annotation.getViewName(this.getClass());
    }// end of method

}// end of class
