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

    public static String CONTATTO = "contatto";
    public static String INDIRIZZO = "indirizzo";
    public static String POST_CONTATTO = "telefono";
    public static String POST_INDIRIZZO = "note";

    protected PersonaPresenter personaPresenter;
    protected PersonaService personaService;
    protected PersonaViewDialog contattoDialog;
    protected Persona contattoTemporaneo;
    protected ATextField contattoField;

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
        personaService = (PersonaService) personaPresenter.getService();
        contattoDialog = new PersonaViewDialog(personaPresenter, this::saveUpdateCon, this::deleteUpdateCon, this::annullaCon);

        contattoField = (ATextField) getField(CONTATTO);
        if (contattoField != null) {
            contattoField.addFocusListener(e -> contattoDialog.open(getPersona(), Operation.EDIT, CONTATTO));
        }// end of if cycle

        addressPresenter = StaticContextAccessor.getBean(AddressPresenter.class);
        addressService = (AddressService) addressPresenter.getService();
        addressDialog = new AddressViewDialog(addressPresenter, this::saveUpdateInd, this::deleteUpdateInd, this::annullaInd);

        indirizzoField = (ATextField) getField(INDIRIZZO);
        if (indirizzoField != null) {
            indirizzoField.addFocusListener(e -> addressDialog.open(getIndirizzo(), Operation.EDIT, INDIRIZZO));
        }// end of if cycle
    }// end of method

    /**
     * Regola in lettura eventuali valori NON associati al binder
     * Dal DB alla UI
     * Sovrascritto
     */
    protected void readSpecificFields() {
        contattoField.setValue(getPersonaCorrenteValue());
        indirizzoField.setValue(getIndirizzoCorrenteValue());
    }// end of method


    /**
     * Regola in scrittura eventuali valori NON associati al binder
     * Dallla  UI al DB
     * Sovrascritto
     */
    protected void writeSpecificFields() {
        Company company = super.getCurrentItem();
        company.setContatto(contattoTemporaneo);
        company.setIndirizzo(indirizzoTemporaneo);
        service.save(company);
    }// end of method


    protected void saveUpdateCon(Persona entityBean, AViewDialog.Operation operation) {
        contattoTemporaneo = entityBean;
        contattoField.setValue(entityBean.toString());
        ((ATextField) getField(POST_CONTATTO)).focus();
        Notification.show("La modifica di persona è stata confermata ma devi registrare questa company per renderla definitiva", 3000, Notification.Position.BOTTOM_START);
    }// end of method


    protected void deleteUpdateCon(Persona entityBean) {
        contattoTemporaneo = null;
        contattoField.setValue("");
        ((ATextField) getField(POST_CONTATTO)).focus();
        Notification.show("La cancellazione di persona è stata confermata ma devi registrare questa company per renderla definitiva", 3000, Notification.Position.BOTTOM_START);
    }// end of method


    protected void annullaCon(Persona entityBean) {
        ((ATextField) getField(POST_CONTATTO)).focus();
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