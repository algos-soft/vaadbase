package it.algos.vaadtest.application;

import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: ven, 06-apr-2018
 * Time: 14:43
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AppCost {
	public static final String TAG_BOL ="bolla";
	public static final String TAG_PRO ="prova";


	public static final String MENU_VERT ="menuVerticale";
	public static final String MENU_DIV ="menuDiv";
}// end of class