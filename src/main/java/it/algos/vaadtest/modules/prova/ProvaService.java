package it.algos.vaadtest.modules.prova;

import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.backend.service.AService;
import it.algos.vaadbase.backend.entity.AEntity;
import static it.algos.vaadtest.application.AppCost.TAG_PRO;

/**
 * Project vaadtest <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 9-mag-2018 18.04.25 <br>
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
@Qualifier(TAG_PRO)
@Slf4j
@AIScript(sovrascrivibile = false)
public class ProvaService extends AService {


    /**
     * La repository viene iniettata dal costruttore e passata al costruttore della superclasse, <br>
     * Spring costruisce una implementazione concreta dell'interfaccia MongoRepository (come previsto dal @Qualifier) <br>
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici <br>
     */
    private ProvaRepository repository;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param repository per la persistenza dei dati
     */
    public ProvaService(@Qualifier(TAG_PRO) MongoRepository repository) {
        super(repository);
        this.repository = (ProvaRepository) repository;
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

    /**
     * Creazione in memoria di una nuova entity che NON viene salvata
     * Eventuali regolazioni iniziali delle property
     * Senza properties per compatibilità con la superclasse
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public Prova newEntity() {
        return newEntity(0, "");
    }// end of method


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
        List<Prova> lista = null;

        try { // prova ad eseguire il codice
            lista = repository.findAllByOrderByOrdineAsc();
        } catch (Exception unErrore) { // intercetta l'errore
            log.error(unErrore.toString());
            try { // prova ad eseguire il codice
                lista = repository.findAllByOrderByCodeAsc();
            } catch (Exception unErrore2) { // intercetta l'errore
                log.error(unErrore2.toString());
                try { // prova ad eseguire il codice
                    lista = repository.findAll();
                } catch (Exception unErrore3) { // intercetta l'errore
                    log.error(unErrore3.toString());
                }// fine del blocco try-catch
            }// fine del blocco try-catch
        }// fine del blocco try-catch

        return lista;
    }// end of method


    /**
     * Fetches the entities whose 'main text property' matches the given filter text.
     * <p>
     * The matching is case insensitive. When passed an empty filter text,
     * the method returns all categories. The returned list is ordered by name.
     * The 'main text property' is different in each entity class and chosen in the specific subclass
     *
     * @param filter the filter text
     *
     * @return the list of matching entities
     */
    @Override
    public List<Prova> findFilter(String filter) {
        String normalizedFilter = filter.toLowerCase();
        List<Prova> lista = findAll();

        return lista.stream()
                .filter(entity -> entity.getCode().toLowerCase().contains(normalizedFilter))
                .sorted((entity1, entity2) -> entity1.getCode().compareToIgnoreCase(entity2.getCode()))
                .collect(Collectors.toList());
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