package it.algos.vaadbase.modules.company;

import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.application.BaseCost;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: gio, 22-mar-2018
 * Time: 11:15
 * Estende la l'interaccia MongoRepository col casting alla Entity relativa di questa repository
 * Annotated with @SpringComponent (obbligatorio)
 * Annotated with @Scope (obbligatorio = 'singleton')
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica
 * Annotated with @AIScript (facoltativo) per controllare la ri-creazione di questo file nello script del framework
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(BaseCost.TAG_COM)
@AIScript(sovrascrivibile = false)
public interface CompanyRepository extends MongoRepository<Company, String> {

    public Company findByCode(String code);

    public List<Company> findByOrderByCodeAsc();

}// end of class
