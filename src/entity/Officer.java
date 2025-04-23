package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controller.EnquiryHandler;
import behavior.RoleBehavior;
import behavior.OfficerBehavior;
import database.Database;
import utility.InputValidation;

/**
 * The {@code Officer} class extends the {@link Applicant} class
 * and represents an officer who manages projects. The officer can register to
 * handle a project, view and reply to project enquiries, manage flat bookings,
 * and generate reports related to the project they are assigned to.
 * 
 * The officer's role behavior is determined using the RoleBehavior interface
 * and OfficerBehavior class.
 * 
 */
public class Officer extends Applicant {
    private Project assignedProject = null;
    private List<RegistrationForm> registrationForms = new ArrayList<>();
    private EnquiryHandler enqHandler;
    private RoleBehavior currentBehavior;

    /**
     * Constructs an {@code Officer} with the specified details.
     * 
     * @param name          The name of the officer.
     * @param NRIC          The NRIC of the officer.
     * @param age           The age of the officer.
     * @param maritalStatus The marital status of the officer.
     */
    public Officer(String name, String NRIC, int age, String maritalStatus) {
        super(name, NRIC, age, maritalStatus);
        if (assignedProject != null) {
            this.enqHandler = new EnquiryHandler(assignedProject.getName(), Database.enquiryList,
                    EnquiryHandler.FILTER_BY_PROJECT);
        } else {
            this.enqHandler = new EnquiryHandler(null, EnquiryHandler.FILTER_BY_PROJECT);
        }
        this.currentBehavior = new OfficerBehavior();
    }

    /**
     * Constructs an {@code Officer} with the specified details and password. Used
     * when loading from csv.
     * 
     * @param name          The name of the officer.
     * @param NRIC          The NRIC of the officer.
     * @param age           The age of the officer.
     * @param maritalStatus The marital status of the officer.
     * @param password      The password of the officer.
     */
    public Officer(String name, String NRIC, int age, String maritalStatus, String password) {
        super(name, NRIC, age, maritalStatus, password);
        if (assignedProject != null) {
            this.enqHandler = new EnquiryHandler(assignedProject.getName(), Database.enquiryList,
                    EnquiryHandler.FILTER_BY_PROJECT);
        } else {
            this.enqHandler = new EnquiryHandler(null, EnquiryHandler.FILTER_BY_PROJECT);
        }
        this.currentBehavior = new OfficerBehavior();
    }

    /**
     * Sets the current behavior of the officer.
     * 
     * @param behavior The new behavior to set.
     */
    public void setBehavior(RoleBehavior behavior) {
        this.currentBehavior = behavior;
    }

    /**
     * Displays the officer's menu options.
     * 
     * @param db The Database object containing all the data.
     */
    public void showMenu(Database db) {
        this.currentBehavior.showMenu(this, db);
    }

    /**
     * Sets the enquiry handler for the officer for a specific project.
     * 
     * @param projectName The project name for which to filter the enquiries.
     */
    public void setEnqHandler(String projectName) {
        this.enqHandler = new EnquiryHandler(projectName, Database.enquiryList, EnquiryHandler.FILTER_BY_PROJECT);
    }

    /**
     * Gets the enquiry handler associated with the officer.
     * 
     * @return The officer's enquiry handler.
     */
    public EnquiryHandler getEnquiryHandler() {
        return this.enqHandler;
    }

    /**
     * Sets the project that the officer is assigned to.
     * 
     * @param project The project to assign to the officer.
     */
    public void setAssignedProject(Project project) {
        this.assignedProject = project;
    }

    /**
     * Gets the project that the officer is assigned to.
     * 
     * @return The project the officer is assigned to.
     */
    public Project getAssignedProject() {
        return assignedProject;
    }

    /**
     * Gets the list of registration forms for the officer.
     * 
     * @return The list of registration forms.
     */
    public List<RegistrationForm> getRegistrationForms() {
        return this.registrationForms;
    }

    /**
     * Registers the officer to a project.
     * 
     * @param db The Database object containing the project list.
     */
    public void registerToProject(Database db) {
        String project = InputValidation.getString("Enter the project name you want to register: ",
                input -> !input.trim().isEmpty(),
                "Project name cannot be empty");
        if (assignedProject != null && assignedProject.getName().equals(project)) {
            System.out.println("You are already handling this project as an officer.");
            return;
        }

        if (assignedProject != null) {
            System.out.println("Cannot register to handle another project as you are currently handling a project.");
            return;
        }

        ApplicationForm form = this.getApplyForm();
        if (form != null && form.getAppliedProjectName().equals(project)) {
            System.out.println("Cannot register as an officer after applying for the same project as an applicant.");
            return;
        }

        if (registrationForms.size() != 0) {
            for (RegistrationForm reg : this.registrationForms) {
                if (reg.getRegistrationStatus() == "Pending") {
                    System.out.println(
                            "Your application to handle the project: "
                                    + reg.getRegisteredProjectName()
                                    + " is still pending approval. For now, you cannot register for other projects.");
                    return;
                }
                ;
            }
        }

        RegistrationForm newForm = new RegistrationForm(this.getName(), this.getNRIC(), this.getAge(),
                this.getMaritalStatus(), project);
        Project registeredProject = null;
        for (Project tmp : db.projectList) {
            if (project.equals(tmp.getName())) {
                registeredProject = tmp;
            }
        }
        if (registeredProject == null) {
            System.out.println("Your project you are registering is not available!");
            return;
        } else {
            registeredProject.getListOfRegisterForm().add(newForm);
            registrationForms.add(newForm);
        }
        System.out.println(" Registered for officer position in project " + project + " (Pending approval)");
    }

    /**
     * Views the registration status of the officer's project applications.
     */
    public void viewRegistrationStatus() {
        if (registrationForms.size() == 0) {
            System.out.println("You have no registration form.");
            return;
        }
        for (RegistrationForm reg : this.registrationForms) {
            System.out.println("The registration status of " + "your registered project "
                    + reg.getRegisteredProjectName() + " : " + reg.getRegistrationStatus());
        }

    }

    /**
     * Views the details of the project the officer is currently handling.
     */
    public void viewHandledProjectDetails() {
        if (assignedProject != null) {
            assignedProject.viewProjectDetails();
        } else {
            System.out.println("You are not handling any project.");
        }
    }

    /**
     * Views and replies to project enquiries for the officer's assigned project.
     */
    public void viewAndReplyEnquiries() {
        if (assignedProject == null) {
            System.out.println("No project assigned. Cannot view or reply to project enquiries.");
            return;
        }
        enqHandler.reloadFilteredEnquiries(Database.enquiryList, assignedProject.getName());
        enqHandler.ReplyEnquiry();
    }

    /**
     * Handles the flat booking process for an applicant associated with the
     * officer's assigned project.
     * 
     * @param db The Database object containing the flat booking list.
     */
    public void handleFlatBooking(Database db) {
        if (assignedProject == null) {
            System.out.println("No project is currently assigned to you.");
            return;
        }

        String applicantNRIC = InputValidation.getString(
                "Enter applicant NRIC: ",
                input -> !input.trim().isEmpty(),
                "NRIC cannot be empty.");

        ApplicationForm targetApplication = null;
        for (ApplicationForm app : assignedProject.getListOfApplyForm()) {
            if (app.getApplicantNRIC().equals(applicantNRIC) &&
                    app.getApplicationStatus().equalsIgnoreCase("successful")) {
                targetApplication = app;
                break;
            }
        }

        if (targetApplication == null) {
            System.out.println("No matching successful application found for NRIC: " + applicantNRIC);
            return;
        }

        String maritalStatus = targetApplication.getApplicant().getMaritalStatus().toLowerCase();
        int flatPrice = 0;

        String flatType = InputValidation.getString("Enter flat type to book (2-Room / 3-Room): ",
                input -> !input.trim().isEmpty(),
                "Flat type cannot be empty");

        if (maritalStatus.equals("single") && flatType.equalsIgnoreCase("3-Room")) {
            System.out.println("Single applicants can only apply for 2-Room flats.");
            return;
        }

        if (flatType.equalsIgnoreCase("2-Room")) {
            if (assignedProject.getNumType1() <= 0) {
                System.out.println("No available 2-Room flats.");
                return;
            }
            assignedProject.setNumType1(assignedProject.getNumType1() - 1);
            flatPrice = assignedProject.getSellPriceType1();
        } else if (flatType.equalsIgnoreCase("3-Room")) {
            if (assignedProject.getNumType2() <= 0) {
                System.out.println("No available 3-Room flats.");
                return;
            }
            assignedProject.setNumType2(assignedProject.getNumType2() - 1);
            flatPrice = assignedProject.getSellPriceType2();
        } else {
            System.out.println("Invalid flat type specified.");
            return;
        }

        targetApplication.updateStatus("Booked");
        Database.applicationHistory.add(targetApplication);
        Applicant applicant = null;
        for (Applicant a : db.applicantList) {
            if (a.getNRIC().equalsIgnoreCase(applicantNRIC)) {
                applicant = a;
                break;
            }
        }
        if (applicant == null) {
            System.out.println("Applicant record not found.");
            return;
        }

        System.out.println("Flat booking successful for applicant: " + applicant.getName());
        FlatBooking flatBooking = new FlatBooking(applicant.getName(), applicant.getNRIC(), applicant.getAge(),
                applicant.getMaritalStatus(), assignedProject.getName(), flatType, assignedProject.getNeighborhood(),
                flatPrice);
        applicant.setFlatBooking(flatBooking);
        generateReceipt(flatBooking);
        db.flatBookingList.add(flatBooking);
    }

    /**
     * Generates a receipt for the flat booking.
     * 
     * @param flatBooking The flat booking to generate the receipt for.
     */
    public void generateReceipt(FlatBooking flatBooking) {
        flatBooking.viewFlatBookingDetails();
    }

    /**
     * Views the application forms submitted for the project the officer is
     * handling.
     */
    public void viewApplicationsInHandledProject() {
        if (assignedProject == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }

        List<ApplicationForm> applicationList = assignedProject.getListOfApplyForm();
        if (applicationList.isEmpty()) {
            System.out.println("No application forms have been submitted for this project.");
            return;
        }

        System.out.println("=== Application Forms for Project: " + assignedProject.getName() + " ===");
        for (ApplicationForm app : applicationList) {
            app.viewDetails();
        }
    }

}
