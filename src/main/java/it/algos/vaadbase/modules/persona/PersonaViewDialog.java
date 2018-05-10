package it.algos.vaadbase.modules.persona;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.IntegerRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.ui.dialog.AViewDialog;
import it.algos.vaadbase.backend.service.IAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import static it.algos.vaadbase.application.BaseCost.TAG_PER;

/**
 * Project vaadbase <br>
 * Created by Algos
 * User: Gac
 * Date: 10-mag-2018 6.41.22
 * <p>
 * Estende la classe astratta ADialog per visualizzare i fields <br>
 * <p>
 * Not annotated with @SpringComponent (sbagliato)
 * Annotated with @Scope (obbligatorio = 'prototype')
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Qualifier(TAG_PER)
@AIScript(sovrascrivibile = true)
public class PersonaViewDialog extends AViewDialog<Persona> {

    /**
     * Costruttore
     *
     * @param presenter per gestire la business logic del package
     * @param itemSaver   funzione associata al bottone 'registra'
     * @param itemDeleter funzione associata al bottone 'annulla'
     */
    public PersonaViewDialog(IAPresenter presenter, BiConsumer<Persona, AViewDialog.Operation> itemSaver, Consumer<Persona> itemDeleter) {
        super(presenter, itemSaver, itemDeleter);
    }// end of constructor


}// end of class