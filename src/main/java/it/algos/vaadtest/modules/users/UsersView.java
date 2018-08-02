package it.algos.vaadtest.modules.users;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.algos.vaadbase.backend.data.Role;
import it.algos.vaadbase.modules.persona.PersonaViewDialog;
import it.algos.vaadbase.modules.utente.Utente;
import it.algos.vaadbase.ui.AViewList;
import it.algos.vaadbase.ui.MainView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import static it.algos.vaadbase.application.BaseCost.PAGE_USERS;

@Tag("users-view")
@HtmlImport("src/views/admin/users/users-view.html")
@Route(value = PAGE_USERS, layout = MainView.class)
@PageTitle("Pippoz")
//@Secured(Role.ADMIN)
public class UsersView extends AViewList {

//	@Id("search")
//	private SearchBar search;

	@Id("grid")
	private Grid<Utente> grid;


	private final BeanValidationBinder<Utente> binder = new BeanValidationBinder<>(Utente.class);

	@Autowired
	public UsersView( ) {
		super(null, null);
		this.presenter = presenter;
//		form.setBinder(binder);

//		setupEventListeners();
		setupGrid();
//		presenter.setView(this);
		this.add(new Label("Prova"));
	}

	private void setupGrid() {
//		grid.addColumn(User::getEmail).setWidth("270px").setHeader("Email").setFlexGrow(5);
//		grid.addColumn(u -> u.getFirstName() + " " + u.getLastName()).setHeader("Name").setWidth("200px").setFlexGrow(5);
//		grid.addColumn(User::getRole).setHeader("Role").setWidth("150px");
	}

//	@Override
//	public Grid<User> getGrid() {
//		return grid;
//	}
//
//	@Override
//	protected CrudEntityPresenter<User> getPresenter() {
//		return presenter;
//	}
//
//	@Override
//	protected String getBasePage() {
//		return PAGE_USERS;
//	}
//
//	@Override
//	public SearchBar getSearchBar() {
//		return search;
//	}
//
//	@Override
//	protected BeanValidationBinder<User> getBinder() {
//		return binder;
//	}
}
