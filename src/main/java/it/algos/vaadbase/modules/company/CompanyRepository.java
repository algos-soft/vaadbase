package it.algos.vaadbase.modules.company;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import static it.algos.vaadbase.application.BaseCost.TAG_COM;

/**
 * Project vaadbase
 * Created by Algos
 * User: Gac
 * Date: 2018-04-02
 * Estende la l'interaccia MongoRepository col casting alla Entity relativa di questa repository
 * Annotated with @SpringComponent (obbligatorio)
 * Annotated with @Scope (obbligatorio = 'singleton')
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica
 * Annotated with @AIScript (facoltativo) per controllare la ri-creazione di questo file nello script del framework
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_COM)
@AIScript(sovrascrivibile = true)
public interface CompanyRepository extends MongoRepository<Company, String> {


    //@TODO
    // Le query riportate sono INDICATIVE e possono essere sostituite

	public Company findByCode(String code);

	public List<Company> findAllByOrderByCodeAsc();

	public Company findByDescrizione(String descrizione);

	public List<Company> findAllByOrderByDescrizioneAsc();

}// end of class