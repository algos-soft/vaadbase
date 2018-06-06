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
import org.springframework.data.domain.Sort;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.backend.service.AService;
import it.algos.vaadbase.backend.entity.AEntity;
import it.algos.vaadbase.modules.company.Company;
import static it.algos.vaadtest.application.AppCost.TAG_PRO;

/**
 * Project vaadtest <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 6-giu-2018 15.46.15 <br>
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
            entity = newEntity((Company) null, 0, code);
            save(entity);
        }// end of if cycle

        return entity;
    }// end of method

    /**
     * Crea una entity e la registra <br>
     *
     * @param code di riferimento (obbligatorio ed unico)
     *
     * @return la entity appena creata
     */
    public Prova crea(String code) {
        Prova entity = newEntity((Company) null, 0, code);
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
    public Prova newEntity() {
        return newEntity((Company) null, 0, "");
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
    public Prova newEntity(Company company, int ordine, String code) {
        Prova entity = null;

        entity = findByKeyUnica(code);
		if (entity != null) {
			return findByKeyUnica(code);
		}// end of if cycle
		
        entity = Prova.builder()
				.ordine(ordine != 0 ? ordine : this.getNewOrdine(company))
				.code(code.equals("") ? null : code)
                .build();

        return (Prova) addCompany(entity, company);
    }// end of method


    /**
     * Returns instances of the company <br>
     * Lista ordinata <br>
     *
     * @return lista ordinata di tutte le entities
     */
    public List<Prova> findAllByCompany() {
        List<Prova> lista = null;
        Company company = null;

        if (login != null) {
            company = (Company) login.getCompany();
        }// end of if cycle

        if (company != null) {
            lista = findAllByCompany(company);
        }// end of if cycle

        return lista;
    }// end of method


    /**
     * Returns instances of the company <br>
     * Lista ordinata <br>
     *
     * @return lista ordinata di tutte le entities
     */
    public List<Prova> findAllByCompany(Company company) {
        return repository.findAllByCompanyOrderByOrdineAsc(company);
    }// end of method



    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria ed unica) <br>
     *
     * @param code di riferimento (obbligatorio)
     *
     * @return istanza della Entity, null se non trovata
     */
    public Prova findByKeyUnica(String code) {
        Prova entity = null;
        Company company = null;

        if (login != null) {
            company = login.getCompany();
        }// end of if cycle

        if (company != null) {
            entity = findByKeyUnica(company, code);
        }// end of if cycle

        return entity;
    }// end of method


    /**
     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria ed unica) <br>
     *
     * @param code di riferimento (obbligatorio)
     *
     * @return istanza della Entity, null se non trovata
     */
    public Prova findByKeyUnica(Company company, String code) {
        return repository.findByCompanyAndCode(company, code);
    }// end of method

    

    /**
     * Ordine di presentazione (obbligatorio, unico per tutte le company), <br>
     * Viene calcolato in automatico alla creazione della entity <br>
     * Recupera dal DB il valore massimo pre-esistente della property <br>
     * Incrementa di uno il risultato <br>
     */
    public int getNewOrdine(Company company) {
        int ordine = 0;
        List<Prova> lista = findAllByCompany(company);

        if (lista != null && lista.size() > 0) {
            ordine = lista.get(lista.size() - 1).getOrdine();
        }// end of if cycle

        return ordine + 1;
    }// end of method

}// end of class