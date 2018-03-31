package it.algos.vaadbase.modules.company;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.application.BaseCost;
import it.algos.vaadbase.backend.data.AData;
import it.algos.vaadbase.backend.service.IAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.time.LocalDateTime;

/**
 * Project vbase
 * Created by Algos
 * User: gac
 * Date: lun, 19-mar-2018
 * Time: 19:58
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(BaseCost.TAG_COM)
public class CompanyData extends AData {

    /**
     * Il service viene iniettato dal costruttore, in modo che sia disponibile nella superclasse,
     * dove viene usata l'interfaccia IAService
     * Spring costruisce al volo, quando serve, una implementazione di IAService (come previsto dal @Qualifier)
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici
     */
    private IAService service;


    /**
     * Costruttore @Autowired
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation
     * Se ci sono DUE o più costruttori, va in errore
     * Se ci sono DUE costruttori, di cui uno senza parametri, inietta quello senza parametri
     * Si usa un @Qualifier(), per avere la sottoclasse specifica
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti
     *
     * @param service iniettato da Spring come sottoclasse concreta specificata dal @Qualifier
     */
    public CompanyData(CompanyService service) {
        super(service);
        this.service = (IAService) service;
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
            log.warn("Algos - Creazione dati iniziali ADataGenerator(@PostConstruct).loadData() -> companyData.loadData(): " + numRec + " schede");
        } else {
            numRec = service.count();
            log.info("Algos - Data. La collezion Company è presente: " + numRec + " schede");
        }// end of if/else cycle
    }// end of method


    /**
     * Creazione della collezione
     */
    private void creaData() {
        this.creaCompany("crf","Compagnia Teatrale");
        this.creaCompany("alfa","Alfetta Sud");
        this.creaCompany("SIP","Società Italiana Piemonte");
    }// end of method


    /**
     * Creazione della singola company
     */
    private void creaCompany(String code, String descrizione) {
        Company company = new Company();
        company.setCode(code);
        company.setDescrizione(descrizione);
        company.note = null;
        service.save(company);
    }// end of method


}// end of class
