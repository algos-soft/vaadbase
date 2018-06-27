package it.algos.vaadbase.ui.menu;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.ui.AView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;


/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: sab, 05-mag-2018
 * Time: 21:35
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AMenuDiv extends Div implements IAMenu {

    protected List<RouterLink> arrayRouterLink;
    protected H2 title;


    public AMenuDiv() {
    }// end of constructor

    @Override
    public Component getComponent() {
        Div navigation = null;
        Div header = null;

        arrayRouterLink = new ArrayList<>();
        title = new H2();
        title.addClassName("main-layout__title");

//        --Crea i menu per la gestione delle @routes (standard e specifiche)
//        this.addAllRoutes();

        if (arrayRouterLink.size() > 0) {
            navigation = new Div();
            for (RouterLink link : arrayRouterLink) {
                link.addClassName("main-layout__nav-item");
                navigation.add(link);
            }// end of for cycle
        }// end of if cycle

        if (navigation != null) {
            navigation.addClassName("main-layout__nav");

            header = new Div(title, navigation);
            header.addClassName("main-layout__header");
            add(header);
        }// end of if cycle

        addClassName("main-layout");
//        this.creaBody();
//        updateView();

        return this;
    }// end of Spring constructor


    /**
     * Adds a view to the UI
     *
     * @param viewClazz the view class to instantiate
     */
    public RouterLink addView(Class<? extends AView> viewClazz, Icon icon, String tagMenu) {
        RouterLink routerLink = null;

        try { // prova ad eseguire il codice
            routerLink = new RouterLink("", viewClazz);

        } catch (Exception unErrore) { // intercetta l'errore
            log.error(unErrore.toString());
        }// fine del blocco try-catch

        if (routerLink != null) {
            routerLink.add(icon, new Text(tagMenu));
            routerLink.addClassName("main-layout__nav-item");
            arrayRouterLink.add(routerLink);
        }// end of if cycle

        return routerLink;
    }// end of method

}// end of class
