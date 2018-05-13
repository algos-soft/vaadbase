package it.algos.vaadbase.modules.persona;

import com.vaadin.flow.component.notification.Notification;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.application.StaticContextAccessor;
import it.algos.vaadbase.backend.entity.AEntity;
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

    private AddressService addressService;
    private Address indirizzoTemporaneo;

    /**
     * Costruttore
     *
     * @param presenter   per gestire la business logic del package
     * @param itemSaver   funzione associata al bottone 'registra'
     * @param itemDeleter funzione associata al bottone 'annulla'
     */
    public PersonaViewDialog(IAPresenter presenter, BiConsumer<Persona, AViewDialog.Operation> itemSaver, Consumer<Persona> itemDeleter) {
        super(presenter, itemSaver, itemDeleter);
    }// end of constructor


    /**
     * Aggiunge al binder eventuali fields specifici, prima di trascrivere la entityBean nel binder
     * Sovrascritto
     * Aggiunge il field al binder
     * Aggiunge il field alla fieldList interna
     */
    @Override
    protected void addSpecificAlgosFields() {
        AddressPresenter addressPresenter = StaticContextAccessor.getBean(AddressPresenter.class);
        final AddressViewDialog dialog = new AddressViewDialog(addressPresenter, this::saveUpdate, this::deleteUpdate);
        addressService = (AddressService) addressPresenter.getService();

        ATextField indirizzoField = (ATextField) fieldService.create(null, binderClass, "indirizzo");
        if (indirizzoField != null) {
            indirizzoField.addFocusListener(e -> dialog.open(getCurrentItem().getIndirizzo(), Operation.EDIT));
            fieldMap.put("indirizzo", indirizzoField);
        }// end of if cycle

    }// end of method

    /**
     * Regola in lettura eventuali valori NON associati al binder
     * Dal DB alla UI
     * Sovrascritto
     */
    protected void readSpecificFields() {
        ATextField indirizzoField = (ATextField) getField("indirizzo");
        indirizzoField.setValue(getCurrentItem().getIndirizzo().toString());
    }// end of method


    /**
     * Regola in scrittura eventuali valori NON associati al binder
     * Dallla  UI al DB
     * Sovrascritto
     */
    protected void writeSpecificFields() {
        ATextField indirizzoField = (ATextField) getField("indirizzo");
    }// end of method


    protected void saveUpdate(Address entityBean, AViewDialog.Operation operation) {
        indirizzoTemporaneo = entityBean;
        ATextField indirizzoField = (ATextField) getField("indirizzo");
        indirizzoField.setValue(entityBean.toString());

//        Persona persona = super.getCurrentItem();
//        persona.setIndirizzo(entityBean);
//        service.save(persona);

        Notification.show("L'indirizzo che vedi è stato cambiato ma Persona NON è stata ancora registrata", 3000, Notification.Position.BOTTOM_START);
    }// end of method


    protected void deleteUpdate(AEntity entityBean) {
        Persona persona = super.getCurrentItem();
        persona.setIndirizzo(null);
        service.save(persona);
//        Notification.show(entityBean + " successfully deleted.", 3000, Notification.Position.BOTTOM_START);
//        updateView();
    }// end of method

}// end of class