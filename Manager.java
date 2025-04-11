
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.ZoneId;

public class Manager extends UserAccount {
    private Project handledProject = null;
    private EnquiryHandler manager;

    public Manager(String name, String NRIC, int age, String maritalStatus) {
        super(name, NRIC, age, maritalStatus);
    }

    public Manager(String name, String NRIC, int age, String maritalStatus, String password) {
        super(name, NRIC, age, maritalStatus, password);
        this.manager = new EnquiryHandler();
    }

    public void setHandledProject(Project p) {
        this.handledProject = p;
    }

    public Project getHandledProject() {
        return this.handledProject;
    }

    public void createProjectListing(Scanner sc, Database db) {
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

            String[] officers = new String[officerSlots];

            // Check if current date is the application open date. If yes, then
            // automatically set visibility as ON.
            LocalDate today = LocalDate.now();
            LocalDate projectOpenDate = openDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            String visibilityStatus;
            if (!projectOpenDate.isAfter(today)) {
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
            this.setHandledProject(newProject);

            db.projectList.add(newProject);
            CSVWriter.saveProject(newProject, "ProjectList.csv");
            System.out.println("Project listing created successfully.");
        } catch (Exception e) {
            System.out.println("An error occurred during project creation.");
        }
    }

    public void editProjectListing(Scanner sc, Database db) {
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
        CSVWriter.overwriteProjects(db.projectList, "ProjectList.csv");
        System.out.println("Project listing updated successfully.");
    }

    public void deleteProjectListing(Scanner sc, Database db) {
        System.out.println("Enter the name of the project to delete: ");
        String projectName = sc.nextLine();
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
        CSVWriter.deleteProject(projectName);
        System.out.println("Project listing deleted successfully.");
    }

    public void setVisibility(Scanner sc, Database db) {
        Project target = null;
        System.out.print("Enter the project name you want to toggle its visibility: ");
        String targetName = sc.nextLine();
        for (Project project : db.projectList) {
            if (targetName.equalsIgnoreCase(project.getName())) {
                target = project;
                break;
            }
        }
        if (target == null) {
            System.out.println("Your project you are finding is not available.");
            return;
        }
        while (true) {
            System.out.print("Set the visibility of project (on/off): ");
            String visibilityInput = sc.nextLine().trim().toLowerCase();

            if (visibilityInput.equals("on")) {
                target.setVisibility(visibilityInput);
                CSVWriter.overwriteProjects(db.projectList, "ProjectList.csv");
                System.out.println("Project visibility set to ON.");
                break;
            } else if (visibilityInput.equals("off")) {
                target.setVisibility(visibilityInput);
                CSVWriter.overwriteProjects(db.projectList, "ProjectList.csv");
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

    public void viewFilteredProjects(Scanner scanner, Database db) {
        if (!db.projectList.isEmpty()) {
            db.projectList.get(0).displayProjectsWithFilters(scanner, db);
        } else {
            System.out.println("No projects available.");
        }
    }

    public void viewRegistration() {
        handledProject.viewListOfRegistration(); // new function for class Project
    }

    public void viewApplication() {
        handledProject.viewListOfApplication(); // new function for class Project
    }

    public void assignOfficerToProject(Scanner sc, Database db) {
        System.out.print("Enter registration form ID: ");
        int ID = sc.nextInt();
        sc.nextLine(); // create ID for registration form
        RegistrationForm registerForm = null;
        List<RegistrationForm> registerList = handledProject.getListOfRegisterForm(); // new function for class Project

        for (RegistrationForm form : registerList) {
            if (form.getRegistrationID() == ID) {
                registerForm = form;
                break;
            }
        }

        if (registerForm == null) {
            System.out.println("Your ID you enter is invalid!");
        } else {
            System.out.print(
                    "Do you want to approve or reject the registration form with ID " + ID + " (approve/reject)?: ");
            while (true) {
                String assign = sc.nextLine();

                if (assign.equals("approve")) {
                    if (handledProject.getNumOfficerSlots() <= 0) {
                        System.out.println("No available officer slot.");
                        break;
                    }
                    registerForm.setRegistrationStatus("approved");
                    handledProject.setNumOfficerSlots(handledProject.getNumOfficerSlots() - 1);
                    String officerName = registerForm.getOfficerName();
                    String NRICofficer = registerForm.getOfficerNRIC();
                    String[] currentOfficers = handledProject.getOfficers();

                    boolean assigned = false;

                    for (int i = 0; i < currentOfficers.length; i++) {
                        if (currentOfficers[i] == null || currentOfficers[i].isEmpty()) {
                            currentOfficers[i] = officerName;

                            // Find the officer and assign the project
                            for (Officer officer : db.officerList) {
                                if (officer != null && officer.getNRIC().equalsIgnoreCase(NRICofficer)) {
                                    officer.setAssignedProject(handledProject);
                                    CSVWriter.overwriteProjects(db.projectList, "ProjectList.csv");
                                    System.out.println("Officer " + officerName + " approved and assigned to project "
                                            + handledProject.getName());
                                    assigned = true;
                                    break;
                                }
                            }

                            if (assigned) {
                                break;
                            }
                        }
                    }

                    if (!assigned) {
                        System.out.println("Officer slots are full for this project.");
                    }
                    break;

                    // // Expand and update officer list
                    // for (int i = 0; i < currentOfficers.length; i++) {
                    // if (currentOfficers[i] == null || currentOfficers[i].isEmpty()) {

                    // currentOfficers[i] = officerName;
                    // for (Officer o : db.officerList) {
                    // if (o != null && o.getNRIC().equalsIgnoreCase(NRICofficer)) { // create
                    // function in
                    // // Officer
                    // o.setAssignedProject(handledProject);
                    // CSVWriter.overwriteProjects(db.projectList, "ProjectList.csv");
                    // System.out.println(" Officer " + officerName + " approved and assigned to
                    // project "
                    // + handledProject.getName());
                    // break;
                    // }
                    // }
                    // break;
                    // } else {
                    // System.out.println("Officer slots are full for this project.");
                    // return;
                    // }
                    // }
                    // break;
                } else if (assign.equals("reject")) {
                    registerForm.setRegistrationStatus("rejected");
                    System.out.println("Registration rejected.");
                    break;
                } else {
                    System.out.println("Invalid input.Please try again!");
                }
            }

        }
    }

    public void manageApplicationForm(Scanner sc) {
        System.out.print("Enter application form ID: ");
        int ID = sc.nextInt();
        sc.nextLine(); // create ID for registration form
        ApplicationForm applyForm = null;
        List<ApplicationForm> applyList = handledProject.getListOfApplyForm();
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
            while (true) {
                String assign = sc.nextLine();

                if (assign.equals("approve")) {
                    applyForm.updateStatus("successful");

                    System.out.println(" Applicant " + applyForm.getApplicantName() + " approved to project "
                            + handledProject.getName());
                    break;
                } else if (assign.equals("reject")) {
                    applyForm.updateStatus("unsuccessful");
                    applyForm.getApplicant().setApplyForm(null);
                    applyForm.getApplicant().resetAvailablilty();
                    System.out.println(" Applicant " + applyForm.getApplicantName() + " rejected to project "
                            + handledProject.getName());
                } else {
                    System.out.println("Invalid input.Please try again!");
                }
            }

        }
    }

    public void viewWithdrawalRequest() {
        this.handledProject.viewRequestWithdrawal();
    }

    public void manageWithdrawalRequest(Scanner sc) {

        System.out.println("Enter application ID with withdrawal request: ");
        int ID = sc.nextInt();
        sc.nextLine();

        List<ApplicationForm> applyList = handledProject.getListOfApplyForm();
        ApplicationForm targetForm = null;

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
        System.out.print("Approve withdrawal? (yes/no): ");
        String input = sc.nextLine().trim().toLowerCase();

        if (input.equals("yes")) {
            applyList.remove(targetForm);
            System.out.println(" Withdrawal request approved. Application removed.");
        } else {
            System.out.println(" Withdrawal request not approved. No changes made.");
        }
    }

    public void viewAllEnquiries(Database db) {
        for (Project pro : db.projectList) {
            manager.viewProjectEnquiries(pro.getName());
            System.out.println("\n");
        }
    }

    public void viewAndReplyEnquiries(Database db) {
        for (Project pro : db.projectList) {
            System.out.println("The list of enquiries in the project " + pro.getName());
            manager.viewProjectEnquiries(pro.getName());
            List<Enquiry> lis = manager.getProjectEnquiries(pro.getName());
            for (Enquiry enquiry : lis) {
                System.out.println("Enquiry: " + enquiry.getContent());
                System.out.print("Enter your reply: ");
                Scanner sc = new Scanner(System.in);
                String reply = sc.nextLine();
                enquiry.updateResponse(reply);
                System.out.println("Response saved.");
            }
        }
    }

}
