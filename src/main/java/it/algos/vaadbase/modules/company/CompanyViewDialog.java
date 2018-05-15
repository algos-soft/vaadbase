package it.algos.vaadbase.modules.company;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.application.StaticContextAccessor;
import it.algos.vaadbase.modules.address.Address;
import it.algos.vaadbase.modules.address.AddressPresenter;
import it.algos.vaadbase.modules.address.AddressService;
import it.algos.vaadbase.modules.address.AddressViewDialog;
import it.algos.vaadbase.modules.persona.Persona;
import it.algos.vaadbase.modules.persona.PersonaPresenter;
import it.algos.vaadbase.modules.persona.PersonaService;
import it.algos.vaadbase.modules.persona.PersonaViewDialog;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.ui.dialog.AViewDialog;
import it.algos.vaadbase.ui.fields.ATextArea;
import it.algos.vaadbase.ui.fields.ATextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static it.algos.vaadbase.application.BaseCost.TAG_COM;

/**
 * Project vaadbase <br>
 * Created by Algos
 * User: Gac
 * Date: 9-mag-2018 19.52.23
 * <p>
 * Estende la classe astratta ADialog per visualizzare i fields <br>
 * <p>
 * Not annotated with @SpringComponent (sbagliato)
 * Annotated with @Scope (obbligatorio = 'prototype')
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Qualifier(TAG_COM)
@AIScript(sovrascrivibile = true)
public class CompanyViewDialog extends AViewDialog<Company> {

    private final static String PERSONA = "contatto";
    private final static String INDIRIZZO = "indirizzo";
    private final static String POST_PERSONA = "telefono";
    private final static String POST_INDIRIZZO = "note";

    private PersonaPresenter personaPresenter;
    private PersonaService personaService;
    private PersonaViewDialog personaDialog;
    private Persona personaTemporanea;
    private ATextField personaField;

    private AddressPresenter addressPresenter;
    private AddressService addressService;
    private AddressViewDialog addressDialog;
    private Address indirizzoTemporaneo;
    private ATextField indirizzoField;


    /**
     * Costruttore
     *
     * @param presenter   per gestire la business logic del package
     * @param itemSaver   funzione associata al bottone 'registra'
     * @param itemDeleter funzione associata al bottone 'annulla'
     */
    @Autowired
    public CompanyViewDialog(IAPresenter presenter, BiConsumer<Company, AViewDialog.Operation> itemSaver, Consumer<Company> itemDeleter) {
        super(presenter, itemSaver, itemDeleter);
    }// end of constructor


    /**
     * Aggiunge al binder eventuali fields specifici, prima di trascrivere la entityBean nel binder
     * Sovrascritto
     * Dopo aver creato un AField specifico, usare il metodo super.addFieldBinder() per:
     * Inizializzare AField
     */
    @Override
    protected void addSpecificAlgosFields() {
        personaPresenter = StaticContextAccessor.getBean(PersonaPresenter.class);
        personaDialog = new PersonaViewDialog(personaPresenter, this::saveUpdatePer, this::deleteUpdatePer, this::annullaPer);
        personaService = (PersonaService) personaPresenter.getService();

        personaField = (ATextField) getField(PERSONA);
        if (personaField != null) {
            personaField.addFocusListener(e -> personaDialog.open(getPersona(), Operation.EDIT));
        }// end of if cycle

        addressPresenter = StaticContextAccessor.getBean(AddressPresenter.class);
        addressDialog = new AddressViewDialog(addressPresenter, this::saveUpdateInd, this::deleteUpdateInd, this::annullaInd);
        addressService = (AddressService) addressPresenter.getService();

        indirizzoField = (ATextField) getField(INDIRIZZO);
        if (indirizzoField != null) {
            indirizzoField.addFocusListener(e -> addressDialog.open(getIndirizzo(), Operation.EDIT));
        }// end of if cycle
    }// end of method

    /**
     * Regola in lettura eventuali valori NON associati al binder
     * Dal DB alla UI
     * Sovrascritto
     */
    protected void readSpecificFields() {
        personaField.setValue(getPersonaCorrenteValue());
        indirizzoField.setValue(getIndirizzoCorrenteValue());
    }// end of method


    /**
     * Regola in scrittura eventuali valori NON associati al binder
     * Dallla  UI al DB
     * Sovrascritto
     */
    protected void writeSpecificFields() {
        Company company = super.getCurrentItem();
        company.setContatto(personaTemporanea);
        company.setIndirizzo(indirizzoTemporaneo);
        service.save(company);
    }// end of method


    protected void saveUpdatePer(Persona entityBean, AViewDialog.Operation operation) {
        personaTemporanea = entityBean;
        personaField.setValue(entityBean.toString());
        ((ATextField) getField(POST_PERSONA)).focus();
        Notification.show("La modifica di persona è stata confermata ma devi registrare questa company per renderla definitiva", 3000, Notification.Position.BOTTOM_START);
    }// end of method


    protected void deleteUpdatePer(Persona entityBean) {
        personaTemporanea = null;
        personaField.setValue("");
        ((ATextField) getField(POST_PERSONA)).focus();
        Notification.show("La cancellazione di persona è stata confermata ma devi registrare questa company per renderla definitiva", 3000, Notification.Position.BOTTOM_START);
    }// end of method


    protected void annullaPer(Persona entityBean) {
        ((ATextField) getField(POST_PERSONA)).focus();
    }// end of method


    protected void saveUpdateInd(Address entityBean, AViewDialog.Operation operation) {
        indirizzoTemporaneo = entityBean;
        indirizzoField.setValue(entityBean.toString());
        ((ATextArea) getField(POST_INDIRIZZO)).focus();
        Notification.show("La modifica di indirizzo è stata confermata ma devi registrare questa company per renderla definitiva", 3000, Notification.Position.BOTTOM_START);
    }// end of method


    protected void deleteUpdateInd(Address entityBean) {
        indirizzoTemporaneo = null;
        indirizzoField.setValue("");
        ((ATextArea) getField(POST_INDIRIZZO)).focus();
        Notification.show("La cancellazione di indirizzo è stata confermata ma devi registrare questa company per renderla definitiva", 3000, Notification.Position.BOTTOM_START);
    }// end of method


    protected void annullaInd(Address entityBean) {
        ((ATextArea) getField(POST_INDIRIZZO)).focus();
    }// end of method


    private Address getIndirizzoCorrente() {
        Address indirizzo = null;
        Company company = getCurrentItem();

        if (company != null) {
            indirizzo = company.getIndirizzo();
        }// end of if cycle

        return indirizzo;
    }// end of method


    private String getIndirizzoCorrenteValue() {
        String value = "";
        Address indirizzo = getIndirizzoCorrente();

        if (indirizzo != null) {
            value = indirizzo.toString();
        }// end of if cycle

        return value;
    }// end of method


    private Address getIndirizzo() {
        Address indirizzo = getIndirizzoCorrente();

        if (indirizzo == null) {
            indirizzo = addressService.newEntity();
        }// end of if cycle

        return indirizzo;
    }// end of method


    private Persona getPersonaCorrente() {
        Persona persona = null;
        Company company = getCurrentItem();

        if (company != null) {
            persona = company.getContatto();
        }// end of if cycle

        return persona;
    }// end of method


    private String getPersonaCorrenteValue() {
        String value = "";
        Persona persona = getPersonaCorrente();

        if (persona != null) {
            value = persona.toString();
        }// end of if cycle

        return value;
    }// end of method


    private Persona getPersona() {
        Persona persona = getPersonaCorrente();

        if (persona == null) {
            persona = personaService.newEntity();
        }// end of if cycle

        return persona;
    }// end of method

}// end of class