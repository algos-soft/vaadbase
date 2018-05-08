package it.algos.vaadbase.modules.company;

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
import static it.algos.vaadbase.application.BaseCost.TAG_COM;

/**
 * Project vaadbase <br>
 * Created by Algos
 * User: Gac
 * Date: 8-mag-2018 18.52.38
 * Estende la Entity astratta AForm di tipo AView per visualizzare i fields
 * Annotated with @Scope (obbligatorio = 'prototype')
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica
 * Annotated with @AIScript (facoltativo) per controllare la ri-creazione di questo file nello script del framework
 */
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_COM)
@AIScript(sovrascrivibile = true)
public class CompanyViewDialog extends ADialog<Company> {

    /**
     * Costruttore
     */
    public CompanyViewDialog(BiConsumer<Company, ADialog.Operation> itemSaver, Consumer<Company> itemDeleter, IAService service) {
        super("Company", itemSaver, itemDeleter, service, Company.class);
    }// end of constructor


}// end of class