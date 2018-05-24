package it.algos.vaadbase.modules.role;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.presenter.APresenter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import it.algos.vaadbase.backend.service.IAService;
import it.algos.vaadbase.ui.IAView;
import static it.algos.vaadbase.application.BaseCost.TAG_ROL;

/**
 * Project vaadbase <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 24-mag-2018 20.31.30 <br>
 * <br>
 * Estende la classe astratta APresenter che gestisce la business logic del package <br>
 * <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope (obbligatorio = 'singleton') <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 * <p>
 * Constructor use @Lazy to avoid the Circular Dependency
 * A simple way to break the cycle is saying Spring to initialize one of the beans lazily.
 * That is: instead of fully initializing the bean, it will create a proxy to inject it into the other bean.
 * The injected bean will only be fully created when it’s first needed.
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_ROL)
@AIScript(sovrascrivibile = true)
public class RolePresenter extends APresenter {

    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola il modello-dati specifico e lo passa al costruttore della superclasse <br>
     *
     * @param service layer di collegamento per la Repository e la Business Logic
     * @param view    vista principale gestita da Flow
     */
    public RolePresenter(@Qualifier(TAG_ROL) IAService service, @Lazy @Qualifier(TAG_ROL) IAView view) {
        super(Role.class, service, view);
     }// end of Spring constructor


}// end of class