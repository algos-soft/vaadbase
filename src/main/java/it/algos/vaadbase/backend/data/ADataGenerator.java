package it.algos.vaadbase.backend.data;

import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;

import static it.algos.vaadbase.application.BaseCost.TAG_COM;
import static it.algos.vaadbase.application.BaseCost.TAG_ROL;

/**
 * Project vbase
 * Created by Algos
 * User: gac
 * Date: lun, 19-mar-2018
 * Time: 19:02
 */
@Slf4j
//@SpringComponent
public class ADataGenerator {


    /**
     * La classe generator specifica viene iniettata dal costruttore, in modo che sia disponibile nella superclasse
     * Spring costruisce al volo, quando serve, una implementazione di IAData (come previsto dal @Qualifier)
     */
    private IAData roleData;


    /**
     * La classe generator specifica viene iniettata dal costruttore, in modo che sia disponibile nella superclasse
     * Spring costruisce al volo, quando serve, una implementazione di IAData (come previsto dal @Qualifier)
     */
    private IAData companyData;


    /**
     * Costruttore @Autowired
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation
     * Se ci sono DUE o più costruttori, va in errore
     * Se ci sono DUE costruttori, di cui uno senza parametri, inietta quello senza parametri
     *
     * @param roleData    iniettato da Spring
     * @param companyData iniettato da Spring
     */
    public ADataGenerator(@Qualifier(TAG_ROL) IAData roleData, @Qualifier(TAG_COM) IAData companyData) {
        this.roleData = roleData;
        this.companyData = companyData;
    }// end of Spring constructor


    /**
     * Metodo invocato subito DOPO il costruttore
     * <p>
     * Performing the initialization in a constructor is not suggested
     * as the state of the UI is not properly set up when the constructor is invoked.
     * <p>
     * Ci possono essere diversi metodi con @PostConstruct e firme diverse e funzionano tutti,
     * ma l'ordine con cui vengono chiamati NON è garantito
     */
    @PostConstruct
    public void loadData() {
        roleData.loadData();
        companyData.loadData();
    }// end of method


}// end of class
