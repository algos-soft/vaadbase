package it.algos.vaadbase.modules.persona;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import it.algos.vaadbase.ui.dialog.AViewDialog;
import com.vaadin.flow.component.icon.VaadinIcons;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.BeforeEnterEvent;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.backend.service.IAService;
import it.algos.vaadbase.ui.AViewList;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import javax.annotation.PostConstruct;
import java.util.List;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import it.algos.vaadbase.backend.entity.AEntity;
import it.algos.vaadbase.ui.enumeration.EARoleType;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.ui.annotation.AIView;
import it.algos.vaadbase.ui.MainLayout;
import static it.algos.vaadbase.application.BaseCost.TAG_PER;

/**
 * Project vaadbase <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 10-mag-2018 6.41.22 <br>
 * <br>
 * Estende la classe astratta AViewList per visualizzare la Grid <br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio per le injections)
 * Annotated with @Scope (obbligatorio = 'singleton')
 * Not annotated with @SpringView (sbagliato) perché usa la @Route di VaadinFlow
 * Annotated with @Route (obbligatorio) per la selezione della vista. @Route(value = "") per la vista iniziale
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_PER)
@Route(value = TAG_PER, layout = MainLayout.class)
@AIScript(sovrascrivibile = true)
public class PersonaViewList extends AViewList {


    /**
     * Icona visibile nel menu (facoltativa)
     */
    public static final VaadinIcons VIEW_ICON = VaadinIcons.ASTERISK;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param presenter per gestire la business logic del package
     */
     public PersonaViewList(@Qualifier(TAG_PER) IAPresenter presenter) {
        super(presenter);
        dialog = new PersonaViewDialog(presenter, this::saveUpdate, this::deleteUpdate);
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


   private Button createEditButton(Persona entityBean) {
        Button edit = new Button("Modifica", event -> dialog.open(entityBean, AViewDialog.Operation.EDIT));
        edit.setIcon(new Icon("lumo", "edit"));
        edit.addClassName("review__edit");
        edit.getElement().setAttribute("theme", "tertiary");
        return edit;
    }// end of method

    /**
     * Crea la scritta esplicativa
     * Può essere sovrascritto per un'intestazione specifica (caption) della grid
     */
    @Override
    protected void addDeveloperAlert() {
        VerticalLayout layout =new VerticalLayout();
        layout.setMargin(false);
        layout.setSpacing(false);
        layout.add(new Label("Lista visibile solo al developer"));
        layout.add(new Label("NON usa la company"));
        layout.add(new Label("L'entity è 'embedded' nelle collezioni che la usano (no @Annotation property DbRef)"));
        layout.add(new Label("In pratica questa lista non dovrebbe mai essere usata (serve come test o per le sottoclassi specifiche)"));
        add(layout);
    }// end of method

}// end of class