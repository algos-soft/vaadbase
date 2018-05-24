package it.algos.vaadbase.boot;

import it.algos.vaadbase.application.BaseCost;
import it.algos.vaadbase.modules.address.AddressViewList;
import it.algos.vaadbase.modules.company.CompanyViewList;
import it.algos.vaadbase.modules.persona.PersonaViewList;
import it.algos.vaadbase.modules.role.RoleData;
import it.algos.vaadbase.modules.role.RoleViewList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: dom, 06-mag-2018
 * Time: 18:43
 * <p>
 * Running logic after the Spring context has been initialized
 * The method onApplicationEvent() will be executed nella sottoclasse before the application is up and <br>
 * <p>
 * Aggiunge tutte le @Route (views) standard e specifiche di questa applicazione <br>
 * <p>
 * Not annotated with @SpringComponent (SpringBoot crea la sottoclasse concreta) <br>
 * Not annotated with @Scope (inutile) <br>
 */
public abstract class ABoot implements ServletContextListener {

    /**
     * Inietta da Spring come 'singleton'
     */
    @Autowired
    public RoleData role;

//    /**
//     * Inietta da Spring come 'singleton'
//     */
//    @Autowired
//    public StatoData stato;


    /**
     * Executed on container startup
     * Setup non-UI logic here
     * Utilizzato per:
     * - registrare nella xxxApp, il servlet context non appena è disponibile
     * - regolare alcuni flag dell'applicazione, uguali e validi per tutte le sessioni e tutte le request <br>
     * - lanciare gli schedulers in background <br>
     * - costruire e regolare una versione demo <br>
     * - controllare l'esistenza di utenti abilitati all'accesso <br>
     * <p>
     * Puo essere (eventualmente) sovrascritto dalla sottoclasse:
     * Deve (DEVE) richiamare anche il metodo della superclasse (questo)
     * prima (PRIMA) delle regolazioni specifiche della sottoclasse <br>
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }// end of method


    /**
     * Running logic after the Spring context has been initialized
     * Any class that use this @EventListener annotation,
     * will be executed before the application is up and its onApplicationEvent method will be called
     * <p>
     * Viene normalmente creata una sottoclasse per l'applicazione specifica:
     * - per regolare eventualmente alcuni flag in maniera non standard
     * - lanciare gli schedulers in background <br>
     * - costruire e regolare una versione demo <br>
     * - controllare l'esistenza di utenti abilitati all'accesso <br>
     * <p>
     * Stampa a video (productionMode) i valori per controllo
     */
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
    }// end of method


    /**
     * Executed on container shutdown
     * <p>
     * Clean stuff here <br>
     * Può essere sovrascritta dalla sottoclasse <br>
     * Deve (DEVE) richiamare anche il metodo della superclasse (questo) <br>
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }// end of method

    /**
     * Inizializzazione dei dati standard di alcune collections sul DB Mongo
     */
    protected void iniziaDataStandard() {
        this.role.findOrCrea();
//        this.logtype.findOrCrea();
//        this.stato.findOrCrea();
    }// end of method


    /**
     * Aggiunge le @Route (view) standard
     * Nella sottoclasse concreta che invoca questo metodo, aggiunge le @Route (view) specifiche dell'applicazione
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in BaseCost
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view
     */
    protected void addRouteStandard() {
        BaseCost.MENU_CLAZZ_LIST.add(RoleViewList.class);
        BaseCost.MENU_CLAZZ_LIST.add(CompanyViewList.class);
        BaseCost.MENU_CLAZZ_LIST.add(AddressViewList.class); //@todo da levare per il deploy finale
        BaseCost.MENU_CLAZZ_LIST.add(PersonaViewList.class); //@todo da levare per il deploy finale
    }// end of method

}// end of class
