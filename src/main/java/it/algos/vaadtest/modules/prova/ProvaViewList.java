package it.algos.vaadtest.modules.prova;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import it.algos.vaadbase.modules.company.Company;
import it.algos.vaadbase.modules.company.CompanyService;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import it.algos.vaadbase.ui.dialog.AForm;
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
import static it.algos.vaadtest.application.AppCost.TAG_PRO;

/**
 * Project vaadbase <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 6-mag-2018 14.40.06 <br>
 * <br>
 * Estende la classe astratta AView per visualizzare la Grid e il Detail<br>
 * <p>
 * Annotated with @SpringComponent (obbligatorio per le injections)
 * Annotated with @Scope (obbligatorio = 'singleton')
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica
 * NON annotated with @SpringView (escluso) perché usa la Route di VaadinFlow
 * Annotated with @Route (obbligatorio) per la selezione della vista. @Route(value = "") per la vista iniziale
 * Annotated with @AIView (facoltativo) per selezionarne la 'visibilità' secondo il ruolo dell'User collegato
 * Annotated with @AIScript (facoltativo) per controllare la ri-creazione di questo file nello script del framework
 * Costruttore con un link @Autowired al IAPresenter, di tipo @Lazy per evitare un loop nella injection
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_PRO)
@Route(value = TAG_PRO, layout = MainLayout.class)
@AIScript(sovrascrivibile = true)
public class ProvaViewList extends AViewList {


    /**
     * Icona visibile nel menu (facoltativa)
     */
    public static final VaadinIcons VIEW_ICON = VaadinIcons.ASTERISK;


    /**
     * Costruttore @Autowired <br>
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param presenter iniettato da Spring come sottoclasse concreta specificata dal @Qualifier
     */
     public ProvaViewList(@Qualifier(TAG_PRO) IAPresenter presenter) {
        super(presenter);
        form = new ProvaForm(this::saveUpdate, this::deleteUpdate, service);
   }// end of Spring constructor


     /**
      * Crea il corpo centrale della view
      * Componente grafico obbligatorio
      * Sovrascritto nella sottoclasse della view specifica (AList, AForm, ...)
      */
     protected void addGrid() {
         super.addGrid();
         ComponentRenderer renderer = new ComponentRenderer<>(this::createEditButton);
         grid.addColumn(renderer);
         this.setFlexGrow(0);
     }// end of method


   private Button createEditButton(Prova entityBean) {
        Button edit = new Button("Modifica", event -> form.open(entityBean, AForm.Operation.EDIT));
        edit.setIcon(new Icon("lumo", "edit"));
        edit.addClassName("review__edit");
        edit.getElement().setAttribute("theme", "tertiary");
        return edit;
    }// end of method


}// end of class