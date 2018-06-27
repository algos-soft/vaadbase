package it.algos.vaadbase.wizard.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import it.algos.vaadbase.service.ATextService;
import it.algos.vaadbase.ui.AView;
import it.algos.vaadbase.ui.MainLayout;
import it.algos.vaadbase.wizard.enumeration.Chiave;
import it.algos.vaadbase.wizard.enumeration.Progetto;
import it.algos.vaadbase.wizard.scripts.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static it.algos.vaadbase.application.BaseCost.TAG_WIZ;

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
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Route(value = TAG_WIZ, layout = MainLayout.class)
@Qualifier(TAG_WIZ)
public class WizardView extends AView {


    public final static String NORMAL_WIDTH = "9em";
    public final static String NORMAL_HEIGHT = "3em";
    /**
     * Icona visibile nel menu (facoltativa)
     * Nella menuBar appare invece visibile il MENU_NAME, indicato qui
     * Se manca il MENU_NAME, di default usa il 'name' della view
     */
    public static final VaadinIcon VIEW_ICON = VaadinIcon.MAGIC;
    private static final String PROJECT_BASE_NAME = "vaadbase";
    private static Progetto PROGETTO_STANDARD_SUGGERITO = Progetto.test;
    private static String NOME_PACKAGE_STANDARD_SUGGERITO = "prova";
    private static String LABEL_A = "Creazione di un nuovo project";
    private static String LABEL_B = "Update di un project esistente";
    private static String LABEL_C = "Creazione di un nuovo package";
    private static String LABEL_D = "Modifica di un package esistente";
    /**
     * Libreria di servizio. Inietta da Spring nel costruttore come 'singleton'
     */
    @Autowired
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
    private TDialogoPackage dialogPackage;
    @Autowired
    private TDialogoNewProject newProject;
    @Autowired
    private TDialogoUpdateProject updateProject;
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

    public WizardView() {
    }// end of Spring constructor


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        this.removeAll();
        checkIniziale();
    }// end of method

    public void checkIniziale() {
        this.setMargin(true);
        this.setSpacing(true);

        String currentProject = System.getProperty("user.dir");
        currentProject = currentProject.substring(currentProject.lastIndexOf("/")+1);

        if (currentProject.equals(PROJECT_BASE_NAME)) {
            iniziaBase();
            this.add(creaMenuBase());
        } else {
            iniziaProject();
            this.add(creaMenuProject());
        }// end of if/else cycle

    }// end of method


    public void iniziaBase() {
        labelUno = new Label("Creazione di un nuovo project, tramite dialogo wizard");
        this.add(labelUno);
        labelDue = new Label("Aggiornamento di un project esistente, tramite dialogo wizard");
        this.add(labelDue);
        labelTre = new Label("Creazione di un nuovo package (modulo), tramite dialogo wizard");
        this.add(labelTre);
        labelQuattro = new Label("Modifica di un package (modulo) esistente, tramite dialogo wizard");
        this.add(labelQuattro);
    }// end of method

    public void iniziaProject() {
        labelUno = new Label("Update di questo project");
        this.add(labelUno);
        labelDue = new Label("Creazione di un nuovo package (modulo), tramite dialogo wizard");
        this.add(labelDue);
        labelTre = new Label("Modifica di un package (modulo) esistente, tramite dialogo wizard");
        this.add(labelTre);
    }// end of method

    private Component creaMenuBase() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setMargin(false);
        layout.setSpacing(true);

        buttonUno = new Button("New project");
        buttonUno.addClickListener(event -> newProject.open(new TRecipient() {
            @Override
            public void gotInput(Map<Chiave, Object> mappaInput) {
                elaboraNewProject(mappaInput);
            }// end of inner method
        }));// end of lambda expressions and anonymous inner class
        layout.add(buttonUno);

        buttonDue = new Button("Update project");
        buttonDue.addClickListener(event -> updateProject.open(new TRecipient() {
            @Override
            public void gotInput(Map<Chiave, Object> mappaInput) {
                elaboraUpdateProject(mappaInput);
            }// end of inner method
        }));// end of lambda expressions and anonymous inner class
        layout.add(buttonDue);

        buttonTre = new Button("Package");
        buttonTre.addClickListener(event -> dialogPackage.open(new TRecipient() {
            @Override
            public void gotInput(Map<Chiave, Object> mappaInput) {
                elaboraPackage(mappaInput);
            }// end of inner method
        }, false, PROGETTO_STANDARD_SUGGERITO, NOME_PACKAGE_STANDARD_SUGGERITO));// end of lambda expressions and anonymous inner class
        layout.add(buttonTre);

        return layout;
    }// end of method


    private Component creaMenuProject() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setMargin(false);
        layout.setSpacing(true);

        String currentProject = System.getProperty("user.dir");
        currentProject = currentProject.substring(currentProject.lastIndexOf("/")+1);
        mappaInput.put(Chiave.newProjectName, currentProject);

        buttonUno = new Button("Update project");
        buttonUno.addClickListener(event -> elaboraUpdateProject(mappaInput));
        layout.add(buttonUno);

        buttonDue = new Button("Package");
        buttonDue.addClickListener(event -> dialogPackage.open(new TRecipient() {
            @Override
            public void gotInput(Map<Chiave, Object> mappaInput) {
                elaboraPackage(mappaInput);
            }// end of inner method
        }, false, PROGETTO_STANDARD_SUGGERITO, NOME_PACKAGE_STANDARD_SUGGERITO));// end of lambda expressions and anonymous inner class
        layout.add(buttonDue);

        return layout;
    }// end of method


    private void elaboraNewProject(Map<Chiave, Object> mappaInput) {
        newProject.close();

        if (mappaInput != null) {
            elabora.newProject(mappaInput);
        }// end of if cycle

    }// end of method


    private void elaboraUpdateProject(Map<Chiave, Object> mappaInput) {
        updateProject.close();

        if (mappaInput != null) {
            elabora.updateProject(mappaInput);
        }// end of if cycle

    }// end of method


    private void elaboraPackage(Map<Chiave, Object> mappaInput) {
        dialogPackage.close();

        if (mappaInput != null) {
            elabora.newPackage(mappaInput);
        }// end of if cycle

    }// end of method

}// end of class
