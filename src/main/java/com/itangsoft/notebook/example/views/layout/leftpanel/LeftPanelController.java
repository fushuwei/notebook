package com.itangsoft.notebook.example.views.layout.leftpanel;

import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.itangsoft.notebook.Routes;
import com.itangsoft.notebook.Slots;
import com.itangsoft.notebook.base.BaseComponentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LeftPanel Controller
 *
 * @author fushuwei
 */
@Controller(route = Routes.ROUTE_EXAMPLE_SINGLE_PAGE_LAYOUT_HOME,
    selector = Slots.SELECTOR_EXAMPLE_LAYOUT_LEFT_PANEL,
    componentInterface = ILeftPanelComponent.class,
    component = LeftPanelComponent.class)
public class LeftPanelController
    extends BaseComponentController<ILeftPanelComponent>
    implements ILeftPanelComponent.Controller {

    private static final Logger logger = LoggerFactory.getLogger(LeftPanelController.class);

    public LeftPanelController() {
    }

    @Override
    public void openFile(String fileName) {
        logger.info(fileName);
        this.router.route(Routes.ROUTE_EXAMPLE_SINGLE_PAGE_LAYOUT_CONTENT, fileName);
    }
}
