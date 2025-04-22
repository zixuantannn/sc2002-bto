package controller;

import boundary.menu.CommonMenu;

public class UserMenuHandler {
    private final CommonMenu menu;

    public UserMenuHandler(CommonMenu menu) {
        this.menu = menu;
    }

    public void show() {
        menu.displayMenu();
    }
}
