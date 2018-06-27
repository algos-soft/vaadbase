package it.algos.vaadtest.modules.prova;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import it.algos.vaadbase.ui.dialog.IADialog;
import it.algos.vaadbase.ui.dialog.AViewDialog;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Project vaadtest <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 9-giu-2018 11.41.15 <br>
 * <br>
 * Estende la classe astratta AViewList per visualizzare la Grid <br>
 * <p>
 * Not annotated with @SpringView (sbagliato) perché usa la @Route di VaadinFlow <br>
 * Not annotated with @SpringComponent (sbagliato) perché usa la @Route di VaadinFlow <br>
 * Annotated with @Scope (obbligatorio = 'singleton') <br>
 * Annotated with @Route (obbligatorio) per la selezione della vista. @Route(value = "") per la vista iniziale <br>
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica <br>
 * Annotated with @Slf4j (facoltativo) per i logs automatici <br>
 * Annotated with @AIScript (facoltativo Algos) per controllare la ri-creazione di questo file dal Wizard <br>
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Route(value = TAG_PRO, layout = MainLayout.class)
@Qualifier(TAG_PRO)
@Slf4j
@AIScript(sovrascrivibile = true)
public class ProvaViewList extends AViewList {


    /**
     * Icona visibile nel menu (facoltativa)
     */
    public static final VaadinIcon VIEW_ICON = VaadinIcon.REFRESH;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param presenter per gestire la business logic del package
     * @param dialog    per visualizzare i fields
     */
    @Autowired
    public ProvaViewList(@Qualifier(TAG_PRO) IAPresenter presenter, @Qualifier(TAG_PRO) IADialog dialog) {
        super(presenter, dialog);
        ((ProvaViewDialog) dialog).fixFunzioni(this::save, this::delete);
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


   private Button createEditButton(Prova entityBean) {
        Button edit = new Button(EDIT_NAME, event -> dialog.open(entityBean, AViewDialog.Operation.EDIT));
        edit.setIcon(new Icon("lumo", "edit"));
        edit.addClassName("review__edit");
        edit.getElement().setAttribute("theme", "tertiary");
        return edit;
    }// end of method


}// end of class