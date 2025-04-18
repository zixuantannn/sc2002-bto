
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Manager extends UserAccount {
    private Project handledProject = null;
    private EnquiryHandler enqHandler;

    public Manager(String name, String NRIC, int age, String maritalStatus) {
        super(name, NRIC, age, maritalStatus);
    }

    public Manager(String name, String NRIC, int age, String maritalStatus, String password) {
        super(name, NRIC, age, maritalStatus, password);
        this.enqHandler = new EnquiryHandler(Database.enquiryList);
    }

    public void setHandledProject(Project p) {
        this.handledProject = p;
    }

    public Project getHandledProject() {
        return this.handledProject;
    }

    public EnquiryHandler getEnquiryHandler() {
        return this.enqHandler;
    }

    public void createProjectListing(Database db) {
        try {

            // Check if manager already handling a project
            if (this.getHandledProject() != null) {
                System.out.println("You are already handling a project, hence you cannot create any new projects.");
                return;
            }

            String name = InputValidation.getString("Enter project name: ", s -> !s.isEmpty(),
                    "Project name cannot be empty.");

            for (Project p : db.projectList) {
                if (p.getName().equalsIgnoreCase(name)) {
                    System.out.println("Error: A project with this name already exists. Please choose a unique name.");
                    return; // Exit the method as the project name is not unique
                }
            }

            String neighborhood = InputValidation.getString("Enter neighborhood: ", s -> !s.isEmpty(),
                    "Neighborhood cannot be empty.");

            // Number of 2-Room flats (with automatic price setting to 0 if number is 0)
            int numType1 = InputValidation.getInt("Enter number of 2-Room flats: ", n -> n >= 0,
                    "Number must be zero or positive.");
            int sellPriceType1 = 0;
            if (numType1 > 0) {
                sellPriceType1 = InputValidation.getInt("Enter selling price for 2-Room flats (positive price): ",
                        p -> p > 0, "Price must be positive.");
            }

            // Number of 3-Room flats (with automatic price setting to 0 if number is 0)
            int numType2 = InputValidation.getInt("Enter number of 3-Room flats: ", n -> n >= 0,
                    "Number must be zero or positive.");
            int sellPriceType2 = 0;
            if (numType2 > 0) {
                sellPriceType2 = InputValidation.getInt("Enter selling price for 3-Room flats (positive price): ",
                        p -> p > 0, "Price must be positive.");
            }

            Date openDate = InputValidation.getDate("Enter new opening date (yyyy-MM-dd): ", "yyyy-MM-dd",
                    "Invalid date format. Please use yyyy-MM-dd.");

            Date closeDate = null;
            while (closeDate == null || closeDate.before(openDate)) {
                closeDate = InputValidation.getDate("Enter new closing date (yyyy-MM-dd): ", "yyyy-MM-dd",
                        "Invalid date format. Please use yyyy-MM-dd.");
                if (closeDate.before(openDate)) {
                    System.out.println("Closing date must be after or equal to the opening date. Please try again.");
                }
            }
            String manager = this.getName();
            int officerSlots = InputValidation.getInt("Enter available officer slots (max 10): ",
                    n -> n >= 0 && n <= 10, "Officer slots must be between 0 and 10.");

            String[] officers = new String[0];

            // Check if current date is the application open date. If yes, then
            // automatically set visibility as ON.
            LocalDate today = LocalDate.now();
            LocalDate projectOpenDate = openDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate projectCloseDate = closeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            String visibilityStatus;
            if (!projectOpenDate.isAfter(today) && !projectCloseDate.isBefore(today)) {
                visibilityStatus = "on";
            } else {
                visibilityStatus = "off";
            }

            Project newProject = new Project(
                    name,
                    neighborhood,
                    numType1,
                    sellPriceType1,
                    numType2,
                    sellPriceType2,
                    openDate,
                    closeDate,
                    manager,
                    officerSlots,
                    officers,
                    visibilityStatus);

            // Manager who created a project is automatically assigned to the project
            // this.setHandledProject(newProject);

            db.projectList.add(newProject);
            // CSVWriter.saveProject(newProject, "ProjectList.csv");
            System.out.println("Project listing created successfully.");
        } catch (Exception e) {
            System.out.println("An error occurred during project creation.");
        }
    }

    public void editProjectListing(Database db) {
        String projectName = InputValidation.getString("Enter the name of the project to edit: ", s -> !s.isEmpty(),
                "Project name cannot be empty.");
        Project target = null;

        for (Project p : db.projectList) {
            if (p.getName().equalsIgnoreCase(projectName) && p.getManager().equals(this.getName())) {
                target = p;
                break;
            }
        }

        if (target == null) {
            System.out.println("Project not found or you do not have permission to edit this project.");
            return;
        }

        // Start the loop to allow multiple edits
        boolean editing = true;
        while (editing) {
            System.out.println("Select field to edit:");
            System.out.println(
                    "1. Name\n2. Neighborhood\n3. Number of 2-Room flats\n4. Selling Price for 2-Room flats\n5. Number of 3-Room flats\n6. Selling Price for 3-Room flats\n7. Application Opening Date\n8. Application Closing Date\n9. Available Officer Slots\n0. Stop editing");

            int choice = InputValidation.getInt("Enter your choice: ", n -> n >= 0 && n <= 9,
                    "Invalid choice. Enter a number between 0 and 9.");

            switch (choice) {
                case 1:
                    System.out.println("Current project name: " + target.getName());
                    String newName = InputValidation.getString("Enter new project name: ", s -> !s.isEmpty(),
                            "Name cannot be empty.");

                    // Check for unique project name (excluding the current project)
                    for (Project p : db.projectList) {
                        if (p.getName().equalsIgnoreCase(newName) && !p.getName().equalsIgnoreCase(target.getName())) {
                            System.out.println(
                                    "Error: A project with this name already exists. Please choose a unique name.");
                            return; // Exit if name is not unique
                        }
                    }
                    target.setName(newName);
                    break;
                case 2:
                    System.out.println("Current neighborhood: " + target.getNeighborhood());
                    target.setNeighborhood(InputValidation.getString("Enter new neighborhood: ", s -> !s.isEmpty(),
                            "Neighborhood cannot be empty."));
                    break;
                case 3:
                    System.out.println("Current number of 2-Room flats: " + target.getNumType1());
                    int originalNumType1 = target.getNumType1(); // Store the original number of 2-Room flats

                    // Get the new number of 2-Room flats
                    int newNumType1 = InputValidation.getInt("Enter new number of 2-Room flats: ", n -> n >= 0,
                            "Must be zero or positive.");
                    target.setNumType1(newNumType1);

                    // Check if the number of flats has changed from 0 to greater than 0
                    if (originalNumType1 == 0 && newNumType1 > 0) {
                        // Ask for a non-zero price only if the number of flats is being changed from 0
                        // to > 0
                        int newSellPriceType1 = InputValidation.getInt(
                                "Enter selling price for 2-Room flats (positive price): ", p -> p > 0,
                                "Price must be positive.");
                        target.setSellPriceType1(newSellPriceType1);
                    } else if (newNumType1 == 0) {
                        // If number of 2-Room flats is 0, set price to 0
                        target.setSellPriceType1(0);
                    }
                    break;
                case 4:
                    System.out.println("Current selling price for 2-Room flats: " + target.getSellPriceType1());
                    target.setSellPriceType1(
                            InputValidation.getInt("Enter selling price for 2-Room flats (positive price): ",
                                    p -> p > 0, "Price must be positive."));
                    break;
                case 5:
                    System.out.println("Current number of 3-Room flats: " + target.getNumType2());
                    int originalNumType2 = target.getNumType2(); // Store the original number of 3-Room flats

                    // Get the new number of 3-Room flats
                    int newNumType2 = InputValidation.getInt("Enter new number of 3-Room flats: ", n -> n >= 0,
                            "Must be zero or positive.");
                    target.setNumType2(newNumType2);

                    // Check if the number of flats has changed from 0 to greater than 0
                    if (originalNumType2 == 0 && newNumType2 > 0) {
                        // Ask for a non-zero price only if the number of flats is being changed from 0
                        // to > 0
                        int newSellPriceType2 = InputValidation.getInt(
                                "Enter selling price for 3-Room flats (positive price): ", p -> p > 0,
                                "Price must be positive.");
                        target.setSellPriceType2(newSellPriceType2);
                    } else if (newNumType2 == 0) {
                        // If number of 3-Room flats is 0, set price to 0
                        target.setSellPriceType2(0);
                    }
                    break;
                case 6:
                    System.out.println("Current selling price for 3-Room flats: " + target.getSellPriceType2());
                    target.setSellPriceType2(
                            InputValidation.getInt("Enter selling price for 3-Room flats (positive price): ",
                                    p -> p > 0, "Price must be positive."));
                    break;
                case 7:
                    System.out.println("Current opening date: " + target.getOpenDate());
                    System.out.println("Current closing date: " + target.getCloseDate());
                    Date newOpenDate = null;
                    while (newOpenDate == null || newOpenDate.after(target.getCloseDate())) {
                        newOpenDate = InputValidation.getDate("Enter new opening date (yyyy-MM-dd): ", "yyyy-MM-dd",
                                "Invalid date format. Please use yyyy-MM-dd.");
                        if (newOpenDate.after(target.getCloseDate())) {
                            System.out.println("Opening date cannot be after the closing date. Please try again.");
                        }
                    }
                    target.setOpenDate(newOpenDate);
                    break;
                case 8:
                    System.out.println("Current opening date: " + target.getOpenDate());
                    System.out.println("Current closing date: " + target.getCloseDate());
                    Date newCloseDate = null;
                    while (newCloseDate == null || newCloseDate.before(target.getOpenDate())) {
                        newCloseDate = InputValidation.getDate("Enter new closing date (yyyy-MM-dd): ", "yyyy-MM-dd",
                                "Invalid date format. Please use yyyy-MM-dd.");
                        if (newCloseDate.before(target.getOpenDate())) {
                            System.out.println(
                                    "Closing date must be after or equal to the opening date. Please try again.");
                        }
                    }
                    target.setCloseDate(newCloseDate);
                    break;
                case 9:
                    System.out.println("Current available officer slots: " + target.getNumOfficerSlots());
                    target.setNumOfficerSlots(InputValidation.getInt("Enter new available officer slots (max 10): ",
                            n -> n >= 0 && n <= 10, "Officer slots must be between 0 and 10."));
                    break;
                case 0:
                    // Stop editing and exit the loop
                    editing = false;
                    System.out.println("Stopping editing. Returning to previous menu...");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
        // CSVWriter.overwriteProjects(db.projectList, "ProjectList.csv");
        System.out.println("Project listing updated successfully.");
    }

    public void deleteProjectListing(Database db) {
        String projectName = InputValidation.getString("Enter the name of the project to delete: ",
                input -> !input.trim().isEmpty(),
                "Project name cannot be empty.");
        Project target = null;

        for (Project p : db.projectList) {
            if (p.getName().equalsIgnoreCase(projectName) && p.getManager().equals(this.getName())) {
                target = p;
                break;
            }
        }

        if (target == null) {
            System.out.println("Project not found or you do not have permission to delete this project.");
            return;
        }

        db.projectList.remove(target);
        // CSVWriter.deleteProject(projectName);
        System.out.println("Project listing deleted successfully.");
    }

    public void setVisibility(Database db) {
        Project target = null;
        String targetName = InputValidation.getString("Enter the project name you want to toggle its visibility: ",
                s -> !s.isEmpty(),
                "Cannot be empty.");

        // Check if the project exists first
        boolean projectExists = false;
        boolean isProjectHandler = false;

        for (Project project : db.projectList) {
            if (targetName.equalsIgnoreCase(project.getName())) {
                projectExists = true;
                // Check if the user is the project manager
                if (project.getManager().equals(this.getName())) {
                    target = project;
                    isProjectHandler = true;
                }
                break;
            }
        }

        if (!projectExists) {
            System.out.println("The project you are looking for does not exist.");
            return;
        } else if (!isProjectHandler) {
            System.out.println("You are not the manager of this project.");
            return;
        } else {
            // Continue with toggling visibility or other logic
            System.out.println("Project found. Proceeding with visibility toggle...");
        }
        while (true) {
            String visibilityInput = InputValidation.getOnOrOff("Set the visibility of project (on/off): ",
                    "Please enter 'on' or 'off' ");

            if (visibilityInput.equals("on")) {
                target.setVisibility(visibilityInput);
                // CSVWriter.overwriteProjects(db.projectList, "ProjectList.csv");
                System.out.println("Project visibility set to ON.");
                break;
            } else if (visibilityInput.equals("off")) {
                target.setVisibility(visibilityInput);
                // CSVWriter.overwriteProjects(db.projectList, "ProjectList.csv");
                System.out.println("Project visibility set to OFF.");
                break;
            } else {
                System.out.println("Invalid input. Please enter 'on' or 'off'.");
            }
        }

    }

    public void viewAllProject(Database db) {
        System.out.println("View all projects: ");
        for (int i = 0; i < db.projectList.size(); i++) {
            db.projectList.get(i).viewProjectDetails(); // new function for class Project
        }
    }

    public void viewMyProject() {
        System.out.println("View the details of your handled project:");
        handledProject.viewProjectDetails();
    }

    public void viewFilteredProjects(Database db) {
        if (!db.projectList.isEmpty()) {
            db.projectList.get(0).displayProjectsWithFilters(db);
        } else {
            System.out.println("No projects available.");
        }
    }

    public boolean viewRegistration() {
        if (this.handledProject == null) {
            // Initialize handledProject or handle the error appropriately
            System.out.println("Currently not handling any project.");
            return false;
        }
        return handledProject.viewListOfRegistration(); // new function for class Project
    }

    public boolean viewApplication() {
        if (this.handledProject == null) {
            // Initialize handledProject or handle the error appropriately
            System.out.println("Currently not handling any project.");
            return false;
        }
        return handledProject.viewListOfApplication(); // new function for class Project
    }

    public void viewProjectsCreatedByManager(Database database) {
        List<Project> lis = new ArrayList<>();
        boolean check = false;
        for (Project project : database.projectList) {
            if (this.getName().equalsIgnoreCase(project.getManager())) {
                project.viewProjectDetails();
                check = true;
            }
        }
        if (!check) {
            System.out.println("You have not created any project!");
        }
    }

    public void assignOfficerToProject(Database db) {
        RegistrationForm registerForm = null;
        List<RegistrationForm> registerList = handledProject.getListOfRegisterForm();

        if (registerList.size() == 0) {
            System.out.println("No registration forms found.");
            return;
        }

        int ID = InputValidation.getInt("Enter registration form ID or 0 to cancel: ", i -> i >= 0,
                "Please enter a positive value");
        if (ID == 0) {
            return;
        }

        for (RegistrationForm form : registerList) {
            if (form.getRegistrationID() == ID) {
                registerForm = form;
                break;
            }
        }

        if (registerForm == null) {
            System.out.println("The ID you entered is invalid!");
            return;
        }

        String assignment = InputValidation.getApproveOrReject(
                "Do you want to approve or reject the registration form with ID " + ID + " (approve/reject)?: ",
                "Invalid input. Please try again!");

        if (assignment.equalsIgnoreCase("approve")) {
            if (handledProject.getNumOfficerSlots() <= 0) {
                System.out.println("No available officer slot.");
                return;
            }

            registerForm.setRegistrationStatus("approved");
            handledProject.setNumOfficerSlots(handledProject.getNumOfficerSlots() - 1);

            String officerName = registerForm.getOfficerName();
            String NRICofficer = registerForm.getOfficerNRIC();

            List<String> currentOfficers = new ArrayList<>();
            for (String officer : handledProject.getOfficers()) {
                if (officer != null && !officer.trim().isEmpty()) {
                    String cleaned = officer.trim().toLowerCase();
                    if (!cleaned.equals("null") && !cleaned.equals("none")) {
                        currentOfficers.add(officer.trim());
                    }
                }
            }

            // Add the new officer (avoid duplicates if needed)
            if (!currentOfficers.contains(officerName)) {
                currentOfficers.add(officerName);
            }

            // Generate officer string with space separator
            String officerCSVString = String.join(" ", currentOfficers);

            // Update officer array with this string in slot 0 only (rest empty)
            handledProject.setOfficers(new String[] { officerCSVString }); // Only set the CSV string without extra
                                                                           // slots

            boolean assigned = false;
            for (Officer officer : db.officerList) {
                if (officer != null && officer.getNRIC().equalsIgnoreCase(NRICofficer)) {
                    officer.setAssignedProject(handledProject);
                    CSVWriter.overwriteProjects(db.projectList, "ProjectList.csv");
                    System.out.println(
                            "Officer " + officerName + " approved and assigned to project " + handledProject.getName());
                    assigned = true;
                    break;
                }
            }

            if (!assigned) {
                System.out.println("Officer with NRIC " + NRICofficer + " not found.");
            }

        } else if (assignment.equalsIgnoreCase("reject")) {
            registerForm.setRegistrationStatus("rejected");
            System.out.println("Registration rejected.");
        }
    }

    public void manageApplicationForm() {

        ApplicationForm applyForm = null;
        List<ApplicationForm> applyList = handledProject.getListOfApplyForm();
        if (applyList.size() == 0) {
            System.out.println("No application requests found.");
            return;
        }
        int ID = InputValidation.getInt("Enter application form ID or 0 to cancel:", i -> i >= 0,
                "Please enter a positive value");
        if (ID == 0) {
            return;
        }
        for (int i = 0; i < applyList.size(); i++) {
            if (ID == applyList.get(i).getApplicationID()) {
                applyForm = applyList.get(i);
                break;
            }
        }

        if (applyForm == null) {
            System.out.println("Your ID you enter is invalid!");
        } else {
            System.out.print(
                    "Do you want to approve or reject the application form with ID " + ID + " (approve/reject)?: ");
            String assign = InputValidation.getApproveOrReject("Please enter 'approve' or 'reject': ",
                    "Invalid input. Please try again!");

            if (assign.equalsIgnoreCase("approve")) {
                applyForm.updateStatus("successful");
                System.out.println("Applicant " + applyForm.getApplicantName() + " approved to project "
                        + handledProject.getName());
            } else if (assign.equalsIgnoreCase("reject")) {
                applyForm.updateStatus("unsuccessful");
                applyForm.getApplicant().setApplyForm(null);
                applyForm.getApplicant().resetAvailablilty();
                System.out.println("Applicant " + applyForm.getApplicantName() + " rejected from project "
                        + handledProject.getName());
            }

        }
    }

    public boolean viewWithdrawalRequest() {
        if (this.handledProject == null) {
            // Initialize handledProject or handle the error appropriately
            System.out.println("Currently not handling any project.");
            return false;
        }
        this.handledProject.viewRequestWithdrawal();
        return true;
    }

    public void manageWithdrawalRequest() {

        List<ApplicationForm> applyList = handledProject.getListOfApplyForm();
        ApplicationForm targetForm = null;
        if (applyList.size() == 0) {
            System.out.println("No withdrawal requests found.");
            return;
        }
        boolean exists = false;
        for (ApplicationForm form : applyList) {
            if (form.getWithdrawalRequest() != null) {
                exists = true;
                break;
            }
        }
        if (exists == false) {
            System.out.println("No withdrawal requests found.");
            return;
        }
        int ID = InputValidation.getInt("Enter application ID with withdrawal request or 0 to cancel:", i -> i >= 0,
                "Please enter a positive value");
        if (ID == 0) {
            return;
        }
        for (ApplicationForm form : applyList) {
            if (form != null && form.getApplicationID() == ID && form.getWithdrawalRequest() != null) {
                targetForm = form;
                break;
            }
        }

        if (targetForm == null) {
            System.out.println("No withdrawal request found for the given ID.");
            return;
        }

        System.out.println("Withdrawal request found for Applicant: " + targetForm.getApplicantName());
        System.out.println("Message: " + targetForm.getWithdrawalRequest());
        String input = InputValidation.getYesOrNo("Approve withdrawal? (yes/no)\n", "Please enter yes or no:");

        if (input.equals("yes")) {
            Applicant ap = targetForm.getApplicant();
            ap.resetAvailablilty();
            ap.setApplyForm(null);
            applyList.remove(targetForm);
            System.out.println(" Withdrawal request approved. Application removed.");
        } else {
            System.out.println(" Withdrawal request not approved. No changes made.");
        }
    }

    public void viewAllEnquiries() {
        enqHandler.viewProjectEnquiries();
    }

    public void ReplyEnquiries() {
        enqHandler.ReplyEnquiry();
    }

    public void generateApplicantBookingReport(Database db) {
        if (this.getHandledProject() == null) {
            System.out.println("You are not handling any project, so no report can be generated.");
            return;
        }

        String managerProjectName = this.getHandledProject().getName();

        System.out.println("\n--- Applicant Flat Booking Report (Project: " + managerProjectName + ") ---");

        String filterMarital = InputValidation.getYesOrNo("Do you want to filter by marital status? (yes/no): ",
                "Invalid input. Please enter 'yes' or 'no'.");
        String maritalStatusFilter = null;
        if (filterMarital.equals("yes")) {
            System.out.print("Enter marital status to filter (Single/Married): ");
            maritalStatusFilter = InputValidation.getString("Enter marital status to filter(Single/Married): ",
                    input -> input.equalsIgnoreCase("Single") || input.equalsIgnoreCase("Married"),
                    "Please enter a valid marital status ('Single' or 'Married').");
        }

        String filterFlatType = InputValidation.getYesOrNo("Do you want to filter by flat type? (yes/no): ",
                "Invalid input. Please enter 'yes' or 'no'.");
        String flatTypeFilter = null;
        if (filterFlatType.equals("yes")) {
            flatTypeFilter = InputValidation.getString(
                    "Enter flat type to filter (2-Room / 3-Room): ",
                    input -> input.equalsIgnoreCase("2-Room") || input.equalsIgnoreCase("3-Room"),
                    "Invalid flat type. Please enter '2-Room' or '3-Room'.");
        }

        boolean found = false;
        System.out.printf("\n%-20s %-5s %-15s %-25s %-10s\n", "Name", "Age", "Marital Status", "Project Name",
                "Flat Type");
        System.out.println("-------------------------------------------------------------------------------");

        for (FlatBooking booking : db.flatBookingList) {
            if (!booking.getProjectName().equalsIgnoreCase(managerProjectName)) {
                continue; // skip bookings not under this manager's project
            }

            boolean matches = true;

            if (maritalStatusFilter != null &&
                    !booking.getApplicantMaritalStatus().equalsIgnoreCase(maritalStatusFilter)) {
                matches = false;
            }

            if (flatTypeFilter != null &&
                    !booking.getFlatType().equalsIgnoreCase(flatTypeFilter)) {
                matches = false;
            }

            if (matches) {
                found = true;
                System.out.printf("%-20s %-5d %-15s %-25s %-10s\n",
                        booking.getApplicantName(),
                        booking.getApplicantAge(),
                        booking.getApplicantMaritalStatus(),
                        booking.getProjectName(),
                        booking.getFlatType());
            }
        }

        if (!found) {
            System.out.println("No matching records found.");
        }
    }

}
