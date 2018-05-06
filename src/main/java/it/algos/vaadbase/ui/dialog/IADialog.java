package it.algos.vaadbase.ui.dialog;

import com.vaadin.flow.spring.annotation.SpringComponent;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: sab, 05-mag-2018
 * Time: 18:56
 */
@SpringComponent
public interface IADialog<T> {

    /**
     * Opens the given item for editing in the dialog.
     *
     * @param item      The item to edit; it may be an existing or a newly created
     *                  instance
     * @param operation The operation being performed on the item
     */
    public void open(T item, AForm.Operation operation);

}// end of interface
