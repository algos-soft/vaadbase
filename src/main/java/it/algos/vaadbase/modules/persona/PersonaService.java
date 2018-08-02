package it.algos.vaadbase.modules.persona;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.backend.entity.AEntity;
import it.algos.vaadbase.backend.service.AService;
import it.algos.vaadbase.modules.address.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static it.algos.vaadbase.application.BaseCost.TAG_PER;

/**
 * Project vaadbase <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 10-mag-2018 6.41.22 <br>
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
@Qualifier(TAG_PER)
@Slf4j
@AIScript(sovrascrivibile = false)
public class PersonaService extends AService {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param repository per la persistenza dei dati
     */
    public PersonaService(@Qualifier(TAG_PER) MongoRepository repository) {
        super(repository);
    }// end of Spring constructor


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata
     * Eventuali regolazioni iniziali delle property
     * Senza properties per compatibilità con la superclasse
     *
     * @return la nuova entity appena creata (non salvata)
     */
    @Override
    public Persona newEntity() {
        return newEntity("", "");
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata
     * Eventuali regolazioni iniziali delle property
     * Properties obbligatorie
     * Gli argomenti (parametri) della new Entity DEVONO essere ordinati come nella Entity (costruttore lombok)
     *
     * @param nome:    obbligatorio
     * @param cognome: obbligatorio
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Persona newEntity(String nome, String cognome) {
        return newEntity(nome, cognome, "", "", (Address) null);
    }// end of method


    /**
     * Creazione in memoria di una nuova entity che NON viene salvata <br>
     * Eventuali regolazioni iniziali delle property <br>
     * All properties <br>
     * Gli argomenti (parametri) della new Entity DEVONO essere ordinati come nella Entity (costruttore lombok) <br>
     *
     * @param nome:      obbligatorio
     * @param cognome:   obbligatorio
     * @param telefono:  facoltativo
     * @param email:     facoltativo
     * @param indirizzo: via, nome e numero (facoltativo)
     *
     * @return la nuova entity appena creata (non salvata)
     */
    public Persona newEntity(String nome, String cognome, String telefono, String email, Address indirizzo) {
        Persona entity = null;

        entity = Persona.builderPersona()
                .nome(nome)
                .cognome(cognome)
                .telefono(telefono.equals("") ? null : telefono)
                .email(email.equals("") ? null : email)
                .indirizzo(indirizzo)
                .build();

        return entity;
    }// end of method

    /**
     * Property unica (se esiste).
     */
    public String getPropertyUnica(AEntity entityBean) {
        return ((Persona) entityBean).getNome()+((Persona) entityBean).getCognome();
    }// end of method

}// end of class