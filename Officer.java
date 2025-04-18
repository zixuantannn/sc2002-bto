import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Officer extends Applicant {
    private Project assignedProject = null;
    private List<RegistrationForm> registrationForms = new ArrayList<>();
    private EnquiryHandler enqHandler;
    private RoleBehavior currentBehavior;

    public Officer(String name, String NRIC, int age, String maritalStatus) {
        super(name, NRIC, age, maritalStatus);
        if (assignedProject != null) {
            this.enqHandler = new EnquiryHandler(assignedProject.getName(), Database.enquiryList,
                    EnquiryHandler.FILTER_BY_PROJECT);
        } else {
            this.enqHandler = new EnquiryHandler(null);
        }
        this.currentBehavior = new OfficerBehavior();
    }

    public Officer(String name, String NRIC, int age, String maritalStatus, String password) {
        super(name, NRIC, age, maritalStatus, password);
        if (assignedProject != null) {
            this.enqHandler = new EnquiryHandler(assignedProject.getName(), Database.enquiryList,
                    EnquiryHandler.FILTER_BY_PROJECT);
        } else {
            this.enqHandler = new EnquiryHandler(null);
        }
        this.currentBehavior = new OfficerBehavior();
    }

    public void setBehavior(RoleBehavior behavior) {
        this.currentBehavior = behavior;
    }

    public void showMenu(Database db, Scanner scanner) {
        this.currentBehavior.showMenu(this, db, scanner);
    }

    public void setEnqHandler(String projectName) {
        this.enqHandler = new EnquiryHandler(projectName, Database.enquiryList, EnquiryHandler.FILTER_BY_PROJECT);
    }

    public EnquiryHandler getEnquiryHandler() {
        return this.enqHandler;
    }

    public void setAssignedProject(Project project) {
        this.assignedProject = project;
    }

    public Project getAssignedProject() {
        return assignedProject;
    }

    public List<RegistrationForm> getRegistrationForms() {
        return this.registrationForms;
    }

    public void registerToProject(Scanner scanner, Database db) {
        System.out.print("Enter the project name you want to register : ");
        String project = scanner.nextLine();
        if (assignedProject != null && assignedProject.getName().equals(project)) {
            System.out.println("You are already handling this project as an officer.");
            return;
        }

        if (assignedProject != null) {
            System.out.println("Cannot register as an officer when handling a project as an officer.");
        }

        ApplicationForm form = this.getApplyForm();
        if (form != null && form.getAppliedProjectName().equals(project)) {
            System.out.println("Cannot register as an officer after applying for the project as an applicant.");
            return;
        }

        if (registrationForms.size() != 0) {
            for (RegistrationForm reg : this.registrationForms) {
                if (reg.getRegistrationStatus() == "Pending") {
                    System.out.println(
                            "Cannot register as an officer for a new project as you have pending approval for the project "
                                    + reg.getRegisteredProjectName() + ".");
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

    public void viewHandledProjectDetails() {
        if (assignedProject != null) {
            assignedProject.viewProjectDetails();
        } else {
            System.out.println("You are not handling any project.");
        }
    }

    public void viewAndReplyEnquiries() {
        if (assignedProject == null) {
            System.out.println("No project assigned. Cannot view or reply to project enquiries.");
            return;
        }
        enqHandler.reloadFilteredEnquiries(Database.enquiryList, assignedProject.getName());
        enqHandler.ReplyEnquiry();
    }

    public void handleFlatBooking(Scanner scanner, Database db) {
        if (assignedProject == null) {
            System.out.println("No project is currently assigned to you.");
            return;
        }

        System.out.print("Enter applicant NRIC: ");
        String applicantNRIC = scanner.nextLine().trim();

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

        System.out.print("Enter flat type to book (2-Room / 3-Room): ");
        String flatType = scanner.nextLine().trim();

        if (flatType.equalsIgnoreCase("2-Room")) {
            if (assignedProject.getNumType1() <= 0) {
                System.out.println("No available 2-Room flats.");
                return;
            }
            assignedProject.setNumType1(assignedProject.getNumType1() - 1);
        } else if (flatType.equalsIgnoreCase("3-Room")) {
            if (assignedProject.getNumType2() <= 0) {
                System.out.println("No available 3-Room flats.");
                return;
            }
            assignedProject.setNumType2(assignedProject.getNumType2() - 1);
        } else {
            System.out.println("Invalid flat type specified.");
            return;
        }

        targetApplication.updateStatus("booked");

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
                applicant.getMaritalStatus(), assignedProject.getName(), flatType);
        generateReceipt(flatBooking);
        db.flatBookingList.add(flatBooking);
        // CSVWriter.saveFlatBooking(db.flatBookingList, "FlatBookingList.csv");
    }

    public void generateReceipt(FlatBooking flatBooking) {
        flatBooking.viewFlatBookingDetails();
    }

}
