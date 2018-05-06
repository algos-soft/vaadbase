package it.algos.vaadbase.ui.dialog;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.backend.service.IAService;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: sab, 05-mag-2018
 * Time: 19:10
 */
@Slf4j
public  class ADialog<T extends Serializable> extends Dialog implements IADialog {


    /**
     * Constructs a new instance.
     *
     * @param itemType    The readable name of the item type
     * @param itemSaver   Callback to save the edited item
     * @param itemDeleter Callback to delete the edited item
     */
    protected ADialog(String itemType, BiConsumer<T, AForm.Operation> itemSaver, Consumer<T> itemDeleter, IAService service, Class binderClass) {
    }// end of constructor

        /**
         * Opens the given item for editing in the dialog.
         *
         * @param item      The item to edit; it may be an existing or a newly created
         *                  instance
         * @param operation The operation being performed on the item
         */
    @Override
    public void open(Object item, AForm.Operation operation) {
        if (item == null) {
            Notification.show("Qualcosa non ha funzionato in AForm.open()", 3000, Notification.Position.BOTTOM_START);
            return;
        }// end of if cycle

//        currentItem = item;
//        titleField.setText(operation.getNameInTitle() + " " + itemType);
//        if (registrationForSave != null) {
//            registrationForSave.remove();
//        }
//        registrationForSave = saveButton
//                .addClickListener(e -> saveClicked(operation));
//        binder.readBean(currentItem);
//
//        deleteButton.setEnabled(operation.isDeleteEnabled());
        open();

    }// end of method

}// end of class
