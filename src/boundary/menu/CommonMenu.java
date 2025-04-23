package boundary.menu;

/**
 * Common menu interface that defines basic operations for all user roles
 * in the system, such as displaying the main menu and logging out.
 */
public interface CommonMenu {

    /**
     * Logs the user out of the system.
     */
    public void logout();

    /**
     * Displays main menu for the current user.
     */
    public void displayMenu();
}
