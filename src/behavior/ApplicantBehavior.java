package behavior;

import entity.UserAccount;
import boundary.ui.*;
import entity.Applicant;
import database.Database;

public class ApplicantBehavior implements RoleBehavior {
    public void showMenu(UserAccount user, Database db) {
        new ApplicantUI((Applicant) user, db).displayMenu();
    }
}
