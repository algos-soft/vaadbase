package it.algos.vaadbase.modules.preferenza;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import static it.algos.vaadbase.application.BaseCost.TAG_PRE;

/**
 * Project vaadbase <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 25-mag-2018 16.19.24 <br>
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
@Qualifier(TAG_PRE)
@AIScript(sovrascrivibile = false)
public interface PreferenzaRepository extends MongoRepository<Preferenza, String> {

    //@TODO
    // Le query riportate sono INDICATIVE e possono essere sostituite

	public Preferenza findByCode(String code);

	public List<Preferenza> findAllByOrderByCodeAsc();

	public Preferenza findByDescrizione(String descrizione);

	public List<Preferenza> findAllByOrderByDescrizioneAsc();

	public Preferenza findByOrdine(int ordine);

	public List<Preferenza> findAllByOrderByOrdineAsc();

	public List<Preferenza> findTop1AllByOrderByOrdineDesc();

}// end of class