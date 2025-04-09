import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

public class Manager extends UserAccount {
    private Project handledProject = null;

    public Manager(String name, String NRIC, int age, String maritalStatus) {
        super(name, NRIC, age, maritalStatus);
    }

    public Manager(String name, String NRIC, int age, String maritalStatus, String password) {
        super(name, NRIC, age, maritalStatus, password);
    }

    public void setHandledProject(Project p) {
        this.handledProject = p;
    }

    public Project getHandledProject() {
        return this.handledProject;
    }

    public void createProjectListing(Scanner sc, Database db) {
        try {
            System.out.print("Enter project name: ");
            String name = sc.nextLine();

            System.out.print("Enter neighborhood: ");
            String neighborhood = sc.nextLine();

            System.out.print("Enter number of 2-Room flats: ");
            int numType1 = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter selling price for 2-Room flats: ");
            int sellPriceType1 = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter number of 3-Room flats: ");
            int numType2 = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter selling price for 3-Room flats: ");
            int sellPriceType2 = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter application opening date (yyyy-MM-dd): ");
            String openDateStr = sc.nextLine();
            System.out.print("Enter application closing date (yyyy-MM-dd): ");
            String closeDateStr = sc.nextLine();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date openDate = sdf.parse(openDateStr);
            Date closeDate = sdf.parse(closeDateStr);

            String manager = this.getName();

            System.out.print("Enter available officer slots (max 10): ");
            int officerSlots = sc.nextInt();
            sc.nextLine();
            if (officerSlots > 10) {
                System.out.println("Officer slots cannot exceed 10. Setting to 10.");
                officerSlots = 10;
            }

            String[] officers = new String[10];

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
                    officers);

            db.projectList.add(newProject);
            System.out.println("Project listing created successfully.");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Project creation failed.");
        }
    }

    public void editProjectListing(Scanner sc, Database db) {
        System.out.println("Enter the name of the project to edit: ");
        String projectName = sc.nextLine();
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

        System.out.println("Select field to edit:");
        System.out.println("1. Name");
        System.out.println("2. Neighborhood");
        System.out.println("3. Number of 2-Room flats");
        System.out.println("4. Selling Price for 2-Room flats");
        System.out.println("5. Number of 3-Room flats");
        System.out.println("6. Selling Price for 3-Room flats");
        System.out.println("7. Application Opening Date");
        System.out.println("8. Application Closing Date");
        System.out.println("9. Available Officer Slots");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Enter new project name: ");
                String newName = sc.nextLine();
                target.setName(newName);
                break;
            case 2:
                System.out.println("Enter new neighborhood: ");
                String newNeighborhood = sc.nextLine();
                target.setNeighborhood(newNeighborhood);
                break;
            case 3:
                System.out.println("Enter new number of 2-Room flats: ");
                int newNumType1 = sc.nextInt();
                sc.nextLine();
                target.setNumType1(newNumType1);
                break;
            case 4:
                System.out.println("Enter new selling price for 2-Room flats: ");
                int newSellPriceType1 = sc.nextInt();
                sc.nextLine();

                System.out.println("Edit selling price not implemented. Please add a setter in Project class.");
                break;
            case 5:
                System.out.println("Enter new number of 3-Room flats: ");
                int newNumType2 = sc.nextInt();
                sc.nextLine();
                target.setNumType2(newNumType2);
                break;
            case 6:
                System.out.println("Enter new selling price for 3-Room flats: ");
                int newSellPriceType2 = sc.nextInt();
                sc.nextLine();

                System.out.println("Edit selling price not implemented. Please add a setter in Project class.");
                break;
            case 7:
                System.out.println("Enter new opening date (yyyy-MM-dd): ");
                String newOpenDateStr = sc.nextLine();
                try {
                    Date newOpenDate = new SimpleDateFormat("yyyy-MM-dd").parse(newOpenDateStr);
                    target.setOpenDate(newOpenDate);
                } catch (ParseException e) {
                    System.out.println("Invalid date format.");
                }
                break;
            case 8:
                System.out.println("Enter new closing date (yyyy-MM-dd): ");
                String newCloseDateStr = sc.nextLine();
                try {
                    Date newCloseDate = new SimpleDateFormat("yyyy-MM-dd").parse(newCloseDateStr);
                    target.setCloseDate(newCloseDate);
                } catch (ParseException e) {
                    System.out.println("Invalid date format.");
                }
                break;
            case 9:
                System.out.println("Enter new available officer slots (max 10): ");
                int newSlots = sc.nextInt();
                sc.nextLine();
                if (newSlots > 10) {
                    System.out.println("Officer slots cannot exceed 10. Setting to 10.");
                    newSlots = 10;
                }
                target.setNumOfficerSlots(newSlots);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

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
        System.out.println("Project listing deleted successfully.");
    }

    public void setVisibility(Scanner sc, Database database) {
        Project target = null;
        System.out.print("Enter the project name you want to toggle its visibility: ");
        String targetName = sc.nextLine();
        for (Project project : database.projectList) {
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
                System.out.println("Project visibility set to ON.");
                break;
            } else if (visibilityInput.equals("off")) {
                target.setVisibility(visibilityInput);
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
        for (int i = 0; i < registerList.size(); i++) {
            if (ID == registerList.get(i).getRegistrationID()) {
                registerForm = registerList.get(i);
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

                    // Expand and update officer list
                    for (int i = 0; i < currentOfficers.length; i++) {
                        if (currentOfficers[i] == null || currentOfficers[i].isEmpty()) {

                            currentOfficers[i] = officerName;
                            for (Officer o : db.officerList) {
                                if (o != null && o.getNRIC().equalsIgnoreCase(NRICofficer)) { // create function in
                                                                                              // Officer
                                    o.setAssignedProject(handledProject);
                                    System.out.println(" Officer " + officerName + " approved and assigned to project "
                                            + handledProject.getName());
                                    break;
                                }
                            }
                            break;
                        } else {
                            System.out.println("Officer slots are full for this project.");
                            return;
                        }
                    }
                    break;
                } else if (assign.equals("reject")) {
                    registerForm.setRegistrationStatus("rejected");

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
            pro.viewEnquiryList();
            System.out.println("\n");
        }
    }

    public void viewAndReplyEnquiries(Database db) {
        for (Project pro : db.projectList) {
            System.out.println("The list of enquiries in the project " + pro.getName());
            List<Enquiry> lis = pro.getEnquiryList();
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