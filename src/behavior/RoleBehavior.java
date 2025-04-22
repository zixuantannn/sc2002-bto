package behavior;

import entity.UserAccount;
import database.Database;

public interface RoleBehavior {
    public void showMenu(UserAccount user, Database db);
}
