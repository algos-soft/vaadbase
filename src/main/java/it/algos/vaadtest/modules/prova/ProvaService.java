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
 * Date: 8-giu-2018 18.42.14 <br>
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
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * Costruttore <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param repository per la persistenza dei dati
     */
    @Autowired
    public ProvaService(@Qualifier(TAG_PRO) MongoRepository repository) {
        super(repository);
    }// end of Spring constructor


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle properties <br>
     * Senza parametri per compatibilità con la superclasse <br>
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public AEntity newEntity() {
        return addCompany(Prova.builder().ordine(getNewOrdine()).build());
    }// end of method


//    /**
//     * Returns instances of the company <br>
//     * Lista ordinata <br>
//     *
//     * @return lista ordinata di tutte le entities
//     */
//    public List<Prova> findAllByCompany() {
//        List<Prova> lista = null;
//        Company company = null;
//
//        if (login != null) {
//            company = (Company) login.getCompany();
//        }// end of if cycle
//
//        if (company != null) {
//            lista = findAllByCompany(company);
//        }// end of if cycle
//
//        return lista;
//    }// end of method
//
//
//    /**
//     * Returns instances of the company <br>
//     * Lista ordinata <br>
//     *
//     * @return lista ordinata di tutte le entities
//     */
//    public List<Prova> findAllByCompany(Company company) {
//        return ((ProvaRepository) repository).findAllByCompanyOrderByOrdineAsc(company);
//    }// end of method
//
//
//    /**
//     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria ed unica) <br>
//     *
//     * @param code di riferimento (obbligatorio)
//     *
//     * @return istanza della Entity, null se non trovata
//     */
//    public Prova findByKeyUnica(String code) {
//        Prova entity = null;
//        Company company = null;
//
//        if (login != null) {
//            company = login.getCompany();
//        }// end of if cycle
//
//        if (company != null) {
//            entity = findByKeyUnica(company, code);
//        }// end of if cycle
//
//        return entity;
//    }// end of method
//
//
//    /**
//     * Recupera una istanza della Entity usando la query della property specifica (obbligatoria ed unica) <br>
//     *
//     * @param code di riferimento (obbligatorio)
//     *
//     * @return istanza della Entity, null se non trovata
//     */
//    public Prova findByKeyUnica(Company company, String code) {
//        return ((ProvaRepository) repository).findByCompanyAndCode(company, code);
//    }// end of method

    

}// end of class