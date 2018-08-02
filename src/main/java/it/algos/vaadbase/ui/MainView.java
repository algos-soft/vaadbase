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
import it.algos.vaadbase.modules.role.RoleViewList;
import it.algos.vaadbase.modules.utente.UtenteViewList;
import it.algos.vaadbase.security.SecurityUtils;
import it.algos.vaadbase.service.AAnnotationService;
import it.algos.vaadbase.service.AReflectionService;
import it.algos.vaadbase.ui.components.AppNavigation;
import it.algos.vaadbase.ui.entities.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadbase.application.BaseCost.*;

@Tag("main-view")
@HtmlImport("src/main-view.html")
// Override elements with Lumo styled ones
@HtmlImport("bower_components/vaadin-form-layout/theme/lumo/vaadin-form-item.html")
@HtmlImport("bower_components/vaadin-form-layout/theme/lumo/vaadin-form-layout.html")

@PageTitle("Backery")
@Viewport(VIEWPORT)
public class MainView
		extends PolymerTemplate<TemplateModel>
		implements RouterLayout, BeforeEnterObserver {

	@Id("appNavigation")
	private AppNavigation appNavigation;

	@Autowired
	public MainView(AAnnotationService annotation, AReflectionService reflection) {
		List<PageInfo> pages = new ArrayList<>();

//		pages.add(new PageInfo(PAGE_STOREFRONT, ICON_STOREFRONT, TITLE_STOREFRONT));
//		pages.add(new PageInfo(PAGE_DASHBOARD, ICON_DASHBOARD, TITLE_DASHBOARD));
		if (SecurityUtils.isAccessGranted(UtenteViewList.class)) {
			pages.add(new PageInfo(PAGE_USERS, ICON_USERS, TITLE_USERS));
		}
		if (SecurityUtils.isAccessGranted(RoleViewList.class)) {
			pages.add(new PageInfo(PAGE_PRODUCTS, ICON_PRODUCTS, TITLE_PRODUCTS));
		}
		pages.add(new PageInfo("company", ICON_STOREFRONT, "Company"));
		pages.add(new PageInfo(PAGE_USERS, ICON_USERS, TITLE_USERS));

		appNavigation.init(pages, PAGE_DEFAULT, PAGE_LOGOUT);
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if (!SecurityUtils.isAccessGranted(event.getNavigationTarget())) {
			event.rerouteToError(AccessDeniedException.class);
		}
	}
}
