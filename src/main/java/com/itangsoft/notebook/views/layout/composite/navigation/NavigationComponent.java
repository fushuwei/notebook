package com.itangsoft.notebook.views.layout.composite.navigation;

import com.github.nalukit.nalu.client.component.AbstractCompositeComponent;
import com.itangsoft.notebook.model.Menu;
import com.itangsoft.notebook.service.MenuServiceFactory;
import elemental2.dom.HTMLElement;
import org.dominokit.domino.ui.icons.Icons;
import org.dominokit.domino.ui.tree.Tree;
import org.dominokit.domino.ui.tree.TreeItem;
import org.dominokit.domino.ui.utils.DominoElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Navigation Component
 *
 * @author fushuwei
 */
public class NavigationComponent
    extends AbstractCompositeComponent<INavigationComponent.Controller, HTMLElement>
    implements INavigationComponent {

    private static final Logger logger = LoggerFactory.getLogger(NavigationComponent.class);

    public NavigationComponent() {
        super();
    }

    @Override
    public void render() {
        Tree<Menu> menuTree = Tree.create("我的笔记", new Menu())
            .setAutoCollapse(false)
            .enableSearch()
            .enableFolding();

        MenuServiceFactory.INSTANCE.queryMenus().onSuccess(response -> {
            try {
                buildMenuTree(menuTree, response);
            } catch (Exception e) {
                logger.error("菜单渲染失败", e);
            }
        }).onFailed(failedResponse -> {
            logger.error("Error: " + failedResponse.getStatusCode() + " - " +
                failedResponse.getStatusText() + " - ", failedResponse.getThrowable());
        }).send();

        initElement(DominoElement.div().appendChild(menuTree).element());
    }

    public void buildMenuTree(Tree<Menu> menuTree, List<Menu> menus) {
        // 渲染导航树
        menus.forEach(menu -> {
            if (menu.getParentId() == null || "".equals(menu.getParentId())) {
                TreeItem<Menu> treeItem = buildMenuItem(menu, getMenuMapping(menus));
                menuTree.appendChild(treeItem);
            }
        });
    }

    public TreeItem<Menu> buildMenuItem(Menu menu, Map<String, List<Menu>> mapping) {
        TreeItem<Menu> treeItem;

        // 判断是目录还是文件
        if (menu.isFolder()) {
            treeItem = TreeItem.create(menu.getName(), Icons.ALL.folder(), menu)
                .setExpandIcon(Icons.ALL.folder_open());
        } else {
            treeItem = TreeItem.create(menu.getName(), Icons.ALL.insert_drive_file(), menu)
                .setActiveIcon(Icons.ALL.file_check_outline_mdi());

            // 对文件节点添加点击事件
            treeItem.addClickListener(evt -> getController().onMenuClick(menu));
        }

        // 判断是否存在子菜单
        List<Menu> subMenuList;
        if ((subMenuList = mapping.get(menu.getId())) != null && !subMenuList.isEmpty()) {
            subMenuList.forEach(subMenu -> {
                TreeItem<Menu> subTreeItem = buildMenuItem(subMenu, mapping);
                treeItem.appendChild(subTreeItem);
            });
        }

        // 判断当前目录是否展开状态
        if (menu.isExpand()) {
            treeItem.expand();
        }

        return treeItem;
    }

    /**
     * 将菜单数据转成 parentId 和 subMenuList 的键值对
     *
     * @param menus 所有菜单
     * @return Map
     */
    public Map<String, List<Menu>> getMenuMapping(List<Menu> menus) {
        Map<String, List<Menu>> mapping = new HashMap<>();
        for (Menu menu : menus) {
            // 一级菜单不需要转换，因为其parentId为空
            if (menu.getParentId() == null || "".equals(menu.getParentId())) {
                continue;
            }
            List<Menu> tempList = new ArrayList<>();
            if (mapping.containsKey(menu.getParentId())) {
                tempList = mapping.get(menu.getParentId());
            }
            tempList.add(menu);
            mapping.put(menu.getParentId(), tempList);
        }
        return mapping;
    }
}
