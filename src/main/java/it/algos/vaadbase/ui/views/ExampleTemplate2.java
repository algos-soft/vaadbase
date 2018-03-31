package it.algos.vaadbase.ui.views;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.algos.vaadbase.spring.MessageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Simple template example.
 */
@Tag("example-template_2")
@HtmlImport("src/example-template.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ExampleTemplate2  {

    /**
     * Template model which defines the single "value" property.
     */
    public interface ExampleModel extends TemplateModel {

        void setValue(String name);
    }

//    public ExampleTemplate2(@Autowired MessageBean bean) {
//        // Set the initial value to the "value" property.
//        getModel().setValue(bean.getMessage());
//    }
//
//    public void setValue(String value) {
//        getModel().setValue(value);
//    }
}
