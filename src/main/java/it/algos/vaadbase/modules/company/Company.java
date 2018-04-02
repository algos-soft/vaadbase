package it.algos.vaadbase.modules.company;

import it.algos.vaadbase.backend.annotation.EACompanyRequired;
import it.algos.vaadbase.ui.annotation.*;
import it.algos.vaadbase.ui.enumeration.EAListButton;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.context.annotation.Scope;
import lombok.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.ui.enumeration.EARoleType;
import it.algos.vaadbase.backend.entity.AEntity;
import it.algos.vaadbase.ui.annotation.AIColumn;
import it.algos.vaadbase.ui.annotation.AIField;
import it.algos.vaadbase.ui.enumeration.EAFieldAccessibility;
import it.algos.vaadbase.ui.enumeration.EAFieldType;
import it.algos.vaadbase.annotation.*;
import static it.algos.vaadbase.application.BaseCost.TAG_COM;

/**
 * Project vaadbase
 * Created by Algos
 * User: Gac
 * Date: 2018-04-02
 * <br>
 * Estende la Entity astratta AEntity che contiene la key property ObjectId <br>
 * Annotated with @SpringComponent (obbligatorio) <br>
 * Annotated with @Document (facoltativo) per avere un nome della collection (DB Mongo) diverso dal nome della Entity <br>
 * Annotated with @Scope (obbligatorio = 'singleton') <br>
 * Annotated with @Data (Lombok) for automatic use of Getter and Setter <br>
 * Annotated with @NoArgsConstructor (Lombok) for JavaBean specifications <br>
 * Annotated with @AllArgsConstructor (Lombok) per usare il costruttore completo nel Service <br>
 * Annotated with @Builder (Lombok) lets you automatically produce the code required to have your class
 * be instantiable with code such as: Person.builder().name("Adam Savage").city("San Francisco").build(); <br>
 * Annotated with @EqualsAndHashCode (facoltativo) per ??? <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica <br>
 * Annotated with @AIEntity (facoltativo) per alcuni parametri generali del modulo <br>
 * Annotated with @AIList (facoltativo) per le colonne della Lista e loro visibilità/accessibilità relativa all'utente <br>
 * Annotated with @AIForm (facoltativo) per i fields del Form e loro visibilità/accessibilità relativa all'utente <br>
 * Annotated with @AIScript (facoltativo) per controllare la ri-creazione di questo file nello script del framework <br>
 * Inserisce SEMPRE la versione di serializzazione che viene poi filtrata per non mostrarla in List e Form <br>
 * Le singole property sono annotate con @AIField (obbligatorio per il tipo di Field) e @AIColumn (facoltativo) <br>
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
@AIEntity(roleTypeVisibility = EARoleType.user, company = EACompanyRequired.nonUsata)
@AIList(fields = {"code", "descrizione"}, dev = EAListButton.standard, admin = EAListButton.noSearch, user = EAListButton.show)
@AIForm(fields = {"code", "descrizione"})
@AIScript(sovrascrivibile = true)
public class Company extends AEntity {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;

    

    /**
     * codice di riferimento (obbligatorio, unico)
     */
    @NotEmpty
    @Size()
    @Indexed()
    @AIField(type = EAFieldType.text, required = true, focus = true, widthEM = 12, dev = EAFieldAccessibility.allways)
    @AIColumn(width = 210)
    private String code;

    /**
     * descrizione (obbligatoria, non unica)
     */
    @NotEmpty(message = "La descrizione è obbligatoria")
    @Size(min = 2, max = 50)
    @AIField(type = EAFieldType.text, firstCapital = true, widthEM = 24, help = "Descrizione della company")
    @AIColumn(width = 370)
    private String descrizione;

    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getCode();
    }// end of method



}// end of entity class