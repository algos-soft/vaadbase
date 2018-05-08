package it.algos.vaadbase.modules.company;

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
 * Annotated with @Scope (obbligatorio = 'session') <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica <br>
 * Annotated with @AIScript (facoltativo) per controllare la ri-creazione di questo file nello script del framework <br>
 * Costruttore con dei link @Autowired di tipo @Lazy per evitare un loop nella injection <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_COM)
@AIScript(sovrascrivibile = true)
public class CompanyPresenter extends APresenter {

    /**
     * Costruttore @Autowired (nella superclasse) <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola il modello-dati specifico <br>
     */
    public CompanyPresenter(@Qualifier(TAG_COM) IAService service, @Lazy @Qualifier(TAG_COM) IAView view) {
        super(Company.class, service, view);
     }// end of Spring constructor


}// end of class