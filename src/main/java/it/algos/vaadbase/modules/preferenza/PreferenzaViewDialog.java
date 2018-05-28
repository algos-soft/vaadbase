package it.algos.vaadbase.modules.preferenza;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.converter.AConverterPrefByte;
import it.algos.vaadbase.enumeration.EAPrefType;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.ui.dialog.AViewDialog;
import it.algos.vaadbase.ui.fields.ACheckBox;
import it.algos.vaadbase.ui.fields.AComboBox;
import it.algos.vaadbase.ui.fields.AIntegerField;
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
        AbstractField valueField = getField(VALUE_FIELD_NAME);
        byte[] byteValue;
        Object genericValue;
        byteValue = getCurrentItem().getValue();

        if (valueField != null) {
            formLayout.remove(valueField);
            fieldMap.remove(VALUE_FIELD_NAME);
        }// end of if cycle

        EAPrefType type = getType();
        type = type != null ? type : EAPrefType.string;
        genericValue = type.bytesToObject(byteValue);

        valueField = sincro(type);
        switch (type) {
            case string:
                valueField.setValue((String) genericValue);
                break;
            case integer:
                valueField.setValue(genericValue.toString());
                break;
            case bool:
                valueField.setValue((boolean) genericValue);
                break;
            default:
                log.warn("Switch - caso non definito");
                break;
        } // end of switch statement

        AComboBox comboType = (AComboBox) getField(TIPO_FIELD_NAME);
        comboType.setValue(type);
        if (operation == Operation.ADD) {
            comboType.setEnabled(true);
            comboType.addValueChangeListener(e -> sincro((EAPrefType) e.getValue()));
        } else {
            comboType.setEnabled(false);
        }// end of if/else cycle

    }// end of method


    /**
     * Cambia il valueField sincronizzandolo col comboBox
     * Senza valori, perché è attivo SOLO in modalita ADD (new record)
     */
    protected AbstractField sincro(EAPrefType type) {
        String caption = "Valore ";
        AbstractField valueField = getField(VALUE_FIELD_NAME);
        fieldMap.remove(VALUE_FIELD_NAME);

        if (valueField != null) {
            formLayout.remove(valueField);
            valueField = null;
        }// end of if cycle

        type = type != null ? type : EAPrefType.string;
        switch (type) {
            case string:
                valueField = new ATextField(caption + "(string)");
                break;
            case integer:
                valueField = new AIntegerField(caption + "(solo numeri)");
                break;
            case bool:
                valueField = new ACheckBox(caption + "(vero/falso)");
                break;
            default:
                log.warn("Switch - caso non definito");
                break;
        } // end of switch statement

        if (valueField != null) {
            formLayout.add(valueField);
            fieldMap.put(VALUE_FIELD_NAME, valueField);
        }// end of if cycle

        return valueField;
    }// end of method


    /**
     * Regola in scrittura eventuali valori NON associati al binder
     * Dalla  UI al DB
     * Sovrascritto
     */
    protected void writeSpecificFields() {
        EAPrefType type = getType();
        AbstractField valueField = getField(VALUE_FIELD_NAME);
        byte[] byteValue;
        Object genericValue = null;

        if (valueField != null) {
            genericValue = valueField.getValue();
            byteValue = type.objectToBytes(genericValue);
            getCurrentItem().setValue(byteValue);
        }// end of if cycle

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