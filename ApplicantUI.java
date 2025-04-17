
import java.util.InputMismatchException;
import java.util.Scanner;

public class ApplicantUI implements ApplicantMenu {
    Applicant applicant = null;
    Database database = null;
    Scanner scanner = null;

    public ApplicantUI(Applicant applicant, Database database, Scanner scanner) {
        this.applicant = applicant;
        this.database = database;
        this.scanner = scanner;
    }

    public void logout() {
        EnquiryHandler.syncEnquiriesOnLogout(Database.enquiryList, applicant.getEnquiryHandler().getEnquiryList());
   //   CSVWriter.saveEnquirieToCSV(Database.enquiryList, "EnquiryList.csv");
        System.out.println("You have successfully logged out.");
    }

    public void displayMenu() {
        int choice = 0;

        do {
            System.out.println("\n--- Applicant Menu ---");
            System.out.println("1. View All Available Projects");
            System.out.println("2. Apply for BTO Project");
            System.out.println("3. View Application Status");
            System.out.println("4. View the Applied Project");
            System.out.println("5. Withdrawal Apply Form");
            System.out.println("6. Submit Enquiry");
            System.out.println("7. Display All Enquiries");
            System.out.println("8. Edit Enquiries");
            System.out.println("9. Remove Enquiry");
            System.out.println("10. View your Flat Booking.");
            System.out.println("11. Logout\n");
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
                    viewAllAvailableProjects();
                    break;
                case 2:
                    applyForBTOProject();
                    break;
                case 3:
                    viewApplyFormStatus();
                    break;
                case 4:
                    viewTheAppliedProject();
                    break;
                case 5:
                    withdrawalApplyForm();
                    break;
                case 6:
                    submitEnquiry();
                    break;
                case 7:
                    displayEnquiries();
                    break;
                case 8:
                    editEnquiries();
                    break;
                case 9:
                    removeEnquiries();
                    break;
                case 10:
                    viewFlatBooking();
                    break;
                case 11:
                    logout();
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
        this.applicant.applyForProject(this.database, this.scanner);
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
        this.applicant.withdrawalApplication(this.scanner);
    }

    public void submitEnquiry() {
        System.out.println("Submitting an enquiry...");
        this.applicant.sendEnquiry(this.scanner, this.database);
    }

    public void displayEnquiries() {
        System.out.println("Displaying all enquiries...");
        this.applicant.displayEnquiry();
    }

    public void editEnquiries() {
        System.out.println("Modify enquiry...");
        this.applicant.modifyEnquiry(this.scanner);
    }

    public void removeEnquiries() {
        System.out.println("Remove enquiry...");
        this.applicant.removeEnquiry(this.scanner);
    }

    public void viewFlatBooking(){
        System.out.println("View Flat Booking...");
        this.applicant.viewTheFlatBooking(this.database);
    }    

}
