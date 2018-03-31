package it.algos.vaadbase.modules.role;

import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: gio, 22-mar-2018
 * Time: 21:09
 */
public enum EARole {
    developer, admin, user, guest;
}// end of enumeration class
