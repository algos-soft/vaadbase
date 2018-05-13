package it.algos.vaadbase.modules.company;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.application.StaticContextAccessor;
import it.algos.vaadbase.modules.persona.PersonaPresenter;
import it.algos.vaadbase.modules.persona.PersonaService;
import it.algos.vaadbase.modules.persona.PersonaViewDialog;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.ui.dialog.AViewDialog;
import it.algos.vaadbase.ui.fields.ATextField;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Date: 9-mag-2018 19.52.23
 * <p>
 * Estende la classe astratta ADialog per visualizzare i fields <br>
 * <p>
 * Not annotated with @SpringComponent (sbagliato)
 * Annotated with @Scope (obbligatorio = 'prototype')
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Qualifier(TAG_COM)
@AIScript(sovrascrivibile = true)
public class CompanyViewDialog extends AViewDialog<Company> {


    /**
     * Costruttore
     *
     * @param presenter   per gestire la business logic del package
     * @param itemSaver   funzione associata al bottone 'registra'
     * @param itemDeleter funzione associata al bottone 'annulla'
     */
    @Autowired
    public CompanyViewDialog(IAPresenter presenter, BiConsumer<Company, AViewDialog.Operation> itemSaver, Consumer<Company> itemDeleter) {
        super(presenter, itemSaver, itemDeleter);
    }// end of constructor


    /**
     * Aggiunge al binder eventuali fields specifici, prima di trascrivere la entityBean nel binder
     * Sovrascritto
     * Dopo aver creato un AField specifico, usare il metodo super.addFieldBinder() per:
     * Inizializzare AField
     */
    @Override
    protected void addSpecificAlgosFields() {
        PersonaPresenter personaPresenter = StaticContextAccessor.getBean(PersonaPresenter.class);
        final PersonaViewDialog dialog = new PersonaViewDialog(personaPresenter, null, null);
        PersonaService personaService = (PersonaService) personaPresenter.getService();

        ATextField contattoField = null;
        contattoField = (ATextField) fieldService.create(binder, binderClass, "contatto");

        if (contattoField != null) {
            fieldMap.put("contatto", contattoField);
            contattoField.addFocusListener(e -> dialog.open(personaService.newEntity(), AViewDialog.Operation.ADD));
        }// end of if cycle
    }// end of method


//    /**
//     * Aggiunge i componenti grafici AField al layout
//     * Inserimento automatico nel layout ''verticale''
//     * La sottoclasse può sovrascrivere integralmente questo metodo per realizzare un layout personalizzato
//     * La sottoclasse può sovrascrivere questo metodo; richiamarlo e poi aggiungere altri AField al layout verticale
//     * Nel layout sono già presenti una Label (sopra) ed una Toolbar (sotto)
//     *
//     * @param layout in cui inserire i campi (window o panel)
//     */
//    @Override
//    protected void layoutFields() {
//        AField fieldNote = getField("note");
//        AField fieldFunz = getField("funzioni");
//        int posVis = fieldList.indexOf(fieldNote);
//        int posFunz = fieldList.indexOf(fieldFunz);
//        fieldList.remove(posFunz);
//        fieldList.add(posVis, fieldFunz);
//
//        super.layoutFields(layout);
//    }// end of method

}// end of class