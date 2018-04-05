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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public final static int DURATA = 2000;
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


    public void open(TRecipient recipient, boolean newPackage, Progetto progetto, String packageName) {
        this.recipient = recipient;
        this.newPackage = newPackage;
        this.project = progetto != null ? progetto : PROGETTO_STANDARD_SUGGERITO;
        this.packageName = text.isValid(packageName) ? packageName : "";
        this.removeAll();
        super.open();

        this.add(new Label());
        this.add(creaRadio());
        this.add(creaBody());
        this.add(creaFooter());

        sincroRadio(groupTitolo.getValue());
        addListener();
        sincroPackageNew(packageName);
    }// end of method

    private void addListener() {
        groupTitolo.addValueChangeListener(event -> sincroRadio(event.getValue()));//end of lambda expressions
        fieldComboProgetti.addValueChangeListener(event -> sincroProject(event.getValue()));//end of lambda expressions
        fieldTextPackage.addValueChangeListener(event -> sincroPackage(event.getValue()));//end of lambda expressions
        fieldComboPackage.addValueChangeListener(event -> sincroPackage(event.getValue()));//end of lambda expressions
    }// end of method


    private Component creaRadio() {
        VerticalLayout layoutLabel = new VerticalLayout();
        layoutLabel.setMargin(true);
        layoutLabel.setSpacing(true);

        groupTitolo = new RadioButtonGroup<>();
        groupTitolo.setItems(RADIO_NEW, RADIO_UPDATE);
        groupTitolo.getElement().getStyle().set("display", "flex");
        groupTitolo.getElement().getStyle().set("flexDirection", "column");

        if (newPackage) {
            groupTitolo.setValue(RADIO_NEW);
        } else {
            groupTitolo.setValue(RADIO_UPDATE);
        }// end of if/else cycle

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

        if (Arrays.asList(progetti).contains(project)) {
            fieldComboProgetti.setValue(project);
        }// end of if cycle

        return fieldComboProgetti;
    }// end of method


    private Component creaPackage() {
        packagePlaceHolder = new HorizontalLayout();
        String label = "Package";

        fieldComboPackage = new ComboBox<>();
        fieldComboPackage.setAllowCustomValue(true);
        fieldComboPackage.setLabel(label);

        fieldTextPackage = new TextField();
        fieldTextPackage.setLabel(label);
        fieldTextPackage.setEnabled(true);

        return packagePlaceHolder;
    }// end of method


    private Component creaEntity() {
        fieldTextEntity = new TextField();
        fieldTextEntity.setLabel("Entity");

        fieldTextEntity.setEnabled(true);
        return fieldTextEntity;
    }// end of method


    private Component creaTag() {
        fieldTextTag = new TextField();
        fieldTextTag.setLabel("Tag");

        fieldTextTag.setEnabled(true);
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
                Notification.show("Stai tentando di creare una EntityClass SENZA la property 'code'. Non è possibile.", DURATA, Notification.Position.MIDDLE);
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
            packageName = "";
        }// end of if cycle

        if (radioSelected.equals(RADIO_UPDATE)) {
            newPackage = false;
        }// end of if cycle

        sincroProject(fieldComboProgetti.getValue());
    }// end of method


    private void sincroProject(Progetto progettoSelezionato) {
        String namePackage;
        packagePlaceHolder.removeAll();

        if (newPackage) {
            packagePlaceHolder.add(fieldTextPackage);
            fieldTextPackage.setValue("");
            sincroPackage(packageName);
        } else {
            packagePlaceHolder.add(fieldComboPackage);
            if (progettoSelezionato == null) {
                invalida(true);
                fieldComboPackage.setValue(null);
            } else {
                packages = recuperaPackageEsistenti(progettoSelezionato.getNameProject());
                if (packages != null && fieldComboPackage != null) {
                    fieldComboPackage.setItems(packages);
                }// end of if cycle

                namePackage = packageName;
                if (text.isValid(namePackage)) {
                    if (packages.contains(namePackage)) {
                        fieldComboPackage.setValue(namePackage);
                    }// end of if cycle
                }// end of if cycle
            }// end of if/else cycle
            sincroPackage(fieldComboPackage.getValue() != null ? fieldComboPackage.getValue() : "");
        }// end of if/else cycle
    }// end of method


    private void sincroPackage(String valueFromPackage) {
        if (text.isValid(valueFromPackage) && valueFromPackage.length() > 2) {

            if (newPackage && isPackageEsistente()) {
                Notification.show("Esiste già questo package", DURATA, Notification.Position.MIDDLE);
                packageName = fieldTextPackage.getValue();
                groupTitolo.setValue(RADIO_UPDATE);
            } else {
                valueFromPackage = valueFromPackage.toLowerCase();
                fieldTextPackage.setValue(valueFromPackage);
                fieldTextEntity.setValue(text.primaMaiuscola(fieldTextEntity.getValue()));

                invalida(false);
                if (confirmButton != null) {
                    confirmButton.setVisible(true);
                }// end of if cycle

                fieldTextEntity.setValue(text.primaMaiuscola(valueFromPackage));
                fieldTextTag.setValue(valueFromPackage.substring(0, 3).toUpperCase());
            }// end of if/else cycle
        } else {
            invalida(true);
            Notification.show("Il nome del package deve essere di almeno 3 caratteri", DURATA, Notification.Position.MIDDLE);
            if (confirmButton != null) {
                confirmButton.setVisible(false);
            }// end of if cycle

            fieldTextEntity.setValue("");
            fieldTextTag.setValue("");
        }// end of if/else cycle


////        setMappa();
    }// end of method

    private void sincroPackageNew(String packageName) {
        fieldTextPackage.setValue(packageName);
        this.packageName = packageName;
    }// end of method

    private void invalida(boolean status) {
        if (fieldComboPackage != null) {
            fieldComboPackage.setInvalid(status);
        }// end of if cycle
        if (fieldTextPackage != null) {
            fieldTextPackage.setInvalid(status);
        }// end of if cycle
        if (fieldTextEntity != null) {
            fieldTextEntity.setInvalid(status);
        }// end of if cycle
        if (fieldTextTag != null) {
            fieldTextTag.setInvalid(status);
        }// end of if cycle
    }// end of method

    private boolean isPackageEsistente() {
        boolean esiste = false;
        String pathModules = getPathModules();
        List<String> packagesEsistenti = file.getSubdiretories(pathModules);

        if (packagesEsistenti.contains(getPackage())) {
            esiste = true;
        }// end of if cycle

        return esiste;
    }// end of method

    private boolean isEntityEsistente() {
        String java = ".java";
        String entity = text.primaMaiuscola(fieldTextEntity.getValue()) + java;

        return checkBase(entity);
    }// end of method

    private boolean isTagEsistente() {
        boolean esiste = false;

        if (fieldTextTag.getValue().equals("ROL")) {
            esiste = true;
        } else {
            esiste = false;
        }// end of if/else cycle

        return esiste;
    }// end of method


    private String getPathModules() {
        String path = "";
        String sep = "/";
        String userDir = System.getProperty("user.dir");
        Progetto project = fieldComboProgetti.getValue();
        String projectName = "";

        if (project != null) {
            projectName = project.getNameProject();
        }// end of if cycle

        String ideaProjectRootPath = text.levaCodaDa(userDir, sep);
        String projectBasePath = ideaProjectRootPath + sep + projectName;

        path += projectBasePath;
        path += DIR_JAVA;
        path += sep;
        path += projectName;
        path += sep;
        path += ENTITIES_NAME;

        return path;
    }// end of method

    private boolean checkBase(String name) {
        boolean esiste = false;
        String path = "";
        String sep = "/";
        String pack = getPackage();
        String pathModules = getPathModules();

        path += pathModules;
        path += pack;
        path += sep;
        path += name;

        if (path.endsWith(sep)) {
            path = text.levaCodaDa(path, sep);
            esiste = file.isEsisteDirectory(path);
        } else {
            esiste = file.isEsisteFile(path);
        }// end of if/else cycle

        return esiste;
    }// end of method

    private String getPackage() {
        String pack = "";

        if (newPackage) {
            pack = fieldTextPackage.getValue();
        } else {
            pack = fieldComboPackage.getValue() != null ? fieldComboPackage.getValue().toLowerCase() : "";
        }// end of if/else cycle

        return pack;
    }// end of method


    private void setMappa() {
        if (mappaInput != null) {
            mappaInput.put(Chiave.targetProjectName, fieldComboProgetti.getValue());
            if (fieldComboPackage != null) {
                mappaInput.put(Chiave.newPackageName, fieldComboPackage.getValue() != null ? fieldComboPackage.getValue().toLowerCase() : "");
            }// end of if cycle
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
        return file.getSubdiretories(getPathModules());
    }// end of method

}// end of class
