package it.algos.vaadbase.wizard.scripts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.service.AFileService;
import it.algos.vaadbase.service.ATextService;
import it.algos.vaadbase.wizard.enumeration.Chiave;
import it.algos.vaadbase.wizard.enumeration.Progetto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: gio, 03-mag-2018
 * Time: 14:07
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public abstract class TDialogo extends Dialog {

    protected final static String NORMAL_WIDTH = "9em";
    protected final static String NORMAL_HEIGHT = "3em";
    protected static final String DIR_MAIN = "/src/main";
    protected static final String DIR_JAVA = DIR_MAIN + "/java/it/algos";
    protected static final String ENTITIES_NAME = "modules";
    protected ATextService text;
    protected AFileService file;
    protected TRecipient recipient;
    protected Map<Chiave, Object> mappaInput = new HashMap<>();

    protected ComboBox<Progetto> fieldComboProgetti;

    protected NativeButton confirmButton;
    protected NativeButton cancelButton;
    protected RadioButtonGroup<String> groupTitolo;

    /**
     * Costruttore @Autowired (nella sottoclasse concreta)
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation
     */
    public TDialogo(ATextService text, AFileService file) {
        this.text = text;
        this.file = file;
    }// end of Spring constructor


    @PostConstruct
    public void creaDialogo() {
        this.setCloseOnEsc(false);
        this.setCloseOnOutsideClick(false);
    }// end of method


    protected Component creaFooter() {
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout layoutFooter = new HorizontalLayout();
        layoutFooter.setSpacing(true);
        layoutFooter.setMargin(true);

        cancelButton = new NativeButton("Annulla", event -> {
            recipient.gotInput(null);
            this.close();
        });//end of lambda expressions
        cancelButton.setWidth(NORMAL_WIDTH);
        cancelButton.setHeight(NORMAL_HEIGHT);
        cancelButton.setVisible(true);

        confirmButton = new NativeButton("Conferma", event -> {
            chiudeDialogo();
        });//end of lambda expressions
        confirmButton.setWidth(NORMAL_WIDTH);
        confirmButton.setHeight(NORMAL_HEIGHT);
        confirmButton.setVisible(false);

        layoutFooter.add(cancelButton, confirmButton);
        layout.add(layoutFooter);
        return layout;
    }// end of method


    private void chiudeDialogo() {
        setMappa();
        recipient.gotInput(mappaInput);
        this.close();
    }// end of method


    protected void setMappa() {
    }// end of method

}// end of class
