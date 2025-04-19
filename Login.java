
public class Login {

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

        String password = InputValidation.getString("Enter your password: ",
                pw -> pw != null && !pw.trim().isEmpty(),
                "Password cannot be empty.");

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
                        System.out.println("Password changed successfully.");
                        System.out.println("Please login again with your new password.\n");
                        return null; // Forces relogin
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
