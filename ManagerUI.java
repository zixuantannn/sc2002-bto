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
        System.out.println("You have successfully logged out.");
    }

    public void displayMenu() {
        int choice = 0;

        do {
            // setTheHandledProject();

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
            System.out.println("5. View All and Filtered Project Listings");
            System.out.println("6. Manage HDB Officer Registrations(Approve/Reject)");
            System.out.println("7. Approve or Reject BTO Applications");
            System.out.println("8. Approve or Reject BTO Withdrawal");
            System.out.println("9. Generate and Filter Reports");
            System.out.println("10. View all Enquiries of All Projects.");
            System.out.println("11. View and Reply All Enquiries of All Projects.");
            System.out.println("12. Log out");

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
                    manageOfficerRegistration();
                    break;
                case 7:
                    manageBTOApplication();
                    break;
                case 8:
                    manageBTOWithdrawal();
                    break;
                case 9:
                    break;
                case 10:
                    viewAllEnquiry();
                    break;
                case 11:
                    viewAndReplyAllEnquiry();
                    break;
                case 12:
                    logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again...");

            }
        } while (choice != 12);
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
            return;
        }

        // Set the project as the handled project
        this.manager.setHandledProject(targetProject);
        System.out.println("Successfully set the project '" + targetProject.getName() + "' as the handled project.");
    }

    public void viewAllOrFilteredProjectListings() {
        System.out.println("View projects...");
        System.out.println("Do you want to view all project listings?(yes/no)");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("yes")) {
            this.manager.viewAllProject(this.database);
        }

        System.out.println("Do you want to view filtered project listings?(yes/no)");
        answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("yes")) {
            this.manager.viewFilteredProjects(this.scanner, this.database);
        }
    }

    public void manageOfficerRegistration() {
        System.out.println("Manage officer registration...");
        System.out.println("Do you want to view all available officer registration forms?(yes/no)");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("yes")) {
            this.manager.viewRegistration();
        }
        System.out.println("Approve or reject officer registration form:");
        this.manager.assignOfficerToProject(this.scanner, this.database);
    }

    public void manageBTOApplication() {
        System.out.println("Manage BTO application form...");
        System.out.println("Do you want to view all available application forms?(yes/no)");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("yes")) {
            this.manager.viewApplication();
        }
        System.out.println("Approve or reject BTO application form:");
        this.manager.manageApplicationForm(this.scanner);
    }

    public void manageBTOWithdrawal() {
        System.out.println("Manage withdrawal request...");
        System.out.println("Do you want to view all available withdrawal forms?(yes/no)");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("yes")) {
            this.manager.viewWithdrawalRequest();
        }
        System.out.println("Approve or reject officer registration form:");
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
}
