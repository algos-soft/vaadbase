package it.algos.vaadbase.ui;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.service.AAnnotationService;
import it.algos.vaadbase.service.AReflectionService;
import it.algos.vaadbase.service.ATextService;
import it.algos.vaadbase.ui.annotation.AIField;
import it.algos.vaadbase.ui.enumeration.EAFieldType;
import it.algos.vaadbase.ui.fields.AIntegerField;
import it.algos.vaadbase.ui.fields.ATextArea;
import it.algos.vaadbase.ui.fields.ATextField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.lang.reflect.Field;

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
        EAFieldType type = annotation.getFormType(reflectionJavaField);
        String stringMessage = "Code must contain at least 3 printable characters";
        String intMessage = " deve contenere solo caratteri numerici";
        String mess = "";
        StringLengthValidator stringConverter = null;
        StringToIntegerConverter integerConverter = null;
        String message;
        int min = 0;

        message = annotation.getMessage(reflectionJavaField);
        min = annotation.getSizeMin(reflectionJavaField);
        type = annotation.getFormType(reflectionJavaField);
        String caption = annotation.getFormFieldNameCapital(reflectionJavaField);
        AIField fieldAnnotation = annotation.getAIField(reflectionJavaField);
        String width = annotation.getWidthEM(reflectionJavaField);
        boolean required = annotation.isRequired(reflectionJavaField);
        boolean focus = annotation.isFocus(reflectionJavaField);
//        boolean enabled = annotation.isFieldEnabled(reflectedJavaField, nuovaEntity);
        Class targetClazz = annotation.getComboClass(reflectionJavaField);

        switch (type) {
            case text:
                mess = fieldName + " deve contenere almeno " + min + " caratteri";
                message = text.isValid(message) ? message : mess;
                stringConverter = new StringLengthValidator(message, min, null);
                field = new ATextField(caption);
                    binder.forField(field).withValidator(stringConverter).bind(fieldName);
                break;
            case textarea:
                field = new ATextArea(caption);
//                    binder.forField(field).bind(fieldName);
                break;
            case integer:
                mess = fieldName + intMessage;
                message = text.isValid(message) ? message : mess;
                integerConverter = new StringToIntegerConverter(0, message);
                field = new AIntegerField(caption);
                    binder.forField(field).withConverter(integerConverter).bind(fieldName);
                break;
            case link:
                field = new ATextField(caption);
//                if (binder != null) {
//                    binder.forField(field).bind(fieldName);
//                }// end of if cycle
                break;
            default:
                field = new ATextField(caption);
                break;
        } // end of switch statement

        return field;
    }// end of method

}// end of class
