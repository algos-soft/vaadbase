package it.algos.vaadbase.modules.role;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import static it.algos.vaadbase.application.BaseCost.TAG_ROL;

/**
 * Project vaadbase <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 24-mag-2018 9.55.36 <br>
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
@Qualifier(TAG_ROL)
@AIScript(sovrascrivibile = false)
public interface RoleRepository extends MongoRepository<Role, String> {

    //@TODO
    // Le query riportate sono INDICATIVE e possono essere sostituite

	public Role findByCode(String code);

	public List<Role> findAllByOrderByCodeAsc();

	public Role findByOrdine(int ordine);

	public List<Role> findAllByOrderByOrdineAsc();

	public List<Role> findTop1AllByOrderByOrdineDesc();

}// end of class