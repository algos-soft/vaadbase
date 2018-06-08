package it.algos.vaadtest.modules.prova;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.modules.company.Company;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import static it.algos.vaadtest.application.AppCost.TAG_PRO;

/**
 * Project vaadtest <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 8-giu-2018 18.42.14 <br>
 * <br>
 * Estende la l'interaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope (obbligatorio = 'singleton') <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_PRO)
@AIScript(sovrascrivibile = false)
public interface ProvaRepository extends MongoRepository<Prova, String> {

    //@TODO
    // Le query riportate sono INDICATIVE e possono essere sostituite

	public Prova findByCompanyAndCode(Company company, String code);

	public List<Prova> findAllByCompanyOrderByOrdineAsc(Company company);

}// end of class