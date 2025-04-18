import java.util.Scanner;

public class ApplicantBehavior implements RoleBehavior {
    public void showMenu(UserAccount user, Database db) {
        new ApplicantUI((Applicant) user, db).displayMenu();
    }
}
