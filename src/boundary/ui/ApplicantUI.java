package boundary.ui;

import entity.Applicant;
import database.Database;
import utility.InputValidation;
import boundary.menu.ApplicantMenu;
import controller.EnquiryHandler;
import database.CSVWriter;

import java.util.InputMismatchException;

/**
 * The {@code ApplicantUI} class implements the {@link ApplicantMenu} interface
 * and handles the user interface for an applicant. It provides the applicant
 * with
 * various options such as viewing available projects, applying for projects,
 * submitting
 * and editing enquiries, and more.
 */
public class ApplicantUI implements ApplicantMenu {
    Applicant applicant = null;
    Database database = null;

    /**
     * Constructs an instance of {@code ApplicantUI}.
     * 
     * @param applicant the authenticated {@link Applicant} user
     * @param database  the {@link Database} instance containing system data
     */
    public ApplicantUI(Applicant applicant, Database database) {
        this.applicant = applicant;
        this.database = database;
    }

    /**
     * Logs the applicant out, syncing enquiries and saving application history.
     */
    public void logout() {
        EnquiryHandler.syncEnquiriesOnLogout(Database.enquiryList, applicant.getEnquiryHandler().getEnquiryList());
        // CSVWriter.saveEnquirieToCSV(Database.enquiryList, "EnquiryList.csv");
        System.out.println("You have successfully logged out.");
    }

    /**
     * Displays the applicant menu with different options. The user is prompted to
     * select an option and the corresponding action is performed.
     */
    public void displayMenu() {
        int choice = 0;

        do {
            System.out.println("\n--- Applicant Menu ---");
            System.out.println("1. View All Available Projects");
            System.out.println("2. Apply for BTO Project");
            System.out.println("3. View All Application Forms (Latest and History)");
            System.out.println("4. View Application Status");
            System.out.println("5. View the Applied Project");
            System.out.println("6. Withdrawal Apply Form");
            System.out.println("7. Submit Enquiry");
            System.out.println("8. Display All Enquiries");
            System.out.println("9. Edit Enquiries");
            System.out.println("10. Remove Enquiry");
            System.out.println("11. View your Flat Booking.");
            System.out.println("12. Logout\n");

            choice = InputValidation.getInt("Choose an option: ",
                    input -> input >= 1 && input <= 12,
                    "Please enter a number between 1 and 12.");

            switch (choice) {

                case 1:
                    viewAllAvailableProjects();
                    break;
                case 2:
                    applyForBTOProject();
                    break;
                case 3:
                    viewAllApplicationForms();
                    break;
                case 4:
                    viewApplyFormStatus();
                    break;
                case 5:
                    viewTheAppliedProject();
                    break;
                case 6:
                    withdrawalApplyForm();
                    break;
                case 7:
                    submitEnquiry();
                    break;
                case 8:
                    displayEnquiries();
                    break;
                case 9:
                    editEnquiries();
                    break;
                case 10:
                    removeEnquiries();
                    break;
                case 11:
                    viewFlatBooking();
                    break;
                case 12:
                    logout();
                    CSVWriter.saveApplicationHistory("data/ApplicationHistory.csv");
                    return;
                default:
                    System.out.println("Invalid choice! Please choose a valid option.");
            }
        } while (choice != 12);
        System.out.println("\n");
    }

    /**
     * Displays all available BTO projects.
     */
    public void viewAllAvailableProjects() {
        System.out.println("Displaying all available BTO projects based on your user group and visibility...");
        this.applicant.viewAvailableProjects(database);
    }

    /**
     * Initiates the process for the applicant to apply for a BTO project.
     */
    public void applyForBTOProject() {
        System.out.println("Applying for a BTO project...");
        this.applicant.applyForProject(this.database);
        ;
    }

    /**
     * Views the status of the applicant's application form.
     */
    public void viewApplyFormStatus() {
        this.applicant.viewApplicationStatus();
    }

    /**
     * Displays the details of the applicant's applied BTO project.
     */
    public void viewTheAppliedProject() {
        System.out.println("Viewing your applied BTO project...");
        this.applicant.viewAppliedProject();
    }

    /**
     * Submit withdrawal form for application.
     */
    public void withdrawalApplyForm() {
        System.out.println("Withdrawing the application form...");
        this.applicant.withdrawalApplication();
    }

    /**
     * Submits an enquiry.
     */
    public void submitEnquiry() {
        System.out.println("Submitting an enquiry...");
        this.applicant.sendEnquiry(this.database);
    }

    /**
     * Displays all the enquiries made by the applicant.
     */
    public void displayEnquiries() {
        System.out.println("Displaying all enquiries...");
        this.applicant.displayEnquiry();
    }

    /**
     * Edits an existing enquiry submitted by the applicant.
     */
    public void editEnquiries() {
        System.out.println("Modify enquiry...");
        this.applicant.modifyEnquiry();
    }

    /**
     * Removes an enquiry made by the applicant.
     */
    public void removeEnquiries() {
        System.out.println("Remove enquiry...");
        this.applicant.removeEnquiry();
    }

    /**
     * Views the applicant's flat booking details.
     */
    public void viewFlatBooking() {
        System.out.println("View Flat Booking...");
        this.applicant.viewTheFlatBooking(this.database);
    }

    /**
     * Views all application forms submitted by the applicant
     */
    public void viewAllApplicationForms() {
        this.applicant.viewAllApplicationForms();
    }
}
