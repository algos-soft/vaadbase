package it.algos.vaadbase.modules.preferenza;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.backend.entity.AEntity;
import it.algos.vaadbase.backend.service.AService;
import it.algos.vaadbase.enumeration.EAPrefType;
import it.algos.vaadbase.modules.company.Company;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static it.algos.vaadbase.application.BaseCost.TAG_PRE;

/**
 * Project vaadbase <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 25-mag-2018 16.19.24 <br>
 * <br>
 * Estende la classe astratta AService. Layer di collegamento per la Repository. <br>
 * <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Service (ridondante) <br>
 * Annotated with @Scope (obbligatorio = 'singleton') <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la classe specifica <br>
 * Annotated with @@Slf4j (facoltativo) per i logs automatici <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@SpringComponent
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_PRE)
@Slf4j
@AIScript(sovrascrivibile = false)
public class PreferenzaService extends AService {


    /**
     * La repository viene iniettata dal costruttore e passata al costruttore della superclasse, <br>
     * Spring costruisce una implementazione concreta dell'interfaccia MongoRepository (come previsto dal @Qualifier) <br>
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici <br>
     */
    private PreferenzaRepository repository;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param repository per la persistenza dei dati
     */
    public PreferenzaService(@Qualifier(TAG_PRE) MongoRepository repository) {
        super(repository);
        this.repository = (PreferenzaRepository) repository;
    }// end of Spring constructor


    /**
     * Ricerca di una entity (la crea se non la trova) <br>
     *
     * @param code        codice di riferimento (obbligatorio)
     * @param descrizione (facoltativa)
     * @param type        (obbligatorio) per convertire in byte[] i valori
     * @param value       (obbligatorio) memorizza tutto in byte[]
     *
     * @return la entity trovata o appena creata
     */
    public Preferenza findOrCrea(String code, String descrizione, EAPrefType type, Object value) {
        Preferenza entity = findByKeyUnica(code);

        if (entity == null) {
            entity = crea(code, descrizione, type, value);
        }// end of if cycle

        return entity;
    }// end of method

    /**
     * Crea una entity e la registra <br>
     *
     * @param code        codice di riferimento (obbligatorio)
     * @param descrizione (facoltativa)
     * @param type        (obbligatorio) per convertire in byte[] i valori
     * @param value       (obbligatorio) memorizza tutto in byte[]
     *
     * @return la entity appena creata
     */
    public Preferenza crea(String code, String descrizione, EAPrefType type, Object value) {
        Preferenza entity = newEntity((Company) null, 0, code, descrizione, type, value);
        save(entity);
        return entity;
    }// end of method

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata
     * Eventuali regolazioni iniziali delle property
     * Senza properties per compatibilità con la superclasse
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public Preferenza newEntity() {
        return newEntity(null, 0, "", "", null, null);
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * Properties obbligatorie
     * Gli argomenti (parametri) della new Entity DEVONO essere ordinati come nella Entity (costruttore lombok) <br>
     *
     * @param code        codice di riferimento (obbligatorio)
     * @param descrizione (facoltativa)
     * @param type        (obbligatorio) per convertire in byte[] i valori
     * @param value       (obbligatorio) memorizza tutto in byte[]
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Preferenza newEntity(String code, String descrizione, EAPrefType type, Object value) {
        return newEntity((Company) null, 0, code, descrizione, type, value);
    }// end of method

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     * Gli argomenti (parametri) della new Entity DEVONO essere ordinati come nella Entity (costruttore lombok) <br>
     *
     * @param company     di appartenenza (obbligatoria, se manca viene recuperata dal login)
     * @param ordine      di presentazione (obbligatorio con inserimento automatico se è zero)
     * @param code        codice di riferimento (obbligatorio)
     * @param descrizione (facoltativa)
     * @param type        (obbligatorio) per convertire in byte[] i valori
     * @param value       (obbligatorio) memorizza tutto in byte[]
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Preferenza newEntity(Company company, int ordine, String code, String descrizione, EAPrefType type, Object value) {
        Preferenza entity = null;

        entity = findByKeyUnica(code);
        if (entity != null) {
            return findByKeyUnica(code);
        }// end of if cycle

        entity = Preferenza.builder()
                .ordine(ordine != 0 ? ordine : this.getNewOrdine())
                .code(code.equals("") ? null : code)
                .descrizione(descrizione.equals("") ? null : descrizione)
                .type(type)
                .value(type != null ? type.objectToBytes(value) : (byte[]) null)
                .build();

        return (Preferenza) addCompany(entity, company);
    }// end of method

    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria ed unica) <br>
     *
     * @param code di riferimento (obbligatorio)
     *
     * @return istanza della Entity, null se non trovata
     */
    public Preferenza findByKeyUnica(String code) {
        return repository.findByCode(code);
    }// end of method


    /**
     * Returns all instances of the type <br>
     * La Entity è EACompanyRequired.nonUsata. Non usa Company. <br>
     * Lista ordinata <br>
     *
     * @return lista ordinata di tutte le entities
     */
    @Override
    public List<Preferenza> findAll() {
        List<Preferenza> lista = null;

        lista = repository.findAllByOrderByOrdineAsc();

        return lista;
    }// end of method


//    /**
//     * Fetches the entities whose 'main text property' matches the given filter text.
//     * <p>
//     * The matching is case insensitive. When passed an empty filter text,
//     * the method returns all categories. The returned list is ordered by name.
//     * The 'main text property' is different in each entity class and chosen in the specific subclass
//     *
//     * @param filter the filter text
//     *
//     * @return the list of matching entities
//     */
//    @Override
//    public List<Preferenza> findFilter(String filter) {
//        String normalizedFilter = filter.toLowerCase();
//        List<Preferenza> lista = findAll();
//
//        return lista.stream()
//                .filter(entity -> entity.getCode().toLowerCase().contains(normalizedFilter))
//                .collect(Collectors.toList());
//    }// end of method


//    /**
//     * Property unica (se esiste).
//     */
//    @Override
//    public String getPropertyUnica(AEntity entityBean) {
//        return ((Preferenza) entityBean).getCode();
//    }// end of method

//    /**
//     * Opportunità di controllare (per le nuove schede) che la key unica non esista già. <br>
//     * Invocato appena prima del save(), solo per una nuova entity <br>
//     *
//     * @param entityBean nuova da creare
//     */
//    @Override
//    public boolean isEsisteEntityKeyUnica(AEntity entityBean) {
//        return findByKeyUnica(((Preferenza) entityBean).getCode()) != null;
//    }// end of method

//    /**
//     * Opportunità di usare una idKey specifica. <br>
//     * Invocato appena prima del save(), solo per una nuova entity <br>
//     *
//     * @param entityBean da salvare
//     */
//    protected void creaIdKeySpecifica(AEntity entityBean) {
//        entityBean.id = ((Preferenza) entityBean).getCode();
//    }// end of method

    /**
     * Ordine di presentazione (obbligatorio, unico per tutte le eventuali company), <br>
     * Viene calcolato in automatico alla creazione della entity <br>
     * Recupera dal DB il valore massimo pre-esistente della property <br>
     * Incrementa di uno il risultato <br>
     */
    public int getNewOrdine() {
        int ordine = 0;

        List<Preferenza> lista = repository.findTop1AllByOrderByOrdineDesc();
        if (lista != null && lista.size() == 1) {
            ordine = lista.get(0).getOrdine();
        }// end of if cycle

        return ordine + 1;
    }// end of method


}// end of class