package it.algos.vaadbase.ui;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.*;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.algos.vaadbase.modules.role.RoleService;
import it.algos.vaadbase.presenter.IAPresenter;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadbase.application.BaseCost.TAG_ROL;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: sab, 24-mar-2018
 * Time: 15:58
 */
@Slf4j
@Tag("storefront-view")
@HtmlImport("src/views/storefront-view.html")
@PageTitle("Pippoz")
//@Route(value = "",layout = MainView.class)
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class HomeView extends AView {


    /**
     * Costruttore @Autowired
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation
     * Si usa un @Qualifier(), per avere la sottoclasse specifica
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti
     * Use @Lazy to avoid the Circular Dependency
     * A simple way to break the cycle is saying Spring to initialize one of the beans lazily.
     * That is: instead of fully initializing the bean, it will create a proxy to inject it into the other bean.
     * The injected bean will only be fully created when it’s first needed.
     *
     * @param presenter iniettato da Spring come sottoclasse concreta specificata dal @Qualifier
     */
    public HomeView(@Lazy @Qualifier(TAG_ROL) IAPresenter presenter) {
        super(presenter);
    }// end of Spring constructor


//    public HomeView() {
//        super();
//    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
int a=87;
        UI.getCurrent().navigate("role");
    }// end of method

}// end of class
