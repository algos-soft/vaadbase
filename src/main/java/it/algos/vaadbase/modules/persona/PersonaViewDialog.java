package it.algos.vaadbase.modules.persona;

import com.vaadin.flow.component.notification.Notification;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.application.StaticContextAccessor;
import it.algos.vaadbase.modules.address.Address;
import it.algos.vaadbase.modules.address.AddressPresenter;
import it.algos.vaadbase.modules.address.AddressService;
import it.algos.vaadbase.modules.address.AddressViewDialog;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.ui.dialog.AViewDialog;
import it.algos.vaadbase.ui.fields.ATextField;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static it.algos.vaadbase.application.BaseCost.TAG_PER;

/**
 * Project vaadbase <br>
 * Created by Algos
 * User: Gac
 * Date: 10-mag-2018 6.41.22
 * <p>
 * Estende la classe astratta ADialog per visualizzare i fields <br>
 * <p>
 * Not annotated with @SpringComponent (sbagliato)
 * Annotated with @Scope (obbligatorio = 'prototype')
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Qualifier(TAG_PER)
@AIScript(sovrascrivibile = true)
public class PersonaViewDialog extends AViewDialog<Persona> {

    private final static String INDIRIZZO = "indirizzo";
    private AddressPresenter addressPresenter;
    private AddressService addressService;
    private AddressViewDialog addressDialog;
    private Address indirizzoTemporaneo;
    private ATextField indirizzoField;
    private Consumer<Persona> itemAnnulla;

    /**
     * Constructs a new instance.
     *
     * @param presenter   per gestire la business logic del package
     * @param itemSaver   funzione associata al bottone 'registra'
     * @param itemDeleter funzione associata al bottone 'cancella'
     */
    public PersonaViewDialog(IAPresenter presenter, BiConsumer<Persona, AViewDialog.Operation> itemSaver, Consumer<Persona> itemDeleter) {
        super(presenter, itemSaver, itemDeleter, false);
    }// end of constructor

    /**
     * Constructs a new instance.
     *
     * @param presenter   per gestire la business logic del package
     * @param itemSaver   funzione associata al bottone 'registra'
     * @param itemDeleter funzione associata al bottone 'cancella'
     * @param itemAnnulla funzione associata al bottone 'annulla'
     */
    public PersonaViewDialog(IAPresenter presenter, BiConsumer<Persona, AViewDialog.Operation> itemSaver, Consumer<Persona> itemDeleter,Consumer<Persona> itemAnnulla) {
        super(presenter, itemSaver, itemDeleter, true);
        this.itemAnnulla = itemAnnulla;
    }// end of constructor


    /**
     * Aggiunge al binder eventuali fields specifici, prima di trascrivere la entityBean nel binder
     * Sovrascritto
     * Aggiunge il field al binder
     * Aggiunge il field alla fieldList interna
     */
    @Override
    protected void addSpecificAlgosFields() {
        addressPresenter = StaticContextAccessor.getBean(AddressPresenter.class);
        addressDialog = new AddressViewDialog(addressPresenter, this::saveUpdate, this::deleteUpdate, this::annullaInd);
        addressService = (AddressService) addressPresenter.getService();

        indirizzoField=(ATextField) getField(INDIRIZZO);
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
        indirizzoField.setValue(getIndirizzoCorrenteValue());
    }// end of method


    /**
     * Regola in scrittura eventuali valori NON associati al binder
     * Dallla  UI al DB
     * Sovrascritto
     */
    protected void writeSpecificFields() {
        Persona persona = super.getCurrentItem();
        persona.setIndirizzo(indirizzoTemporaneo);
        service.save(persona);
    }// end of method


    private void saveUpdate(Address entityBean, AViewDialog.Operation operation) {
        indirizzoTemporaneo = entityBean;
        indirizzoField.setValue(entityBean.toString());

        Notification.show("La modifica di indirizzo è stata confermata ma devi registrare questa persona per renderla definitiva", 3000, Notification.Position.BOTTOM_START);
    }// end of method


    private void deleteUpdate(Address entityBean) {
        indirizzoTemporaneo = null;
        indirizzoField.setValue("");

        Notification.show("La cancellazione di indirizzo è stata confermata ma devi registrare questa persona per renderla definitiva", 3000, Notification.Position.BOTTOM_START);
    }// end of method


    protected void annullaInd(Address entityBean) {
        cancelButton.focus();
    }// end of method

    private Address getIndirizzoCorrente() {
        Address indirizzo = null;
        Persona persona = getCurrentItem();

        if (persona != null) {
            indirizzo = persona.getIndirizzo();
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

    public void close() {
        super.close();
        if (itemAnnulla != null) {
            itemAnnulla.accept(null);
        }// end of if cycle
    }// end of method

}// end of class