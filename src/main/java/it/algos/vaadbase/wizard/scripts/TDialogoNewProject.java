package it.algos.vaadbase.wizard.scripts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.service.AFileService;
import it.algos.vaadbase.service.ATextService;
import it.algos.vaadbase.wizard.enumeration.Chiave;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: gio, 03-mag-2018
 * Time: 13:55
 * Dialogo per la creazione/modifica di un nuovo project
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TDialogoNewProject extends TDialogo {

    private TextField fieldTextProject;

    /**
     * Costruttore @Autowired
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation
     */
    public TDialogoNewProject(ATextService text, AFileService file) {
        super(text, file);
    }// end of Spring constructor


    public void open(TRecipient recipient) {
        this.recipient = recipient;

        this.removeAll();
        super.open();

        this.add(new Label("Creazione di un nuovo project"));
        this.add(creaBody());
        this.add(creaFooter());

//        addListener();
//        sincroPackageNew(packageName);
    }// end of method

    private Component creaBody() {
        String label = "Project name";

        fieldTextProject = new TextField();
        fieldTextProject.setLabel(label);
        fieldTextProject.setEnabled(true);
        fieldTextProject.addValueChangeListener(event -> sincroProject(event.getValue()));//end of lambda expressions

        return new VerticalLayout(fieldTextProject);
    }// end of method

    private void sincroProject(String valueFromProject) {
        if (text.isValid(valueFromProject) && valueFromProject.length() > 2) {
            confirmButton.setVisible(true);
        } else {
            confirmButton.setVisible(false);
        }// end of if/else cycle
    }// end of method


    protected void setMappa() {
        if (mappaInput != null) {
            mappaInput.put(Chiave.newProjectName, fieldTextProject.getValue());
        }// end of if cycle
    }// end of method

}// end of class
