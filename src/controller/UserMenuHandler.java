package controller;

import boundary.menu.CommonMenu;

/**
 * The {@code UserMenuHandler} class is responsible for handling the user
 * interface
 * related to the menu display. It interacts with the {@link CommonMenu} class
 * to
 * display the available options for the user.
 */
public class UserMenuHandler {
    private final CommonMenu menu;

    /**
     * Constructs a new {@code UserMenuHandler} instance with the specified menu.
     *
     * @param menu The {@link CommonMenu} instance to be displayed to the user.
     */
    public UserMenuHandler(CommonMenu menu) {
        this.menu = menu;
    }

    /**
     * Displays the menu to the user by calling the {@link CommonMenu#displayMenu()}
     * method.
     */
    public void show() {
        menu.displayMenu();
    }
}
