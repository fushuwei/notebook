package com.itangsoft.notebook.views.layout.content;

import com.github.nalukit.nalu.client.component.annotation.AcceptParameter;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import com.itangsoft.notebook.Routes;
import com.itangsoft.notebook.Slots;
import com.itangsoft.notebook.base.BaseComponentController;

/**
 * Content Controller
 *
 * @author fushuwei
 */
@Controller(route = Routes.ROUTE_CONTENT,
    selector = Slots.SELECTOR_LAYOUT_CONTENT,
    componentInterface = IContentComponent.class,
    component = ContentComponent.class)
public class ContentController
    extends BaseComponentController<IContentComponent>
    implements IContentComponent.Controller {

    String fileName;

    public ContentController() {
    }

    @AcceptParameter("fileName")
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
