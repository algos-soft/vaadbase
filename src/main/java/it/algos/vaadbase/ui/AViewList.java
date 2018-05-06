package it.algos.vaadbase.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import it.algos.vaadbase.backend.entity.AEntity;
import it.algos.vaadbase.backend.service.IAService;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.ui.dialog.AForm;
import it.algos.vaadbase.ui.dialog.IADialog;
import it.algos.vaadbase.ui.menu.AMenuDiv;
import it.algos.vaadbase.ui.menu.IAMenu;
import it.algos.vaadtest.modules.prova.Prova;
import lombok.extern.slf4j.Slf4j;

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
 * NON annotated with @SpringComponent - Non funziona - Va in conflitto con la @Route
 * NON annotated with @SpringView - Non funziona perché usa la Route di VaadinFlow
 * NON annotated with @Scope - Viene ricreata ogni volta
 * Annotated with @Route (obbligatorio) per la selezione della vista.
 *
 * @Route(value = "") per la vista iniziale - Ce ne pouò essere solo una per applicazione
 * Annotated with @Qualifier (obbligatorio) nel costruttore per permettere a Spring di istanziare la sottoclasse specifica
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 */
@Slf4j
public abstract class AViewList extends Div {

    protected IAPresenter presenter;
    protected IAService service;
    protected Grid<AEntity> grid;
    protected IADialog dialog;
    protected IAMenu menu;

    //    protected List<RouterLink> arrayRouterLink;
    protected H2 title;


    public AViewList(IAPresenter presenter, IAMenu menu) {
        this.presenter = presenter;
        this.menu = menu;
        this.service = presenter.getService();
//        this.entityClazz = (Prova)presenter.getEntityClazz();
    }// end of constructor

    /**
     * Creazione di una view (AViewList) contenente una Grid
     * <p>
     * 1) Menu: Contenitore grafico per la barra di menu principale e per il menu/bottone del Login
     * 2) Top: Contenitore grafico per la caption
     * 3) Body: Corpo centrale della view. Utilizzando una Grid dentro un Panel, si ottine l'effetto scorrevole
     * 4) Footer - Barra inferiore (eventuale) per info, copyright, ecc.
     */
    protected void inizia2() {
        this.creaBody();
        updateView();
    }// end of method


    protected void inizia() {
//        Div navigation = null;
//        Div header = null;
//
//        arrayRouterLink = new ArrayList<>();
//        title = new H2();
//        title.addClassName("main-layout__title");
//
//        //--Crea i menu per la gestione delle @routes (standard e specifiche)
//        this.addAllRoutes();
//
//        if (arrayRouterLink.size() > 0) {
//            navigation = new Div();
//            for (RouterLink link : arrayRouterLink) {
//                link.addClassName("main-layout__nav-item");
//                navigation.add(link);
//            }// end of for cycle
//        }// end of if cycle
//
//        if (navigation != null) {
//            navigation.addClassName("main-layout__nav");
//
//            header = new Div(title, navigation);
//            header.addClassName("main-layout__header");
//            add(header);
//        }// end of if cycle
//
//        addClassName("main-layout");
        this.add(menu.getComponent());
        this.creaBody();
        updateView();
    }// end of Spring constructor


//    /**
//     * Aggiunge tutte le @routes (standard e specifiche)
//     */
//    protected void addAllRoutes() {
//        this.addRoutesStandard();
//        this.addRoutesSpecifiche();
//    }// end of method


//    /**
//     * Aggiunge le @routes (moduli/package) standard
//     * Alcuni moduli sono specifici di un collegamento come programmatore
//     * Alcuni moduli sono già definiti per tutte le applicazioni (LogMod, VersMod, PrefMod)
//     * Vengono usati come da relativo flag: AlgosApp.USE_LOG, AlgosApp.USE_VERS, AlgosApp.USE_PREF
//     */
//    protected void addRoutesStandard() {
//        addView(HomeView.class, HomeView.VIEW_ICON, HomeView.MENU_NAME);
//        addView(RoleList.class, RoleList.VIEW_ICON, RoleList.MENU_NAME);
//        addView(CompanyView.class, CompanyView.VIEW_ICON, CompanyView.MENU_NAME);
//    }// end of method


//    /**
//     * Creazione delle @routes (moduli/package) specifiche dell'applicazione.
//     * <p>
//     * Aggiunge al menu generale, le @routes (moduli/package) disponibili alla partenza dell'applicazione
//     * Ogni modulo può eventualmente modificare il proprio menu
//     * <p>
//     * Deve (DEVE) essere sovrascritto dalla sottoclasse
//     * Chiama il metodo  addView(...) della superclasse per ogni vista (modulo/package)
//     * La vista viene aggiunta alla barra di menu principale (di partenza)
//     */
//    protected void addRoutesSpecifiche() {
//    }// end of method


    /**
     * Crea il corpo centrale della view list
     * Componente grafico obbligatorio
     * Sovrascritto nella sottoclasse della view specifica
     */
    protected void creaBody() {
        List<String> gridPropertiesName = service.getGridPropertiesName();

        if (AEntity.class.isAssignableFrom(Prova.class)) {
            try { // prova ad eseguire il codice
                grid = new Grid(Prova.class);
            } catch (Exception unErrore) { // intercetta l'errore
                log.error(unErrore.toString());
                return;
            }// fine del blocco try-catch
        }// end of if cycle

        List colonne = grid.getColumns();
        for (Object colonna : colonne) {
            grid.removeColumn((Grid.Column) colonna);
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


//    /**
//     * Adds a view to the UI
//     *
//     * @param viewClazz the view class to instantiate
//     */
//    protected RouterLink addView(Class<? extends AView> viewClazz, VaadinIcons icon, String tagMenu) {
//        RouterLink routerLink = null;
//        try { // prova ad eseguire il codice
//            routerLink = new RouterLink("", viewClazz);
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            log.error(unErrore.toString());
//        }// fine del blocco try-catch
//
//        if (routerLink != null) {
//            routerLink.add(new Icon(icon), new Text(tagMenu));
//            routerLink.addClassName("main-layout__nav-item");
//            arrayRouterLink.add(routerLink);
//        }// end of if cycle
//
//        return routerLink;
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


    protected void updateView() {
//        List items = service.findFilter(searchField.getValue());
        List items = service.findAll();
        grid.setItems(items);
    }// end of method

}// end of class
