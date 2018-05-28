package it.algos.vaadbase.ui.fields;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.checkbox.Checkbox;

import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: lun, 28-mag-2018
 * Time: 08:37
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ACheckBox extends Checkbox implements IAField{

    public ACheckBox(String labelText) {
        super(labelText);
    }

    public ACheckBox(boolean initialValue) {
        super(initialValue);
    }

    public ACheckBox(String labelText, boolean initialValue) {
        super(labelText, initialValue);
    }

    @Override
    public AbstractField getField() {
        return this;
    }// end of method

}// end of class
