package it.algos.vaadtest.modules.prova;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.ui.dialog.ADialog;
import it.algos.vaadbase.backend.service.IAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import static it.algos.vaadtest.application.AppCost.TAG_PRO;

/**
 * Project vaadbase <br>
 * Created by Algos
 * User: Gac
 * Date: 8-mag-2018 11.39.38
 * Estende la Entity astratta AForm di tipo AView per visualizzare i fields
 * Annotated with @Scope (obbligatorio = 'prototype')
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica
 * Annotated with @AIScript (facoltativo) per controllare la ri-creazione di questo file nello script del framework
 */
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_PRO)
@AIScript(sovrascrivibile = true)
public class ProvaViewDialog extends ADialog<Prova> {

    /**
     * Costruttore
     */
    public ProvaViewDialog(BiConsumer<Prova, ADialog.Operation> itemSaver, Consumer<Prova> itemDeleter, IAService service) {
        super("Prova", itemSaver, itemDeleter, service, Prova.class);
    }// end of constructor


}// end of class