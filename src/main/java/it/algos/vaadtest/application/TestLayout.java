package it.algos.vaadtest.application;

import com.vaadin.flow.router.RouterLink;
import it.algos.vaadbase.ui.MainLayout;
import it.algos.vaadbase.wizard.ui.WizardView;
import it.algos.vaadtest.modules.bolla.BollaView;
import it.algos.vaadtest.modules.prova.ProvaView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import static it.algos.vaadbase.application.BaseCost.TAG_WIZ;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: gio, 19-apr-2018
 * Time: 22:30
 * Questa sottoclasse di mainLayout è specifica di ogni applicazione
 * Viene invocata dalla view Home con Route = ""
 * Genera il menu con tutte i moduli/package dell'applicazione.
 * Sia quelli standard, sia quelli specifici.
 * Questa classe NON va annotata con @SpringComponent
 */
@Slf4j
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class TestLayout extends MainLayout {


    public TestLayout() {
        super();
    }// end of constructor


    protected void inizia() {
        super.inizia();
        title.setText("VaadTest");
    }// end of method


    /**
     * Creazione delle viste (moduli/package) specifiche dell'applicazione.
     * <p>
     * Aggiunge al menu generale, le viste (moduli/package) disponibili alla partenza dell'applicazione
     * Ogni modulo può eventualmente modificare il proprio menu
     * <p>
     * Deve (DEVE) essere sovrascritto dalla sottoclasse
     * Chiama il metodo  addView(...) della superclasse per ogni vista (modulo/package)
     * La vista viene aggiunta alla barra di menu principale (di partenza)
     */
    protected void addVisteSpecifiche() {
          addView(WizardView.class, WizardView.VIEW_ICON, TAG_WIZ);
          addView(BollaView.class, BollaView.VIEW_ICON, BollaView.MENU_NAME);
    }// end of method


}// end of class
