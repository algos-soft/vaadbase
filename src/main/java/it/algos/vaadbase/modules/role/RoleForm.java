package it.algos.vaadbase.modules.role;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import it.algos.vaadbase.backend.service.IAService;
import it.algos.vaadbase.ui.dialog.AForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: ven, 27-apr-2018
 * Time: 09:34
 */
@Slf4j
//@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RoleForm extends AForm<Role> {

    /**
     * Costruttore
     */
    public RoleForm(BiConsumer<Role, AForm.Operation> itemSaver, Consumer<Role> itemDeleter, IAService service) {
        super("Role", itemSaver, itemDeleter, service, Role.class);
        }// end of constructor


}// end of class
