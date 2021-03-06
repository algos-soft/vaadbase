package it.algos.vaadbase.backend.entity;

import it.algos.vaadbase.modules.company.Company;
import it.algos.vaadbase.modules.company.CompanyService;
import it.algos.vaadbase.ui.annotation.AIColumn;
import it.algos.vaadbase.ui.annotation.AIField;
import it.algos.vaadbase.ui.enumeration.EAFieldAccessibility;
import it.algos.vaadbase.ui.enumeration.EAFieldType;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: ven, 22-dic-2017
 * Time: 12:14
 */
@Getter
public abstract class ACEntity extends AEntity {


    /**
     * Riferimento alla company2 (per le sottoclassi che usano questa classe)
     * - Nullo se il flag AlgosApp.USE_MULTI_COMPANY=false
     * - Facoltativo od obbligatorio a seconda della sottoclasse, se il flag AlgosApp.USE_MULTI_COMPANY=true
     * riferimento dinamico CON @DBRef
     */
    @DBRef
    @AIField(type = EAFieldType.combo, clazz = CompanyService.class, dev = EAFieldAccessibility.newOnly, admin = EAFieldAccessibility.showOnly)
    @AIColumn(name = "Company", width = 115)
    public Company company;


}// end of class
