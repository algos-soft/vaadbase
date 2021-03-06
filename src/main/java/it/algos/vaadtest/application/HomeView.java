package it.algos.vaadtest.application;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.ui.AView;
import it.algos.vaadbase.ui.MainLayout;
import it.algos.vaadbase.ui.MainView;
import it.algos.vaadtest.training.ExampleTemplate;
import it.algos.vaadtest.training.VaadinWelcome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import static it.algos.vaadbase.application.BaseCost.TAG_HOM;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: sab, 24-mar-2018
 * Time: 15:58
 * Vista di partenza iniziale di questa applicazione <br>
 * <p>
 * Non annotated with @SpringComponent (sbagliato) <br>
 * Non annotated with @Scope (inutile) <br>
 * Non annotated with @SpringView (sbagliato) perché usa la @Route di VaadinFlow
 * Annotated with @Route (obbligatorio) per la selezione della vista. @Route(value = "") per la vista iniziale
 * Annotated with @AIScript (facoltativo) per controllare la ri-creazione di questo file nello script di algos <br>
 */
@Route(value = "")
@UIScope
@AIScript(sovrascrivibile = false)
public class HomeView extends AView {


    /**
     * Icona visibile nel menu (facoltativa)
     * Nella menuBar appare invece visibile il MENU_NAME, indicato qui
     * Se manca il MENU_NAME, di default usa il 'name' della view
     */
    public static final VaadinIcon VIEW_ICON = VaadinIcon.HOME;

    /**
     * Label del menu (facoltativa)
     * SpringNavigator usa il 'name' della Annotation @SpringView per identificare (internamente) e recuperare la view
     * Nella menuBar appare invece visibile il MENU_NAME, indicato qui
     * Se manca il MENU_NAME, di default usa il 'name' della view
     */
    public static final String MENU_NAME = TAG_HOM;


    /**
     * Costruttore @Autowired
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation
     * Si usa un @Qualifier(), per avere la sottoclasse specifica
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti
     * Use @Lazy to avoid the Circular Dependency
     * A simple way to break the cycle is saying Spring to initialize one of the beans lazily.
     * That is: instead of fully initializing the bean, it will create a proxy to inject it into the other bean.
     * The injected bean will only be fully created when it’s first needed.
     */
    public HomeView() {
    }// end of constructor




    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        removeAll();
        add(new VaadinWelcome());
//        add(new ExampleTemplate());
//        add(new Button("Click me",evt-> {add(new Label("added from button"));}));
    }// end of method



}// end of class
