package it.algos.vaadbase.modules.utente;

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
import static it.algos.vaadbase.application.BaseCost.TAG_UTE;

/**
 * Project vaadbase <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 25-lug-2018 6.31.31 <br>
 * <br>
 * Estende la classe astratta APresenter che gestisce la business logic del package <br>
 * <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope (obbligatorio = 'singleton') <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_UTE)
@AIScript(sovrascrivibile = true)
public class UtentePresenter extends APresenter {

    /**
     * Costruttore <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola il modello-dati specifico e lo passa al costruttore della superclasse <br>
     *
     * @param service layer di collegamento per la Repository e la Business Logic
     */
    @Autowired
    public UtentePresenter(@Qualifier(TAG_UTE) IAService service) {
        super(Utente.class, service);
     }// end of Spring constructor


}// end of class