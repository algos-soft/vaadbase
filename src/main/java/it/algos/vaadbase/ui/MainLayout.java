package it.algos.vaadbase.ui;

import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.extern.slf4j.Slf4j;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: mar, 27-mar-2018
 * Time: 08:20
 * <p>
 * The main layout contains the header with the navigation buttons, and the child views below that.
 * <p>
 * Annotated with @Theme (obbligatorio)
 * Questa classe NON va annotata con @SpringComponent
 * Questa classe viene 'invocata' dalle views annotate con @Route(value = xxx, layout = MainLayout.class)
 * Anche le view NON vanno annotate con @SpringComponent
 * Si possono usare delle views che non richiamano questo layout ed hanno un proprio layout java che non usa html
 */
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Theme(Lumo.class)
@BodySize(height = "100vh", width = "100vw")
public class MainLayout extends PolymerTemplate<TemplateModel> implements RouterLayout, BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
int a=87;
    }// end of method

}// end of class
