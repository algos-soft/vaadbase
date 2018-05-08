package it.algos.vaadbase.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcons;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.ui.menu.IAMenu;
import lombok.extern.slf4j.Slf4j;


/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: sab, 05-mag-2018
 * Time: 21:36
 */
@Slf4j
@SpringComponent
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AUI extends UI {

    /**
     * Contenitore grafico per la barra di menu principale e per il menu/bottone del Login
     * A seconda del layout può essere posizionato in alto, oppure a sinistra
     */
    protected IAMenu menuLayout;


    public AUI() {
        inizia();
    }// end of constructor


    protected void inizia() {
        //--Crea i menu per la gestione delle @routes (standard e specifiche)
        this.addAllRoutes();

        //--avvia la menubar, dopo aver aggiunto tutte le viste
//        menuLayout.start();
    }// end of method


    /**
     * Aggiunge tutte le @routes (standard e specifiche)
     */
    protected void addAllRoutes() {
        this.addRoutesStandard();
        this.addRoutesSpecifiche();
    }// end of method


    /**
     * Aggiunge le @routes (moduli/package) standard
     * Alcuni moduli sono specifici di un collegamento come programmatore
     * Alcuni moduli sono già definiti per tutte le applicazioni (LogMod, VersMod, PrefMod)
     * Vengono usati come da relativo flag: AlgosApp.USE_LOG, AlgosApp.USE_VERS, AlgosApp.USE_PREF
     */
    protected void addRoutesStandard() {
//        menuLayout.addView(HomeView.class, HomeView.VIEW_ICON, HomeView.MENU_NAME);
//        menuLayout.addView(RoleList.class, RoleList.VIEW_ICON, RoleList.MENU_NAME);
//        menuLayout.addView(CompanyView.class, CompanyView.VIEW_ICON, CompanyView.MENU_NAME);
    }// end of method


    /**
     * Adds a view to the UI
     *
     * @param viewClazz the view class to instantiate
     */
    public RouterLink addView(Class<? extends AView> viewClazz, VaadinIcons icon, String tagMenu) {
        RouterLink routerLink = null;

        try { // prova ad eseguire il codice
            routerLink = new RouterLink("", viewClazz);

        } catch (Exception unErrore) { // intercetta l'errore
            log.error(unErrore.toString());
        }// fine del blocco try-catch

        if (routerLink != null) {
            routerLink.add(new Icon(icon), new Text(tagMenu));
            routerLink.addClassName("main-layout__nav-item");
        }// end of if cycle

        return routerLink;
    }// end of method

    /**
     * Creazione delle @routes (moduli/package) specifiche dell'applicazione.
     * <p>
     * Aggiunge al menu generale, le @routes (moduli/package) disponibili alla partenza dell'applicazione
     * Ogni modulo può eventualmente modificare il proprio menu
     * <p>
     * Deve (DEVE) essere sovrascritto dalla sottoclasse
     * Chiama il metodo  addView(...) della superclasse per ogni vista (modulo/package)
     * La vista viene aggiunta alla barra di menu principale (di partenza)
     */
    protected void addRoutesSpecifiche() {
    }// end of method

}// end of class
