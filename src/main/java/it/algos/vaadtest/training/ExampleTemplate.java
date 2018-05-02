package it.algos.vaadtest.training;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: mer, 02-mag-2018
 * Time: 17:59
 */
@Tag("example-template")
@HtmlImport("example-template.html")
public class ExampleTemplate extends PolymerTemplate<ExampleModel> {

    /**
     * Template model which defines the single "value" property.
     */
    public interface ExampleModel extends TemplateModel {

        void setValue(String value);

        String getValue();
    }// end of interface

    /**
     * Public setter to use from other Java classes. Not mandatory, but useful.
     */
    public void setValue(String value) {
        getModel().setValue(value);
    }

    @EventHandler
    private void handleClick() {
        try {
            Thread.sleep(2000);
            getModel().setValue("Vaadin!");
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }



//    @EventHandler
//    private void handleClick() {
//        getModel().setValue("Vaadin!");
//    }// end of method 

}// end of class
