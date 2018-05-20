package it.algos.vaadbase.modules.address;

import it.algos.vaadbase.backend.annotation.EACompanyRequired;
import it.algos.vaadbase.ui.annotation.*;
import it.algos.vaadbase.ui.enumeration.EAListButton;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.context.annotation.Scope;
import lombok.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.ui.enumeration.EARoleType;
import it.algos.vaadbase.backend.entity.AEntity;
import it.algos.vaadbase.backend.entity.ACEntity;
import it.algos.vaadbase.ui.annotation.AIColumn;
import it.algos.vaadbase.ui.annotation.AIField;
import it.algos.vaadbase.ui.enumeration.EAFieldAccessibility;
import it.algos.vaadbase.ui.enumeration.EAFieldType;
import it.algos.vaadbase.annotation.*;
import static it.algos.vaadbase.application.BaseCost.TAG_ADD;

/**
 * Project vaadbase <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 9-mag-2018 21.12.07 <br>
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
@Document(collection = "address")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Qualifier(TAG_ADD)
@AIEntity(company = EACompanyRequired.nonUsata)
@AIList(fields = {"indirizzo", "localita", "cap"})
@AIForm(fields = {"indirizzo", "localita", "cap"})
@AIScript(sovrascrivibile = false)
public class Address extends AEntity {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;

    
	/**
     * indirizzo: via, nome e numero (obbligatoria, non unica)
     */
    @NotNull(message = "L'indirizzo è obbligatorio")
    @Size(min = 2, max = 50)
    @AIField(type = EAFieldType.text, required = true, focus = true, widthEM = 12)
    @AIColumn(width = 210)
    private String indirizzo;


    /**
     * località (obbligatoria, non unica)
     */
    @NotNull(message = "La località è obbligatoria")
    @Size(min = 2, max = 50)
    @AIField(type = EAFieldType.text, firstCapital = true, widthEM = 24)
    @AIColumn(width = 370)
    private String localita;


    /**
     * codice di avviamento postale (facoltativo, non unica)
     */
    @Size(min = 5, max = 5, message = "Il codice postale deve essere di 5 cifre")
    @AIField(type = EAFieldType.text, widthEM = 5)
    @AIColumn(width = 370)
    private String cap;

//    /**
//     * stato (obbligatoria, non unica)
//     * riferimento dinamico con @DBRef (obbligatorio per il ComboBox)
//     */
//    @DBRef
//    @NotNull
//    @AIField(type = EAFieldType.combo, clazz = StatoService.class)
//    @AIColumn(width = 140)
//    private Stato stato;

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        String value = "";
        String spazio = " ";
        String sep = " - ";

        value += indirizzo;
        value += (localita != null && !localita.equals("")) ? sep + localita : "";
        value += spazio + cap;

        return value.trim();
    }// end of method


}// end of entity class