package it.algos.vaadbase.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcons;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PageConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import it.algos.vaadbase.modules.company.CompanyView;
import it.algos.vaadbase.modules.role.RoleView;
import it.algos.vaadtest.application.HomeView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: mar, 27-mar-2018
 * Time: 08:20
 * <p>
 * The main layout contains the header with the navigation buttons, and the child views below that.
 * <p>
 * Annotated with @Theme (obbligatorio)
 * Questa classe NON va annotata con @SpringComponent
 * Questa classe viene 'invocata' dalle views annotate con @Route(value = xxx, layout = MainLayout.class)
 * Anche le view NON vanno annotate con @SpringComponent (?)
 * Si possono usare delle views che non richiamano questo layout ed hanno un proprio layout java che non usa html (?)
 */
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Theme(Lumo.class)
@HtmlImport("frontend://styles/shared-styles.html")
//@HtmlImport("src/main-view.html")
@BodySize(height = "100vh", width = "100vw")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
public class MainLayout extends Div implements RouterLayout, AfterNavigationObserver, PageConfigurator {


    protected static final String ACTIVE_ITEM_STYLE = "main-layout__nav-item--selected";

    protected List<RouterLink> arrayRouterLink;
    protected H2 title;


    public MainLayout() {
        inizia();
    }// end of constructor


    protected void inizia() {
        Div navigation = null;
        Div header = null;

        arrayRouterLink = new ArrayList<>();
        title = new H2();
        title.addClassName("main-layout__title");

        //--Crea i menu per la gestione delle SpringView (standard e specifiche)
        this.addAllViste();

        if (arrayRouterLink.size() > 0) {
            navigation = new Div();
            for (RouterLink link : arrayRouterLink) {
                navigation.add(link);
            }// end of for cycle
        }// end of if cycle

        if (navigation != null) {
            navigation.addClassName("main-layout__nav");

            header = new Div(title, navigation);
            header.addClassName("main-layout__header");
            add(header);
        }// end of if cycle

        addClassName("main-layout");
    }// end of Spring constructor

    /**
     * Aggiunge tutte le viste (SpringView) standard e specifiche
     */
    protected void addAllViste() {
        this.addVisteStandard();
        this.addVisteSpecifiche();
    }// end of method


    /**
     * Aggiunge le viste (moduli/package) standard
     * Alcuni moduli sono specifici di un collegamento come programmatore
     * Alcuni moduli sono già definiti per tutte le applicazioni (LogMod, VersMod, PrefMod)
     * Vengono usati come da relativo flag: AlgosApp.USE_LOG, AlgosApp.USE_VERS, AlgosApp.USE_PREF
     */
    protected void addVisteStandard() {
        addView(HomeView.class, HomeView.VIEW_ICON, HomeView.MENU_NAME);
        addView(RoleView.class, RoleView.VIEW_ICON, RoleView.MENU_NAME);
        addView(CompanyView.class, CompanyView.VIEW_ICON, CompanyView.MENU_NAME);
    }// end of method


    /**
     * Creazione delle viste (moduli/package) specifiche dell'applicazione.
     * <p>
     * Aggiunge al menu generale, le viste (moduli/package) disponibili alla partenza dell'applicazione
     * Ogni modulo può eventualmente modificare il proprio menu
     * <p>
     * Deve (DEVE) essere sovrascritto dalla sottoclasse
     * Chiama il metodo  addView(...) della superclasse per ogni vista (modulo/package)
     * La vista viene aggiunta alla barra di menu principale (di partenza)
     */
    protected void addVisteSpecifiche() {
    }// end of method


    /**
     * Adds a view to the UI
     *
     * @param viewClazz the view class to instantiate
     */
    protected RouterLink addView(Class<? extends AView> viewClazz, VaadinIcons icon, String tagMenu) {
        RouterLink routerLink = null;
        try { // prova ad eseguire il codice
            routerLink = new RouterLink("", viewClazz);

        } catch (Exception unErrore) { // intercetta l'errore
            log.error(unErrore.toString());
        }// fine del blocco try-catch

        if (routerLink != null) {
            routerLink.add(new Icon(icon), new Text(tagMenu));
            routerLink.addClassName("main-layout__nav-item");
            arrayRouterLink.add(routerLink);
        }// end of if cycle

        return routerLink;
    }// end of method

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        String segment = afterNavigationEvent.getLocation().getFirstSegment();
        boolean active;

        for (RouterLink link : arrayRouterLink) {
            active = segment.equals(link.getHref());
            link.setClassName(ACTIVE_ITEM_STYLE, active);
        }// end of for cycle

    }// end of method

    @Override
    public void configurePage(InitialPageSettings initialPageSettings) {
        initialPageSettings.addMetaTag("apple-mobile-web-app-capable", "yes");
        initialPageSettings.addMetaTag("apple-mobile-web-app-status-bar-style", "black");
    }// end of method

}// end of class
