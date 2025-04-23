package entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;

import controller.EnquiryHandler;
import controller.ApplicantProjectHandler;
import database.Database;
import utility.InputValidation;

/**
 * The {@code Applicant} class represents an applicant in the system.
 * It extends {@code UserAccount} and contains methods for managing
 * the applicant's projects, enquiries, application forms, and flat bookings.
 */
public class Applicant extends UserAccount {
    private int applicantId;
    private boolean appliedForProject;
    private ApplicationForm applyForm = null;
    private EnquiryHandler enqHandler;
    private ApplicantProjectHandler applicantProjHandler;
    private FlatBooking flatBooking;

    private List<ApplicationForm> projectApplied;

    /**
     * Constructs an {@code Applicant} object with the specified details.
     * 
     * @param name          the name of the applicant
     * @param NRIC          the NRIC of the applicant
     * @param age           the age of the applicant
     * @param maritalStatus the marital status of the applicant
     */
    public Applicant(String name, String NRIC, int age, String maritalStatus) {
        super(name, NRIC, age, maritalStatus);
        this.appliedForProject = false;
        this.enqHandler = new EnquiryHandler(NRIC, Database.enquiryList, EnquiryHandler.FILTER_BY_NRIC);
        this.applicantProjHandler = new ApplicantProjectHandler(Database.projectList);
    }

    /**
     * Constructs an {@code Applicant} object with the specified details. Used when
     * loading from csv.
     * 
     * @param name          the name of the applicant
     * @param NRIC          the NRIC of the applicant
     * @param age           the age of the applicant
     * @param maritalStatus the marital status of the applicant
     * @param password      the password for the applicant's account
     */
    public Applicant(String name, String NRIC, int age, String maritalStatus, String password) {
        super(name, NRIC, age, maritalStatus, password);
        this.appliedForProject = false;
        this.projectApplied = new ArrayList<>();
        this.enqHandler = new EnquiryHandler(NRIC, Database.enquiryList, EnquiryHandler.FILTER_BY_NRIC);
        this.applicantProjHandler = new ApplicantProjectHandler(Database.projectList);
    }

    /**
     * Gets the enquiry handler associated with the applicant.
     * 
     * @return the {@code EnquiryHandler} object
     */
    public EnquiryHandler getEnquiryHandler() {
        return enqHandler;
    }

    /**
     * Gets the application form associated with the applicant.
     * 
     * @return the {@code ApplicationForm} object
     */
    public ApplicationForm getApplyForm() {
        return this.applyForm;
    }

    /**
     * Sets the application form for the applicant.
     * 
     * @param form the {@code ApplicationForm} to set
     */
    public void setApplyForm(ApplicationForm form) {
        this.applyForm = form;
    }

    /**
     * Gets the list of projects that the applicant has applied for.
     * 
     * @return the list of {@code ApplicationForm} objects
     */
    public List<ApplicationForm> getProjectAppliedList() {
        return projectApplied;
    }

    /**
     * Checks if the applicant has applied for a project.
     * 
     * @return {@code true} if the applicant has applied for a project,
     *         {@code false} otherwise
     */
    public boolean getAvailability() {
        return appliedForProject;
    }

    /**
     * Sets the availability status of the applicant to indicate that they have
     * applied for a project.
     */
    public void setAvailability() {
        this.appliedForProject = true;
    }

    /**
     * Gets the flat booking associated with the applicant.
     * 
     * @return the {@code FlatBooking} object
     */
    public FlatBooking getFlatBooking() {
        return flatBooking;
    }

    /**
     * Sets the flat booking for the applicant.
     * 
     * @param flatBooking the {@code FlatBooking} to set
     */
    public void setFlatBooking(FlatBooking flatBooking) {
        this.flatBooking = flatBooking;
    }

    /**
     * Resets the availability status of the applicant to indicate that they have
     * not applied for a project.
     */
    public void resetAvailablilty() {
        this.appliedForProject = false;
    }

    /**
     * Adds a project application form to the list of projects the applicant has
     * applied for.
     * 
     * @param applicationForm the {@code ApplicationForm} to add
     */
    public void addProjectList(ApplicationForm applicationForm) {
        this.projectApplied.add(applicationForm);
    }

    /**
     * Displays the available projects that the applicant can apply for.
     * 
     * @param db the {@code Database} object containing project data
     */
    public void viewAvailableProjects(Database db) {
        List<Project> filteredProject = applicantProjHandler.filterAvailableProject(db, this);
        applicantProjHandler.viewAvailableProjects(filteredProject);

    }

    /**
     * Allows the applicant to apply for a project.
     * 
     * @param db the {@code Database} object containing project data
     */
    public void applyForProject(Database db) {
        applicantProjHandler.applyForProject(db, this);
    }

    /**
     * Displays the projects that the applicant has applied for.
     */
    public void viewAppliedProject() {
        applicantProjHandler.viewAppliedProject(this);
    }

    /**
     * Displays the status of the applicant's latest project application.
     */
    public void viewApplicationStatus() {
        applicantProjHandler.viewApplicationStatus(this);
    }

    /**
     * Allows the applicant to withdraw their project application.
     */
    public void withdrawalApplication() {
        applicantProjHandler.withdrawalApplication(this);
    }

    /**
     * Allows the applicant to send an enquiry, either general or project-related.
     * 
     * @param db the {@code Database} object containing project data
     */
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

    /**
     * Displays all the enquiries sent by the applicant.
     */
    public void displayEnquiry() {
        enqHandler.displayMyEnquiries();
    }

    /**
     * Allows the applicant to modify an enquiry they have sent.
     */
    public void modifyEnquiry() {
        enqHandler.modifyEnquiry();
    }

    /**
     * Allows the applicant to remove an enquiry they have sent.
     */
    public void removeEnquiry() {
        enqHandler.removeEnquiry();

    }

    /**
     * Allows the applicant to view their flat booking details.
     * 
     * @param database the {@code Database} object containing flat booking data
     */
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

    /**
     * Displays the applicant's current application form and application history.
     */
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
