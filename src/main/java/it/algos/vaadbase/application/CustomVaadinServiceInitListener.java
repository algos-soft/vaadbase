package it.algos.vaadbase.application;

import com.vaadin.flow.server.*;
import com.vaadin.flow.spring.annotation.SpringComponent;

/**
 * Configures the {@link VaadinService}:
 * <ul>
 *   <li>adds a {@link BootstrapListener} to add favicons, viewport,
 *   etc to the initial HTML sent to the browser (see {@link CustomBootstrapListener})</li>
 *   <li>adds a {@link DependencyFilter} to allow dependency bundling
 *   in the production mode (when all individual .html dependencies are combined a single
 *   file to improve the page load performance)</li>
 * </ul>
 */
@SpringComponent
public class CustomVaadinServiceInitListener implements VaadinServiceInitListener {

	@Override
	public void serviceInit(ServiceInitEvent event) {
		event.addBootstrapListener(new CustomBootstrapListener());
	}
}
