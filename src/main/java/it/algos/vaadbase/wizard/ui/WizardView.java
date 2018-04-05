package it.algos.vaadbase.wizard.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import it.algos.vaadbase.application.BaseCost;
import it.algos.vaadbase.service.ATextService;
import it.algos.vaadbase.ui.AView;
import it.algos.vaadbase.wizard.enumeration.Chiave;
import it.algos.vaadbase.wizard.enumeration.Progetto;
import it.algos.vaadbase.wizard.scripts.TDialogo;
import it.algos.vaadbase.wizard.scripts.TElabora;
import it.algos.vaadbase.wizard.scripts.TRecipient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;

/**
 * Project vbase
 * Created by Algos
 * User: gac
 * Date: mar, 20-mar-2018
 * Time: 08:57
 * Annotated with @Route (obbligatorio)
 * Annotated with @Theme (facoltativo)
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Route(value = BaseCost.PAGE_WIZARD)
@Theme(Lumo.class)
@Qualifier(BaseCost.TAG_WIZ)
public class WizardView extends AView {


    public final static String NORMAL_WIDTH = "9em";
    public final static String NORMAL_HEIGHT = "3em";
    private static Progetto PROGETTO_STANDARD_SUGGERITO = Progetto.vaadin;
    private static String NOME_PACKAGE_STANDARD_SUGGERITO = "prova";
    private static String LABEL_A = "Creazione di un nuovo project";
    private static String LABEL_B = "Update di un project esistente";
    private static String LABEL_C = "Creazione di un nuovo package";
    private static String LABEL_D = "Modifica di un package esistente";

    /**
     * Libreria di servizio. Inietta da Spring nel costruttore come 'singleton'
     */
    private ATextService text;

    private Label labelUno;
    private Label labelDue;
    private Label labelTre;
    private Label labelQuattro;
    private Button buttonUno;
    private Button buttonDue;
    private Button buttonTre;
    private Button buttonQuattro;
    private NativeButton confirmButton;
    private NativeButton cancelButton;
    @Autowired
    private TDialogo dialog;
    @Autowired
    private TElabora elabora;
    private TRecipient recipient;

    private ComboBox fieldComboProgetti;
    private TextField fieldTextPackage;
    private TextField fieldTextEntity; // suggerito
    private TextField fieldTextTag; // suggerito
    private Checkbox fieldCheckBoxCompany;
    private Checkbox fieldCheckBoxSovrascrive;

    private Map<Chiave, Object> mappaInput = new HashMap<>();

    @Autowired
    public WizardView(ATextService text) {
        this.text = text;
    }// end of Spring constructor


    //    @PostConstruct
    public void inizia() {
        this.setMargin(true);
        this.setSpacing(true);

        labelUno = new Label("Creazione di un nuovo project, tramite dialogo wizard");
        this.add(labelUno);
        labelDue = new Label("Aggiornamento di un project esistente, tramite dialogo wizard");
        this.add(labelDue);
        labelTre = new Label("Creazione di un nuovo package (modulo), tramite dialogo wizard");
        this.add(labelTre);
        labelQuattro = new Label("Modifica di un package (modulo) esistente, tramite dialogo wizard");
        this.add(labelQuattro);

    }// end of method

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        this.removeAll();

        //--componente grafico facoltativo
        this.regolaMenu();
        this.add(menu);
        this.add(creaMenu());

        inizia();
    }// end of method

    private Component creaMenu() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setMargin(false);
        layout.setSpacing(true);

        buttonUno = new Button("Crea project", event -> Notification.show("Non ancora funzionante", 3000, Notification.Position.MIDDLE));
        layout.add(buttonUno);

        buttonDue = new Button("Package");
        buttonDue.addClickListener(event -> dialog.open(new TRecipient() {
            @Override
            public void gotInput(Map<Chiave, Object> mappaInput) {
                elabora(mappaInput);
            }// end of inner method
        }, true, Progetto.vaadin, NOME_PACKAGE_STANDARD_SUGGERITO));// end of lambda expressions and anonymous inner class
        layout.add(buttonDue);

        return layout;
    }// end of method


    private void elabora(Map<Chiave, Object> mappaInput) {
        dialog.close();

        if (mappaInput != null) {
            elabora.newPackage(mappaInput);
        }// end of if cycle

    }// end of method

}// end of class
