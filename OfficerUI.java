import java.util.InputMismatchException;
import java.util.Scanner;

public class OfficerUI implements OfficerMenu {
    Officer officer = null;
    Database db = null;
    Scanner scanner = new Scanner(System.in);

    public OfficerUI(Officer officer, Database db, Scanner scanner) {
        this.officer = officer;
        this.db = db;
        this.scanner = scanner;
    }

    public void logout() {
        EnquiryHandler.syncEnquiriesOnLogout(Database.enquiryList, officer.getEnquiryHandler().getEnquiryList());
        CSVWriter.saveEnquirieToCSV(Database.enquiryList, "EnquiryList.csv");
        System.out.println("You have successfully logged out.");
    }

    public void displayMenu() {
        int choice = 0;

        do {
            System.out.println("\n--- Officer Menu ---");
            System.out.println("1. Register Project as Officer under Compliant Conditions");
            System.out.println("2. View available Project"); // check Project if available slots >= 0
            System.out.println("3. Check Registration Status");
            System.out.println("4. View Handled Project Details");
            System.out.println("5. View and Reply Enquiry List");
            System.out.println("6. Flat Selection and Booking Management");
            System.out.println("7. Logout\n");
            System.out.print("Choose an option: ");

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
                    registerProjectAsOfficer();
                    break;
                case 2:
                    viewProjectsForApplyOfficer();
                    break;
                case 3:
                    checkRegistrationStatus();
                    break;
                case 4:
                    viewHandledProject();
                    break;
                case 5:
                    viewAndReplyEnquiryList();
                    break;
                case 6:
                    handleFlatBook();
                    break;
                case 7:
                    logout();
                    return;
                default:
                    System.out.println("Invalid choice! Please choose a valid option.");
            }
        } while (choice != 7);
        System.out.println("\n");
    }

    public void registerProjectAsOfficer() {
        System.out.println("Registering a new project as Officer...");
        this.officer.registerToProject(this.scanner, this.db);
    }

    public void viewProjectsForApplyOfficer() {
        System.out.println("View all available projects for apply officer...");
        boolean check = false;
        for (Project project : this.db.projectList) {
            if (project.getNumOfficerSlots() > 0) {
                project.viewProjectDetails();
                check = true;
            }
        }
        if (!check) {
            System.out.println("There is no available project fo registering as Officer!");
        }
    }

    public void checkRegistrationStatus() {
        System.out.println("Checking registration status...");
        this.officer.viewRegistrationStatus();
    }

    public void viewHandledProject() {
        System.out.println("Viewing handled projects...");
        this.officer.viewHandledProjectDetails();
    }

    public void viewAndReplyEnquiryList() {
        System.out.println("Viewing and replying to enquiry list...");
        this.officer.viewAndReplyEnquiries();
    }

    public void handleFlatBook() {
        System.out.println("Handling flat booking process...");
        this.officer.handleFlatBooking(this.scanner, this.db);
    }

}
