package it.algos.vaadtest.application;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.boot.AVersBoot;
import it.algos.vaadbase.modules.preferenza.PreferenzaService;
import it.algos.vaadbase.modules.versione.VersioneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Log delle versioni, modifiche e patch installate
 * Executed on container startup
 * Setup non-UI logic here
 * <p>
 * Classe eseguita solo quando l'applicazione viene caricata/parte nel server (Tomcat od altri) <br>
 * Eseguita quindi ad ogni avvio/riavvio del server e NON ad ogni sessione <br>
 * È OBBLIGATORIO aggiungere questa classe nei listeners del file web.WEB-INF.web.xml
 */
@SpringComponent
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Slf4j
@AIScript(sovrascrivibile = false)
public class VersBootStrap extends AVersBoot {

    private final static String CODE_PROJECT = "T";

    /**
     * La injection viene fatta da SpringBoot in automatico <br>
     */
    @Autowired
    private VersioneService vers;


    /**
     * La injection viene fatta da SpringBoot in automatico <br>
     */
    @Autowired
    private PreferenzaService pref;


    /**
     * Executed on container startup
     * Setup non-UI logic here
     * <p>
     * This method is called prior to the servlet context being initialized (when the Web application is deployed).
     * You can initialize servlet context related data here.
     * <p>
     * Tutte le aggiunte, modifiche e patch vengono inserite con una versione <br>
     * L'ordine di inserimento è FONDAMENTALE
     */
    @PostConstruct
    public int inizia() {
        int k = 0;
        super.inizia();
        codeProject = CODE_PROJECT;

        //--programma di test
        //--non fa nulla, solo informativo
        if (installa(++k)) {
            crea("Test", "Versione corrente");
        }// fine del blocco if

//        //--creata una nuova preferenza
//        if (installa(++k)) {
//            creaPrefBool("Pippoz", "Uso del log di registrazione per il livello debug. Di default true");
//        }// fine del blocco if

        return k;
    }// end of method


}// end of bootstrap class
