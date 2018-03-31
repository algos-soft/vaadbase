package it.algos.vaadbase.modules.company;

import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.application.BaseCost;
import it.algos.vaadbase.backend.entity.AEntity;
import it.algos.vaadbase.backend.service.AService;
import it.algos.vaadbase.backend.service.IAService;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: gio, 22-mar-2018
 * Time: 11:14
 * Estende la l'interaccia MongoRepository col casting alla Entity relativa di questa repository
 * Annotated with @@Slf4j (facoltativo) per i logs automatici
 * Annotated with @SpringComponent (obbligatorio)
 * Annotated with @Scope (obbligatorio = 'singleton')
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica
 * Annotated with @AIScript (facoltativo) per controllare la ri-creazione di questo file nel 'wizard'
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(BaseCost.TAG_COM)
@AIScript(sovrascrivibile = false)
public class CompanyService  extends AService {

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
    public CompanyService(@Qualifier(BaseCost.TAG_COM) MongoRepository repository) {
        super(repository);
        this.repository = (CompanyRepository) repository;
//        super.entityClass = Company.class;
    }// end of Spring constructor


//    /**
//     * Ricerca di una entity (la crea se non la trova)
//     * Properties obbligatorie
//     *
//     * @param code        di riferimento interno (obbligatorio ed unico)
//     * @param descrizione ragione sociale o descrizione della company (visibile - obbligatoria)
//     *
//     * @return la entity trovata o appena creata
//     */
//    public Company findOrCrea(String code, String descrizione) {
//        Company entity = findByKeyUnica(code);
//
//        if (entity == null) {
//            entity = newEntity(code, descrizione);
//            save(entity);
//        }// end of if cycle
//
//        return entity;
//    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata
     * Eventuali regolazioni iniziali delle property
     * Senza properties per compatibilità con la superclasse
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Company newEntity() {
        return newEntity("", "");
    }// end of method




    /**
     * Creazione in memoria di una nuova entity che NON viene salvata
     * Eventuali regolazioni iniziali delle property
     * All properties
     * Gli argomenti (parametri) della new Entity DEVONO essere ordinati come nella Entity (costruttore lombok)
     *
     * @param code        di riferimento interno (obbligatorio ed unico)
     * @param descrizione ragione sociale o descrizione della company (visibile - obbligatoria)
     * @param contatto    persona di riferimento (facoltativo)
     * @param telefono    della company (facoltativo)
     * @param email       della company (facoltativo)
     * @param indirizzo   della company (facoltativo)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Company newEntity(String code, String descrizione) {
        Company entity = findByKeyUnica(code);

        if (entity == null) {
            entity = Company.builder()
                    .code(code)
                    .descrizione(descrizione)
                    .build();
        }// end of if cycle

        return entity;
    }// end of method


    /**
     * Opportunità di controllare (per le nuove schede) che la key unica non esista già.
     * Invocato appena prima del save(), solo per una nuova entity
     *
     * @param entityBean nuova da creare
     */
//    @Override
    protected boolean isEsisteEntityKeyUnica(AEntity entityBean) {
        return findByKeyUnica(((Company) entityBean).getCode()) != null;
    }// end of method


    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria ed unica)
     *
     * @param code codice di riferimento (obbligatorio)
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
//    @Override
    public List findAll() {
        return repository.findByOrderByCodeAsc();
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

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    @Override
    public int count() {
        return 0;
    }

    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     *
     * @return the entity with the given id or {@literal null} if none found
     *
     * @throws IllegalArgumentException if {@code id} is {@literal null}
     */
    @Override
    public AEntity find(String id) {
        return null;
    }

//    /**
//     * Colonne visibili (e ordinate) nella Grid
//     * Sovrascrivibile
//     * La colonna key ID normalmente non si visualizza
//     * 1) Se questo metodo viene sovrascritto, si utilizza la lista della sottoclasse specifica (con o senza ID)
//     * 2) Se la classe AEntity->@AIList(columns = ...) prevede una lista specifica, usa quella lista (con o senza ID)
//     * 3) Se non trova AEntity->@AIList, usa tutti i campi della AEntity (senza ID)
//     * 4) Se trova AEntity->@AIList(showsID = true), questo viene aggiunto, indipendentemente dalla lista
//     * 5) Vengono visualizzati anche i campi delle superclassi della classe AEntity
//     * Ad esempio: company della classe ACompanyEntity
//     *
//     * @return lista di fields visibili nella Grid
//     */
//    @Override
//    public List<Field> getListFields() {
//        return null;
//    }

//    /**
//     * Fields visibili (e ordinati) nel Form
//     * Sovrascrivibile
//     * Il campo key ID normalmente non viene visualizzato
//     * 1) Se questo metodo viene sovrascritto, si utilizza la lista della sottoclasse specifica (con o senza ID)
//     * 2) Se la classe AEntity->@AIForm(fields = ...) prevede una lista specifica, usa quella lista (con o senza ID)
//     * 3) Se non trova AEntity->@AIForm, usa tutti i campi della AEntity (senza ID)
//     * 4) Se trova AEntity->@AIForm(showsID = true), questo viene aggiunto, indipendentemente dalla lista
//     * 5) Vengono visualizzati anche i campi delle superclassi della classe AEntity
//     * Ad esempio: company della classe ACompanyEntity
//     *
//     * @return lista di fields visibili nel Form
//     */
//    @Override
//    public List<Field> getFormFields() {
//        return null;
//    }

//    /**
//     * Saves a given entity.
//     * Use the returned instance for further operations
//     * as the save operation might have changed the entity instance completely.
//     *
//     * @param entityBean to be saved
//     *
//     * @return the saved entity
//     */
//    @Override
//    public AEntity save(AEntity entityBean) {
//        return null;
//    }

//    /**
//     * Saves a given entity.
//     * Use the returned instance for further operations
//     * as the save operation might have changed the entity instance completely.
//     *
//     * @param oldBean      previus state
//     * @param modifiedBean to be saved
//     *
//     * @return the saved entity
//     */
//    @Override
//    public AEntity save(AEntity oldBean, AEntity modifiedBean) throws Exception {
//        return null;
//    }

//    /**
//     * Deletes a given entity.
//     *
//     * @param entityBean must not be null
//     *
//     * @return true, se la entity è stata effettivamente cancellata
//     *
//     * @throws IllegalArgumentException in case the given entity is {@literal null}.
//     */
//    @Override
//    public boolean delete(AEntity entityBean) {
//        return false;
//    }

//    /**
//     * Deletes all entities of the collection.
//     */
//    @Override
//    public boolean deleteAll() {
//        return false;
//    }
}// end of class
