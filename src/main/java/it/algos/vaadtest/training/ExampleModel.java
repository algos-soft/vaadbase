package it.algos.vaadtest.training;

import com.vaadin.flow.templatemodel.TemplateModel;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: mer, 02-mag-2018
 * Time: 18:02
 */
public interface ExampleModel extends TemplateModel {
    void setValue(String value);
    String getValue();
}// end of interface
