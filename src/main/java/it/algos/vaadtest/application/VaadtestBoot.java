package it.algos.vaadtest.application;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.application.BaseCost;
import it.algos.vaadbase.boot.ABoot;
import it.algos.vaadbase.developer.DeveloperView;
import it.algos.vaadbase.modules.address.AddressViewList;
import it.algos.vaadbase.modules.company.CompanyViewList;
import it.algos.vaadbase.modules.persona.PersonaViewList;
import it.algos.vaadbase.modules.preferenza.PreferenzaViewList;
import it.algos.vaadbase.modules.role.RoleViewList;
import it.algos.vaadbase.modules.utente.UtenteViewList;
import it.algos.vaadbase.modules.versione.VersioneViewList;
import it.algos.vaadbase.wizard.ui.WizardView;
import it.algos.vaadtest.modules.bolla.BollaViewList;
import it.algos.vaadtest.modules.prova.ProvaViewList;
import it.algos.vaadtest.modules.prova2.Prova2ViewList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: dom, 06-mag-2018
 * Time: 18:45
 * <p>
 * Estende la classe ABoot per le regolazioni iniziali di questa applicazione <br>
 * Running logic after the Spring context has been initialized
 * The method onApplicationEvent() will be executed before the application is up and <br>
 * Aggiunge tutte le @Route (views) standard e specifiche di questa applicazione <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope (obbligatorio) <br>
 * Annotated with @AIScript (facoltativo) per controllare la ri-creazione di questo file nello script di algos <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@AIScript(sovrascrivibile = false)
public class VaadtestBoot extends ABoot {

    @Autowired
    protected CompanyData company;


    /**
     * Running logic after the Spring context has been initialized
     * Any class that use this @EventListener annotation,
     * will be executed before the application is up and its onApplicationEvent method will be called
     * <p>
     * Viene normalmente creata una sottoclasse per l'applicazione specifica:
     * - per regolare eventualmente alcuni flag in maniera non standard
     * - per lanciare eventualmente gli schedulers in background
     * - per costruire e regolare eventualmente una versione demo
     * - per controllare eventualmente l'esistenza di utenti abilitati all'accesso
     * <p>
     * Stampa a video (productionMode) i valori per controllo
     */
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        super.onApplicationEvent(event);
    }// end of method


    /**
     * Inizializzazione dei dati standard di alcune collections sul DB
     */
    @Override
    protected void iniziaData() {
        super.iniziaDataStandard();
        this.company.findOrCrea();
//        this.utente.findOrCrea();
        utenteService.crea(companyService.getAlgos(), "gac", "fulvia");

    }// end of method


    /**
     * Regola alcune informazioni dell'applicazione
     */
    @Override
    protected void regolaInfo() {
        super.regolaInfo();
        footer.project = "Vaadbase";
        footer.version = "1.0";
        footer.data = LocalDate.of(2018, 7, 25);
    }// end of method


    /**
     * Regola alcune preferenze iniziali
     * Se non esistono, le crea
     * Se esistono, sostituisce i valori esistenti con quelli indicati qui
     */
    @Override
    protected void regolaPreferenze() {
        super.regolaPreferenze();
    }// end of method


    /**
     * Aggiunge le @Route (view) specifiche di questa applicazione
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in BaseCost
     * Vengono aggiunte dopo quelle standard
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view
     */
    @Override
    protected void addRouteSpecifiche() {
        BaseCost.MENU_CLAZZ_LIST.add(CompanyViewList.class);
        BaseCost.MENU_CLAZZ_LIST.add(PreferenzaViewList.class);
        BaseCost.MENU_CLAZZ_LIST.add(RoleViewList.class);
        BaseCost.MENU_CLAZZ_LIST.add(AddressViewList.class);
        BaseCost.MENU_CLAZZ_LIST.add(PersonaViewList.class);
        BaseCost.MENU_CLAZZ_LIST.add(DeveloperView.class);
        BaseCost.MENU_CLAZZ_LIST.add(WizardView.class);
        BaseCost.MENU_CLAZZ_LIST.add(VersioneViewList.class);
        BaseCost.MENU_CLAZZ_LIST.add(UtenteViewList.class);

        BaseCost.MENU_CLAZZ_LIST.add(Prova2ViewList.class);
        BaseCost.MENU_CLAZZ_LIST.add(ProvaViewList.class);
        BaseCost.MENU_CLAZZ_LIST.add(HomeView.class);
        BaseCost.MENU_CLAZZ_LIST.add(BollaViewList.class);
    }// end of method


}// end of class