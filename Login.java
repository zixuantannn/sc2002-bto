import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Login {
    private Scanner scanner;

    public Login(Scanner scanner) {
        this.scanner = scanner;
    }

    static public UserAccount authenticate(Database db, String input, String position) {
        UserAccount user = null;

        switch (position.toLowerCase()) {
            case "applicant":
                for (Applicant ap : db.applicantList) {
                    if (ap.getNRIC().equals(input)) {
                        user = ap;
                        break;
                    }
                }
                break;
            case "officer":
                for (Officer officer : db.officerList) {
                    if (officer.getNRIC().equals(input)) {
                        user = officer;
                        break;
                    }
                }
                break;
            case "manager":
                for (Manager manager : db.managerList) {
                    if (manager.getNRIC().equals(input)) {
                        user = manager;
                        break;
                    }
                }
                break;
            default:
                System.out.println("Invalid role.");
                return null;
        }

        if (user == null) {
            System.out.println("Invalid ID.");
            return null;
        }

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (password.equals(user.getPassword())) {
            System.out.println("\nLogin successful! Welcome, " + position + " " + user.getName() + "!");
            if ("password".equals(user.getPassword())) {
                System.out.println("You are using a default password.");
                System.out.println("Do you want to change password (yes/no)?");
                String answer = this.scanner.nextLine();
                if (answer.equalsIgnoreCase("yes")) {
                    System.out.print("Enter your new password: ");
                    String newPassword = scanner.nextLine();
                    user.changePassword(newPassword);
                    System.out.println("Password changed successfully.");
                }
            }
            return user;
        } else {
            System.out.println("Incorrect password.");
            return null;
        }
    }
}

