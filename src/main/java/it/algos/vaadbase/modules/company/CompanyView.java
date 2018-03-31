package it.algos.vaadbase.modules.company;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import it.algos.vaadbase.application.BaseCost;
import it.algos.vaadbase.service.ATextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project vbase
 * Created by Algos
 * User: gac
 * Date: mar, 20-mar-2018
 * Time: 08:57
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Route(value = "company")
@Theme(Lumo.class)
@PageTitle("Company")
@Qualifier(BaseCost.TAG_COM)
public class CompanyView extends VerticalLayout {


    /**
     * Il service viene iniettato dal costruttore, in modo che sia disponibile nella superclasse,
     * dove viene usata l'interfaccia IAService
     * Spring costruisce al volo, quando serve, una implementazione di IAService (come previsto dal @Qualifier)
     * Qui si una una interfaccia locale (col casting nel costruttore) per usare i metodi specifici
     */
    private CompanyService service;


    @Autowired
    public CompanyView(CompanyService service) {
        this.service = service;
        Label label = new Label("prova company");
        this.add(label);
        Grid grid = new Grid(Company.class);
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("creazione");
        grid.removeColumnByKey("modifica");
        grid.removeColumnByKey("note");
        List<Company> items = service.findAll();
        grid.setItems(items);
        this.add(grid);
        for (Company company : items) {
            this.add(new Label(company.getCode()));
        }// end of for cycle


    }


}// end of class
//