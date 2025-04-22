package behavior;

import entity.UserAccount;
import boundary.ui.OfficerUI;
import entity.Officer;
import database.Database;

public class OfficerBehavior implements RoleBehavior {
    public void showMenu(UserAccount user, Database db) {
        new OfficerUI((Officer) user, db).displayMenu();
    }
}