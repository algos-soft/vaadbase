package it.algos.vaadbase.ui.menu;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.util.ArrayList;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: sab, 24-mar-2018
 * Time: 16:05
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AMenu extends HorizontalLayout {


    public AMenu() {
        super();
    }// end of costructor


    /**
     * Initializes this layout.
     * This method is intended to build the view and configure non-component functionality.
     * Performing the initialization in a constructor is not suggested as the state of the UI
     * is not properly set up when the constructor is invoked.
     *
     * Se viene sovrascritto dalla sottoclasse, deve (DEVE) richiamare anche il metodo della superclasse
     * di norma DOPO aver effettuato alcune regolazioni <br>
     * Nella sottoclasse specifica viene eventualmente regolato il nome del modulo di partenza <br>
     */
    @PostConstruct
    protected void inizia() {
        this.setMargin(false);
        this.setSpacing(true);

        //--Crea i menu per la gestione delle Views (standard e specifiche)
        this.addAllViste();

        //--avvia la menubar, dopo aver aggiunto tutte le viste
        this.start();
    }// end of method


    /**
     * Aggiunge tutte le Views standard e specifiche
     */
    protected void addAllViste() {
//        //--l'eventuale menu Home è sempre il primo
//        if (usaItemMenuHome) {
//        menuLayout.addView( AHomeView.class);
//        }// end of if cycle

        this.addVisteStandard();
        this.addVisteSpecifiche();

//        //--l'eventuale menu Help è sempre l'ultimo
//        if (usaItemMenuHelp) {
//            menuLayout.addView(HelpNavView.class);
//        }// end of if cycle
    }// end of method


    /**
     * Aggiunge le viste (moduli) standard
     * Alcuni moduli sono specifici di un collegamento come programmatore
     * Alcuni moduli sono già definiti per tutte le applicazioni (LogMod, VersMod, PrefMod)
     * Vengono usati come da relativo flag: AlgosApp.USE_LOG, AlgosApp.USE_VERS, AlgosApp.USE_PREF
     */
    protected void addVisteStandard() {
//        if (AlgosApp.USE_SECURITY) {
//            menuLayout.addView(Role.class, RoleList.class);
//        }// end of if cycle
//
////        if (LibParams.useVers()) {
////            menuLayout.addView(Versione.class, VersioneNavView.class);
////        }// end of if cycle
////        menuLayout.addView(Preferenza.class, PreferenzaNavView.class);
//
//        menuLayout.addView(Stato.class, StatoList.class);
////        menuLayout.addView(Indirizzo.class, IndirizzoNavView.class);
////        menuLayout.addView(Persona.class, PersonaNavView.class);
//
//        if (AlgosApp.USE_LOG) {
//            menuLayout.addView(Log.class, LogList.class);
//            menuLayout.addView(Logtype.class, LogtypeList.class);
//        }// end of if cycle
//
//        if (AlgosApp.USE_MULTI_COMPANY) {
//            menuLayout.addView(Company.class, CompanyList.class);
//        }// end of if cycle
//        menuLayout.addView(Persona.class, PersonaList.class);
//        menuLayout.addView(Address.class, AddressList.class);
    }// end of method


    /**
     * Creazione delle viste (moduli) specifiche dell'applicazione.
     * La superclasse AlgosUIParams crea (flag true/false) le viste (moduli) usate da tutte le applicazioni
     * I flag si regolano in @PostConstruct:init() //@todo manca
     * <p>
     * Aggiunge al menu generale, le viste (moduli) disponibili alla partenza dell'applicazione
     * Ogni modulo può eventualmente modificare il proprio menu
     * <p>
     * Deve (DEVE) essere sovrascritto dalla sottoclasse
     * Chiama il metodo  addView(...) della superclasse per ogni vista (modulo)
     * La vista viene aggiunta alla barra di menu principale (di partenza)
     */
    protected void addVisteSpecifiche() {
    }// end of method


    /**
     * Lancio della vista iniziale
     * Chiamato DOPO aver finito di costruire il MenuLayout e la AlgosUI
     * Deve (DEVE) essere sovrascritto dalla sottoclasse
     */
    protected void startVistaIniziale() {
    }// end of method


    /**
     * Avvia la menubar, dopo aver aggiunto tutte le viste
     */
    public void start() {
        this.removeAll();

        Button  roleButton = new Button("Role", event -> {
            UI.getCurrent().navigate("role");
        });//end of lambda expressions
        this.add(roleButton);

        Button  wizardButton = new Button("Wizard", event -> {
            UI.getCurrent().navigate("wizard");
        });//end of lambda expressions
        this.add(wizardButton);

        Button  addressButton = new Button("Address", event -> {
            UI.getCurrent().navigate("address");
        });//end of lambda expressions
        this.add(addressButton);

        Button  companyButton = new Button("Company", event -> {
            UI.getCurrent().navigate("company");
        });//end of lambda expressions
        this.add(companyButton);

//        if (AlgosApp.USE_SECURITY) {
//            if (firstMenuBar.getItems().size() > 0) {
//                this.addComponent(new HorizontalLayout(firstMenuBar, loginButton));
//            }// end of if/else cycle
//
//            if (login.isAdmin() && secondMenuBar.getItems().size() > 0) {
//                this.addComponent(secondMenuBar);
//            }// end of if cycle
//
//            if (login.isDeveloper() && thirdMenuBar.getItems().size() > 0) {
//                this.addComponent(thirdMenuBar);
//            }// end of if cycle
//        } else {
//            if (firstMenuBar.getItems().size() > 0) {
//                this.addComponent(firstMenuBar);
//            }// end of if/else cycle

//            if (secondMenuBar.getItems().size() > 0) {
//                this.addComponent(secondMenuBar);
//            }// end of if cycle
//
//            if (thirdMenuBar.getItems().size() > 0) {
//                this.addComponent(thirdMenuBar);
//            }// end of if cycle
//        }// end of if/else cycle

    }// end of method


}// end of class
