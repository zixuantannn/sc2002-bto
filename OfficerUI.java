import java.util.Date;
import java.util.InputMismatchException;
import java.util.Date;

public class OfficerUI implements OfficerMenu {
    Officer officer = null;
    Database db = null;

    public OfficerUI(Officer officer, Database db) {
        this.officer = officer;
        this.db = db;
    }

    public void logout() {
        EnquiryHandler.syncEnquiriesOnLogout(Database.enquiryList, officer.getEnquiryHandler().getEnquiryList());
        // CSVWriter.saveEnquirieToCSV(Database.enquiryList, "EnquiryList.csv");
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
            System.out.println("6. View Application Forms in Handled Project");
            System.out.println("7. Flat Selection and Booking Management");
            System.out.println("8. Logout\n");

            choice = InputValidation.getInt("Choose an option: ",
                    input -> input >= 1 && input <= 7,
                    "Please enter a number between 1 and 7.");

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
                    viewApplicationForms();
                    break;
                case 7:
                    handleFlatBook();
                    break;
                case 8:
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
        this.officer.registerToProject(this.db);
    }

    /*
     * public void viewProjectsForApplyOfficer() {
     * System.out.println("View all available projects for apply officer...");
     * boolean check = false;
     * for (Project project : this.db.projectList) {
     * if (project.getNumOfficerSlots() > 0) {
     * project.viewProjectDetails();
     * check = true;
     * }
     * }
     * if (!check) {
     * System.out.println("There is no available project fo registering as Officer!"
     * );
     * }
     * }
     */

    public void viewProjectsForApplyOfficer() {
        System.out.println("View all available projects for apply officer...");
        boolean check = false;
        Date now = new Date(); // current time

        for (Project project : this.db.projectList) {
            boolean isVisible = project.getVisibility().equalsIgnoreCase("on");
            boolean isWithinDate = project.getOpenDate().before(now) && project.getCloseDate().after(now);
            boolean hasOfficerSlots = project.getNumOfficerSlots() > 0;

            if (isVisible && isWithinDate && hasOfficerSlots) {
                project.viewProjectDetails();
                check = true;
            }
        }

        if (!check) {
            System.out.println("There is no available project for registering as Officer!");
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
        this.officer.handleFlatBooking(this.db);
    }

    public void viewApplicationForms() {
        officer.viewApplicationsInHandledProject();
    }


}
