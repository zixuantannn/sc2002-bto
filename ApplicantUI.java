
import java.util.InputMismatchException;

public class ApplicantUI implements ApplicantMenu {
    Applicant applicant = null;
    Database database = null;

    public ApplicantUI(Applicant applicant, Database database) {
        this.applicant = applicant;
        this.database = database;
    }

    public void logout() {
        EnquiryHandler.syncEnquiriesOnLogout(Database.enquiryList, applicant.getEnquiryHandler().getEnquiryList());
        // CSVWriter.saveEnquirieToCSV(Database.enquiryList, "EnquiryList.csv");
        System.out.println("You have successfully logged out.");
    }

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
                    CSVWriter.saveApplicationHistory("applicationHistory.csv");
                    return;
                default:
                    System.out.println("Invalid choice! Please choose a valid option.");
            }
        } while (choice != 11);
        System.out.println("\n");
    }

    public void viewAllAvailableProjects() {
        System.out.println("Displaying all available BTO projects based on your user group and visibility...");
        this.applicant.viewAvailableProjects(database);
    }

    public void applyForBTOProject() {
        System.out.println("Applying for a BTO project...");
        this.applicant.applyForProject(this.database);
        ;
    }

    public void viewApplyFormStatus() {
        this.applicant.viewApplicationStatus();
    }

    public void viewTheAppliedProject() {
        System.out.println("Viewing your applied BTO project...");
        this.applicant.viewAppliedProject();
    }

    public void withdrawalApplyForm() {
        System.out.println("Withdrawing the application form...");
        this.applicant.withdrawalApplication();
    }

    public void submitEnquiry() {
        System.out.println("Submitting an enquiry...");
        this.applicant.sendEnquiry(this.database);
    }

    public void displayEnquiries() {
        System.out.println("Displaying all enquiries...");
        this.applicant.displayEnquiry();
    }

    public void editEnquiries() {
        System.out.println("Modify enquiry...");
        this.applicant.modifyEnquiry();
    }

    public void removeEnquiries() {
        System.out.println("Remove enquiry...");
        this.applicant.removeEnquiry();
    }

    public void viewFlatBooking() {
        System.out.println("View Flat Booking...");
        this.applicant.viewTheFlatBooking(this.database);
    }

    public void viewAllApplicationForms() {
        this.applicant.viewAllApplicationForms();
    }
}
