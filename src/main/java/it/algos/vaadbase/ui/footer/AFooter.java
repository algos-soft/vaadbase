package it.algos.vaadbase.ui.footer;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.application.BaseCost;
import it.algos.vaadbase.backend.login.ALogin;
import it.algos.vaadbase.service.AHtmlService;
import it.algos.vaadbase.service.ATextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: sab, 30-giu-2018
 * Time: 13:39
 * VerticalLayout has 100% width and undefined height by default.
 * Barra inferiore di messaggi all'utente.
 * Può essere visibile o nascosta a seconda del flag booleano KEY_DISPLAY_FOOTER_INFO
 * La visibilità viene gestita da AlgosUI
 * Tipicamente dovrebbe mostrare:
 * Copyright di Algos
 * Nome dell'applicazione
 * Versione dell'applicazione
 * Livello di accesso dell'utente loggato (developer, admin, utente) eventualmente oscurato per l'utente semplice
 * Company selezionata (nel caso di applicazione multiCompany)
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AFooter extends VerticalLayout {

    private final static String DEVELOPER_COMPANY = "Algos® ";
    private final static String DEV_TAG = "Dev: ";
    private final static String ADMIN_TAG = "Admin: ";
    private final static String USER_TAG = "User: ";
    public LocalDate data = LocalDate.now();
    public String project = "";
    public String version = "";
    public HorizontalLayout toolBar=new HorizontalLayout();
    public HorizontalLayout infoBar=new HorizontalLayout();

    /**
     * Inietta da Spring come 'session'
     */
    @Autowired
    public ALogin login;

    @Autowired
    public AHtmlService html;
    /**
     * Inietta da Spring come 'session'
     */
    @Autowired
    public ATextService text;

    private String message = "";
    private Label label;

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
    protected void inizia() {
        this.setMargin(false);
        this.setSpacing(true);

        if (BaseCost.DEBUG) {// @TODO costante provvisoria da sostituire con preferenzeService
        }// end of if cycle

        toolBar.setMargin(false);
        toolBar.setSpacing(true);
        this.add(toolBar);

        infoBar.setMargin(false);
        infoBar.setSpacing(true);
        this.add(infoBar);

        this.start();
    }// end of method

    public void setAppMessage(String message) {
        this.message = message;
        infoBar.removeAll();
        this.start();
    }// end of method


    public void start() {
        String message;
        String sep = " - ";
        String spazio = " ";
        String tag = "all companies";
        String companyName = login.getCompany() != null ? login.getCompany().getDescrizione() : "";
        String userName = "";

        //@todo RIMETTERE
//        companyCode = LibSession.getCompany() != null ? LibSession.getCompany().getCode() : "";
//        if (AlgosApp.USE_MULTI_COMPANY) {
//            if (LibText.isValid(companyCode)) {
//                message += " - " + companyCode;
//            } else {
//                message += " - " + tag;

//            }// end of if/else cycle
//        }// end of if cycle

        //@todo RIMETTERE
//        if (LibSession.isDeveloper()) {
//            message += " (dev)";
//        } else {
//            if (LibSession.isAdmin()) {
//                message += " (admin)";
//            } else {
//                message += " (buttonUser)";
//            }// end of if/else cycle
//        }// end of if/else cycle
//
//        label = new LabelRosso(DEVELOPER_NAME + message);

//        message = html.setVerdeBold(DEVELOPER_COMPANY + sep + PROJECT);
//        message += spazio;
//        message += html.setBold(VERSION);
//        message += " del ";
//        message += DATA.toString();
//        if (text.isValid(companyName)) {
//            message += sep;
//            message += html.setBluBold(companyName);
//        }// end of if cycle
//        if (text.isValid(userName)) {
//            switch (login.getTypeLogged()) {
//                case user:
//                    message += sep;
//                    message += html.setBluBold(USER_TAG + userName);
//                    break;
//                case admin:
//                    message += sep;
//                    message += html.setVerdeBold(ADMIN_TAG + userName);
//                    break;
//                case developer:
//                    message += sep;
//                    message += html.setRossoBold(DEV_TAG + userName);
//                    break;
//                default:
//                    break;
//            } // end of switch statement
//        }// end of if cycle

        message = DEVELOPER_COMPANY + sep + project + " " + version + " del " + data.toString();
        infoBar.add(new Label(message));
    }// end of method

}// end of class
