package it.algos.vaadbase.ui.fields;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.TextField;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.List;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: sab, 12-mag-2018
 * Time: 20:44
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AComboBox<T> extends ComboBox implements IAField {

    public AComboBox() {
        this("");
    }// end of constructor

    public AComboBox(String label) {
        super(label);
    }// end of constructor

    public AComboBox(String label, List items) {
        super(label, items);
    }// end of constructor

    @Override
    public AComboBox getField() {
        return null;
    }// end of method

}// end of class
