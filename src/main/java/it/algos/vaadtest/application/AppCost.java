package it.algos.vaadtest.application;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: ven, 06-apr-2018
 * Time: 14:43
 * Completa la classe BaseCost con le costanti statiche specifiche di questa applicazione <br>
 * <p>
 * Non annotated with @SpringComponent (inutile) <br>
 * Non annotated with @Scope (inutile) <br>
 * Annotated with @AIScript (facoltativo) per controllare la ri-creazione di questo file nello script di algos <br>
 */
@AIScript(sovrascrivibile = false)
public class AppCost {
	public final static String TAG_PRO = "prova";
	public final static String TAG_BOL = "bolla";
    public static final String TAG_HOME_MENU = "home";
}// end of static class