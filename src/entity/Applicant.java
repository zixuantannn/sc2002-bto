package entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;

import controller.EnquiryHandler;
import controller.ApplicantProjectHandler;
import database.Database;
import utility.InputValidation;

public class Applicant extends UserAccount {
    private int applicantId;
    private boolean appliedForProject;
    private ApplicationForm applyForm = null;
    private EnquiryHandler enqHandler;
    private ApplicantProjectHandler applicantProjHandler;
    private FlatBooking flatBooking;

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
        this.projectApplied = new ArrayList<>();
        this.enqHandler = new EnquiryHandler(NRIC, Database.enquiryList, EnquiryHandler.FILTER_BY_NRIC);
        this.applicantProjHandler = new ApplicantProjectHandler(Database.projectList);
    }

    public EnquiryHandler getEnquiryHandler() {
        return enqHandler;
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

    public FlatBooking getFlatBooking() {
        return flatBooking;
    }

    public void setFlatBooking(FlatBooking flatBooking) {
        this.flatBooking = flatBooking;
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

    public void applyForProject(Database db) {
        applicantProjHandler.applyForProject(db, this);
    }

    // To display all the project applied
    public void viewAppliedProject() {
        applicantProjHandler.viewAppliedProject(this);
    }

    // To display only the lastest project
    public void viewApplicationStatus() {
        applicantProjHandler.viewApplicationStatus(this);
    }

    public void withdrawalApplication() {
        applicantProjHandler.withdrawalApplication(this);
    }

    public void sendEnquiry(Database db) {
        String content;
        int choice = -1;
        do {
            System.out.println("----- Enquiry Type -----");
            System.out.println("1. General Enquiry");
            System.out.println("2. Project Related Enquiry");
            choice = InputValidation.getInt("What type of enquiry do you want to submit (1/2):",
                    input -> input == 1 || input == 2,
                    "Invalid choice. Please enter 1 or 2 only.");

        } while (choice != 1 && choice != 2);

        if (choice == 1) {
            // General enquiry
            content = InputValidation.getString("Enter your enquiry content: ",
                    input -> !input.trim().isEmpty(),
                    "Enquiry content cannot be empty. ");
            enqHandler.createEnquiry(this.getNRIC(), content);

        } else if (choice == 2) {
            // Project-related enquiry
            List<Project> availableProjects = applicantProjHandler.filterAvailableProject(db, this);
            if (availableProjects.isEmpty()) {
                System.out.println(
                        "No available projects to make an enquiry about. You may make a general enquiry instead.");
                return;
            }

            String projectName = InputValidation.getString("Enter the project name: ",
                    input -> availableProjects.stream().anyMatch(p -> p.getName().equalsIgnoreCase(input)),
                    "Project not found. Please try again. ");

            content = InputValidation.getString("Enter your enquiry content: ",
                    input -> !input.trim().isEmpty(),
                    "Enquiry content cannot be empty. ");
            enqHandler.createEnquiry(this.getNRIC(), content, projectName);
        }

    }

    public void displayEnquiry() {
        enqHandler.displayMyEnquiries();
    }

    public void modifyEnquiry() {
        enqHandler.modifyEnquiry();
    }

    public void removeEnquiry() {
        enqHandler.removeEnquiry();

    }

    public void viewTheFlatBooking(Database database) {
        boolean found = false;
        for (FlatBooking booking : database.flatBookingList) {
            if (booking.getApplicantNRIC().equals(this.getNRIC())) {
                booking.viewFlatBookingDetails();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("You have no flat booking!\n");
        }
    }

    public void viewAllApplicationForms() {
        boolean hasAny = false;
        System.out.println("=== Your Latest Application Form (if any) ===");
        if (this.getApplyForm() != null) {
            this.getApplyForm().viewDetails();
            hasAny = true;
        } else {
            System.out.println("You do not have any active application form.");
        }

        String viewHistory = InputValidation.getYesOrNo("\nWould you like to view your application history? (yes/no): ",
                "Please enter 'yes' or 'no'.");
        if (viewHistory.equalsIgnoreCase("yes")) {
            System.out.println("\n=== Your Application History ===");
            for (ApplicationForm form : Database.applicationHistory) {
                if (form.getApplicantNRIC().equals(this.getNRIC())) {
                    form.viewDetails();
                    hasAny = true;
                }
            }
        }

        if (!hasAny) {
            System.out.println("You have no application form records to display.\n");
        }
    }
}
