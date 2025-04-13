import java.util.Scanner;

public class Login {

    static public UserAccount authenticate(Database db, String input, String position, Scanner scanner) {
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
                String answer = InputValidation.getYesOrNo("Do you want to change password (yes/no)?\n",
                        "Please enter 'yes' or 'no'.");

                if (answer.equalsIgnoreCase("yes")) {
                    // Prompt user for a new strong password
                    String newPassword = InputValidation.getStrongPassword("Enter your new strong password: ",
                            "Password must be at least 8 characters long and contain letters and digits.");
                    if (newPassword != null) {
                        user.changePassword(newPassword); // Change the password if it's strong

                        if (user instanceof Officer) {
                            CSVWriter.updatePassword("OfficerList.csv", db, user);
                        } else if (user instanceof Applicant) {
                            CSVWriter.updatePassword("Applicant.csv", db, user);

                        } else if (user instanceof Manager) {
                            CSVWriter.updatePassword("ManagerList.csv", db, user);
                        }

                        System.out.println("Password changed successfully.");
                    }
                }
            }
            return user;
        } else {
            System.out.println("Incorrect password.");
            return null;
        }
    }
}
