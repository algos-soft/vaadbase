package it.algos.vaadbase.wizard.scripts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.service.AFileService;
import it.algos.vaadbase.service.ATextService;
import it.algos.vaadbase.wizard.enumeration.Progetto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.Arrays;

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
public class TDialogoUpdateProject extends TDialogo {


    /**
     * Costruttore @Autowired
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation
     */
    public TDialogoUpdateProject(ATextService text, AFileService file) {
        super(text, file);
    }// end of Spring constructor


    public void open(TRecipient recipient) {
        this.recipient = recipient;

        this.removeAll();
        super.open();

        this.add(new Label("Update di un project esistente"));
        this.add(creaBody());
        this.add(creaFooter());
//
//        sincroRadio(groupTitolo.getValue());
//        addListener();
//        sincroPackageNew(packageName);
    }// end of method

    private Component creaBody() {
        fieldComboProgetti = new ComboBox<>();
        fieldComboProgetti.setAllowCustomValue(false);
        String label = "Progetto";
        Progetto[] progetti = Progetto.values();

        fieldComboProgetti.setLabel(label);
        fieldComboProgetti.setItems(Arrays.asList(progetti));

//        if (Arrays.asList(progetti).contains(project) && isProgettoEsistente()) {
//            fieldComboProgetti.setValue(project);
//        } else {
//            fieldComboProgetti.setValue(null);
//        }// end of if/else cycle

        return fieldComboProgetti;
    }// end of method

}// end of class
