package it.algos.vaadbase.modules.address;

import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.ui.dialog.AViewDialog;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static it.algos.vaadbase.application.BaseCost.TAG_ADD;

/**
 * Project vaadbase <br>
 * Created by Algos
 * User: Gac
 * Date: 9-mag-2018 21.12.07
 * <p>
 * Estende la classe astratta ADialog per visualizzare i fields <br>
 * <p>
 * Not annotated with @SpringComponent (sbagliato)
 * Annotated with @Scope (obbligatorio = 'prototype')
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Qualifier(TAG_ADD)
@AIScript(sovrascrivibile = true)
public class AddressViewDialog extends AViewDialog<Address> {


    /**
     * Constructs a new instance.
     *
     * @param presenter   per gestire la business logic del package
     * @param itemSaver   funzione associata al bottone 'registra'
     * @param itemDeleter funzione associata al bottone 'annulla'
     */
    public AddressViewDialog(IAPresenter presenter, BiConsumer<Address, AViewDialog.Operation> itemSaver, Consumer<Address> itemDeleter) {
        this(presenter, itemSaver, itemDeleter, false);
    }// end of constructor

    /**
     * Constructs a new instance.
     *
     * @param presenter               per gestire la business logic del package
     * @param itemSaver               funzione associata al bottone 'registra'
     * @param itemDeleter             funzione associata al bottone 'annulla'
     * @param confermaSenzaRegistrare cambia il testo del bottone 'Registra' in 'Conferma'
     */
    public AddressViewDialog(IAPresenter presenter, BiConsumer<Address, AViewDialog.Operation> itemSaver, Consumer<Address> itemDeleter, boolean confermaSenzaRegistrare) {
        super(presenter, itemSaver, itemDeleter, confermaSenzaRegistrare);
    }// end of constructor

}// end of class