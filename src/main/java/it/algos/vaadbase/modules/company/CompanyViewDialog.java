package it.algos.vaadbase.modules.company;

import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.backend.service.IAService;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.ui.dialog.ADialog;
import it.algos.vaadbase.ui.dialog.AViewDialog;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static it.algos.vaadbase.application.BaseCost.TAG_COM;

/**
 * Project vaadbase <br>
 * Created by Algos
 * User: Gac
 * Date: 8-mag-2018 18.52.38
 * <p>
 * Estende la classe astratta ADialog per visualizzare i fields <br>
 * <p>
 * Not annotated with @SpringComponent (sbagliato)
 * Annotated with @Scope (obbligatorio = 'prototype')
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Qualifier(TAG_COM)
@AIScript(sovrascrivibile = true)
public class CompanyViewDialog extends AViewDialog<Company> {
    public CompanyViewDialog(IAPresenter presenter, BiConsumer<Company, Operation> itemSaver, Consumer<Company> itemDeleter) {
        super(presenter, itemSaver, itemDeleter);
    }
//    /**
//     * Costruttore
//     *
//     * @param itemSaver   funzione associata al bottone 'registra'
//     * @param itemDeleter funzione associata al bottone 'annulla'
//     * @param service     layer di collegamento per la Repository
//     */
//    public CompanyViewDialog(BiConsumer<Company, AViewDialog.Operation> itemSaver, Consumer<Company> itemDeleter, IAService service) {
//        super("Company", itemSaver, itemDeleter, service, Company.class);
//    }// end of constructor


}// end of class