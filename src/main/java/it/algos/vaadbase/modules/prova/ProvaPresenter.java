package it.algos.vaadbase.modules.prova;

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
import static it.algos.vaadbase.application.BaseCost.TAG_PRO;

/**
 * Project vaadbase
 * Created by Algos
 * User: Gac
 * Date: 5-apr-2018 12.31.00* <br>

 * Estende la Entity astratta APresenter che gestisce la business logic <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope (obbligatorio = 'session') <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica <br>
 * Annotated with @AIScript (facoltativo) per controllare la ri-creazione di questo file nello script del framework <br>
 * Costruttore con dei link @Autowired di tipo @Lazy per evitare un loop nella injection <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_PRO)
@AIScript(sovrascrivibile = true)
public class ProvaPresenter extends APresenter {

    /**
     * Costruttore @Autowired (nella superclasse) <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     * Regola il modello-dati specifico <br>
     */
    public ProvaPresenter(@Lazy @Qualifier(TAG_PRO) IAService service, @Lazy @Qualifier(TAG_PRO) IAView view) {
        super(Prova.class, service, view);
     }// end of Spring constructor


}// end of class