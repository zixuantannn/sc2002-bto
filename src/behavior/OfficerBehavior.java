package behavior;

import entity.UserAccount;
import boundary.ui.OfficerUI;
import entity.Officer;
import database.Database;

/**
 * Defines the behavior specific to users with the officer role.
 * Shows the Officer's menu when they log in.
 */
public class OfficerBehavior implements RoleBehavior {

    /**
     * Displays the menu interface for the logged-in Officer.
     *
     * @param user the logged-in user, expected to be an {@link Officer}
     * @param db   the database containing system data
     */
    public void showMenu(UserAccount user, Database db) {
        new OfficerUI((Officer) user, db).displayMenu();
    }
}