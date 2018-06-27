package it.algos.vaadbase.ui.menu;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.SpringComponent;
import it.algos.vaadbase.ui.AView;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: sab, 05-mag-2018
 * Time: 21:33
 */
@SpringComponent
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public interface IAMenu  {

    /**
     * Adds a view to the UI
     *
     * @param viewClazz the view class to instantiate
     */
    public RouterLink addView(Class<? extends AView> viewClazz, Icon icon, String tagMenu);

    public Component getComponent();

}// end of interface
