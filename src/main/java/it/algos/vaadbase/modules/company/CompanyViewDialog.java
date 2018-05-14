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

    private PersonaPresenter personaPresenter;
    private AddressPresenter addressPresenter;
    private PersonaService personaService;
    private AddressService addressService;
    private Persona personaTemporanea;
    private Address indirizzoTemporaneo;
    private ATextField contattoField;
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
        addressPresenter = StaticContextAccessor.getBean(AddressPresenter.class);
        final PersonaViewDialog dialog = new PersonaViewDialog(personaPresenter, this::saveUpdate, this::deleteUpdate, true);
        final AddressViewDialog dialog2 = new AddressViewDialog(addressPresenter, this::saveUpdate2, this::deleteUpdate2, true);
        personaService = (PersonaService) personaPresenter.getService();
        addressService = (AddressService) addressPresenter.getService();
        contattoField = (ATextField) fieldService.create(binder, binderClass, "contatto");
        indirizzoField = (ATextField) fieldService.create(binder, binderClass, "indirizzo");

        if (contattoField != null) {
            fieldMap.put("contatto", contattoField);
            contattoField.addFocusListener(e -> dialog.open(getCurrentItem().getContatto() != null ? getCurrentItem().getContatto() : personaService.newEntity(), AViewDialog.Operation.ADD));
        }// end of if cycle


        if (indirizzoField != null) {
            fieldMap.put("indirizzo", indirizzoField);
            indirizzoField.addFocusListener(e -> dialog2.open(getCurrentItem().getIndirizzo() != null ? getCurrentItem().getIndirizzo() : addressService.newEntity(), Operation.EDIT));
        }// end of if cycle
    }// end of method


    /**
     * Regola in lettura eventuali valori NON associati al binder
     * Dal DB alla UI
     * Sovrascritto
     */
    protected void readSpecificFields() {
        contattoField = (ATextField) getField("contatto");
        contattoField.setValue(getCurrentItem().getContatto() != null ? getCurrentItem().getContatto().toString() : "");
        indirizzoField = (ATextField) getField("indirizzo");
        indirizzoField.setValue(getCurrentItem().getIndirizzo() != null ? getCurrentItem().getIndirizzo().toString() : "");
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


    protected void saveUpdate(Persona entityBean, AViewDialog.Operation operation) {
        personaTemporanea = entityBean;
        contattoField = (ATextField) getField("contatto");
        contattoField.setValue(entityBean.toString());

        Notification.show("La modifica di persona è stata confermata ma devi registrare questa company per renderla definitiva", 3000, Notification.Position.BOTTOM_START);
    }// end of method


    protected void deleteUpdate(Persona entityBean) {
        personaTemporanea = null;
        contattoField = (ATextField) getField("contatto");
        contattoField.setValue("");

        Notification.show("La cancellazione di persona è stata confermata ma devi registrare questa company per renderla definitiva", 3000, Notification.Position.BOTTOM_START);
    }// end of method


    protected void saveUpdate2(Address entityBean, AViewDialog.Operation operation) {
        indirizzoTemporaneo = entityBean;
        indirizzoField = (ATextField) getField("indirizzo");
            indirizzoField.setValue(entityBean.toString());

        Notification.show("La modifica di indirizzo è stata confermata ma devi registrare questa company per renderla definitiva", 3000, Notification.Position.BOTTOM_START);
    }// end of method


    protected void deleteUpdate2(Address entityBean) {
        indirizzoTemporaneo = null;
        indirizzoField = (ATextField) getField("indirizzo");
        indirizzoField.setValue("");

        Notification.show("La cancellazione di indirizzo è stata confermata ma devi registrare questa company per renderla definitiva", 3000, Notification.Position.BOTTOM_START);
    }// end of method


}// end of class