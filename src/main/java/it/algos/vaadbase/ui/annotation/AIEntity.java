package it.algos.vaadbase.ui.annotation;


import it.algos.vaadbase.backend.annotation.EACompanyRequired;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import it.algos.vaadbase.ui.enumeration.*;

/**
 * Project springvaadin
 * Created by Algos
 * User: gac
 * Date: ven, 13-ott-2017
 * Time: 14:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //can use in class and interface.
public @interface AIEntity {

    /**
     * (Optional) Visibilità a secondo del ruolo dell'User collegato
     * Defaults to guest.
     */
    EARoleType roleTypeVisibility() default EARoleType.guest;

    EACompanyRequired company() default EACompanyRequired.nonUsata;

}// end of interface annotation
