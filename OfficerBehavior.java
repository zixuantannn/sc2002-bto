import java.util.Scanner;
public class OfficerBehavior implements RoleBehavior {
    public void showMenu(UserAccount user, Database db, Scanner scanner) {
        new OfficerUI((Officer) user, db, scanner).displayMenu();
    }
}

