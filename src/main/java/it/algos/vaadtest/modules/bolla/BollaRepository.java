package it.algos.vaadtest.modules.bolla;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import static it.algos.vaadtest.application.AppCost.TAG_BOL;

/**
 * Project vaadtest <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 29-mag-2018 17.12.37 <br>
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
@Qualifier(TAG_BOL)
@AIScript(sovrascrivibile = false)
public interface BollaRepository extends MongoRepository<Bolla, String> {

    //@TODO
    // Le query riportate sono INDICATIVE e possono essere sostituite

	public Bolla findByCode(String code);

	public List<Bolla> findAllByOrderByCodeAsc();

	public Bolla findByDescrizione(String descrizione);

	public List<Bolla> findAllByOrderByDescrizioneAsc();

	public Bolla findByOrdine(int ordine);

	public List<Bolla> findAllByOrderByOrdineAsc();

	public List<Bolla> findTop1AllByOrderByOrdineDesc();

}// end of class