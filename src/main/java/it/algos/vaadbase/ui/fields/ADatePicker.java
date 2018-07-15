package it.algos.vaadbase.ui.fields;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.datepicker.DatePicker;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: ven, 13-lug-2018
 * Time: 18:10
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Slf4j
public class ADatePicker extends DatePicker implements IAField {



    public ADatePicker(String label) {
        super(label);
    }// end of Spring constructor

    @Override
    protected void setSynchronizedEvent(String synchronizedEvent) {
        super.setSynchronizedEvent(synchronizedEvent);
    }

    @Override
    protected void setPresentationValue(LocalDate newPresentationValue) {
        super.setPresentationValue(newPresentationValue);
    }

    @Override
    public void setValue(LocalDate value) {
        super.setValue(value);
    }
    public void setValue(LocalDateTime value) {
        super.setValue(null);
    }

    @Override
    public LocalDate getValue() {
        return super.getValue();
    }

    @Override
    public AbstractField getField() {
        return null;
    }// end of method

}// end of class
