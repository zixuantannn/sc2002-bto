import java.util.Scanner;
public class ApplicantBehavior implements RoleBehavior {
    public void showMenu(UserAccount user, Database db, Scanner scanner) {
        new ApplicantUI((Applicant) user, db, scanner).displayMenu();
    }
}
