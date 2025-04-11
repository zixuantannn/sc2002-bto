import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Database db = new Database();
        CSVImporter.importEnquiry(db, "EnquiryList.csv");
        CSVImporter.importApplicants(db, "ApplicantList.csv");
        CSVImporter.importManagers(db, "ManagerList.csv");
        CSVImporter.importOfficers(db, "OfficerList.csv");
        CSVImporter.importProjects(db, "ProjectList.csv");

        System.out.println("Data loaded successfully.");
        System.out.println("=== Applicants ===");
        for (Applicant a : db.applicantList) {
            if (a != null)
                System.out.println(a.getName());
        }

        System.out.println("\n=== Managers ===");
        for (Manager m : db.managerList) {
            if (m != null)
                System.out.println(m.getName());
        }
        System.out.println("\n=== Officers ===");
        int officerCount = 0;
        for (Officer o : db.officerList) {
            if (o != null) {
                System.out.println(o.getName());
                officerCount++;
            }
        }
        System.out.println("Total officers loaded: " + officerCount);

        int projectCount = 0;
        System.out.println("\n=== Project Details ===");
        for (Project p : db.projectList) {
            if (p != null) {
                p.viewProjectDetails();
                projectCount++;
            }
        }
        System.out.println("Total projects loaded: " + projectCount);

        int enquiryCount = 0;
        int nextid = 0;
        System.out.println("\n=== Enquiry Details ===");
        for (Enquiry e : db.enquiryList) {
            if (e != null) {
                e.viewDetails();
                enquiryCount++;
            }
        }
        System.out.println("Total enquiry loaded: " + enquiryCount);
        System.out.println("Next enquiry ID: " + Enquiry.getCount());

        System.out.println("\n \n");

        while (true) {
            printMainMenu();
            int choice;
            try {
                System.out.print("\nEnter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    printRoleMenu();
                    int roleChoice = scanner.nextInt();
                    scanner.nextLine();

                    switch (roleChoice) {
                        case 1:
                            System.out.println("Applicant Selected!");
                            System.out.print("Enter your NRIC: ");
                            String input = scanner.nextLine();
                            boolean availability = login(db, input, "applicant");
                            if (availability) {
                                Applicant user = null;
                                for (Applicant ap : db.applicantList) {
                                    if (ap.getNRIC().equals(input)) {
                                        user = ap;
                                        break;
                                    }
                                }
                                ApplicantUI applicantUI = new ApplicantUI(user, db, scanner);
                                applicantUI.displayMenu();
                            }
                            break;
                        case 2:
                            System.out.println("Officer Selected!");
                            System.out.print("Enter your NRIC: ");
                            String input1 = scanner.nextLine();
                            boolean availability1 = login(db, input1, "officer");
                            if (availability1) {
                                Officer user = null;
                                for (Officer officer : db.officerList) {
                                    if (officer.getNRIC().equals(input1)) {
                                        user = officer;
                                        break;
                                    }
                                }
                                OfficerUI officerUI = new OfficerUI(user, db, scanner);
                                officerUI.displayMenu();

                            }
                            break;
                        case 3:
                            System.out.println("Manager Selected!");
                            System.out.print("Enter your NRIC: ");
                            String input2 = scanner.nextLine();
                            boolean availability2 = login(db, input2, "manager");
                            if (availability2) {
                                Manager user = null;
                                for (Manager manager : db.managerList) {
                                    if (manager.getNRIC().equals(input2)) {
                                        user = manager;
                                        break;
                                    }
                                }
                                ManagerUI managerUI = new ManagerUI(user, db, scanner);
                                managerUI.displayMenu();
                            }
                            break;
                        default:
                            System.out.println("Invalid role choice!");
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Feature to create a new applicant account is under construction.");
                    break;
                case 3:
                    System.out.println("Exiting the BTO App. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
            System.out.println("-----------------------------");
        }
    }

    private static void printMainMenu() {
        String line = "+--------------------------------------------+";
        System.out.println(line);
        System.out.printf("|%s|\n", centerString(44, "Welcome to the BTO App"));
        System.out.println(line);
        System.out.printf("| %-42s |\n", "1. Login");
        System.out.printf("| %-42s |\n", "2. Create new Applicant account");
        System.out.printf("| %-42s |\n", "3. Exit");
        System.out.println(line);
        System.out.print("Choice: ");
    }

    private static void printRoleMenu() {
        String line = "+--------------------------------------------+";
        System.out.println(line);
        System.out.printf("|%s|\n", centerString(44, "Select Your Role"));
        System.out.println(line);
        System.out.printf("| %-42s |\n", "1. Applicant");
        System.out.printf("| %-42s |\n", "2. Officer");
        System.out.printf("| %-42s |\n", "3. Manager");
        System.out.println(line);
        System.out.print("Choice: ");
    }

    private static String centerString(int width, String s) {
        return String.format("%" + ((width - s.length()) / 2 + s.length()) + "s%" +
                ((width - s.length() + 1) / 2) + "s", s, "");
    }

    private static boolean login(Database db, String input, String position) {

        UserAccount user = null;
        boolean isApplicant = false;
        boolean isOfficer = false;
        boolean isManager = false;

        for (Applicant ap : db.applicantList) {
            if (ap.getNRIC().equals(input)) {
                isApplicant = true;
                user = ap;
                break;
            }
        }

        for (Officer officer : db.officerList) {
            if (officer.getNRIC().equals(input)) {
                isOfficer = true;
                user = officer;
                break;
            }
        }

        for (Manager manager : db.managerList) {
            if (manager.getNRIC().equals(input)) {
                isManager = true;
                user = manager;
                break;
            }
        }

        if (user == null) {
            System.out.println("Invalid ID.");
            return false;
        }

        String role = position;

        if (role.equals("applicant") && !isApplicant) {
            System.out.println("Invalid ID.");
            return false;
        } else if (role.equals("officer") && !isOfficer) {
            System.out.println("Invalid ID.");
            return false;
        } else if (role.equals("manager") && !isManager) {
            System.out.println("Invalid ID.");
            return false;
        }

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (password.equals(user.getPassword())) {
            System.out.println("\nLogin successful! Welcome, " + role + " " + user.getName() + "!");
            if ("password".equals(user.getPassword())) {
                System.out.println(
                        "You are logging in with a default password, please change your password to a strong one (Alphanumeric and 8+ characters).");
                String newPassword = InputValidation.getPassword("Enter your new password: ",
                        "Password must be alphanumeric and at least 8 characters long.");
                user.changePassword(newPassword);
                System.out.println("You have changed your password successfully.");
            }
            System.out.println("\nWelcome, " + user.getName() + "! You are logged in as " + role + ".");
        } else {
            System.out.println("Incorrect password.");
            return false;
        }
        return true;
    }
}
