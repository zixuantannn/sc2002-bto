import java.util.InputMismatchException;
import java.util.Scanner;

public class ManagerUI implements ManagerMenu{
    Manager manager = null;
    Database database = null;
    Scanner scanner = null;

    public ManagerUI(Manager manager, Database db, Scanner scanner){
        this.manager = manager;
        this.database = db;
        this.scanner = scanner;
    }

    
    public void logout(){
        System.out.println("You have successfully logged out.");
    }

    public void displayMenu() {
        int choice = 0;

        do {
            System.out.println("\n--- Manager Menu ---");
            System.out.println("1. Create BTO project listings");
            System.out.println("2. Modify Existing Project Details");
            System.out.println("3. Remove Project from the System");
            System.out.println("4. Toggle project visibility");
            System.out.println("5. Choose your project you want to handle.");
            System.out.println("6. View All and Filtered Project Listings");
            System.out.println("7. Manage HDB Officer Registrations(Approve/Reject)");
            System.out.println("8. Approve or Reject BTO Applications");
            System.out.println("9. Approve or Reject BTO Withdrawal");
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
                    setTheHandledProject();
                    break;
                case 6:
                    viewAllOrFilteredProjectListings();
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

    public void addNewProjects(){
        System.out.println("Add new project to BTO listing...");
        this.manager.createProjectListing(this.scanner, this.database);
    }

    public void modifyExistingProjectDetails(){
        System.out.println("Modify existing project detail...");
        this.manager.editProjectListing(this.scanner, this.database);
    }

    public void removeProjectFromSystem(){
        System.out.println("Remove project from the system...");
        this.manager.deleteProjectListing(this.scanner, this.database);
    }

    public void toggleVisibility(){
        System.out.println("Toggle visibility...");
        this.manager.setVisibility(this.scanner, this.database);
    }

    public void setTheHandledProject(){
        System.out.println("Set the handled project...");
        if(this.manager.getHandledProject() != null){
            System.out.println("You have already handled the project!");
            System.out.println("You are only able to manage a project during the period of application.");
            return ;
        }
        System.out.print("Enter your project name you want to handle: ");
        Project targetProject = null;
        String target = scanner.nextLine();
        for(Project project : this.database.projectList){
            if(target.equals(project.getName())){
                targetProject = project;
                break;
            }
        }
        if(targetProject == null){
            System.out.println("The project you are finding are not available.");
            return;
        }
        this.manager.setHandledProject(targetProject);
        System.out.println("Successfully");
    }

    public void viewAllOrFilteredProjectListings(){
        System.out.println("View projects...");
        System.out.println("Do you want to view all project listings?(yes/no)");
        String answer = scanner.nextLine();
        if(answer.equalsIgnoreCase("yes")){
            this.manager.viewAllProject(this.database);
        }

        System.out.println("Do you want to view filtered project listings?(yes/no)");
        answer = scanner.nextLine();
        if(answer.equalsIgnoreCase("yes")){
            this.manager.viewFilteredProjects(this.scanner, this.database);
        }
    }

    public void manageOfficerRegistration(){
        System.out.println("Manage officer registration...");
        System.out.println("Do you want to view all available officer registration forms?(yes/no)");
        String response = scanner.nextLine();
        if(response.equalsIgnoreCase("yes")){
            this.manager.viewRegistration();
        }
        System.out.println("Approve or reject officer registration form:");
        this.manager.assignOfficerToProject(this.scanner, this.database);
    }

    public void manageBTOApplication(){
        System.out.println("Manage BTO application form...");
        System.out.println("Do you want to view all available application forms?(yes/no)");
        String response = scanner.nextLine();
        if(response.equalsIgnoreCase("yes")){
            this.manager.viewApplication();
        }
        System.out.println("Approve or reject BTO application form:");
        this.manager.manageApplicationForm(this.scanner);
    }

    public void manageBTOWithdrawal(){
        System.out.println("Manage withdrawal request...");
        System.out.println("Do you want to view all available withdrawal forms?(yes/no)");
        String response = scanner.nextLine();
        if(response.equalsIgnoreCase("yes")){
            this.manager.viewWithdrawalRequest();
        }
        System.out.println("Approve or reject officer registration form:");
        this.manager.manageWithdrawalRequest(this.scanner);
    }

    public void generateAndFilterReport(){

    }

    public void viewAllEnquiry(){
        System.out.println("View all enquiries...");
        this.manager.viewAllEnquiries(this.database);
    }

    public void viewAndReplyAllEnquiry(){
        System.out.println("View and reply all enquiries...");
        this.manager.viewAndReplyEnquiries(this.database);
    }
}