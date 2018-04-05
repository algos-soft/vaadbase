package it.algos.vaadbase.modules.prova;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import static it.algos.vaadbase.application.BaseCost.TAG_PRO;

/**
 * Project vaadbase
 * Created by Algos
 * User: Gac
 * Date: 5-apr-2018 12.31.00
 * <br>
 * Estende la l'interaccia MongoRepository col casting alla Entity relativa di questa repository <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Scope (obbligatorio = 'singleton') <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica <br>
 * Annotated with @AIScript (facoltativo) per controllare la ri-creazione di questo file nello script del framework <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_PRO)
@AIScript(sovrascrivibile = true)
public interface ProvaRepository extends MongoRepository<Prova, String> {


    //@TODO
    // Le query riportate sono INDICATIVE e possono essere sostituite

	public Prova findByCode(String code);

	public List<Prova> findAllByOrderByCodeAsc();

	public Prova findByOrdine(int ordine);

	public List<Prova> findAllByOrderByOrdineAsc();

	public List<Prova> findTop1AllByOrderByOrdineDesc();

}// end of class