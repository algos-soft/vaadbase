package it.algos.vaadtest.modules.users;

import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.templatemodel.TemplateModel;
import it.algos.vaadbase.modules.utente.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;

@Tag("user-form")
@HtmlImport("src/views/admin/users/user-form.html")
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserForm extends PolymerTemplate<TemplateModel>  {

	@Id("title")
	private H3 title;

//	@Id("buttons")
//	private FormButtonsBar buttons;

	@Id("first")
	private TextField first;

	@Id("last")
	private TextField last;

	@Id("email")
	private TextField email;

	@Id("password")
	private PasswordField password;

	@Id("role")
	private ComboBox<String> role;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserForm(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}


}
