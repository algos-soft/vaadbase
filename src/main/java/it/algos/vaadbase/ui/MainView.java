package it.algos.vaadbase.ui;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import it.algos.vaadbase.ui.components.AppNavigation;
import it.algos.vaadbase.ui.entities.PageInfo;
import it.algos.vaadbase.ui.menu.AMenu;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static it.algos.vaadbase.application.BaseCost.ICON_WIZARD;

/**
 * Project vaadbase
 * Created by Algos
 * User: gac
 * Date: sab, 24-mar-2018
 * Time: 16:00
 */
@Slf4j
//@SpringComponent
@Tag("main-view")
@HtmlImport("src/main-view.html")
@Theme(Lumo.class)
@BodySize(height = "100vh", width = "100vw")
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MainView extends PolymerTemplate<TemplateModel> implements RouterLayout, BeforeEnterObserver {

    //    @Autowired
    @Id("appNavigation")
    private AppNavigation appNavigation;

    private AMenu menu;

    public MainView(AMenu menu) {
        this.menu = menu;
        this.appNavigation = appNavigation;
//        this.setMargin(true);
//        this.setSpacing(true);

        List<PageInfo> pages = new ArrayList<>();

        pages.add(new PageInfo("wizard", ICON_WIZARD, "Pippoz"));
        pages.add(new PageInfo("new", ICON_WIZARD, "Nix"));
        appNavigation.init(pages, "wizard", "");


        // This is just a simple label created via Elements API
        Button button = new Button("Click me");
        // This is a simple template example
//        this.add(menu);
//        this.add(button);
    }// end of costructor

    // Handle for instance before navigation clean up
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.add(menu);
    }// end of method

//    private NativeButton getImage() {
//        Input name = new Input();
//
//        Element image = new Element("object");
//        image.setAttribute("type", "image/svg+xml");
//        image.getStyle().set("display", "block");
//
//        NativeButton button = new NativeButton("Generate Image");
//        button.addClickListener(event -> {
//            StreamResource resource = new StreamResource("frontend.images.ambulanza.jpg", () -> getImageInputStream(name));
//            image.setAttribute("data", resource);
//        });// end of anonymous inner class
//
//        UI alfa = UI.getCurrent();
//        Element beta = alfa.getElement();
//        beta.appendChild(name.getElement(), image, button.getElement());
//
//        return button;
//    }// end of method


//    private InputStream getImageInputStream(Input name) {
//        String value = name.getValue();
//        if (value == null) {
//            value = "";
//        }
//        String svg = "<?xml version='1.0' encoding='UTF-8' standalone='no'?>"
//                + "<svg  xmlns='http://www.w3.org/2000/svg' "
//                + "xmlns:xlink='http://www.w3.org/1999/xlink'>"
//                + "<rect x='10' y='10' height='100' width='100' "
//                + "style=' fill: #90C3D4'/><text x='30' y='30' fill='red'>"
//                + value + "</text>" + "</svg>";
//        return new ByteArrayInputStream(svg.getBytes(StandardCharsets.UTF_8));
//    }


}// end of class
