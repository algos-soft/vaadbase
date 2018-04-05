package it.algos.vaadbase.modules.prova;

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
import static it.algos.vaadbase.application.BaseCost.TAG_PRO;

/**
 * Project vaadbase
 * Created by Algos
 * User: Gac
 * Date: 5-apr-2018 12.31.00
 * <br>
 * Estende la Entity astratta AService. Layer di collegamento tra il Presenter e la Repository. <br>
 * Annotated with @@Slf4j (facoltativo) per i logs automatici <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Service (ridondante) <br>
 * Annotated with @Scope (obbligatorio = 'singleton') <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica <br>
 * Annotated with @AIScript (facoltativo) per controllare la ri-creazione di questo file nello script del framework <br>
 */
@Slf4j
@SpringComponent
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_PRO)
@AIScript(sovrascrivibile = true)
public class ProvaService extends AService {


    /**
     * La repository viene iniettata dal costruttore, in modo che sia disponibile nella superclasse, <br>
     * dove viene usata l'interfaccia MongoRepository
     * Spring costruisce al volo, quando serve, una implementazione di RoleRepository (come previsto dal @Qualifier) <br>
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici <br>
     */
    private ProvaRepository repository;


    /**
     * Costruttore @Autowired (nella superclasse) <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     */
    public ProvaService(@Qualifier(TAG_PRO) MongoRepository repository) {
        super(repository);
        this.repository = (ProvaRepository) repository;
        super.entityClass = Prova.class;
   }// end of Spring constructor

    /**
     * Ricerca di una entity (la crea se non la trova) <br>
     *
     * @param code di riferimento (obbligatorio ed unico)
     *
     * @return la entity trovata o appena creata
     */
    public Prova findOrCrea(String code) {
        Prova entity = findByKeyUnica(code);

        if (entity == null) {
            entity = newEntity(0, code);
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
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     * Gli argomenti (parametri) della new Entity DEVONO essere ordinati come nella Entity (costruttore lombok) <br>
     *
     * @param ordine      di presentazione (obbligatorio con inserimento automatico se è zero)
	* @param code        codice di riferimento (obbligatorio)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Prova newEntity(int ordine, String code) {
        Prova entity = null;

        entity = findByKeyUnica(code);
		if (entity != null) {
			return findByKeyUnica(code);
		}// end of if cycle
		
        entity = Prova.builder()
				.ordine(ordine != 0 ? ordine : this.getNewOrdine())
				.code(code)
                .build();

        return entity;
    }// end of method

    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria ed unica) <br>
     *
     * @param code di riferimento (obbligatorio)
     *
     * @return istanza della Entity, null se non trovata
     */
    public Prova findByKeyUnica(String code) {
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
    public List<Prova> findAll() {
        return repository.findAllByOrderByCodeAsc();
    }// end of method


    /**
     * Opportunità di controllare (per le nuove schede) che la key unica non esista già. <br>
     * Invocato appena prima del save(), solo per una nuova entity <br>
     *
     * @param entityBean nuova da creare
     */
    @Override
    protected boolean isEsisteEntityKeyUnica(AEntity entityBean) {
        return findByKeyUnica(((Prova) entityBean).getCode()) != null;
    }// end of method

    /**
     * Opportunità di usare una idKey specifica. <br>
     * Invocato appena prima del save(), solo per una nuova entity <br>
     *
     * @param entityBean da salvare
     */
    protected void creaIdKeySpecifica(AEntity entityBean) {
        entityBean.id = ((Prova)entityBean).getCode();
    }// end of method

    /**
     * Ordine di presentazione (obbligatorio, unico per tutte le eventuali company), <br>
     * Viene calcolato in automatico alla creazione della entity <br>
     * Recupera dal DB il valore massimo pre-esistente della property <br>
     * Incrementa di uno il risultato <br>
     */
    public int getNewOrdine() {
        int ordine = 0;

        List<Prova> lista = repository.findTop1AllByOrderByOrdineDesc();
        if (lista != null && lista.size() == 1) {
            ordine = lista.get(0).getOrdine();
        }// end of if cycle

        return ordine + 1;
    }// end of method

}// end of class