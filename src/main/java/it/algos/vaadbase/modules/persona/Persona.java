package it.algos.vaadbase.modules.persona;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.backend.annotation.EACompanyRequired;
import it.algos.vaadbase.backend.entity.ACEntity;
import it.algos.vaadbase.backend.entity.AEntity;
import it.algos.vaadbase.modules.address.Address;
import it.algos.vaadbase.modules.address.AddressPresenter;
import it.algos.vaadbase.modules.role.Role;
import it.algos.vaadbase.modules.role.RoleService;
import it.algos.vaadbase.modules.utente.Utente;
import it.algos.vaadbase.ui.annotation.*;
import it.algos.vaadbase.ui.enumeration.EAFieldAccessibility;
import it.algos.vaadbase.ui.enumeration.EAFieldType;
import lombok.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Collection;

import static it.algos.vaadbase.application.BaseCost.TAG_PER;

/**
 * Project vaadbase <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 10-mag-2018 6.41.22 <br>
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 * Estende la classe Utente che implementa UserDetails, CredentialsContainer per la Security <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Document (facoltativo) per avere un nome della collection (DB Mongo) diverso dal nome della Entity <br>
 * Annotated with @Scope (obbligatorio = 'singleton') <br>
 * Annotated with @Data (Lombok) for automatic use of Getter and Setter <br>
 * Annotated with @NoArgsConstructor (Lombok) for JavaBean specifications <br>
 * Annotated with @AllArgsConstructor (Lombok) per usare il costruttore completo nel Service <br>
 * Annotated with @Builder (Lombok) con un metodo specifico, per usare quello standard nella (eventuale) sottoclasse <br>
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
@Document(collection = "persona")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderPersona")
@EqualsAndHashCode(callSuper = false)
@Qualifier(TAG_PER)
@AIEntity(company = EACompanyRequired.nonUsata)
@AIList(fields = {"nome", "cognome"})
@AIForm(fields = {"nome", "cognome", "telefono", "email", "indirizzo"})
@AIScript(sovrascrivibile = false)
public class Persona extends Utente {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;


    /**
     * nome (obbligatorio, non unica)
     */
    @NotNull
    @Indexed()
    @Size(min = 4, max = 40)
    @AIField(type = EAFieldType.text, required = true, focus = true, widthEM = 20)
    @AIColumn(width = 200)
    private String nome;

    /**
     * cognome (obbligatorio, non unica)
     */
    @NotNull
    @Indexed()
    @Size(min = 4, max = 40)
    @AIField(type = EAFieldType.text, firstCapital = true, widthEM = 20)
    @AIColumn(width = 200)
    private String cognome;

    /**
     * telefono (facoltativo)
     */
    @AIField(type = EAFieldType.text)
    @AIColumn(width = 160)
    private String telefono;


    /**
     * posta elettronica (facoltativo)
     */
    @AIField(type = EAFieldType.email, widthEM = 24)
    @AIColumn(width = 350, name = "Mail")
    private String email;


    /**
     * indirizzo (facoltativo, non unica)
     * riferimento statico SENZA @DBRef (embedded)
     */
    @AIField(type = EAFieldType.link, clazz = AddressPresenter.class, help = "Indirizzo")
    @AIColumn(width = 400, name = "Indirizzo")
    private Address indirizzo;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return nome + " " + cognome;
    }// end of method


}// end of entity class