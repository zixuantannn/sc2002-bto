
public class OfficerBehavior implements RoleBehavior {
    public void showMenu(UserAccount user, Database db) {
        new OfficerUI((Officer) user, db).displayMenu();
    }
}
