import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();

        // Load CSV files
        CSVImporter.importEnquiry(db, "EnquiryList.csv");
        CSVImporter.importApplicants(db, "ApplicantList.csv");
        CSVImporter.importManagers(db, "ManagerList.csv");
        CSVImporter.importOfficers(db, "OfficerList.csv");
        CSVImporter.importProjects(db, "ProjectList.csv");

        // Display loading confirmation
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
        System.out.println("\n");

        int enquiryCount = 0;
        System.out.println("\n=== Enquiry Details ===");
        for (Enquiry e : db.enquiryList) {
            if (e != null) {
                e.viewDetails();
                enquiryCount++;
            }
        }
        System.out.println("Total enquiry loaded: " + enquiryCount);
        System.out.println("Next enquiry ID: " + Enquiry.getCount());

        while (true) {
            printMainMenu();

            int choice = InputValidation.getInt("Enter your choice: ",
                    i -> i >= 1 && i <= 3,
                    "Please enter a valid option between 1 and 3.");

            System.out.println();

            switch (choice) {
                case 1:
                    printRoleMenu();
                    int roleChoice = InputValidation.getInt("Enter your role (1-3): ",
                            i -> i >= 1 && i <= 3,
                            "Please enter a valid role number (1-3).");

                    String role = getRoleFromChoice(roleChoice);

                    String inputNRIC = InputValidation.getString("Enter your NRIC: ",
                            s -> !s.trim().isEmpty(),
                            "NRIC cannot be empty.");

                    UserAccount user = Login.authenticate(db, inputNRIC, role, new Scanner(System.in));

                    if (user instanceof Applicant) {
                        new ApplicantUI((Applicant) user, db, new Scanner(System.in)).displayMenu();
                    } else if (user instanceof Officer) {
                        new OfficerUI((Officer) user, db, new Scanner(System.in)).displayMenu();
                    } else if (user instanceof Manager) {
                        new ManagerUI((Manager) user, db, new Scanner(System.in)).displayMenu();
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

    private static String getRoleFromChoice(int roleChoice) {
        switch (roleChoice) {
            case 1:
                return "applicant";
            case 2:
                return "officer";
            case 3:
                return "manager";
            default:
                return "";
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
    }

    private static String centerString(int width, String s) {
        return String.format("%" + ((width - s.length()) / 2 + s.length()) + "s%" +
                ((width - s.length() + 1) / 2) + "s", s, "");
    }
}
