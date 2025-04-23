package behavior;

import entity.UserAccount;
import boundary.ui.*;
import entity.Applicant;
import database.Database;

/**
 * Defines the behavior specific to users with the applicant role.
 * Responsible for launching the applicant's menu interface.
 */
public class ApplicantBehavior implements RoleBehavior {

    /**
     * Displays the applicant-specific menu interface.
     *
     * @param user the authenticated {@link UserAccount}, expected to be an
     *             {@link Applicant}
     * @param db   the {@link Database} instance providing access to system data
     */
    public void showMenu(UserAccount user, Database db) {
        new ApplicantUI((Applicant) user, db).displayMenu();
    }
}