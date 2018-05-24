package it.algos.vaadbase.modules.company;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcons;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.ui.AViewList;
import it.algos.vaadbase.ui.MainLayout;
import it.algos.vaadbase.ui.dialog.AViewDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadbase.application.BaseCost.TAG_COM;

/**
 * Project vaadbase <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 9-mag-2018 19.52.23 <br>
 * <br>
 * Estende la classe astratta AViewList per visualizzare la Grid <br>
 * <p>
 * NON annotated with @SpringComponent - Sbagliato perché va in conflitto con la @Route
 * NON annotated with @SpringView - Sbagliato perché usa la Route di VaadinFlow
 * Annotated with @Scope (obbligatorio = 'singleton')
 * Annotated with @Route (obbligatorio) per la selezione della vista. @Route(value = "") per la vista iniziale
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Route(value = TAG_COM, layout = MainLayout.class)
@Qualifier(TAG_COM)
@AIScript(sovrascrivibile = true)
public class CompanyViewList extends AViewList {


    /**
     * Icona visibile nel menu (facoltativa)
     */
    public static final VaadinIcons VIEW_ICON = VaadinIcons.FACTORY;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param presenter per gestire la business logic del package
     */
    @Autowired
    public CompanyViewList(@Qualifier(TAG_COM) IAPresenter presenter) {
        super(presenter);
        dialog = new CompanyViewDialog(presenter, this::saveUpdate, this::deleteUpdate);
    }// end of Spring constructor


    /**
     * Crea il corpo centrale della view
     * Componente grafico obbligatorio
     */
    protected void addGrid() {
        super.addGrid();
        ComponentRenderer renderer = new ComponentRenderer<>(this::createEditButton);
        grid.addColumn(renderer);
        this.setFlexGrow(0);
    }// end of method


    private Button createEditButton(Company entityBean) {
        Button edit = new Button("Modifica", event -> dialog.open(entityBean, AViewDialog.Operation.EDIT));
        edit.setIcon(new Icon("lumo", "edit"));
        edit.addClassName("review__edit");
        edit.getElement().setAttribute("theme", "tertiary");
        return edit;
    }// end of method


}// end of class