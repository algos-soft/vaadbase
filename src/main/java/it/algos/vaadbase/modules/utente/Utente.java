package it.algos.vaadbase.modules.utente;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.backend.annotation.EACompanyRequired;
import it.algos.vaadbase.backend.entity.ACEntity;
import it.algos.vaadbase.backend.entity.AEntity;
import it.algos.vaadbase.modules.role.Role;
import it.algos.vaadbase.modules.role.RoleService;
import it.algos.vaadbase.ui.annotation.*;
import it.algos.vaadbase.ui.enumeration.EAFieldType;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import static it.algos.vaadbase.application.BaseCost.TAG_UTE;

/**
 * Project vaadbase <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 25-lug-2018 6.31.31 <br>
 * <p>
 * Estende la entity astratta AEntity che contiene la key property ObjectId <br>
 * Riporto qui le caratteristiche della classe:
 *
 * @see org.springframework.security.core.userdetails.User;
 * in modo che questa classe estenda comunque AEntity (per omogeneità)
 * <p>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Document (facoltativo) per avere un nome della collection (DB Mongo) diverso dal nome della Entity <br>
 * Annotated with @Scope (obbligatorio = 'singleton') <br>
 * Annotated with @Data (Lombok) for automatic use of Getter and Setter <br>
 * Annotated with @Builder (Lombok) con un metodo specifico, per usare quello standard nella (eventuale) sottoclasse <br>
 * Annotated with @EqualsAndHashCode (Lombok) per l'uguaglianza di due istanze dellaq classe <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica <br>
 * Annotated with @AIEntity (facoltativo Algos) per alcuni parametri generali del modulo <br>
 * Annotated with @AIList (facoltativo Algos) per le colonne automatiche della Lista  <br>
 * Annotated with @AIForm (facoltativo Algos) per i fields automatici del Dialog e del Form <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 * Inserisce SEMPRE la versione di serializzazione <br>
 * Le singole property sono pubbliche in modo da poterne leggere il valore tramite 'reflection'
 * Le singole property sono annotate con @AIField (obbligatorio Algos) per il tipo di Field nel Dialog e nel Form <br>
 * Le singole property sono annotate con @AIColumn (facoltativo Algos) per il tipo di Column nella Grid <br>
 */
@SpringComponent
@Document(collection = "utente")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(builderMethodName = "builderUtente")
@EqualsAndHashCode(callSuper = false)
@Qualifier(TAG_UTE)
@AIEntity(company = EACompanyRequired.nonUsata)
@AIList(fields = {"username", "password", "enabled", "role"})
@AIForm(fields = {"username", "password", "enabled", "role"})
@AIScript(sovrascrivibile = false)
public class Utente extends ACEntity {


    private static final long serialVersionUID = 500L;

    /**
     * Service iniettato da Spring (@Scope = 'singleton'). Unica per tutta l'applicazione. Usata come libreria.
     */
    @Autowired
    public UtenteService service;


    @AIField(type = EAFieldType.text)
    public String username;

    @AIField(type = EAFieldType.text)
    public String password;

    @AIField(type = EAFieldType.checkbox)
    public boolean enabled;

    /**
     * ruolo (obbligatorio, non unico)
     * riferimento dinamico con @DBRef (obbligatorio per il ComboBox)
     */
    @DBRef
    @AIField(type = EAFieldType.combo, required = true, clazz = RoleService.class)
    @AIColumn(name = "Ruolo", width = 200)
    public Role role;

    public boolean isUser() {
        return service.isUser(this);
    }// end of method

    public boolean isAdmin() {
        return service.isAdmin(this);
    }// end of method

    public boolean isDev() {
        return service.isDev(this);
    }// end of method

}// end of entity class