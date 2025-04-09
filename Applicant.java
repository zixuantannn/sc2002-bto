
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Applicant extends UserAccount {
    private int applicantId;
    private boolean appliedForProject;
    private ApplicationForm applyForm = null;

    private List<ApplicationForm> projectApplied;
    private List<Enquiry> enquiryList;

    public Applicant(String name, String NRIC, int age, String maritalStatus) {
        super(name, NRIC, age, maritalStatus);
        this.appliedForProject = false;
    }

    public Applicant(String name, String NRIC, int age, String maritalStatus, String password) {
        super(name, NRIC, age, maritalStatus, password);
        this.appliedForProject = false;
        this.projectApplied = new ArrayList<>(); // need read from excel
        this.enquiryList = new ArrayList<>(); // need read from excel
    }

    public ApplicationForm getApplyForm() {
        return this.applyForm;
    }

    public void setApplyForm(ApplicationForm form) {
        this.applyForm = form;
    }

    public List<Project> viewAvailableProjects(Database db, boolean ok) {
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
        if (filteredProjects.isEmpty()) {
            System.out.println("No projects available.");
        } else {
            if (ok)
                return filteredProjects;
            System.out.println("List of Projects: ");
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
        return filteredProjects;
    }

    public void applyForProject(Database db, Scanner sc) {
        if (appliedForProject) {
            System.out.println("Cannot apply for multiple projects.");
            return;
        }
        List<Project> filteredProjects = this.viewAvailableProjects(db, true);
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

    public void sendEnquiry(Scanner sc) {
        System.out.println("Please enter your enquiry details: ");
        String content = sc.nextLine();
        Enquiry newEnquiry = new Enquiry(this.getNRIC(), content);
        enquiryList.add(newEnquiry);
        System.out.println("New Enquiry submitted.");
    }

    public void displayMyEnquiries() {
        for (Enquiry each : enquiryList) {
            each.viewDetails();
        }
    }

    public void modifyEnquiry() {
        Scanner sc = new Scanner(System.in);

        if (enquiryList.isEmpty()) {
            System.out.println("You have no enquiries to edit.");
            return;
        }

        // Display all enquiries
        displayMyEnquiries();

        System.out.print("Please enter the Enquiry ID to edit: ");
        int id = sc.nextInt();
        sc.nextLine();
        boolean found = false;
        for (Enquiry enquiry : enquiryList) {
            if (enquiry.getID() == id) {
                found = true;
                if (enquiry.getResponse() == null) { // Enquiry have not been answered
                    System.out.println("Please enter the new enquiry: ");
                    String newContent = sc.nextLine();
                    enquiry.updateContent(newContent);
                    System.out.println("Enquiry content updated successfully.");
                } else { // Enquiry have been answered
                    System.out.println("Enquiry has been answered. No further changes available.");
                }
                break;
            }
            if (!found) {
                System.out.println("Enquiry ID not found. Please check again.");
            }

        }
    }

    public void removeEnquiry() {
        Scanner sc = new Scanner(System.in);
        if (enquiryList.isEmpty()) {
            System.out.println("You have no enquiries to edit.");
            return;
        }

        // Display all enquiries
        displayMyEnquiries();

        System.out.print("Please enter the Enquiry ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine(); // To remove newline character left by nextInt()

        boolean found = false;
        Enquiry enquiryToRemove = findEnquiryByID(id, enquiryList);
        if (enquiryToRemove != null) {
            if (enquiryToRemove.getResponse() == null) {
                System.out.println("Chosen Enquiry: \nEnquiry ID:" + enquiryToRemove.getID() + " | Content: "
                        + enquiryToRemove.getContent() + "  | Response: " + enquiryToRemove.getResponse());
                String choice;
                do {
                    System.out.print("Are you sure want to delete this enquiry (yes/no):");
                    choice = sc.next().toLowerCase();
                } while (!choice.equals("yes") && !choice.equals("no"));

                if (choice.equals("yes")) {
                    enquiryList.remove(enquiryToRemove);
                    System.out.println("Enquiry have been removed.");
                } else {
                    System.out.println("No changes have been done");
                }
            } else {
                System.out.println("Enquiry has been answered. No further changes available.");
            }

        } else {
            System.out.println("Enquiry ID not found. Please check again.");
        }

    }

    public Enquiry findEnquiryByID(int id, List<Enquiry> list) { // can move it to EnquiryManager class
        for (Enquiry enquiry : list) {
            if (enquiry.getID() == id) {
                return enquiry;
            }

        }
        return null;
    }

}