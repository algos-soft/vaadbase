package it.algos.vaadbase.developer;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import it.algos.vaadbase.backend.login.ALogin;
import it.algos.vaadbase.modules.company.Company;
import it.algos.vaadbase.modules.company.CompanyService;
import it.algos.vaadbase.ui.AView;
import it.algos.vaadbase.ui.MainLayout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.List;

import static it.algos.vaadbase.application.BaseCost.TAG_DEV;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: dom, 20-mag-2018
 * Time: 17:10
 */
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Route(value = TAG_DEV, layout = MainLayout.class)
@Qualifier(TAG_DEV)
public class DeveloperView extends AView {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private ALogin login;
    private Label labelUno;
    private Button buttonUno;
    private ComboBox<Company> fieldComboCompanies;

    public DeveloperView() {
    }// end of Spring constructor


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        inizia();
    }// end of method


    public void inizia() {
        this.setMargin(true);
        this.setSpacing(true);

        String currentProject = System.getProperty("user.dir");
        currentProject = currentProject.substring(currentProject.lastIndexOf("/") + 1);

        labelUno = new Label("Selezione da codice di una company in sostituzione del login");
        this.add(labelUno);

        List<Company> companies = companyService.findAll();
        String label = "Companies esistenti";
        fieldComboCompanies = new ComboBox<>();
        fieldComboCompanies.setWidth("20em");
        fieldComboCompanies.setAllowCustomValue(false);
        fieldComboCompanies.setLabel(label);
        fieldComboCompanies.setItems(companies);
        fieldComboCompanies.addValueChangeListener(event -> sincroCompany(event.getValue()));//end of lambda expressions
        this.add(fieldComboCompanies);
    }// end of method


    private void sincroCompany(Company valueFromCombo) {
        if (valueFromCombo != null) {
            login.setCompany(valueFromCombo);
        }// end of if cycle
    }// end of method


}// end of class
