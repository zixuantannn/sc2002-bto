import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ManagerUI implements ManagerMenu {
    Manager manager = null;
    Database database = null;
    Scanner scanner = null;

    public ManagerUI(Manager manager, Database db, Scanner scanner) {
        this.manager = manager;
        this.database = db;
        this.scanner = scanner;
    }

    public void logout() {
        EnquiryHandler.syncEnquiriesOnLogout(Database.enquiryList, manager.getEnquiryHandler().getEnquiryList());
        CSVWriter.saveEnquirieToCSV(Database.enquiryList, "EnquiryList.csv");
        System.out.println("You have successfully logged out.");
    }

    public void displayMenu() {
        int choice = 0;

        do {
            setTheHandledProject();

            System.out.println("\n--- Manager Menu ---");

            // Retrieve and display the current handled project (if any)
            if (this.manager.getHandledProject() != null) {
                System.out.println("Current Handled Project: " + this.manager.getHandledProject().getName());
            } else {
                System.out.println("Current Handled Project: NULL");
            }
            System.out.println("1. Create BTO project listings");
            System.out.println("2. Modify Existing Project Details");
            System.out.println("3. Remove Project from the System");
            System.out.println("4. Toggle project visibility");
            System.out.println("5. View All Project Listings");
            System.out.println("6. View the list of projects that you have created only.");
            System.out.println("7. Manage HDB Officer Registrations (Approve/Reject)");
            System.out.println("8. Manage BTO Applications (Approve/Reject)");
            System.out.println("9. Manage BTO Withdrawals (Approve/Reject)");
            System.out.println("10. Generate and Filter Reports");
            System.out.println("11. View all Enquiries of All Projects.");
            System.out.println("12. View and Reply All Enquiries of All Projects.");
            System.out.println("13. Log out");

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
                    addNewProjects();
                    break;
                case 2:
                    modifyExistingProjectDetails();
                    break;
                case 3:
                    removeProjectFromSystem();
                    break;
                case 4:
                    toggleVisibility();
                    break;
                case 5:
                    viewAllOrFilteredProjectListings();
                    break;
                case 6:
                    viewProjectsManagerCreated();
                    break;
                case 7:
                    manageOfficerRegistration();
                    break;
                case 8:
                    manageBTOApplication();
                    break;
                case 9:
                    manageBTOWithdrawal();
                    break;
                case 10:
                    generateReportFlatBooking();
                    break;
                case 11:
                    viewAllEnquiry();
                    break;
                case 12:
                    viewAndReplyAllEnquiry();
                    break;
                case 13:
                    logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again...");

            }
        } while (choice != 13);
        System.out.println("\n");
    }

    public void addNewProjects() {
        System.out.println("Add new project to BTO listing...");
        this.manager.createProjectListing(this.scanner, this.database);
    }

    public void modifyExistingProjectDetails() {
        System.out.println("Modify existing project detail...");
        this.manager.editProjectListing(this.scanner, this.database);
    }

    public void removeProjectFromSystem() {
        System.out.println("Remove project from the system...");
        this.manager.deleteProjectListing(this.scanner, this.database);
    }

    public void toggleVisibility() {
        System.out.println("Toggle visibility...");
        this.manager.setVisibility(this.scanner, this.database);
    }

    public void setTheHandledProject() {
        System.out.println("Automatically setting the handled project...");

        // Get the current system date
        Date currentDate = new Date(); // This will give us the current date and time

        // Iterate through the list of projects to check if any project is within the
        // application period and visibility is "on"
        Project targetProject = null;
        for (Project project : this.database.projectList) {
            // Get the open and close dates from the project
            Date openDate = project.getOpenDate();
            Date closeDate = project.getCloseDate();
            String visibility = project.getVisibility();

            // Check if the current date is within the application period and visibility is
            // "on"
            if ((currentDate.equals(openDate) || currentDate.after(openDate)) &&
                    (currentDate.equals(closeDate) || currentDate.before(closeDate)) &&
                    visibility.equalsIgnoreCase("on")) {
                targetProject = project;
                break; // Set the first project found that satisfies both conditions
            }
        }

        if (targetProject == null) {
            System.out.println("No projects are within the application period with visibility 'on'.");
            this.manager.setHandledProject(targetProject);
            return;
        }

        // Set the project as the handled project
        this.manager.setHandledProject(targetProject);
        System.out.println("Successfully set the project '" + targetProject.getName() + "' as the handled project.");
    }

    public void viewAllOrFilteredProjectListings() {
        System.out.println("View projects...");
        String answer = InputValidation.getYesOrNo("Do you want to view all project listings (yes/no)?\n", "Please enter 'yes' or 'no'.");
        if (answer.equalsIgnoreCase("yes")) {
            this.manager.viewAllProject(this.database);
        }

        answer = InputValidation.getYesOrNo("Do you want to view filtered project listings (yes/no)?\n", "Please enter 'yes' or 'no'.");

        if (answer.equalsIgnoreCase("yes")) {
            this.manager.viewFilteredProjects(this.scanner, this.database);
        }
    }

    public void viewProjectsManagerCreated() {
        this.manager.viewProjectsCreatedByManager(this.database);
    }

    public void manageOfficerRegistration() {
        System.out.println("Manage officer registration...");
        boolean check = false;
        String response = InputValidation.getYesOrNo("Do you want to view all available officer registration forms?(yes/no)\n", "Please enter 'yes' or 'no'.");
        if (response.equalsIgnoreCase("yes")) {
            check = this.manager.viewRegistration();
        }
        if (!check) {
            return;
        }
        System.out.println("Approve or reject officer registration form:");
        this.manager.assignOfficerToProject(this.scanner, this.database);
    }

    public void manageBTOApplication() {
        boolean check = false;
        System.out.println("Manage BTO application form...");
        String response = InputValidation.getYesOrNo("Do you want to view all available application forms?(yes/no)\n", "Please enter 'yes' or 'no'.");

        if (response.equalsIgnoreCase("yes")) {
            check = this.manager.viewApplication();
        }
        if (!check) {
            return;
        }
        System.out.println("Approve or reject BTO application form:");
        this.manager.manageApplicationForm(this.scanner);
    }

    public void manageBTOWithdrawal() {
        boolean check = false;
        System.out.println("Manage withdrawal request...");
        String response = InputValidation.getYesOrNo("Do you want to view all available withdrawal forms?(yes/no)\n", "Please enter 'yes' or 'no'.");

        if (response.equalsIgnoreCase("yes")) {
            check = this.manager.viewWithdrawalRequest();
        }
        if (!check){
            return;
        }
        System.out.println("Approve or reject BTO withdrawal form:");
        this.manager.manageWithdrawalRequest(this.scanner);
    }

    public void generateAndFilterReport() {

    }

    public void viewAllEnquiry() {
        System.out.println("View all enquiries...");
        this.manager.viewAllEnquiries();
    }

    public void viewAndReplyAllEnquiry() {
        System.out.println("View and reply all enquiries...");
        this.manager.viewAndReplyEnquiries(this.database);
    }

    public void generateReportFlatBooking(){
        this.manager.generateApplicantBookingReport(this.database, this.scanner);
    }
}
