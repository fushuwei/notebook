package com.itangsoft.notebook.views.layout.leftpanel;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Window;
import com.itangsoft.notebook.utils.HttpClient;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.tree.Tree;
import org.dominokit.domino.ui.tree.TreeItem;
import org.dominokit.domino.ui.utils.DominoElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LeftPanel Component
 *
 * @author fushuwei
 */
public class LeftPanelComponent extends AbstractComponent<ILeftPanelComponent.Controller, HTMLElement> implements ILeftPanelComponent {

    private static final Logger logger = LoggerFactory.getLogger(LeftPanelComponent.class);

    public LeftPanelComponent() {
        super();
    }

    @Override
    public void render() {
        DominoElement<HTMLDivElement> navigatePanel = DominoElement.div();

        HttpClient.get(GWT.getHostPageBaseURL() + "data/menu.json", null, new RequestCallback() {
            @Override
            public void onResponseReceived(Request request, Response response) {
                if (response.getStatusCode() != Response.SC_OK) {
                    Window.alert("Error: " + response.getStatusCode() + " -> " + response.getStatusText());
                    return;
                }

                Tree<String> menuTree = Tree.create("我的文件夹")
                    .enableSearch()
                    .setAutoCollapse(false);

                try {
                    JSONArray jMenuArray = (JSONArray) JSONParser.parseStrict(response.getText());

                    for (int i = 0; i < jMenuArray.size(); i++) {
                        JSONObject jMenu = (JSONObject) jMenuArray.get(i);

                        TreeItem<String> treeItem;

                        // markdown属性为空表示当前菜单是文件夹
                        if (jMenu.get("markdown") == null || "".equals(String.valueOf(jMenu.get("markdown")))) {
                            treeItem = TreeItem.create(String.valueOf(jMenu.get("name")), Icons.ALL.folder()).setExpandIcon(Icons.ALL.folder_open());
                        } else {
                            treeItem = TreeItem.create(String.valueOf(jMenu.get("name")), Icons.ALL.insert_drive_file()).setActiveIcon(Icons.ALL.description());
                        }

                        if (jMenu.get("children") != null && ((JSONArray) JSONParser.parseStrict(String.valueOf(jMenu.get("children")))).size() > 0) {
                            renderMenu(treeItem, String.valueOf(jMenu.get("children")));
                        }

                        menuTree.appendChild(treeItem);
                    }

                    navigatePanel.appendChild(menuTree);
                } catch (Exception e) {
                    logger.error("菜单渲染失败", e);
                }
            }

            @Override
            public void onError(Request request, Throwable throwable) {
                Window.alert("Error: " + throwable);
            }
        });

        initElement(navigatePanel.element());
    }

    public void renderMenu(TreeItem<String> treeItem, String children) {
        JSONArray jMenuArray = (JSONArray) JSONParser.parseStrict(children);
        for (int i = 0; i < jMenuArray.size(); i++) {
            JSONObject jMenu = (JSONObject) jMenuArray.get(i);

            TreeItem<String> subTreeItem;

            logger.info(jMenu.get("markdown").toString());

            // markdown属性为空表示当前菜单是文件夹
            if (jMenu.get("markdown") == null || "".equals(String.valueOf(jMenu.get("markdown")))) {
                subTreeItem = TreeItem.create(String.valueOf(jMenu.get("name")), Icons.ALL.folder()).setExpandIcon(Icons.ALL.folder_open());
            } else {
                subTreeItem = TreeItem.create(String.valueOf(jMenu.get("name")), Icons.ALL.insert_drive_file()).setActiveIcon(Icons.ALL.description());
            }

            if (jMenu.get("children") != null && ((JSONArray) JSONParser.parseStrict(jMenu.get("children").toString())).size() > 0) {
                renderMenu(subTreeItem, jMenu.get("children").toString());
            }

            treeItem.appendChild(subTreeItem);
        }
    }
}