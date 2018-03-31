package it.algos.vaadbase.modules.address;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.algos.vaadbase.application.BaseCost;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.context.annotation.Scope;
import lombok.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.backend.entity.AEntity;
//import it.algos.vaadbase.enumeration.EARoleType;
//import it.algos.vaadbase.enumeration.EAListButton;
//import it.algos.vaadbase.enumeration.EACompanyRequired;
//import it.algos.vaadbase.enumeration.EAFieldAccessibility;
//import it.algos.vaadbase.enumeration.EAFieldType;
import it.algos.vaadbase.annotation.*;

import static it.algos.vaadbase.application.BaseCost.TAG_ADD;
//import it.algos.vaadbase.entity.AEntity;


/**
 * Project vaadbase
 * Created by Algos
 * User: Gac
 * Date: 2018-03-22
 * Estende la Entity astratta AEntity che contiene la key property ObjectId
 * Annotated with @SpringComponent (obbligatorio)
 * Annotated with @Document (facoltativo) per avere un nome della collection (DB Mongo) diverso dal nome della Entity
 * Annotated with @Scope (obbligatorio = 'session')
 * Annotated with @Data (Lombok) for automatic use of Getter and Setter
 * Annotated with @NoArgsConstructor (Lombok) for JavaBean specifications
 * Annotated with @AllArgsConstructor (Lombok) per usare il costruttore completo nel Service
 * Annotated with @Builder (Lombok) lets you automatically produce the code required to have your class
 * be instantiable with code such as: Person.builder().name("Adam Savage").city("San Francisco").build();
 * Annotated with @EqualsAndHashCode (facoltativo) per ???
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica
 * Annotated with @AIEntity (facoltativo) per alcuni parametri generali del modulo
 * Annotated with @AIList (facoltativo) per le colonne della Lista e loro visibilità/accessibilità relativa all'utente
 * Annotated with @AIForm (facoltativo) per i fields del Form e loro visibilità/accessibilità relativa all'utente
 * Annotated with @AIScript (facoltativo) per controllare la ri-creazione di questo file nello script del framework
 * Inserisce SEMPRE la versione di serializzazione che viene poi filtrata per non mostrarla in List e Form
 * Le singole property sono annotate con @AIField (obbligatorio per il tipo di Field) e @AIColumn (facoltativo)
 */
@SpringComponent
@Document(collection = "address")
@Scope("session")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@Qualifier(TAG_ADD)
//@AIEntity(roleTypeVisibility = EARoleType.user, company = EACompanyRequired.nonUsata)
//@AIList(fields = {"code", "descrizione"}, dev = EAListButton.standard, admin = EAListButton.noSearch, user = EAListButton.show)
//@AIForm(fields = {"code", "descrizione"})
@AIScript(sovrascrivibile = false)
public class Address extends AEntity {


    /**
     * versione della classe per la serializzazione
     */
    private final static long serialVersionUID = 1L;

    //@TODO
    // Le properties riportate sono INDICATIVE e possono/debbono essere sostituite
    //@TODO

    /**
     * codice di riferimento (obbligatorio)
     */
    @NotEmpty
    @Size(min = 2, max = 20)
    @Indexed()
//    @AIField(
//            type = EAFieldType.text,
//            required = true,
//            focus = true,
//            name = "Codice",
//            widthEM = 9,
//            admin = EAFieldAccessibility.allways,
//            user = EAFieldAccessibility.showOnly)
//    @AIColumn(name = "Code", width = 120)
    private String code;



    /**
     * descrizione (facoltativa)
     */
//    @AIField(
//            type = EAFieldType.text,
//            required = true,
//            name = "Descrizione completa",
//            widthEM = 26,
//            admin = EAFieldAccessibility.allways,
//            user = EAFieldAccessibility.showOnly)
//    @AIColumn(name = "Descrizione", width = 500)
    private String descrizione;


    /**
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return getCode();
    }// end of method


}// end of entity class