package it.algos.vaadbase.ui;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.application.StaticContextAccessor;
import it.algos.vaadbase.backend.service.IAService;
import it.algos.vaadbase.converter.AConverterPrefByte;
import it.algos.vaadbase.service.AAnnotationService;
import it.algos.vaadbase.service.AReflectionService;
import it.algos.vaadbase.service.ATextService;
import it.algos.vaadbase.ui.annotation.AIField;
import it.algos.vaadbase.ui.enumeration.EAFieldType;
import it.algos.vaadbase.ui.fields.AComboBox;
import it.algos.vaadbase.ui.fields.AIntegerField;
import it.algos.vaadbase.ui.fields.ATextArea;
import it.algos.vaadbase.ui.fields.ATextField;
import it.algos.vaadbase.validator.AIntegerZeroValidator;
import it.algos.vaadbase.validator.AStringNullValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: ven, 11-mag-2018
 * Time: 17:43
 * NON deve essere astratta, altrimenti Spring non la costruisce
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AFieldService {

    /**
     * Service iniettato da Spring (@Scope = 'singleton'). Unica per tutta l'applicazione. Usata come libreria.
     */
    @Autowired
    public AAnnotationService annotation;

    /**
     * Service iniettato da Spring (@Scope = 'singleton'). Unica per tutta l'applicazione. Usata come libreria.
     */
    @Autowired
    public AReflectionService reflection;


    /**
     * Service iniettato da Spring (@Scope = 'singleton'). Unica per tutta l'applicazione. Usata come libreria.
     */
    @Autowired
    public ATextService text;

    @Autowired
    private AConverterPrefByte prefConverter;

    /**
     * Create a single field.
     * The field type is chosen according to the annotation @AIField.
     *
     * @param binder      collegamento tra i fields e la entityBean
     * @param binderClass della Entity di riferimento
     * @param fieldName   della property
     */
    public AbstractField create(Binder binder, Class binderClass, String fieldName) {
        Field reflectionJavaField = reflection.getField(binderClass, fieldName);

        if (reflectionJavaField != null) {
            return create(binder, reflectionJavaField);
        } else {
            return null;
        }// end of if/else cycle
    }// end of method


    /**
     * Create a single field.
     * The field type is chosen according to the annotation @AIField.
     *
     * @param binder              collegamento tra i fields e la entityBean
     * @param reflectionJavaField di riferimento per estrarre le Annotation
     */
    public AbstractField create(Binder binder, Field reflectionJavaField) {
        AbstractField field = null;
        String fieldName = reflectionJavaField.getName();
//        int minDefault = 3;
        Class clazz = null;
        EAFieldType type = annotation.getFormType(reflectionJavaField);
        boolean notNull;
        String stringMessage = "Code must contain at least 3 printable characters";
        String intMessage = " deve contenere solo caratteri numerici";
        String mess = "";
        StringLengthValidator stringValidator = null;
        StringToIntegerConverter integerConverter = null;
        String message;
        String messageSize;
        int min = 0;

        clazz = annotation.getClazz(reflectionJavaField);
        notNull = annotation.isNotNull(reflectionJavaField);
        message = annotation.getMessage(reflectionJavaField);
        messageSize = annotation.getMessageSize(reflectionJavaField);
        min = annotation.getSizeMin(reflectionJavaField);
        type = annotation.getFormType(reflectionJavaField);
        String caption = annotation.getFormFieldNameCapital(reflectionJavaField);
        AIField fieldAnnotation = annotation.getAIField(reflectionJavaField);
        String width = annotation.getWidthEM(reflectionJavaField);
        boolean required = annotation.isRequired(reflectionJavaField);
        boolean focus = annotation.isFocus(reflectionJavaField);
//        boolean enabled = annotation.isFieldEnabled(reflectedJavaField, nuovaEntity);
        Class targetClazz = annotation.getComboClass(reflectionJavaField);
        AStringNullValidator nullValidator= new AStringNullValidator();
        AIntegerZeroValidator zeroValidator= new AIntegerZeroValidator();

        switch (type) {
            case text:
                field = new ATextField(caption);
                if (notNull) {
                    if (min > 0) {
                        stringValidator = new StringLengthValidator(messageSize, min, null);
                        binder
                                .forField(field)
                                .withValidator(nullValidator)
                                .withValidator(stringValidator)
                                .bind(fieldName);
                    } else {
                        binder
                                .forField(field)
                                .withValidator(nullValidator)
                                .bind(fieldName);
                    }// end of if/else cycle
                } else {
                    if (min > 0) {
                        stringValidator = new StringLengthValidator(messageSize, min, null);
                        binder.forField(field).withValidator(stringValidator).bind(fieldName);
                    } else {
                        binder.forField(field).bind(fieldName);
                    }// end of if/else cycle
                }// end of if/else cycle

                if (focus) {
                    ((ATextField) field).focus();
                }// end of if cycle
                break;
            case textarea:
                field = new ATextArea(caption);
                binder.forField(field).bind(fieldName);
                field.setReadOnly(false);
                break;
            case integer:
                mess = fieldName + intMessage;
                message = text.isValid(message) ? message : mess;
                integerConverter = new StringToIntegerConverter(0, message);
                field = new AIntegerField(caption);
                binder.forField(field)
                        .withConverter(integerConverter)
                        .withValidator(zeroValidator)
                        .bind(fieldName);

                if (focus) {
                    ((AIntegerField) field).focus();
                }// end of if cycle
                break;
            case combo:
                field = new AComboBox(caption);
                if (clazz != null) {
                    try { // prova ad eseguire il codice
                        IAService service = (IAService) StaticContextAccessor.getBean(clazz);
                        List items = ((IAService) service).findAll();
                        ((AComboBox) field).setItems(items);
                    } catch (Exception unErrore) { // intercetta l'errore
                        log.error(unErrore.toString());
                    }// fine del blocco try-catch
                }// end of if cycle
                field.setReadOnly(false);
                break;
            case enumeration:
                field = new AComboBox(caption);
                if (clazz != null) {
                    Object[] items = clazz.getEnumConstants();
                    if (items != null) {
                        ((AComboBox) field).setItems(items);
                    }// end of if cycle
                }// end of if cycle
                binder.forField(field).bind(fieldName);
                break;
            case link:
                field = new ATextField(caption);
                break;
            case pref:
//                field = new ATextField(caption);
//                binder.forField(field).withConverter(prefConverter).bind(fieldName);
                break;
            case noBinder:
                field = new ATextField(caption);
                break;
            default:
                field = new ATextField(caption);
                break;
        } // end of switch statement

        return field;
    }// end of method

}// end of class
