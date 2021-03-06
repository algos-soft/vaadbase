package it.algos.vaadbase.ui.dialog;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.backend.entity.AEntity;

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
    public void open(AEntity item, AViewDialog.Operation operation);


    /**
     * Opens the given item for editing in the dialog.
     *
     * @param item      The item to edit; it may be an existing or a newly created
     *                  instance
     * @param operation The operation being performed on the item
     * @param title of the window dialog
     */
    public void open(AEntity item, AViewDialog.Operation operation, String title);

}// end of interface
