package it.algos.vaadbase.modules.role;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.application.BaseCost;
import it.algos.vaadbase.backend.data.AData;
import it.algos.vaadbase.backend.service.IAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: dom, 12-nov-2017
 * Time: 14:54
 */
@Slf4j
@SpringComponent
@Scope("singleton")
@Qualifier(BaseCost.TAG_ROL)
public class RoleData extends AData {


    /**
     * Il service iniettato dal costruttore, in modo che sia disponibile nella superclasse,
     * dove viene usata l'interfaccia IAService
     * Spring costruisce al volo, quando serve, una implementazione di IAService (come previsto dal @Qualifier)
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici
     */
    private RoleService service;


    /**
     * Costruttore @Autowired
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation
     * Si usa un @Qualifier(), per avere la sottoclasse specifica
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti
     *
     * @param service iniettato da Spring come sottoclasse concreta specificata dal @Qualifier
     */
    public RoleData(@Qualifier(BaseCost.TAG_ROL) IAService service) {
        super(service);
        this.service = (RoleService) service;
    }// end of Spring constructor


    /**
     * Creazione di una collezione
     * Solo se non ci sono records
     * Controlla se la collezione esiste già
     * Creazione della collezione
     */
    public void loadData() {
        int numRec = 0;

        if (nessunRecordEsistente()) {
            this.creaData();
            numRec = service.count();
            log.warn("Algos - Creazione dati iniziali ADataGenerator(@PostConstruct).loadData() -> roleData.loadData(): " + numRec + " schede");
        } else {
            numRec = service.count();
            log.info("Algos - Data. La collezione Role è presente: " + numRec + " schede");
        }// end of if/else cycle
    }// end of method


    /**
     * Creazione della collezione
     */
    private void creaData() {
        for (EARole ruolo : EARole.values()) {
//            service.findOrCrea(ruolo.toString());
        }// end of for cycle
    }// end of method


}// end of class
