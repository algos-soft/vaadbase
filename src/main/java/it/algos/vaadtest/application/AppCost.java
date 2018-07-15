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
	public final static String TAG_VER = "versione";
	public final static String TAG_PRO = "prova";
	public final static String TAG_PRO2 = "prova2";
	public final static String TAG_BOL = "bolla";
    public static final String TAG_HOME_MENU = "home";


	// generali
	public final static String USA_DEBUG = "usaDebug";
	public final static String USA_LOG_DEBUG = "usaLogDebug";
	public final static String USA_COMPANY = "usaCompany";


	// moduli visibili
	public final static String SHOW_COMPANY = "showCompany";
	public final static String SHOW_PREFERENZA = "showPreferenza";
	public final static String SHOW_WIZARD = "showWizard";
	public final static String SHOW_DEVELOPER = "showDeveloper";
	public final static String SHOW_ADDRESS = "showAddress";
	public final static String SHOW_PERSON = "showPerson";
	public final static String SHOW_ROLE = "showRole";
	public final static String SHOW_VERSION = "showVersion";
	public final static String SHOW_LOGGER = "showLogger";


}// end of static class