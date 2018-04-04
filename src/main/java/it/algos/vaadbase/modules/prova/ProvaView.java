package it.algos.vaadbase.modules.prova;



import com.vaadin.flow.component.grid.Grid;
import it.algos.vaadbase.modules.company.Company;
import it.algos.vaadbase.modules.company.CompanyService;
import com.vaadin.flow.component.html.Label;



import com.vaadin.flow.router.BeforeEnterEvent;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.backend.service.IAService;
import it.algos.vaadbase.ui.AView;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import javax.annotation.PostConstruct;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import it.algos.vaadbase.backend.entity.AEntity;
import it.algos.vaadbase.ui.enumeration.EARoleType;
import it.algos.vaadbase.annotation.AIScript;
import it.algos.vaadbase.ui.annotation.AIView;
import it.algos.vaadbase.ui.MainView;
import static it.algos.vaadbase.application.BaseCost.TAG_PRO;

/**
 * Project vaadbase
 * Created by Algos
 * User: Gac
 * Date: 2018-04-04
 * Estende la Entity astratta AList di tipo AView per visualizzare la Grid
 * Annotated with @SpringComponent (obbligatorio)
 * Annotated with @Scope (obbligatorio = 'session')
 * Annotated with @Qualifier (obbligatorio) per permettere a Spring di istanziare la sottoclasse specifica
 * Annotated with @SpringView (obbligatorio) per gestire la visualizzazione di questa view con SprinNavigator
 * Annotated with @AIView (facoltativo) per selezionarne la 'visibilità' secondo il ruolo dell'User collegato
 * Annotated with @AIScript (facoltativo) per controllare la ri-creazione di questo file nello script del framework
 * Costruttore con un link @Autowired al IAPresenter, di tipo @Lazy per evitare un loop nella injection
 */
@Slf4j
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Qualifier(TAG_PRO)
@Route(value = TAG_PRO, layout = MainView.class)
@AIView(roleTypeVisibility = EARoleType.user)
@AIScript(sovrascrivibile = true)
public class ProvaView extends AView {


    /**
     * Label del menu (facoltativa)
     * SpringNavigator usa il 'name' della Annotation @SpringView per identificare (internamente) e recuperare la view
     * Nella menuBar appare invece visibile il MENU_NAME, indicato qui
     * Se manca il MENU_NAME, di default usa il 'name' della view
     */
    public static final String MENU_NAME = TAG_PRO;


    /**
     * Il service viene regolato nel costruttore recuperandolo del presenter
     * in modo che sia disponibile nella superclasse, dove viene usata l'interfaccia IAService
     * Qui si una una sottoclasse locale (col casting nel costruttore) per usare i metodi specifici
     */
    private ProvaService service;


//    /**
//     * Icona visibile nel menu (facoltativa)
//     * Nella menuBar appare invece visibile il MENU_NAME, indicato qui
//     * Se manca il MENU_NAME, di default usa il 'name' della view
//     */
//    public static final Resource VIEW_ICON = VaadinIcons.ASTERISK;


    /**
     * Costruttore @Autowired
     * In the newest Spring release, it’s constructor does not need to be annotated with @Autowired annotation
     * Si usa un @Qualifier(), per avere la sottoclasse specifica
     * Si usa una costante statica, per essere sicuri di scrivere sempre uguali i riferimenti
     * Use @Lazy to avoid the Circular Dependency
     * A simple way to break the cycle is saying Spring to initialize one of the beans lazily.
     * That is: instead of fully initializing the bean, it will create a proxy to inject it into the other bean.
     * The injected bean will only be fully created when it’s first needed.
     *
     * @param presenter iniettato da Spring come sottoclasse concreta specificata dal @Qualifier
     */
     public ProvaView(@Lazy @Qualifier(TAG_PRO) IAPresenter presenter) {
        super(presenter);
        this.service = (ProvaService) service;
       }// end of Spring constructor


//    /**
//     * Crea la scritta esplicativa
//     * Può essere sovrascritto per un'intestazione specifica (caption) della grid
//     */
//    @Override
//    protected void fixCaption(Class<? extends AEntity> entityClazz, List items) {
//        super.fixCaption(entityClazz, items);
//
//        if (login.isDeveloper()) {
//            caption += "</br>Lista visibile a tutti";
//            caption += "</br>Solo il developer vede queste note";
//        }// end of if cycle
//    }// end of method

}// end of class