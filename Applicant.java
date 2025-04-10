
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Applicant extends UserAccount {
    private int applicantId;
    private boolean appliedForProject;
    private ApplicationForm applyForm = null;
    private EnquiryManager manager;

    private List<ApplicationForm> projectApplied;

    public Applicant(String name, String NRIC, int age, String maritalStatus) {
        super(name, NRIC, age, maritalStatus);
        this.appliedForProject = false;
        this.manager = new EnquiryManager();
    }

    public Applicant(String name, String NRIC, int age, String maritalStatus, String password) {
        super(name, NRIC, age, maritalStatus, password);
        this.appliedForProject = false;
        this.projectApplied = new ArrayList<>(); // need read from excel
        this.manager = new EnquiryManager();
    }

    public ApplicationForm getApplyForm() {
        return this.applyForm;
    }

    public void setApplyForm(ApplicationForm form) {
        this.applyForm = form;
    }

    public void setAvailablilty() {
        this.appliedForProject = false;
    }

    public List<Project> filterAvailableProject(Database db) {
        List<Project> allProjects = db.projectList;
        List<Project> filteredProjects = new ArrayList<>();

        for (Project project : allProjects) {
            // Singles, 35 years old and above, can only view and apply 2-room
            if (this.getAge() > 34 && this.getMaritalStatus().equals("Single")) {
                // If at least 1 2-room is available and visibility is on
                if (project.getNumType1() > 0) {
                    filteredProjects.add(project);
                }
            } else if (super.getAge() > 20 && super.getMaritalStatus().equals("Married")) {
                // If either 2-room or 3-room is available and visibility is on
                if ((project.getNumType1() > 0 || project.getNumType2() > 0)) {
                    filteredProjects.add(project);
                }
            } // then how about people who fall out of this range
        }

        filteredProjects.sort(Comparator.comparing(Project::getName));
        return filteredProjects;
    }

    public void viewAvailableProjects(List<Project> filteredProjects) {

        if (filteredProjects.isEmpty()) {
            System.out.println("No projects available.");
        } else {
            boolean check = false;
            for (Project project : filteredProjects) {
                if (project.getVisibility().equals("on")) {
                    project.viewProjectDetails();
                    check = true;
                }
            }
            if (!check) {
                System.out.println("No available project to apply!");
            }
        }
    }

    public void applyForProject(Database db, Scanner sc) {
        if (appliedForProject) {
            System.out.println("Cannot apply for multiple projects.");
            return;
        }
        List<Project> filteredProjects = this.filterAvailableProject(db);
        this.viewAvailableProjects(filteredProjects);
        if (filteredProjects.isEmpty()) {
            System.out.println("No available project to apply!");
        }
        System.out.print("Enter your project name you want to apply: ");
        String appliedProject = sc.nextLine();

        Project project = filteredProjects.stream()
                .filter(p -> appliedProject.equalsIgnoreCase(p.getName())) // filter for project that name matches with
                                                                           // appliedProject
                .findFirst() // find the first match and return as an Optional
                .orElse(null); // if optional is empty (no match), return null

        if (project == null) {
            System.out.println("Project not found !");
        } else {
            this.applyForm = new ApplicationForm(this, appliedProject, "Pending");
            this.appliedForProject = true;
            project.getListOfApplyForm().add(this.applyForm);
            this.projectApplied.add(applyForm);
            System.out.println("Application submitted successfully !");
        }
    }

    // To display all the project applied
    public void viewAppliedProject() {
        if (!projectApplied.isEmpty()) { // list is not empty
            System.out.println("You applied for the following project: ");
            for (ApplicationForm form : projectApplied) {
                System.out.println("Project Name: " + form.getAppliedProjectName() + " | Application Status: "
                        + form.getApplicationStatus());
                // do we need display flat type
            }
        } else {
            System.out.println("You have not applied for any project yet.");
        }
    }

    // To display only the lastest project
    public void viewApplicationStatus() {
        if (!projectApplied.isEmpty()) {
            System.out.println("Lastest Application:");
            System.out.println("Project Name: " + projectApplied.get(0).getAppliedProjectName() +
                    " | Application Status: " + projectApplied.get(0).getApplicationStatus());
        } else {
            System.out.println("You have no latest project.");
        }
    }

    public void withdrawalApplication(Scanner sc) {
        // If there are no projects
        if (projectApplied.isEmpty()) {
            System.out.println("You have not applied for any project. ");
        }
        // If no pending project in the list
        else if (!(projectApplied.get(0)).getApplicationStatus().equals("Pending")) {
            System.out.println("You have no pending projects. ");
        } else {
            System.out.println("What is the reason for withdrawal: ");
            String reason = sc.nextLine();
            this.applyForm.createWithdrawalRequest(reason);
            System.out.println("You have requested withdrawal for your BTO application");
        }
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
            manager.createEnquiry(this.getNRIC(), content);

        } else if (choice == 2) {
            // Project-related enquiry
            List<Project> availableProjects = this.filterAvailableProject(db);
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
            manager.createEnquiry(this.getNRIC(), content, projectName);
        }

    }

    public void displayEnquiry() {
        manager.displayMyEnquiries();
    }

    public void modifyEnquiry(Scanner sc) {
        manager.modifyEnquiry(sc);
    }

    public void removeEnquiry(Scanner sc) {
        manager.removeEnquriy(sc);

    }

}