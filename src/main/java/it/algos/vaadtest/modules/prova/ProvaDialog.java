package it.algos.vaadtest.modules.prova;

import it.algos.vaadbase.backend.service.IAService;
import it.algos.vaadbase.ui.dialog.ADialog;
import it.algos.vaadbase.ui.dialog.AForm;
import it.algos.vaadbase.ui.dialog.IADialog;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: sab, 05-mag-2018
 * Time: 19:06
 */
@Slf4j
public class ProvaDialog extends ADialog {

    /**
     * Costruttore
     */
    public ProvaDialog(BiConsumer<Prova, AForm.Operation> itemSaver, Consumer<Prova> itemDeleter, IAService service) {
        super("Prova", itemSaver, itemDeleter, service, Prova.class);
    }// end of constructor

}// end of class
