package it.algos.vaadbase.ui.dialog;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.shared.Registration;
import it.algos.vaadbase.backend.service.IAService;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.ui.AFieldService;
import it.algos.vaadbase.ui.fields.ATextArea;
import it.algos.vaadbase.ui.fields.ATextField;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: sab, 05-mag-2018
 * Time: 19:10
 */
@Slf4j
public abstract class AViewDialog<T extends Serializable> extends Dialog implements IADialog {


    protected final Button saveButton = new Button("Registra");
    protected final Button cancelButton = new Button("Annulla");
    protected final Button deleteButton = new Button("Elimina");
    private final H2 titleField = new H2();
    private final String confirmText = "Conferma";
    private final FormLayout formLayout = new FormLayout();
    private final HorizontalLayout buttonBar = new HorizontalLayout(saveButton, cancelButton, deleteButton);
    private final ConfirmationDialog<T> confirmationDialog = new ConfirmationDialog<>();
    protected IAService service;
    protected IAPresenter presenter;
    //--collegamento tra i fields e la entityBean
    protected Binder<T> binder;
    protected Class binderClass;
    protected LinkedHashMap<String, AbstractField> fieldMap;
    protected AFieldService fieldService;
    private BiConsumer<T, AViewDialog.Operation> itemSaver;
    private Consumer<T> itemDeleter;
    private Consumer<T> itemAnnulla;
    private String itemType;
    private Registration registrationForSave;
    private T currentItem;

    /**
     * Constructs a new instance.
     *
     * @param presenter per gestire la business logic del package
     */
    public AViewDialog(IAPresenter presenter) {
        this(presenter, null, null);
    }// end of constructor

    /**
     * Constructs a new instance.
     *
     * @param presenter   per gestire la business logic del package
     * @param itemSaver   funzione associata al bottone 'registra'
     * @param itemDeleter funzione associata al bottone 'annulla'
     */
    public AViewDialog(IAPresenter presenter, BiConsumer<T, AViewDialog.Operation> itemSaver, Consumer<T> itemDeleter) {
        this(presenter, itemSaver, itemDeleter, null,false);
    }// end of constructor

    /**
     * Constructs a new instance.
     *
     * @param presenter               per gestire la business logic del package
     * @param itemSaver               funzione associata al bottone 'registra'
     * @param itemDeleter             funzione associata al bottone 'annulla'
     * @param itemAnnulla             funzione associata al bottone 'annulla'
     * @param confermaSenzaRegistrare cambia il testo del bottone 'Registra' in 'Conferma'
     */
    public AViewDialog(IAPresenter presenter, BiConsumer<T, AViewDialog.Operation> itemSaver, Consumer<T> itemDeleter, Consumer<T> itemAnnulla, boolean confermaSenzaRegistrare) {
        this.presenter = presenter;
        this.service = presenter.getService();
        this.itemSaver = itemSaver;
        this.itemDeleter = itemDeleter;
        this.itemAnnulla = itemAnnulla;
        this.binderClass = presenter.getEntityClazz();
        this.fieldService = presenter.getService().getFieldService();

        if (confermaSenzaRegistrare) {
            this.saveButton.setText(confirmText);
        }// end of if cycle

        initTitle();
        initFormLayout();
        initButtonBar();
        initFields();

        setCloseOnEsc(true);
        setCloseOnOutsideClick(false);
        addOpenedChangeListener(event -> {
            if (!isOpened()) {
                getElement().removeFromParent();
            }
        });
    }// end of constructor


    public void fixFunzioni(BiConsumer<T, AViewDialog.Operation> itemSaver, Consumer<T> itemDeleter) {
        this.itemSaver = itemSaver;
        this.itemDeleter = itemDeleter;
    }

    private void initTitle() {
        add(titleField);
    }

    private void initFormLayout() {
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("50em", 2));
        formLayout.addClassName("no-padding");
        Div div = new Div(formLayout);
        div.addClassName("has-padding");
        add(div);
    }

    private void initButtonBar() {
        saveButton.setAutofocus(true);
        saveButton.getElement().setAttribute("theme", "primary");
        cancelButton.addClickListener(e -> close());
        deleteButton.addClickListener(e -> deleteClicked());
        deleteButton.getElement().setAttribute("theme", "tertiary danger");
        buttonBar.setClassName("buttons");
        buttonBar.setSpacing(true);
        add(buttonBar);
    }

    /**
     * Prepara i fields
     * Costruisce una fieldList interna
     * Crea un nuovo binder per questo Dialog e questa Entity
     * Costruisce i componenti grafici AFields (di tipo AbstractField), in base ai reflectedFields ricevuti dal service
     * --e regola le varie properties grafiche (caption, visible, editable, width, ecc)
     * Aggiunge i componenti grafici AField al binder
     * Aggiunge i componenti grafici AField ad una fieldList interna,
     * --necessaria per ''recuperare'' un singolo algosField dal nome
     * Inizializza i componenti grafici AField
     * Aggiunge al binder eventuali fields specifici, prima di trascrivere la entityBean nel bind
     * Legge la entityBean, ed inserisce nel binder i valori nei fields grafici AFields
     * Aggiunge i componenti grafici AField al layout
     * Eventuali regolazioni specifiche per i fields, dopo la trascrizione della entityBean nel binder
     *
     * @param layout              in cui inserire i campi (window o panel)
     * @param reflectedJavaFields previsti nel modello dati della Entity più eventuali aggiunte della sottoclasse
     */
    protected void initFields() {
        AbstractField newField = null;

        if (service == null) {
            return;
        }// end of if cycle

        binder = new Binder(binderClass);
        fieldMap = new LinkedHashMap<>();

        //--Costruisce una lista di nomi delle properties
        List<String> properties = getFieldsList();

        //--Costruisce ogni singolo field
        //--Aggiunge il field al binder, nel metodo create() del fieldService
        //--Aggiunge il field una fieldList protected di questa classe (serve per recuperare il field dal nome)
        for (String fieldName : properties) {
            newField = fieldService.create(binder, binderClass, fieldName);
            if (newField != null) {
                fieldMap.put(fieldName, newField);
            }// end of if cycle
        }// end of for cycle

        //--Aggiunge al binder eventuali fields specifici, prima di trascrivere la entityBean nel binder
        //--rimanda ad un metodo separato per poterlo sovrascrivere
        //--Costruisce eventuali fields specifici, prima di trascrivere la entityBean nel binder
        //--Aggiunge il field al binder, nel metodo sovrascritto della sottoclasse specifica
        //--Aggiunge il field una fieldList protected di questa classe (serve per recuperare il field dal nome)
        addSpecificAlgosFields();

        //--Aggiunge ogni singolo field al layout grafico
        for (String name : fieldMap.keySet()) {
            getFormLayout().add(fieldMap.get(name));
        }// end of for cycle

    }// end of method


    /**
     * Costruisce una lista di nomi delle properties nell'ordine:
     * 1) Cerca nell'annotation @AIForm della Entity
     * 2) Utilizza tutte le properties della Entity (e delle sue superclassi)
     * 3) Sovrascrive la lista nel metodo getSpecificFieldsList() della sottoclasse specifica
     */
    protected List<String> getFieldsList() {
        List<String> properties = null;

        if (service != null) {
            properties = service.getFormPropertiesName();
        }// end of if cycle

        return getSpecificFieldsList(properties);
    }// end of method

    /**
     * Costruisce una lista di nomi delle properties nella sottoclasse specifica
     */
    protected List<String> getSpecificFieldsList(List<String> properties) {
        return properties;
    }// end of method


    /**
     * Aggiunge al binder eventuali fields specifici, prima di trascrivere la entityBean nel binder
     * Sovrascritto
     * Aggiunge il field al binder
     * Aggiunge il field alla fieldList interna
     */
    protected void addSpecificAlgosFields() {
    }// end of method


    /**
     * Opens the given item for editing in the dialog.
     *
     * @param item      The item to edit; it may be an existing or a newly created
     *                  instance
     * @param operation The operation being performed on the item
     */
    @Override
    public void open(Object item, AViewDialog.Operation operation) {
        open(item, operation, "");
    }// end of method

    /**
     * Opens the given item for editing in the dialog.
     *
     * @param item      The item to edit; it may be an existing or a newly created
     *                  instance
     * @param operation The operation being performed on the item
     */
    @Override
    public void open(Object item, AViewDialog.Operation operation, String title) {
        if (item == null) {
            Notification.show("Qualcosa non ha funzionato in AViewDialog.open()", 3000, Notification.Position.BOTTOM_START);
            return;
        }// end of if cycle

        currentItem = (T) item;
        this.itemType = presenter.getView().getName();
        title = title.equals("") ? itemType : title;
        titleField.setText(operation.getNameInTitle() + " " + title);

        if (registrationForSave != null) {
            registrationForSave.remove();
        }
        registrationForSave = saveButton.addClickListener(e -> saveClicked(operation));

        binder.readBean(currentItem);
        readSpecificFields();

        deleteButton.setEnabled(operation.isDeleteEnabled());
        open();

    }// end of method


    /**
     * Regola in lettura eventuali valori NON associati al binder
     * Sovrascritto
     */
    protected void readSpecificFields() {
    }// end of method


    protected void focusOnPost(String currentFieldName) {
        List<String> keys = new ArrayList<>(fieldMap.keySet());
        String nameFocus = "";
        String nameTmp;
        int pos = 0;

        for (int k = 0; k < keys.size(); k++) {
            nameTmp = keys.get(k);
            if (nameTmp.equals(currentFieldName)) {
                pos = keys.indexOf(nameTmp);
                pos++;
                pos = pos < keys.size() ? pos : 0;
                nameFocus = keys.get(pos);
            }// end of if cycle
        }// end of for cycle

        AbstractField field = getField(nameFocus);
        if (field instanceof ATextField) {
            ((ATextField) getField(nameFocus)).focus();
        }// end of if cycle
        if (field instanceof ATextArea) {
            ((ATextArea) getField(nameFocus)).focus();
        }// end of if cycle
    }// end of method


    private void saveClicked(AViewDialog.Operation operation) {
        boolean isValid = binder.writeBeanIfValid(currentItem);

        if (isValid) {
            itemSaver.accept(currentItem, operation);
            writeSpecificFields();
            close();
        } else {
            BinderValidationStatus<T> status = binder.validate();
            Notification.show(status.getValidationErrors().stream()
                    .map(ValidationResult::getErrorMessage)
                    .collect(Collectors.joining("; ")), 3000, Notification.Position.BOTTOM_START);
        }
    }

    /**
     * Regola in scrittura eventuali valori NON associati al binder
     * Dallla  UI al DB
     * Sovrascritto
     */
    protected void writeSpecificFields() {
    }// end of method

    private void deleteClicked() {
        if (confirmationDialog.getElement().getParent() == null) {
            getUI().ifPresent(ui -> ui.add(confirmationDialog));
        }
        confirmDelete();
    }

    public void close() {
        super.close();
        presenter.getView().updateView();
    }// end of method

    /**
     * Opens the confirmation dialog before deleting the current item.
     * <p>
     * The dialog will display the given title and message(s), then call
     * {@link #deleteConfirmed(Serializable)} if the Delete button is clicked.
     *
     * @param title             The title text
     * @param message           Detail message (optional, may be empty)
     * @param additionalMessage Additional message (optional, may be empty)
     */
    protected final void openConfirmationDialog(String title, String message, String additionalMessage) {
        close();
        confirmationDialog.open(title, message, additionalMessage, "Elimina",
                true, getCurrentItem(), this::deleteConfirmed,
                this::open);
    }

    protected void confirmDelete() {
        openConfirmationDialog("Vuoi veramente eliminare “" + getCurrentItem() + "” ?", "L'operazione non è reversibile.", "");
    }// end of method

    private void deleteConfirmed(T item) {
        itemDeleter.accept(item);
        close();
    }


    /**
     * Gets the form layout, where additional components can be added for
     * displaying or editing the item's properties.
     *
     * @return the form layout
     */
    protected final FormLayout getFormLayout() {
        return formLayout;
    }

    /**
     * Gets the binder.
     *
     * @return the binder
     */
    protected final Binder<T> getBinder() {
        return binder;
    }

    /**
     * Gets the item currently being edited.
     *
     * @return the item currently being edited
     */
    protected final T getCurrentItem() {
        return currentItem;
    }

    /**
     * Recupera il field dal nome
     */
    protected AbstractField getField(String publicFieldName) {

        if (fieldMap != null) {
            return fieldMap.get(publicFieldName);
        } else {
            return null;
        }// end of if/else cycle

    }// end of method


    /**
     * The operations supported by this dialog. Delete is enabled when editing
     * an already existing item.
     */
    public enum Operation {
        ADD("Add New", "add", false),
        EDIT("Edit", "edit", true);

        private final String nameInTitle;
        private final String nameInText;
        private final boolean deleteEnabled;

        Operation(String nameInTitle, String nameInText, boolean deleteEnabled) {
            this.nameInTitle = nameInTitle;
            this.nameInText = nameInText;
            this.deleteEnabled = deleteEnabled;
        }

        public String getNameInTitle() {
            return nameInTitle;
        }

        public String getNameInText() {
            return nameInText;
        }

        public boolean isDeleteEnabled() {
            return deleteEnabled;
        }
    }

}// end of class
