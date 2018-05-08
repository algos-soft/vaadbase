package it.algos.vaadbase.boot;

import it.algos.vaadbase.application.BaseCost;
import it.algos.vaadbase.modules.company.CompanyViewList;
import it.algos.vaadbase.modules.role.RoleViewList;
import lombok.extern.slf4j.Slf4j;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: dom, 06-mag-2018
 * Time: 18:43
 * Running logic after the Spring context has been initialized
 * Any class that use this @EventListener annotation,
 * will be executed before the application is up and its onApplicationEvent method will be called
 */
@Slf4j
public abstract class ABoot {


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
     * Le @Route vengono aggiunte ad una Lista statica mantenuta in BaseCost
     * Verranno lette da MainLayout la prima volta che il browser 'chiama' una view
     */
    protected void addRouteStandard() {
        BaseCost.MENU_CLAZZ_LIST.add(RoleViewList.class);
        BaseCost.MENU_CLAZZ_LIST.add(CompanyViewList.class);
    }// end of method

}// end of class
