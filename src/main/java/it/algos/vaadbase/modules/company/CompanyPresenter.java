package it.algos.vaadbase.modules.company;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.backend.service.IAService;
import it.algos.vaadbase.presenter.APresenter;
import it.algos.vaadbase.ui.IAView;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadbase.application.BaseCost.TAG_COM;

/**
 * Project vaadbase <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 8-mag-2018 18.52.38 <br>
 * <br>
 * Estende la classe astratta APresenter che gestisce la business logic del package <br>
 * <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope (obbligatorio = 'singleton') <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @AIScript (facoltativo) per controllare la ri-creazione di questo file nello script del framework <br>
 * <p>
 * Constructor use @Lazy to avoid the Circular Dependency
 * A simple way to break the cycle is saying Spring to initialize one of the beans lazily.
 * That is: instead of fully initializing the bean, it will create a proxy to inject it into the other bean.
 * The injected bean will only be fully created when it’s first needed.
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_COM)
@AIScript(sovrascrivibile = true)
public class CompanyPresenter extends APresenter {

    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola il modello-dati specifico e lo passa al costruttore della superclasse <br>
     *
     * @param service layer di collegamento per la Repository
     * @param view    principale gestita da Flow
     */
    public CompanyPresenter(@Qualifier(TAG_COM) IAService service, @Lazy @Qualifier(TAG_COM) IAView view) {
        super(Company.class, service, view);
    }// end of Spring constructor


}// end of class