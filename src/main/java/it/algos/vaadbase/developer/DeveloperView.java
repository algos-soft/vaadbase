package it.algos.vaadbase.developer;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.Route;
import it.algos.vaadbase.backend.login.ALogin;
import it.algos.vaadbase.modules.company.Company;
import it.algos.vaadbase.modules.company.CompanyService;
import it.algos.vaadbase.modules.preferenza.PreferenzaData;
import it.algos.vaadbase.modules.role.RoleData;
import it.algos.vaadbase.ui.AView;
import it.algos.vaadbase.ui.MainLayout;
import it.algos.vaadbase.ui.fields.ACheckBox;
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
    @Autowired
    private RoleData roleData;
    @Autowired
    private PreferenzaData prefData;

    public DeveloperView() {
    }// end of Spring constructor


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        this.setMargin(true);
        this.setSpacing(true);

        setDeveloper();
        chooseCompany();
        resetData();
    }// end of method


    public void setDeveloper() {
        Label etichetta;
        etichetta = new Label("Login come developer");
        this.add(etichetta);

        ACheckBox checkBox= new ACheckBox("Developer",false);
        checkBox.addValueChangeListener(event -> login.setDeveloper(event.getValue()));//end of lambda expressions
        this.add(checkBox);
    }// end of method


    public void chooseCompany() {
        Label etichetta;
        etichetta = new Label("Selezione da codice di una company in sostituzione del login");
        this.add(etichetta);

        List<Company> companies = companyService.findAll();
        String label = "Companies esistenti";
        ComboBox<Company> fieldComboCompanies = new ComboBox<>();
        fieldComboCompanies.setWidth("20em");
        fieldComboCompanies.setAllowCustomValue(false);
        fieldComboCompanies.setLabel(label);
        fieldComboCompanies.setItems(companies);
        fieldComboCompanies.addValueChangeListener(event -> sincroCompany(event.getValue()));//end of lambda expressions
        this.add(fieldComboCompanies);
    }// end of method


    public void resetData() {
        Label etichetta;
        etichetta = new Label("Ricostruisce completamente i dati iniziali, cancellando quelli non previsti");
        this.add(new Label(""));
        this.add(etichetta);

        String label = "Reset";
        Button resetButton = new Button();
        resetButton.setText(label);
        resetButton.addClickListener(e -> reset());
        this.add(resetButton);
    }// end of method


    private void reset() {
        roleData.crea();
        prefData.crea();
    }// end of method


    private void sincroCompany(Company valueFromCombo) {
        if (valueFromCombo != null) {
            login.setCompany(valueFromCombo);
        }// end of if cycle
    }// end of method


}// end of class
