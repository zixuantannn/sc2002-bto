
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Applicant extends UserAccount {
    private int applicantId;
    private boolean appliedForProject;
    private ApplicationForm applyForm = null;
    private EnquiryHandler enqHandler;
    private ApplicantProjectHandler applicantProjHandler;

    private List<ApplicationForm> projectApplied;

    public Applicant(String name, String NRIC, int age, String maritalStatus) {
        super(name, NRIC, age, maritalStatus);
        this.appliedForProject = false;
        this.enqHandler = new EnquiryHandler(NRIC, Database.enquiryList, EnquiryHandler.FILTER_BY_NRIC);
        this.applicantProjHandler = new ApplicantProjectHandler(Database.projectList);
    }

    public Applicant(String name, String NRIC, int age, String maritalStatus, String password) {
        super(name, NRIC, age, maritalStatus, password);
        this.appliedForProject = false;
        this.projectApplied = new ArrayList<>(); // need read from excel
        this.enqHandler = new EnquiryHandler(NRIC, Database.enquiryList, EnquiryHandler.FILTER_BY_NRIC);
        this.applicantProjHandler = new ApplicantProjectHandler(Database.projectList);
    }

    public ApplicationForm getApplyForm() {
        return this.applyForm;
    }

    public void setApplyForm(ApplicationForm form) {
        this.applyForm = form;
    }

    public List<ApplicationForm> getProjectAppliedList() {
        return projectApplied;
    }

    public boolean getAvailability() {
        return appliedForProject;
    }

    public void setAvailability() {
        this.appliedForProject = true;
    }

    public void resetAvailablilty() {
        this.appliedForProject = false;
    }

    public void addProjectList(ApplicationForm applicationForm) {
        this.projectApplied.add(applicationForm);
    }

    public void viewAvailableProjects(Database db) {
        List<Project> filteredProject = applicantProjHandler.filterAvailableProject(db, this);
        applicantProjHandler.viewAvailableProjects(filteredProject);

    }

    public void applyForProject(Database db, Scanner sc) {
        applicantProjHandler.applyForProject(db, sc, this);
    }

    // To display all the project applied
    public void viewAppliedProject() {
        applicantProjHandler.viewAppliedProject(this);
    }

    // To display only the lastest project
    public void viewApplicationStatus() {
        applicantProjHandler.viewApplicationStatus(this);
    }

    public void withdrawalApplication(Scanner sc) {
        applicantProjHandler.withdrawalApplication(sc, this);
    }

    public void sendEnquiry(Scanner sc, Database db) {
        String content;
        int choice = -1;
        do {
            System.out.println("----- Enquiry Type -----");
            System.out.println("1. General Enquiry");
            System.out.println("2. Project Related Enquiry");

            try {
                System.out.print("What type of enquiry do you want to submit (1/2): ");
                choice = sc.nextInt();
                sc.nextLine(); // consume newline

                if (choice != 1 && choice != 2) {
                    System.out.println("Invalid choice. Please enter 1 or 2 only.");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // consume invalid input
            }

        } while (choice != 1 && choice != 2);

        if (choice == 1) {
            // General enquiry
            System.out.print("Enter your enquiry content: ");
            content = sc.nextLine();
            enqHandler.createEnquiry(this.getNRIC(), content);

        } else if (choice == 2) {
            // Project-related enquiry
            List<Project> availableProjects = applicantProjHandler.filterAvailableProject(db, this);
            if (availableProjects.isEmpty()) {
                System.out.println("No available projects to make an enquiry about.");
                return;
            }

            System.out.print("Enter the project name: ");
            String projectName = sc.nextLine();

            boolean found = availableProjects.stream()
                    .anyMatch(p -> p.getName().equalsIgnoreCase(projectName));

            if (!found) {
                System.out.println("Project not found in available list. Enquiry not submitted.");
                return;
            }
            System.out.print("Enter your enquiry content: ");
            content = sc.nextLine();
            enqHandler.createEnquiry(this.getNRIC(), content, projectName);
        }

    }

    public void displayEnquiry() {
        enqHandler.displayMyEnquiries();
    }

    public void modifyEnquiry(Scanner sc) {
        enqHandler.modifyEnquiry(sc);
    }

    public void removeEnquiry(Scanner sc) {
        enqHandler.removeEnquriy(sc);

    }

}