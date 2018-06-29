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
import it.algos.vaadbase.ui.fields.ATextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadbase.application.BaseCost.TAG_COM;

/**
 * Project vaadbase <br>
 * Created by Algos
 * User: Gac
 * Date: 9-mag-2018 19.52.23
 * <p>
 * Estende la classe astratta AViewDialog per visualizzare i fields <br>
 * <p>
 * Not annotated with @SpringView (sbagliato) perché usa la @Route di VaadinFlow <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope (obbligatorio = 'prototype') <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Qualifier(TAG_COM)
@AIScript(sovrascrivibile = true)
public class CompanyViewDialog extends AViewDialog<Company> {

    public static String CONTATTO = "contatto";
    public static String INDIRIZZO = "indirizzo";

    protected PersonaPresenter contattoPresenter;
    protected PersonaService contattoService;
    protected PersonaViewDialog contattoDialog;
    protected Persona contattoTemporaneo;
    protected ATextField contattoField;

    private AddressPresenter indirizzoPresenter;
    private AddressService indirizzoService;
    private AddressViewDialog indirizzoDialog;
    private Address indirizzoTemporaneo;
    private ATextField indirizzoField;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere dall'interfaccia la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param presenter per gestire la business logic del package
     */
    @Autowired
    public CompanyViewDialog(@Qualifier(TAG_COM) IAPresenter presenter) {
        super(presenter);
    }// end of constructor


    /**
     * Aggiunge al binder eventuali fields specifici, prima di trascrivere la entityBean nel binder
     * Sovrascritto
     * Dopo aver creato un AField specifico, usare il metodo super.addFieldBinder() per:
     * Inizializzare AField
     */
    @Override
    protected void addSpecificAlgosFields() {
        contattoPresenter = StaticContextAccessor.getBean(PersonaPresenter.class);
        contattoService = (PersonaService) contattoPresenter.getService();

        contattoDialog = StaticContextAccessor.getBean(PersonaViewDialog.class);
        contattoDialog.setPresenter(contattoPresenter);
        contattoDialog.fixFunzioni(this::saveUpdateCon, this::deleteUpdateCon, this::annullaCon);
        contattoDialog.fixConfermaAndNotRegistrazione();

        contattoField = (ATextField) getField(CONTATTO);
        if (contattoField != null) {
            contattoField.addFocusListener(e -> contattoDialog.open(getContatto(), Operation.EDIT, CONTATTO));
        }// end of if cycle

        indirizzoPresenter = StaticContextAccessor.getBean(AddressPresenter.class);
        indirizzoService = (AddressService) indirizzoPresenter.getService();

        indirizzoDialog = StaticContextAccessor.getBean(AddressViewDialog.class);
        indirizzoDialog.setPresenter(indirizzoPresenter);
        indirizzoDialog.fixFunzioni(this::saveUpdateInd, this::deleteUpdateInd, this::annullaInd);
        indirizzoDialog.fixConfermaAndNotRegistrazione();

        indirizzoField = (ATextField) getField(INDIRIZZO);
        if (indirizzoField != null) {
            indirizzoField.addFocusListener(e -> indirizzoDialog.open(getIndirizzo(), Operation.EDIT));
        }// end of if cycle
    }// end of method

    /**
     * Regola in lettura eventuali valori NON associati al binder
     * Dal DB alla UI
     * Sovrascritto
     */
    protected void readSpecificFields() {
        contattoTemporaneo = getContattoCorrente();
        contattoField.setValue(contattoTemporaneo != null ? contattoTemporaneo.toString() : "");

        indirizzoTemporaneo = getIndirizzoCorrente();
        indirizzoField.setValue(indirizzoTemporaneo != null ? indirizzoTemporaneo.toString() : "");
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
        focusOnPost(CONTATTO);
        Notification.show("La modifica di persona è stata confermata ma devi registrare questa company per renderla definitiva", 3000, Notification.Position.BOTTOM_START);
    }// end of method


    protected void deleteUpdateCon(Persona entityBean) {
        contattoTemporaneo = null;
        contattoField.setValue("");
        focusOnPost(CONTATTO);
        Notification.show("La cancellazione di persona è stata confermata ma devi registrare questa company per renderla definitiva", 3000, Notification.Position.BOTTOM_START);
    }// end of method


    protected void annullaCon(Persona entityBean) {
        focusOnPost(CONTATTO);
    }// end of method


    protected void saveUpdateInd(Address entityBean, AViewDialog.Operation operation) {
        indirizzoTemporaneo = entityBean;
        indirizzoField.setValue(entityBean.toString());
        focusOnPost(INDIRIZZO);
        Notification.show("La modifica di indirizzo è stata confermata ma devi registrare questa company per renderla definitiva", 3000, Notification.Position.BOTTOM_START);
    }// end of method


    protected void deleteUpdateInd(Address entityBean) {
        indirizzoTemporaneo = null;
        indirizzoField.setValue("");
        focusOnPost(INDIRIZZO);
        Notification.show("La cancellazione di indirizzo è stata confermata ma devi registrare questa company per renderla definitiva", 3000, Notification.Position.BOTTOM_START);
    }// end of method


    protected void annullaInd(Address entityBean) {
        focusOnPost(INDIRIZZO);
    }// end of method


    private Address getIndirizzoCorrente() {
        Address indirizzo = null;
        Company company = getCurrentItem();

        if (company != null) {
            indirizzo = company.getIndirizzo();
        }// end of if cycle

        return indirizzo;
    }// end of method


    private Address getIndirizzo() {
        Address indirizzo = getIndirizzoCorrente();

        if (indirizzo == null) {
            indirizzo = indirizzoService.newEntity();
        }// end of if cycle

        return indirizzo;
    }// end of method


    private Persona getContattoCorrente() {
        Persona persona = null;
        Company company = getCurrentItem();

        if (company != null) {
            persona = company.getContatto();
        }// end of if cycle

        return persona;
    }// end of method


    private Persona getContatto() {
        Persona persona = getContattoCorrente();

        if (persona == null) {
            persona = contattoService.newEntity();
        }// end of if cycle

        return persona;
    }// end of method

}// end of class