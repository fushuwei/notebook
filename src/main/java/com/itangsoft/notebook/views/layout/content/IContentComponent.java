package com.itangsoft.notebook.views.layout.content;

import com.github.nalukit.nalu.client.component.IsComponent;
import elemental2.dom.HTMLElement;

/**
 * Content Component
 *
 * @author fushuwei
 */
public interface IContentComponent extends IsComponent<IContentComponent.Controller, HTMLElement> {

    interface Controller extends IsComponent.Controller {

        public String getFileName();
    }
}
