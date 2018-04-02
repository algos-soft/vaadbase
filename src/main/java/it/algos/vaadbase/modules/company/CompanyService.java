package it.algos.vaadbase.modules.company;

import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.backend.service.AService;
import it.algos.vaadbase.backend.entity.AEntity;
import static it.algos.vaadbase.application.BaseCost.TAG_COM;

/**
 * Project vaadbase
 * Created by Algos
 * User: Gac
 * Date: 2018-04-02
 * Estende la Entity astratta AService. Layer di collegamento tra il Presenter e la Repository.
 * Annotated with @@Slf4j (facoltativo) per i logs automatici
 * Annotated with @SpringComponent (obbligatorio)
 * Annotated with @Service (ridondante)
 * Annotated with @Scope (obbligatorio = 'singleton')
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica
 * Annotated with @AIScript (facoltativo) per controllare la ri-creazione di questo file nello script del framework
 */
@Slf4j
@SpringComponent
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_COM)
@AIScript(sovrascrivibile = true)
public class CompanyService extends AService {


    /**
     * La repository viene iniettata dal costruttore, in modo che sia disponibile nella superclasse,
     * dove viene usata l'interfaccia MongoRepository
     * Spring costruisce al volo, quando serve, una implementazione di RoleRepository (come previsto dal @Qualifier)
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici
     */
    private CompanyRepository repository;


    /**
     * Costruttore @Autowired (nella superclasse)
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation
     * Si usa un @Qualifier(), per avere la sottoclasse specifica
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti
     */
    public CompanyService(@Qualifier(TAG_COM) MongoRepository repository) {
        super(repository);
        this.repository = (CompanyRepository) repository;
        super.entityClass = Company.class;
   }// end of Spring constructor

    /**
     * Ricerca di una entity (la crea se non la trova)
     *
     * @param code di riferimento (obbligatorio ed unico)
     *
     * @return la entity trovata o appena creata
     */
    public Company findOrCrea(String code) {
        Company entity = findByKeyUnica(code);

        if (entity == null) {
            entity = newEntity(code, "");
            save(entity);
        }// end of if cycle

        return entity;
    }// end of method

//    /**
//     * Creazione in memoria di una nuova entity che NON viene salvata
//     * Eventuali regolazioni iniziali delle property
//     * Senza properties per compatibilità con la superclasse
//     *
//     * @return la nuova entity appena creata (non salvata)
//     */
//    @Override
//    public Prova newEntity() {
//        return newEntity("");
//    }// end of method
//
//
//    /**
//     * Creazione in memoria di una nuova entity che NON viene salvata
//     * Eventuali regolazioni iniziali delle property
//     * Properties obbligatorie
//     * Gli argomenti (parametri) della new Entity DEVONO essere ordinati come nella Entity (costruttore lombok)
//     *
//     * @param code codice di riferimento (obbligatorio)
//     *
//     * @return la nuova entity appena creata (non salvata)
//     */
//    public Prova newEntity(String code) {
//        return newEntity(code, "");
//    }// end of method

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata
     * Eventuali regolazioni iniziali delle property
     * All properties
     * Gli argomenti (parametri) della new Entity DEVONO essere ordinati come nella Entity (costruttore lombok)
     *
     * @param code        codice di riferimento (obbligatorio)
	* @param descrizione (facoltativa, non unica)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Company newEntity(String code, String descrizione) {
        Company entity = null;

        entity = findByKeyUnica(code);
		if (entity != null) {
			return findByKeyUnica(code);
		}// end of if cycle
		
        entity = Company.builder()
				.code(code)
				.descrizione(descrizione)
                .build();

        return entity;
    }// end of method

    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria ed unica)
     *
     * @param code di riferimento (obbligatorio)
     *
     * @return istanza della Entity, null se non trovata
     */
    public Company findByKeyUnica(String code) {
        return repository.findByCode(code);
    }// end of method


    /**
     * Returns all instances of the type
     * La Entity è EACompanyRequired.nonUsata. Non usa Company.
     * Lista ordinata
     *
     * @return lista ordinata di tutte le entities
     */
    @Override
    public List<Company> findAll() {
        return repository.findAllByOrderByCodeAsc();
    }// end of method


    /**
     * Opportunità di controllare (per le nuove schede) che la key unica non esista già.
     * Invocato appena prima del save(), solo per una nuova entity
     *
     * @param entityBean nuova da creare
     */
    @Override
    protected boolean isEsisteEntityKeyUnica(AEntity entityBean) {
        return findByKeyUnica(((Company) entityBean).getCode()) != null;
    }// end of method

    /**
     * Opportunità di usare una idKey specifica.
     * Invocato appena prima del save(), solo per una nuova entity
     *
     * @param entityBean da salvare
     */
    protected void creaIdKeySpecifica(AEntity entityBean) {
        entityBean.id = ((Company)entityBean).getCode();
    }// end of method

    

}// end of class