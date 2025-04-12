import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Database db = new Database();
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

        System.out.println("\n \n");

        while (true) {
            printMainMenu();
            Integer choice = InputValidation.getValidatedInt(scanner, "");
            if (choice == null) {
                System.out.println("Exiting program due to invalid input.");
                break;
            }
            System.out.println();
            switch (choice) {
                case 1:
                    printRoleMenu();
                    Integer roleChoice = InputValidation.getValidatedInt(scanner, "Enter your role (1-3): ");
                    if (roleChoice == null) {
                        System.out.println("Exiting program due to invalid input.");
                        break;
                    }

                    Login login = new Login(scanner);

                    switch (roleChoice) {
                        case 1:
                            System.out.println("Applicant Selected!");
                            String input = InputValidation.getValidatedString(scanner, "Enter your NRIC: ", false);
                            if (input == null) break;
                    
                            UserAccount user = login.authenticate(db, input, "applicant");
                            if (user instanceof Applicant) {
                                ApplicantUI applicantUI = new ApplicantUI((Applicant) user, db, scanner);
                                applicantUI.displayMenu();
                            }
                            break;
                    
                        case 2:
                            System.out.println("Officer Selected!");
                            String input1 = InputValidation.getValidatedString(scanner, "Enter your NRIC: ", false);
                            if (input1 == null) break;
                    
                            UserAccount user1 = login.authenticate(db, input1, "officer");
                            if (user1 instanceof Officer){
                                OfficerUI officerUI = new OfficerUI((Officer)user1, db, scanner);
                                officerUI.displayMenu();
                            }
                            break;
                    
                        case 3:
                            System.out.println("Manager Selected!");
                            String input2 = InputValidation.getValidatedString(scanner, "Enter your NRIC: ", false);
                            if (input2 == null) break;
                    
                            UserAccount user2 = login.authenticate(db, input2, "manager");
                            if(user2 instanceof Manager){
                                ManagerUI managerUI = new ManagerUI((Manager)user2, db, scanner);
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
        System.out.println("Choice: ");
    }
    
    private static String centerString(int width, String s) {
        return String.format("%" + ((width - s.length()) / 2 + s.length()) + "s%" +
                ((width - s.length() + 1) / 2) + "s", s, "");
    }
}
