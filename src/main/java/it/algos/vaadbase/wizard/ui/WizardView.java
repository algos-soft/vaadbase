package it.algos.vaadbase.wizard.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import it.algos.vaadbase.application.BaseCost;
import it.algos.vaadbase.service.ATextService;
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

import javax.annotation.PostConstruct;
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
public class WizardView extends VerticalLayout {


    /**
     * Libreria di servizio. Inietta da Spring nel costruttore come 'singleton'
     */
    public ATextService text;

    public final static String NORMAL_WIDTH = "9em";
    public final static String NORMAL_HEIGHT = "3em";

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

    private static Progetto PROGETTO_STANDARD_SUGGERITO = Progetto.vaadin;
    private static String NOME_PACKAGE_STANDARD_SUGGERITO = "prova";


    private static String LABEL_A = "Creazione di un nuovo project";
    private static String LABEL_B = "Update di un project esistente";
    private static String LABEL_C = "Creazione di un nuovo package";
    private static String LABEL_D = "Modifica di un package esistente";

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


    @PostConstruct
    public void inizia() {
        this.setMargin(true);
        this.setSpacing(true);

        this.add(creaMenu());

        labelUno = new Label("Creazione di un nuovo project, tramite dialogo wizard");
        this.add(labelUno);
        labelDue = new Label("Aggiornamento di un project esistente, tramite dialogo wizard");
        this.add(labelDue);
        labelTre = new Label("Creazione di un nuovo package (modulo), tramite dialogo wizard");
        this.add(labelTre);
        labelQuattro = new Label("Modifica di un package (modulo) esistente, tramite dialogo wizard");
        this.add(labelQuattro);

    }// end of method

    private Component creaMenu() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        buttonUno = new Button("Crea project", event -> Notification.show("Non ancora funzionante",3000, Notification.Position.MIDDLE));
        layout.add(buttonUno);

        buttonDue = new Button("Update project", event -> Notification.show("Non ancora funzionante",3000, Notification.Position.MIDDLE));
        layout.add(buttonDue);

        buttonTre = new Button("Crea package");
        buttonTre.addClickListener(event -> dialog.open(new TRecipient() {
            @Override
            public void gotInput(Map<Chiave, Object> mappaInput) {
                elabora(mappaInput);
            }// end of inner method
        }, true));// end of lambda expressions and anonymous inner class
        layout.add(buttonTre);

        buttonQuattro = new Button("Update package");
        buttonQuattro.addClickListener(event -> dialog.open(new TRecipient() {
            @Override
            public void gotInput(Map<Chiave, Object> mappaInput) {
                elabora(mappaInput);
            }// end of inner method
        }, false));// end of lambda expressions and anonymous inner class
        layout.add(buttonQuattro);

        return layout;
    }// end of method


    private void elabora(Map<Chiave, Object> mappaInput) {
        dialog.close();

        if (mappaInput != null) {
            elabora.newPackage(mappaInput);
        }// end of if cycle

    }// end of method


//    private void elabora(Map<Chiave, Object> mappaInput) {
//        elabora.newPackage(mappaInput);
//    }// end of method

//    public void creaDialogo() {
//        VerticalLayout layoutRoot = new VerticalLayout();
//        layoutRoot.setMargin(true);
//        layoutRoot.setSpacing(true);
//        layoutRoot.setWidth("22em");
//        layoutRoot.setHeight("34em");
//
//        layoutRoot.add(dialog);
//        this.add(layoutRoot);
//
//        dialog.setCloseOnEsc(false);
//        dialog.setCloseOnOutsideClick(true);
//
//        dialog.add(new Label());
//        dialog.add(creaLabel());
//        dialog.add(creaBody());
//        dialog.add(creaFooter());
//    }// end of method
//
//
//    public Component creaLabel() {
//        VerticalLayout layoutLabel = new VerticalLayout();
//        layoutLabel.setMargin(false);
//        layoutLabel.setSpacing(false);
//
//        layoutLabel.add(new Label(CAPTION_A));
//
//        return layoutLabel;
//    }// end of method
//
//
//    public Component creaBody() {
//        VerticalLayout layoutBody = new VerticalLayout();
//        layoutBody.setMargin(false);
//        layoutBody.setSpacing(true);
//        VerticalLayout bodyLayout = new VerticalLayout();
//        bodyLayout.setMargin(false);
//        bodyLayout.setSpacing(true);
//
//        bodyLayout.add(new Label());
//        bodyLayout.add(creaCombo());
//        bodyLayout.add(creaPackage(""));
//        bodyLayout.add(creaEntity());
//        bodyLayout.add(creaTag());
//        bodyLayout.add(creaCompany());
//        bodyLayout.add(creaSovrascrive());
//
//        sincronizza();
//
//        layoutBody.add(bodyLayout);
////        layoutBody.setExpandRatio(bodyLayout, 1);
//        return layoutBody;
//    }// end of method
//
//
//    public Component creaCombo() {
//        fieldComboProgetti = new ComboBox();
//        Progetto[] progetti = Progetto.values();
//        String label = "Progetto";
//
//        fieldComboProgetti.setLabel(label);
//        fieldComboProgetti.setItems(progetti);
//        fieldComboProgetti.setValue(PROGETTO_STANDARD_SUGGERITO);
//
//        return fieldComboProgetti;
//    }// end of method
//
//
//    public Component creaPackage(String promptPackage) {
//        fieldTextPackage = new TextField();
//        String label = "Package";
//
//        fieldTextPackage.setLabel(label);
//        fieldTextPackage.setValue(promptPackage.equals("") ? NOME_PACKAGE_STANDARD_SUGGERITO : promptPackage);
//
//        // Handle changes in the value
//        fieldTextPackage.addValueChangeListener(event -> sincronizza());
//
//        return fieldTextPackage;
//    }// end of method
//
//
//    public Component creaEntity() {
//        fieldTextEntity = new TextField();
//        fieldTextEntity.setLabel("Entity");
//        return fieldTextEntity;
//    }// end of method
//
//
//    public Component creaTag() {
//        fieldTextTag = new TextField();
//        fieldTextTag.setLabel("Tag");
//        return fieldTextTag;
//    }// end of method
//
//
//    public Component creaCompany() {
//        fieldCheckBoxCompany = new Checkbox();
//        fieldCheckBoxCompany.setLabel("Utilizza MultiCompany");
//        return fieldCheckBoxCompany;
//    }// end of method
//
//
//    public Component creaSovrascrive() {
//        fieldCheckBoxSovrascrive = new Checkbox();
//        fieldCheckBoxSovrascrive.setLabel("Sovrascrive tutti i files esistenti del package");
//        return fieldCheckBoxSovrascrive;
//    }// end of method
//
//
//    public Component creaFooter() {
//        VerticalLayout layout = new VerticalLayout();
//        HorizontalLayout layoutFooter = new HorizontalLayout();
//        layoutFooter.setSpacing(true);
//        layoutFooter.setMargin(false);
//
//        cancelButton = new NativeButton("Annulla", event -> {
//            dialog.close();
//        });//end of lambda expressions
//        cancelButton.setWidth(NORMAL_WIDTH);
//        cancelButton.setHeight(NORMAL_HEIGHT);
//
//        confirmButton = new NativeButton("Conferma", event -> {
//            setMappa();
//            recipient.gotInput(mappaInput);
//            dialog.close();
//        });//end of lambda expressions
//        confirmButton.setWidth(NORMAL_WIDTH);
//        confirmButton.setHeight(NORMAL_HEIGHT);
//
//        layoutFooter.add(cancelButton, confirmButton);
//
//
////        cancelButton.addClickListener(new Button.ClickListener() {
////            public void buttonClick(Button.ClickEvent event) {
////                winDialog.close();
////            }// end of inner method
////        });// end of anonymous inner class
////
//
//
//        layout.add(layoutFooter);
//        return layout;
//    }// end of method
//
//    public void sincronizza() {
//        String valueFromPackage = fieldTextPackage.getValue();
//        String valueForEntity = text.primaMaiuscola(valueFromPackage);
//        String valueForTag = "";
//
//        if (valueFromPackage.length() < 3) {
//            valueForTag = valueFromPackage;
//        } else {
//            valueForTag = valueFromPackage.substring(0, 3);
//        }// end of if/else cycle
//        valueForTag = valueForTag.toUpperCase();
//
//        fieldTextEntity.setValue(valueForEntity);
//        fieldTextTag.setValue(valueForTag);
//
//        setMappa();
//    }// end of method
//
//
//    public void setMappa() {
//        if (mappaInput != null) {
//            mappaInput.put(Chiave.nameProject, fieldComboProgetti.getValue());
//            mappaInput.put(Chiave.namePackageLower, fieldTextPackage.getValue().toLowerCase());
//            mappaInput.put(Chiave.nameEntityFirstUpper, text.primaMaiuscola(fieldTextEntity.getValue()));
//            mappaInput.put(Chiave.tagBreveTreChar, fieldTextTag.getValue());
//            mappaInput.put(Chiave.usaCompany, fieldCheckBoxCompany.getValue());
//            mappaInput.put(Chiave.usaSovrascrive, fieldCheckBoxSovrascrive.getValue());
//        }// end of if cycle
//    }// end of method

}// end of class
