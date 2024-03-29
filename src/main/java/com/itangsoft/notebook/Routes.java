package com.itangsoft.notebook;

/**
 * Page Routing Address
 *
 * @author fushuwei
 */
public class Routes {

    /**
     * 登录
     */
    public static final String SHELL_LOGIN = "auth";
    public static final String ROUTE_LOGIN = "/" + Routes.SHELL_LOGIN + "/login";

    /**
     * 注册
     */
    public static final String SHELL_REGISTER = "user";
    public static final String ROUTE_REGISTER = "/" + Routes.SHELL_REGISTER + "/register";

    /**
     * 主界面
     */
    public static final String SHELL_LAYOUT = "layout";
    public static final String ROUTE_HOME = "/" + Routes.SHELL_LAYOUT + "/home";

    /**
     * 示例页面
     */

    public static final String SHELL_EXAMPLE_EVENTBUS = "example-eventbus-shell";
    public static final String ROUTE_EXAMPLE_EVENTBUS = "/" + Routes.SHELL_EXAMPLE_EVENTBUS + "/eventbus";

    public static final String SHELL_EXAMPLE_LAYOUT = "example-layout-shell";
    public static final String ROUTE_EXAMPLE_SINGLE_PAGE_LAYOUT_HOME = "/" + Routes.SHELL_EXAMPLE_LAYOUT + "/single-page-layout/home";
    public static final String ROUTE_EXAMPLE_SINGLE_PAGE_LAYOUT_CONTENT = "/" + Routes.SHELL_EXAMPLE_LAYOUT + "/single-page-layout/home/content/:fileName";

    public static final String SHELL_EXAMPLE_WATERMARK = "example-watermark-shell";
    public static final String ROUTE_EXAMPLE_WATERMARK = "/" + Routes.SHELL_EXAMPLE_WATERMARK + "/watermark";
}
