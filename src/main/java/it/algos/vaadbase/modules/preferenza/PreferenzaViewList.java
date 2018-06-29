package it.algos.vaadbase.modules.preferenza;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.enumeration.EAPrefType;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.ui.AViewList;
import it.algos.vaadbase.ui.MainLayout;
import it.algos.vaadbase.ui.dialog.AViewDialog;
import it.algos.vaadbase.ui.dialog.IADialog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import static it.algos.vaadbase.application.BaseCost.TAG_PRE;

/**
 * Project vaadbase <br>
 * Created by Algos <br>
 * User: Gac <br>
 * Date: 25-mag-2018 16.19.24 <br>
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
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Route(value = TAG_PRE, layout = MainLayout.class)
@Qualifier(TAG_PRE)
@Slf4j
@AIScript(sovrascrivibile = true)
public class PreferenzaViewList extends AViewList {


    /**
     * Icona visibile nel menu (facoltativa)
     */
    public static final VaadinIcon VIEW_ICON = VaadinIcon.WRENCH;


    /**
     * Costruttore @Autowired <br>
     * Si usa un @Qualifier(), per avere la sottoclasse specifica <br>
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti <br>
     *
     * @param presenter per gestire la business logic del package
     * @param dialog    per visualizzare i fields
     */
    @Autowired
    public PreferenzaViewList(@Qualifier(TAG_PRE) IAPresenter presenter, @Qualifier(TAG_PRE) IADialog dialog) {
        super(presenter, dialog);
        ((PreferenzaViewDialog) dialog).fixFunzioni(this::save, this::delete);
    }// end of Spring constructor


    /**
     * Crea il corpo centrale della view
     * Componente grafico obbligatorio
     */
    protected void addGrid() {
        super.addGrid();
        ComponentRenderer renderValue = new ComponentRenderer<>(this::renderedValue);
        Grid.Column<Preferenza> colonna = grid.addColumn(renderValue);
        colonna.setHeader("Valore");
        ComponentRenderer renderer = new ComponentRenderer<>(this::createEditButton);
        grid.addColumn(renderer);
        this.setFlexGrow(0);
    }// end of method


    private Component renderedValue(Preferenza entityBean) {
        Component comp = null;
        Object genericValue = null;
        EAPrefType type = entityBean.getType();
        byte[] value = entityBean.getValue();

        switch (type) {
            case string:
                comp = new Label((String) type.bytesToObject(value));
                break;
            case integer:
                comp = new Label(type.bytesToObject(value).toString());
                break;
            case bool:
                genericValue = type.bytesToObject(value);
                if (genericValue instanceof Boolean) {
                    comp = new Checkbox((Boolean) genericValue);
                } else {
                    comp = new Label("Errato");
                }// end of if/else cycle
                break;
            default:
                log.warn("Switch - caso non definito");
                break;
        } // end of switch statement

        return comp;
    }// end of method


    private Button createEditButton(Preferenza entityBean) {
        Button edit = new Button(EDIT_NAME, event -> dialog.open(entityBean, AViewDialog.Operation.EDIT));
        edit.setIcon(new Icon("lumo", "edit"));
        edit.addClassName("review__edit");
        edit.getElement().setAttribute("theme", "tertiary");
        return edit;
    }// end of method


}// end of class