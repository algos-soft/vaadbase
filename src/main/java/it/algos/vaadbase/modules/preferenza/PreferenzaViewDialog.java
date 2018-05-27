package it.algos.vaadbase.modules.preferenza;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.converter.AConverterPrefByte;
import it.algos.vaadbase.enumeration.EAPrefType;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.service.ATextService;
import it.algos.vaadbase.ui.dialog.AViewDialog;
import it.algos.vaadbase.ui.fields.AComboBox;
import it.algos.vaadbase.ui.fields.ATextField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadbase.application.BaseCost.TAG_PRE;

/**
 * Project vaadbase <br>
 * Created by Algos
 * User: Gac
 * Date: 25-mag-2018 16.19.24
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
@Qualifier(TAG_PRE)
@Slf4j
@AIScript(sovrascrivibile = true)
public class PreferenzaViewDialog extends AViewDialog<Preferenza> {

    private final static String TIPO_FIELD_NAME = "type";
    private final static String VALUE_FIELD_NAME = "value";
    @Autowired
    private AConverterPrefByte prefConverter;

    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere dall'interfaccia la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param presenter per gestire la business logic del package
     */
    @Autowired
    public PreferenzaViewDialog(@Qualifier(TAG_PRE) IAPresenter presenter) {
        super(presenter);
    }// end of constructor


    /**
     * Regola in lettura eventuali valori NON associati al binder
     * Sovrascritto
     */
    @Override
    protected void readSpecificFields() {
        String label = "Valore";

        EAPrefType type = getType();
        if (type == null) {
            type = EAPrefType.string;
        }// end of if cycle
        prefConverter.setType(type);

//        AbstractField valueField = getField(VALUE_FIELD_NAME);
//        if (valueField != null) {
//            try { // prova ad eseguire il codice
//                binder.removeBinding(valueField);
//                fieldMap.remove(VALUE_FIELD_NAME);
//                getFormLayout().remove(valueField);
//            } catch (Exception unErrore) { // intercetta l'errore
//                log.error(unErrore.toString());
//            }// fine del blocco try-catch
//        }// end of if cycle
//        valueField = new ATextField(label);
//        prefConverter.setType(type);
//        binder.forField(valueField).withConverter(prefConverter).bind(VALUE_FIELD_NAME);
//        fieldMap.put(VALUE_FIELD_NAME, valueField);
//        getFormLayout().add(valueField);

        AComboBox comboType = (AComboBox) getField(TIPO_FIELD_NAME);
        comboType.addValueChangeListener(e -> prefConverter.setType((EAPrefType) e.getValue()));
        comboType.setValue(type);

        if (operation == Operation.ADD) {
            comboType.setEnabled(true);
        } else {
            comboType.setEnabled(false);
        }// end of if/else cycle

    }// end of method



    private EAPrefType getType() {
        EAPrefType type = null;
        Preferenza preferenza = getCurrentItem();

        if (preferenza != null) {
            type = preferenza.getType();
        }// end of if cycle

        return type;
    }// end of method



}// end of class