package it.algos.vaadtest.training;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: mer, 02-mag-2018
 * Time: 16:49
 */
public class VaadinWelcome extends Composite<Div> implements HasComponents {

    private Image immagine = new Image("frontend/images/ambulanza.jpg", "vaadin");

    public VaadinWelcome() {
        add(immagine);
//        add(new Paragraph("Hello Vaadin 10"));
//        add(new Paragraph("Framework di prova con Vaadin 10"));
    }// end of method

//    add(new Paragraph("Hello Vaadin 10!"));

}// end of class
