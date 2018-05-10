package it.algos.vaadbase.boot;

import it.algos.vaadbase.application.BaseCost;
import it.algos.vaadbase.modules.address.AddressViewList;
import it.algos.vaadbase.modules.company.CompanyViewList;
import it.algos.vaadbase.modules.role.RoleData;
import it.algos.vaadbase.modules.role.RoleViewList;
import org.springframework.beans.factory.annotation.Autowired;

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
public abstract class ABoot {

//    /**
//     * Inietta da Spring come 'singleton'
//     */
//    @Autowired
//    public RoleData role;

//    /**
//     * Inietta da Spring come 'singleton'
//     */
//    @Autowired
//    public StatoData stato;

    /**
     * Inizializzazione dei dati standard di alcune collections sul DB Mongo
     */
    protected void iniziaDataStandard() {
//        this.role.findOrCrea();
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
    }// end of method

}// end of class
