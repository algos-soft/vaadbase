package it.algos.vaadtest.application;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.application.BaseCost;
import it.algos.vaadbase.boot.ABoot;
import it.algos.vaadbase.wizard.ui.WizardView;
import it.algos.vaadtest.modules.bolla.BollaViewList;
import it.algos.vaadtest.modules.prova.ProvaViewList;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

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
        this.iniziaData();
        this.addAllRouteView();
    }// end of method

    /**
     * Inizializzazione dei dati standard di alcune collections sul DB
     */
    protected void iniziaData() {
        super.iniziaDataStandard();
//        this.company2.findOrCrea();
//        this.utente.findOrCrea();
    }// end of method


    /**
     * Aggiunge tutte le @Route (views) standard e specifiche
     */
    protected void addAllRouteView() {
        super.addRouteStandard();
        this.addRouteSpecifiche();
    }// end of method


    /**
     * Aggiunge le @Route (view) specifiche di questa applicazione
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in BaseCost
     * Vengono aggiunte dopo quelle standard
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view
     */
    private void addRouteSpecifiche() {
        BaseCost.MENU_CLAZZ_LIST.add(BollaViewList.class);
        BaseCost.MENU_CLAZZ_LIST.add(HomeView.class);
        BaseCost.MENU_CLAZZ_LIST.add(ProvaViewList.class);
        BaseCost.MENU_CLAZZ_LIST.add(WizardView.class);
    }// end of method


}// end of class