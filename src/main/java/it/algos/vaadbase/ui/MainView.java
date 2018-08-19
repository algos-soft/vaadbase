package it.algos.vaadbase.ui;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.algos.vaadbase.backend.service.IAService;
import it.algos.vaadbase.modules.role.RoleViewList;
import it.algos.vaadbase.modules.utente.UtenteViewList;
import it.algos.vaadbase.presenter.IAPresenter;
import it.algos.vaadbase.security.SecurityUtils;
import it.algos.vaadbase.service.AAnnotationService;
import it.algos.vaadbase.service.AReflectionService;
import it.algos.vaadbase.ui.components.AppNavigation;
import it.algos.vaadbase.ui.dialog.IADialog;
import it.algos.vaadbase.ui.entities.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadbase.application.BaseCost.*;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: sab, 4-ago-2018
 * Time: 08:20
 * <p>
 * The main layout contains the header with the navigation buttons, and the child views below that.
 * Esempio ripreso da Vaadin:Bakery
 * <p>
 * Annotated with @Theme (facoltativo)
 * Questa classe NON va annotata con @SpringComponent
 * Questa classe viene 'invocata' dalle views annotate con @Route(value = xxx, layout = MainView.class)
 * Anche le view NON vanno annotate con @SpringComponent
 * Si possono usare delle views che non richiamano questo layout ed hanno un proprio layout java che non usa html
 */
@Tag("main-view")
@HtmlImport("src/main-view.html")
// Override elements with Lumo styled ones
@HtmlImport("bower_components/vaadin-form-layout/theme/lumo/vaadin-form-item.html")
@HtmlImport("bower_components/vaadin-form-layout/theme/lumo/vaadin-form-layout.html")

@PageTitle("Pippoz")
@Viewport(VIEWPORT)
public class MainView
		extends PolymerTemplate<TemplateModel>
		implements RouterLayout, BeforeEnterObserver {

	/**
	 * L'istanza viene iniettata perché utilizzata da @HtmlImport("src/main-view.html")
	 */
	@Id("appNavigation")
	private AppNavigation appNavigation;

	/**
	 * Il service viene iniettato dal costruttore
	 */
	private AAnnotationService annotation;

	/**
	 * Il service viene iniettato dal costruttore
	 */
	private AReflectionService reflection;


	@Autowired
	public MainView(AAnnotationService annotation, AReflectionService reflection) {
		this.annotation = annotation;
		this.reflection = reflection;
		this.inizia();
	}// end of method

	/**
	 * Metodo invocato dal costruttore
	 * (separato solo per evidenziarlo)
	 */
	public void inizia() {
		List<PageInfo> pages = new ArrayList<>();

//		pages.add(new PageInfo(PAGE_STOREFRONT, ICON_STOREFRONT, TITLE_STOREFRONT));
//		pages.add(new PageInfo(PAGE_DASHBOARD, ICON_DASHBOARD, TITLE_DASHBOARD));

//		if (SecurityUtils.isAccessGranted(UtenteViewList.class)) {
//			pages.add(new PageInfo(PAGE_USERS, ICON_USERS, TITLE_USERS));
//		}
//		if (SecurityUtils.isAccessGranted(RoleViewList.class)) {
//			pages.add(new PageInfo(PAGE_PRODUCTS, ICON_PRODUCTS, TITLE_PRODUCTS));
//		}
//		pages.add(new PageInfo(PAGE_USERS, ICON_USERS, TITLE_USERS));

		pages.add(new PageInfo("role", ICON_STOREFRONT, "Role"));
		pages.add(new PageInfo("company", ICON_STOREFRONT, "Company"));
		appNavigation.init(pages, PAGE_DEFAULT, PAGE_LOGOUT);
	}// end of method

		@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (!SecurityUtils.isAccessGranted(event.getNavigationTarget())) {
			event.rerouteToError(AccessDeniedException.class);
		}
	}// end of method

}// end of class

