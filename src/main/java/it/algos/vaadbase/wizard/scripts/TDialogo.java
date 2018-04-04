package it.algos.vaadbase.wizard.scripts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.service.AFileService;
import it.algos.vaadbase.service.ATextService;
import it.algos.vaadbase.wizard.enumeration.Chiave;
import it.algos.vaadbase.wizard.enumeration.Progetto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Project vbase
 * Created by Algos
 * User: gac
 * Date: mar, 20-mar-2018
 * Time: 16:09
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TDialogo extends Dialog {


    public final static String NORMAL_WIDTH = "9em";
    public final static String NORMAL_HEIGHT = "3em";
    public final static boolean DEFAULT_USA_ORDINE = true;
    public final static boolean DEFAULT_USA_CODE = true;
    public final static boolean DEFAULT_USA_DESCRIZIONE = false;
    public final static boolean DEFAULT_USA_KEY_CODE_SPECIFICA = false;
    public final static boolean DEFAULT_USA_COMPANY = false;
    public final static boolean DEFAULT_USA_SOVRASCRIVE = false;
    private static final String DIR_MAIN = "/src/main";
    private static final String DIR_JAVA = DIR_MAIN + "/java/it/algos";
    private static final String ENTITIES_NAME = "modules";
    private static Progetto PROGETTO_STANDARD_SUGGERITO = Progetto.vaadin;
    private static String NOME_PACKAGE_STANDARD_SUGGERITO = "role";
    private static String CAPTION = "Package";
    private static String RADIO_NEW = "Creazione di un nuovo package";
    private static String RADIO_UPDATE = "Modifica di un package esistente";

    /**
     * Service iniettato da Spring (@Scope = 'singleton'). Unica per tutta l'applicazione. Usata come libreria.
     */
    @Autowired
    public ATextService text;

    /**
     * Service iniettato da Spring (@Scope = 'singleton'). Unica per tutta l'applicazione. Usata come libreria.
     */
    @Autowired
    public AFileService file;

    private Button buttonUno;
    private NativeButton confirmButton;
    private NativeButton cancelButton;
    private Dialog dialog = new Dialog();
    private boolean newPackage;
    private TRecipient recipient;
    private Progetto project;
    private String packageName;
    private List<String> packages;

    private RadioButtonGroup<String> groupTitolo;
    private ComboBox<Progetto> fieldComboProgetti;
    private HorizontalLayout packagePlaceHolder;
    private ComboBox<String> fieldComboPackage;
    private TextField fieldTextPackage;
    private TextField fieldTextEntity; // suggerito
    private TextField fieldTextTag; // suggerito
    private Checkbox fieldCheckBoxPropertyOrdine;
    private Checkbox fieldCheckBoxPropertyCode;
    private Checkbox fieldCheckBoxPropertyDescrizione;
    private Checkbox fieldCheckBoxUsaKeyIdCode;
    private Checkbox fieldCheckBoxCompany;
    private Checkbox fieldCheckBoxSovrascrive;

    private Map<Chiave, Object> mappaInput = new HashMap<>();

    @Autowired
    public TDialogo(ATextService text) {
        this.text = text;
    }// end of Spring constructor


    @PostConstruct
    public void creaDialogo() {
        this.setCloseOnEsc(false);
        this.setCloseOnOutsideClick(false);
    }// end of method


    public void open(TRecipient recipient, Progetto progetto, String packageName) {
        this.recipient = recipient;
        this.project = progetto != null ? progetto : PROGETTO_STANDARD_SUGGERITO;
        this.packageName = text.isValid(packageName) ? packageName : "";
        this.removeAll();
        super.open();

        this.newPackage = true;

        this.add(new Label());
        this.add(creaRadio());
        this.add(creaBody());
        this.add(creaFooter());

        addListener();

        sincroProject();
    }// end of method

    private void addListener() {
        groupTitolo.addValueChangeListener(event -> sincroRadio(event.getValue()));
        fieldComboProgetti.addValueChangeListener(event -> sincroProject());
        fieldComboPackage.addValueChangeListener(event -> sincroPackage());
        fieldTextPackage.addValueChangeListener(event -> sincroPackage());
    }// end of method


    private Component creaRadio() {
        VerticalLayout layoutLabel = new VerticalLayout();
        layoutLabel.setMargin(true);
        layoutLabel.setSpacing(true);

        groupTitolo = new RadioButtonGroup<>();
        groupTitolo.setItems(RADIO_NEW, RADIO_UPDATE);
        groupTitolo.getElement().getStyle().set("display", "flex");
        groupTitolo.getElement().getStyle().set("flexDirection", "column");
        groupTitolo.setValue(RADIO_NEW);

        layoutLabel.add(groupTitolo);
        return layoutLabel;
    }// end of method


    private Component creaBody() {
        VerticalLayout layoutText = new VerticalLayout();
        layoutText.setMargin(true);
        layoutText.setSpacing(false);
        VerticalLayout layoutCheck = new VerticalLayout();
        layoutCheck.setMargin(true);
        layoutCheck.setSpacing(false);

        layoutText.add(creaProject());
        layoutText.add(creaPackage());
        layoutText.add(creaEntity());
        layoutText.add(creaTag());

        layoutCheck.add(creaOrdine());
        layoutCheck.add(creaCode());
        layoutCheck.add(creaDescrizione());
        layoutCheck.add(creaKeyIdCode());
        layoutCheck.add(creaCompany());
        layoutCheck.add(creaSovrascrive());

        return new VerticalLayout(layoutText, layoutCheck);
    }// end of method


    private Component creaProject() {
        fieldComboProgetti = new ComboBox<>();
        fieldComboProgetti.setAllowCustomValue(false);
        String label = "Progetto";
        Progetto[] progetti = Progetto.values();

        fieldComboProgetti.setLabel(label);
        fieldComboProgetti.setItems(Arrays.asList(progetti));
        fieldComboProgetti.setValue(project);

        return fieldComboProgetti;
    }// end of method


    private Component creaPackage() {
        packagePlaceHolder = new HorizontalLayout();

        fieldComboPackage = new ComboBox<>();
        fieldComboPackage.setAllowCustomValue(true);
        String label = "Package";

        fieldComboPackage.setLabel(label);

        fieldTextPackage = new TextField();
        fieldTextPackage.setLabel(label);
        fieldTextPackage.setEnabled(true);

        if (newPackage) {
            fieldTextPackage.focus();
            packagePlaceHolder.add(fieldTextPackage);
        } else {
            fieldComboPackage.focus();
            packagePlaceHolder.add(fieldComboPackage);
        }// end of if/else cycle

        return packagePlaceHolder;
    }// end of method


    private Component creaEntity() {
        fieldTextEntity = new TextField();
        fieldTextEntity.setLabel("Entity");

        fieldTextEntity.setEnabled(true);

        fieldTextEntity.addValueChangeListener(event -> {
            if (text.isValid(event.getValue()) && event.getValue().length() > 2) {
                fieldTextEntity.setInvalid(false);
            } else {
                fieldTextEntity.setInvalid(true);
            }// end of if/else cycle
        });//end of lambda expressions

        return fieldTextEntity;
    }// end of method


    private Component creaTag() {
        fieldTextTag = new TextField();
        fieldTextTag.setLabel("Tag");

        fieldTextTag.setEnabled(true);
        fieldTextTag.addValueChangeListener(event -> {
            if (text.isValid(event.getValue()) && event.getValue().length() > 2) {
                fieldTextTag.setInvalid(false);
            } else {
                fieldTextTag.setInvalid(true);
            }// end of if/else cycle
        });//end of lambda expressions

        return fieldTextTag;
    }// end of method


    private Component creaOrdine() {
        fieldCheckBoxPropertyOrdine = new Checkbox();
        fieldCheckBoxPropertyOrdine.setLabel("Usa la property Ordine (int)");
        fieldCheckBoxPropertyOrdine.setValue(DEFAULT_USA_ORDINE);
        return fieldCheckBoxPropertyOrdine;
    }// end of method

    private Component creaCode() {
        fieldCheckBoxPropertyCode = new Checkbox();
        fieldCheckBoxPropertyCode.setLabel("Usa la property Code (String)");
        fieldCheckBoxPropertyCode.setValue(DEFAULT_USA_CODE);
        return fieldCheckBoxPropertyCode;
    }// end of method

    private Component creaDescrizione() {
        fieldCheckBoxPropertyDescrizione = new Checkbox();
        fieldCheckBoxPropertyDescrizione.setLabel("Usa la property Descrizione (String)");
        fieldCheckBoxPropertyDescrizione.setValue(DEFAULT_USA_DESCRIZIONE);
        return fieldCheckBoxPropertyDescrizione;
    }// end of method

    private Component creaKeyIdCode() {
        fieldCheckBoxUsaKeyIdCode = new Checkbox();
        fieldCheckBoxUsaKeyIdCode.setLabel("Usa la property Code (String) come keyID");
        fieldCheckBoxUsaKeyIdCode.setValue(DEFAULT_USA_KEY_CODE_SPECIFICA);
        return fieldCheckBoxUsaKeyIdCode;
    }// end of method

    private Component creaCompany() {
        fieldCheckBoxCompany = new Checkbox();
        fieldCheckBoxCompany.setLabel("Utilizza MultiCompany");
        fieldCheckBoxCompany.setValue(DEFAULT_USA_COMPANY);
        return fieldCheckBoxCompany;
    }// end of method


    private Component creaSovrascrive() {
        fieldCheckBoxSovrascrive = new Checkbox();
        fieldCheckBoxSovrascrive.setLabel("Sovrascrive tutti i files esistenti del package");
        fieldCheckBoxSovrascrive.setValue(DEFAULT_USA_SOVRASCRIVE);
        return fieldCheckBoxSovrascrive;
    }// end of method


    private Component creaFooter() {
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout layoutFooter = new HorizontalLayout();
        layoutFooter.setSpacing(true);
        layoutFooter.setMargin(true);

        cancelButton = new NativeButton("Annulla", event -> {
            recipient.gotInput(null);
            dialog.close();
        });//end of lambda expressions
        cancelButton.setWidth(NORMAL_WIDTH);
        cancelButton.setHeight(NORMAL_HEIGHT);

        confirmButton = new NativeButton("Conferma", event -> {
            if (fieldCheckBoxPropertyCode.getValue()) {
                chiudeDialogo();
            } else {
//                Notification.show("Stai creando una EntityClass SENZA la property 'code'. È possibile, ma alcune linee di codice andranno riscritte.",2000,Notification.Position.MIDDLE);
                Notification.show("Stai tentando di creare una EntityClass SENZA la property 'code'. Non è possibile.", 3000, Notification.Position.MIDDLE);
            }// end of if/else cycle
        });//end of lambda expressions
        confirmButton.setWidth(NORMAL_WIDTH);
        confirmButton.setHeight(NORMAL_HEIGHT);

        layoutFooter.add(cancelButton, confirmButton);
        layout.add(layoutFooter);
        return layout;
    }// end of method


    private void chiudeDialogo() {
        setMappa();
        recipient.gotInput(mappaInput);
        dialog.close();
    }// end of method


    private void sincroRadio(String radioSelected) {

        if (radioSelected.equals(RADIO_NEW)) {
            newPackage = true;
        }// end of if cycle

        if (radioSelected.equals(RADIO_UPDATE)) {
            newPackage = false;
        }// end of if cycle

        sincroPackage();
    }// end of method

    private void sincroProject() {
        Progetto progetto = fieldComboProgetti.getValue();

        if (progetto == null) {
            invalida(true);
            fieldComboPackage.setValue(null);
        } else {
            packages = recuperaPackageEsistenti(progetto.getNameProject());
            fieldComboPackage.setItems(packages);
        }// end of if/else cycle

        sincroPackage();
    }// end of method

    private void sincroPackage() {
        String valueFromPackage = "";
        String valueForEntity = "";
        String valueForTag = "";

        if (newPackage) {
            valueFromPackage = fieldTextPackage.getValue();
        } else {
            valueFromPackage = fieldComboPackage.getValue() != null ? fieldComboPackage.getValue() : "";
        }// end of if/else cycle

        if (text.isValid(valueFromPackage) && valueFromPackage.length() > 2) {
            invalida(false);
            valueForTag = valueFromPackage.substring(0, 3).toUpperCase();
            if (confirmButton != null) {
                confirmButton.setVisible(true);
            }// end of if cycle
        } else {
            invalida(true);
            valueForTag = valueFromPackage;
            if (confirmButton != null) {
                confirmButton.setVisible(false);
            }// end of if cycle
        }// end of if/else cycle

        valueForEntity = text.primaMaiuscola(valueFromPackage);
        fieldTextEntity.setValue(valueForEntity);
        fieldTextTag.setValue(valueForTag);

        if (isPackagingEsistente()) {
//            labelUno.setText(CAPTION_B);
        } else {
//            labelUno.setText(CAPTION_A);
        }// end of if/else cycle

        setMappa();
    }// end of method

    private void sincroPackageNew() {
        String valueFromPackage = fieldComboPackage.getValue() != null ? fieldComboPackage.getValue() : "";
        String valueForTag = "";
    }// end of method

    private void invalida(boolean status) {
        fieldComboPackage.setInvalid(status);
        fieldTextEntity.setInvalid(status);
        fieldTextTag.setInvalid(status);
    }// end of method

    private boolean checkPackage() {
        boolean esiste = false;

        return esiste;
    }// end of method

    private boolean checkEntity() {
        boolean esiste = false;

        return esiste;
    }// end of method

    private boolean checkTag() {
        boolean esiste = false;

        return esiste;
    }// end of method

    private boolean checkBase() {
        boolean esiste = false;

        String pathFile = "";
        String sep = "/";
        String java = ".java";
        String userDir = System.getProperty("user.dir");
        Progetto project = fieldComboProgetti.getValue();
        String projectName = "";
        String pack = fieldComboPackage.getValue() != null ? fieldComboPackage.getValue().toLowerCase() : "";
        String entity = text.primaMaiuscola(fieldTextEntity.getValue());

        if (project != null) {
            projectName = project.getNameProject();
        }// end of if cycle

        String ideaProjectRootPath = text.levaCodaDa(userDir, sep);
        String projectBasePath = ideaProjectRootPath + sep + projectName;

        pathFile += projectBasePath;
        pathFile += DIR_JAVA;
        pathFile += sep;
        pathFile += projectName;
        pathFile += sep;
        pathFile += ENTITIES_NAME;
        pathFile += sep;
        pathFile += pack;
        pathFile += sep;
        pathFile += entity;
        pathFile += java;

        esiste = file.isEsisteFile(pathFile);
        return esiste;
    }// end of method

    private boolean isPackagingEsistente() {
        String pathFile = "";
        String sep = "/";
        String java = ".java";
        String userDir = System.getProperty("user.dir");
        Progetto project = fieldComboProgetti.getValue();
        String projectName = "";
        String pack = fieldComboPackage.getValue() != null ? fieldComboPackage.getValue().toLowerCase() : "";
        String entity = text.primaMaiuscola(fieldTextEntity.getValue());

        if (project != null) {
            projectName = project.getNameProject();
        }// end of if cycle

        String ideaProjectRootPath = text.levaCodaDa(userDir, sep);
        String projectBasePath = ideaProjectRootPath + sep + projectName;

        pathFile += projectBasePath;
        pathFile += DIR_JAVA;
        pathFile += sep;
        pathFile += projectName;
        pathFile += sep;
        pathFile += ENTITIES_NAME;
        pathFile += sep;
        pathFile += pack;
        pathFile += sep;
        pathFile += entity;
        pathFile += java;

        return file.isEsisteFile(pathFile);
    }// end of method

    private void setMappa() {
        if (mappaInput != null) {
            mappaInput.put(Chiave.targetProjectName, fieldComboProgetti.getValue());
            mappaInput.put(Chiave.newPackageName, fieldComboPackage.getValue() != null ? fieldComboPackage.getValue().toLowerCase() : "");
            mappaInput.put(Chiave.newEntityName, text.primaMaiuscola(fieldTextEntity.getValue()));
            mappaInput.put(Chiave.newEntityTag, fieldTextTag.getValue());
            mappaInput.put(Chiave.flagOrdine, fieldCheckBoxPropertyOrdine.getValue());
            mappaInput.put(Chiave.flagCode, fieldCheckBoxPropertyCode.getValue());
            mappaInput.put(Chiave.flagDescrizione, fieldCheckBoxPropertyDescrizione.getValue());
            mappaInput.put(Chiave.flagKeyCode, fieldCheckBoxUsaKeyIdCode.getValue());
            mappaInput.put(Chiave.flagCompany, fieldCheckBoxCompany.getValue());
            mappaInput.put(Chiave.flagSovrascrive, fieldCheckBoxSovrascrive.getValue());
        }// end of if cycle
    }// end of method

    private List<String> recuperaPackageEsistenti(String projectName) {
        List<String> lista = new ArrayList<>();

        if (projectName.equals("vaadbase")) {
            lista.add("address");
            lista.add("role");
        } else {
            lista.add("pippoz");
            lista.add("plutoz");
            lista.add("paperinoz");
        }// end of if/else cycle

        return lista;
    }// end of method

}// end of class
