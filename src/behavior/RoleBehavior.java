package behavior;

import entity.Applicant;
import entity.Officer;
import entity.UserAccount;
import database.Database;

/**
 * Defines the behavior for different user roles in the system.
 * Both {@link Applicant} and {@link Officer} implement this interface to
 * define how their respective menus are displayed, depending on their login
 * role.
 */
public interface RoleBehavior {

    /**
     * Displays the appropriate menu interface for the logged-in user based on their
     * role.
     * 
     * @param user the logged-in user, which can be an {@link Applicant} or an
     *             {@link Officer}
     * @param db   the database containing the system's data
     */
    public void showMenu(UserAccount user, Database db);
}
