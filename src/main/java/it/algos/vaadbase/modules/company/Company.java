package it.algos.vaadbase.modules.company;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.backend.annotation.EACompanyRequired;
import it.algos.vaadbase.backend.entity.AEntity;
import it.algos.vaadbase.modules.address.Address;
import it.algos.vaadbase.modules.address.AddressPresenter;
import it.algos.vaadbase.modules.persona.Persona;
import it.algos.vaadbase.modules.persona.PersonaPresenter;
import it.algos.vaadbase.ui.annotation.*;
import it.algos.vaadbase.ui.enumeration.EAFieldType;
import lombok.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static it.algos.vaadbase.application.BaseCost.TAG_COM;

/**
 * Project vaadbase <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 8-mag-2018 18.10.53 <br>
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Document (facoltativo) per avere un nome della collection (DB Mongo) diverso dal nome della Entity <br>
 * Annotated with @Scope (obbligatorio = 'singleton') <br>
 * Annotated with @Data (Lombok) for automatic use of Getter and Setter <br>
 * Annotated with @NoArgsConstructor (Lombok) for JavaBean specifications <br>
 * Annotated with @AllArgsConstructor (Lombok) per usare il costruttore completo nel Service <br>
 * Annotated with @Builder (Lombok) lets you automatically produce the code required to have your class
 * be instantiable with code such as: Person.builder().name("Adam Savage").city("San Francisco").build(); <br>
 * Annotated with @EqualsAndHashCode (Lombok) per l'uguaglianza di due istanze dellaq classe <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica <br>
 * Annotated with @AIEntity (facoltativo Algos) per alcuni parametri generali del modulo <br>
 * Annotated with @AIList (facoltativo Algos) per le colonne automatiche della Lista  <br>
 * Annotated with @AIForm (facoltativo Algos) per i fields automatici del Dialog e del Form <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 * Inserisce SEMPRE la versione di serializzazione <br>
 * Le singole property sono annotate con @AIField (obbligatorio Algos) per il tipo di Field nel Dialog e nel Form <br>
 * Le singole property sono annotate con @AIColumn (facoltativo Algos) per il tipo di Column nella Grid <br>
 */
@SpringComponent
@Document(collection = "company")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Qualifier(TAG_COM)
@AIEntity(company = EACompanyRequired.nonUsata)
@AIList(fields = {"code", "descrizione", "telefono", "email"})
@AIForm(fields = {"code", "descrizione", "contatto", "telefono", "email", "indirizzo", "note"})
@AIScript(sovrascrivibile = false)
public class Company extends AEntity {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * codice di riferimento interno (obbligatorio, unico) <br>
     */
    @NotNull
    @Indexed()
    @Size(min = 3)
    @AIField(type = EAFieldType.text, required = true, focus = true, widthEM = 12)
    @AIColumn(width = 210)
    private String code;


    /**
     * descrizione (obbligatoria, non unica) <br>
     */
    @NotNull(message = "La descrizione è obbligatoria")
    @Size(min = 2, max = 50)
    @AIField(type = EAFieldType.text, firstCapital = true, widthEM = 24)
    @AIColumn(width = 370)
    private String descrizione;


    /**
     * persona di riferimento (facoltativo)
     * riferimento statico SENZA @DBRef
     */
    @AIField(type = EAFieldType.link, clazz = PersonaPresenter.class, help = "Riferimento")
    @AIColumn(width = 220, name = "Riferimento")
    private Persona contatto;


    /**
     * telefono (facoltativo)
     */
    @AIField(type = EAFieldType.text)
    @AIColumn(width = 170)
    private String telefono;


    /**
     * posta elettronica (facoltativo)
     */
    @AIField(type = EAFieldType.email, widthEM = 24)
    @AIColumn(width = 350, name = "Mail")
    private String email;


    /**
     * indirizzo (facoltativo)
     * riferimento statico SENZA @DBRef
     */
    @AIField(type = EAFieldType.link, clazz = AddressPresenter.class, help = "Indirizzo")
    @AIColumn(width = 400, name = "Indirizzo")
    private Address indirizzo;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getCode();
    }// end of method


}// end of entity class