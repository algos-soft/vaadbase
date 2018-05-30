package it.algos.vaadbase.modules.preferenza;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.backend.data.AData;
import it.algos.vaadbase.backend.service.IAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadbase.application.BaseCost.TAG_PRE;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: mer, 30-mag-2018
 * Time: 07:20
 * Estende la classe astratta AData per la costruzione inziale della Collection <br>
 * I valori iniziali sono presi da una Enumeration codificata e standard <br>
 * Vengono caricati sul DB (mongo) in modo che se ne possano aggiungere altri specifici per l'applicazione<br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio per le injections) <br>
 * Annotated with @Scope (obbligatorio = 'singleton') <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica <br>
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_PRE)
@Slf4j
public class PreferenzaData extends AData {

    /**
     * Il service viene iniettato dal costruttore, in modo che sia disponibile nella superclasse,
     * dove viene usata l'interfaccia IAService
     * Spring costruisce al volo, quando serve, una implementazione di IAService (come previsto dal @Qualifier)
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici
     */
    private PreferenzaService service;


    /**
     * Costruttore @Autowired
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation
     * Si usa un @Qualifier(), per avere la sottoclasse specifica
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti
     *
     * @param service iniettato da Spring come sottoclasse concreta specificata dal @Qualifier
     */
    @Autowired
    public PreferenzaData(@Qualifier(TAG_PRE) IAService service) {
        super(service);
        this.service = (PreferenzaService) service;
    }// end of Spring constructor


    /**
     * Creazione di una collezione
     * Solo se non ci sono records
     * Controlla se la collezione esiste già
     */
    public void findOrCrea() {
        int numRec = 0;

        if (nessunRecordEsistente()) {
            this.crea();
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
    public void crea() {
        service.deleteAll();

        for (EAPreferenza p : EAPreferenza.values()) {
            service.crea(p.getCode(), p.getDescrizione(), p.getType(), p.getValue());
        }// end of for cycle
    }// end of method

}// end of class
