package it.algos.vaadbase.ui.dialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.shared.Registration;
import it.algos.vaadbase.backend.service.IAService;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.ui.enumeration.EAFieldType;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
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
public abstract class ADialog<T extends Serializable> extends Dialog implements IADialog {


    private final H2 titleField = new H2();
    private final Button saveButton = new Button("Registra");
    private final Button cancelButton = new Button("Annulla");
    private final Button deleteButton = new Button("Elimina");
    private final FormLayout formLayout = new FormLayout();
    private final HorizontalLayout buttonBar = new HorizontalLayout(saveButton, cancelButton, deleteButton);
    private final ConfirmationDialog<T> confirmationDialog = new ConfirmationDialog<>();
    private final String itemType;
    private final BiConsumer<T, ADialog.Operation> itemSaver;
    private final Consumer<T> itemDeleter;
    protected IAService service;
    protected Binder<T> binder;
    protected Class binderClass;
    private Registration registrationForSave;
    private T currentItem;

    /**
     * Constructs a new instance.
     *
     * @param presenter per gestire la business logic del package
     * @param itemSaver   funzione associata al bottone 'registra'
     * @param itemDeleter funzione associata al bottone 'annulla'
     */
    public ADialog(IAPresenter presenter, BiConsumer<T, ADialog.Operation> itemSaver, Consumer<T> itemDeleter) {
        this.service = presenter.getService();
        this.itemType = "prova";//@todo levare
        this.itemSaver = itemSaver;
        this.itemDeleter = itemDeleter;
        this.binderClass = presenter.getEntityClazz();

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


    /**
     * Constructs a new instance.
     *
     * @param itemType    The readable name of the item type
     * @param itemSaver   Callback to save the edited item
     * @param itemDeleter Callback to delete the edited item
     */
    protected ADialog(String itemType, BiConsumer<T, ADialog.Operation> itemSaver, Consumer<T> itemDeleter, IAService service, Class binderClass) {
        this.itemType = itemType;
        this.itemSaver = itemSaver;
        this.itemDeleter = itemDeleter;
        this.service = service;
        this.binderClass = binderClass;

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

    protected void initFields() {
        TextField field;
        EAFieldType type;
        String stringMessage = "Code must contain at least 3 printable characters";
        String intMessage = "Must enter a number";
        StringLengthValidator stringConverter = new StringLengthValidator(stringMessage, 3, null);
        StringToIntegerConverter integerConverter = new StringToIntegerConverter(0, intMessage);
        binder = new Binder(binderClass);

        if (service != null) {
            List<String> properties = service.getFormPropertiesName();

            for (String fieldName : properties) {
                type = service.getAnnotationService().getFormType(binderClass, fieldName);
                field = new TextField(fieldName);
                getFormLayout().add(field);
                switch (type) {
                    case text:
                        binder.forField(field)
                                .withValidator(stringConverter)
                                .bind(fieldName);
                        break;
                    case integer:
                        binder.forField(field)
                                .withConverter(integerConverter)
                                .bind(fieldName);
                        break;
                    default:
                        break;
                } // end of switch statement
            }// end of for cycle
        }// end of if cycle

    }// end of method


    /**
     * Opens the given item for editing in the dialog.
     *
     * @param item      The item to edit; it may be an existing or a newly created
     *                  instance
     * @param operation The operation being performed on the item
     */
    public void open(Object item, ADialog.Operation operation) {
        if (item == null) {
            Notification.show("Qualcosa non ha funzionato in AForm.open()", 3000, Notification.Position.BOTTOM_START);
            return;
        }// end of if cycle

        currentItem = (T) item;
        titleField.setText(operation.getNameInTitle() + " " + itemType);
        if (registrationForSave != null) {
            registrationForSave.remove();
        }
        registrationForSave = saveButton
                .addClickListener(e -> saveClicked(operation));
        binder.readBean(currentItem);

        deleteButton.setEnabled(operation.isDeleteEnabled());
        open();

    }// end of method


    private void saveClicked(ADialog.Operation operation) {
        boolean isValid = binder.writeBeanIfValid(currentItem);

        if (isValid) {
            itemSaver.accept(currentItem, operation);
            close();
        } else {
            BinderValidationStatus<T> status = binder.validate();
            Notification.show(status.getValidationErrors().stream()
                    .map(ValidationResult::getErrorMessage)
                    .collect(Collectors.joining("; ")), 3000, Notification.Position.BOTTOM_START);
        }
    }

    private void deleteClicked() {
        if (confirmationDialog.getElement().getParent() == null) {
            getUI().ifPresent(ui -> ui.add(confirmationDialog));
        }
        confirmDelete();
    }

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
