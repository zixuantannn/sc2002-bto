package boundary.ui;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Date;

import database.Database;
import utility.InputValidation;
import entity.Officer;
import controller.EnquiryHandler;
import entity.Project;
import boundary.menu.OfficerMenu;

/**
 * The {@code OfficerUI} class implements the {@link OfficerMenu} interface
 * and handles the user interface for an officer. It provides the officer with
 * various options such as registering a project, viewing available projects,
 * checking registration status, and more.
 */
public class OfficerUI implements OfficerMenu {
    Officer officer = null;
    Database db = null;

    /**
     * Constructs a {@code OfficerUI} instance with the given officer and database.
     *
     * @param officer The officer interacting with the system.
     * @param db      The database instance providing access to system data.
     */
    public OfficerUI(Officer officer, Database db) {
        this.officer = officer;
        this.db = db;
    }

    /**
     * Logs out the current officer, syncing any pending enquiries and
     * displaying a logout message.
     */
    public void logout() {
        EnquiryHandler.syncEnquiriesOnLogout(Database.enquiryList, officer.getEnquiryHandler().getEnquiryList());
        System.out.println("You have successfully logged out.");
    }

    /**
     * Displays the menu options for the officer, allowing them to choose actions
     * related to project registration, viewing projects, checking registration
     * status,
     * handling enquiries, and managing flat bookings.
     */
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
                    input -> input >= 1 && input <= 8,
                    "Please enter a number between 1 and 8.");

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
        } while (choice != 8);
        System.out.println("\n");
    }

    /**
     * Registers the officer for a project.
     */
    public void registerProjectAsOfficer() {
        System.out.println("Registering a new project as Officer...");
        this.officer.registerToProject(this.db);
    }

    /**
     * Displays all available projects for the officer to apply to, based on
     * visibility, open/close dates, and available officer slots.
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

    /**
     * Display the registration status of the officer.
     */
    public void checkRegistrationStatus() {
        System.out.println("Checking registration status...");
        this.officer.viewRegistrationStatus();
    }

    /**
     * Displays the details of the project that the officer is currently handling.
     */
    public void viewHandledProject() {
        System.out.println("Viewing handled projects...");
        this.officer.viewHandledProjectDetails();
    }

    /**
     * Allows the officer to view and reply to project-related enquiries.
     */

    public void viewAndReplyEnquiryList() {
        System.out.println("Viewing and replying to enquiry list...");
        this.officer.viewAndReplyEnquiries();
    }

    /**
     * Manages the flat booking process for the officer.
     */
    public void handleFlatBook() {
        System.out.println("Handling flat booking process...");
        this.officer.handleFlatBooking(this.db);
    }

    /**
     * Displays the application forms for the projects that the officer is handling.
     */
    public void viewApplicationForms() {
        officer.viewApplicationsInHandledProject();
    }

}
