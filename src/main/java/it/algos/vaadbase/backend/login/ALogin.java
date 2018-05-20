package it.algos.vaadbase.backend.login;

import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.modules.company.Company;
import it.algos.vaadbase.modules.company.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadwam
 * Created by Algos
 * User: gac
 * Date: gio, 10-mag-2018
 * Time: 16:23
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ALogin {

    @Autowired
    private CompanyService service;
    private Company company;

    public Company getCompany() {
        return company;
    }// end of method

    public void setCompany(Company company) {
        this.company = company;
    }// end of method

}// end of class
