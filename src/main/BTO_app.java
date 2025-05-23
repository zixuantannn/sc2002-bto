package main;

import database.Database;
import database.CSVImporter;
import entity.Applicant;
import entity.Officer;
import entity.Manager;
import entity.Project;
import entity.Enquiry;
import utility.InputValidation;
import database.CSVWriter;
import entity.UserAccount;
import boundary.ui.*;
import boundary.menu.CommonMenu;
import controller.UserMenuHandler;
import boundary.menu.Login;

/**
 * The {@code BTO_app} class serves as the entry point for the Build-To-Order
 * (BTO) application system.
 * It initializes the application by loading data from CSV files, authenticates
 * users, and navigates
 * them through role-based interfaces (Applicant, Officer, Manager).
 */
public class BTO_app {
    public static void main(String[] args) {
        Database db = new Database();

        // Load CSV files
        CSVImporter.importEnquiry(db, "data/EnquiryList.csv");
        CSVImporter.importApplicants(db, "data/ApplicantList.csv");
        CSVImporter.importManagers(db, "data/ManagerList.csv");
        CSVImporter.importOfficers(db, "data/OfficerList.csv");
        CSVImporter.importProjects(db, "data/ProjectList.csv");
        CSVImporter.importFlatBookings(db, "data/FlatBookingList.csv");
        CSVImporter.importApplicationHistory("data/ApplicationHistory.csv");

        while (true) {
            printMainMenu();

            int choice = InputValidation.getInt("Enter your choice: ",
                    i -> i >= 1 && i <= 3,
                    "Please enter a valid option between 1 and 3.");

            System.out.println();

            switch (choice) {
                case 1:
                    printRoleMenu();
                    int roleChoice = InputValidation.getInt("Enter your role (1-3): ",
                            i -> i >= 1 && i <= 3,
                            "Please enter a valid role number (1-3).");

                    String roleStr = getRoleFromChoice(roleChoice);

                    String inputNRIC = InputValidation.getString("Enter your NRIC: ",
                            s -> !s.trim().isEmpty(),
                            "NRIC cannot be empty.");

                    UserAccount user = Login.authenticate(db, inputNRIC, roleStr);

                    if (user == null) {
                        System.out.println("Redirecting to login...\n");
                        continue; // Go back to role selection
                    }

                    CommonMenu menu = null;

                    if (user instanceof Officer officer) {
                        System.out.println("1. Continue as Officer");
                        System.out.println("2. Switch to Applicant Mode");

                        int officerMode = InputValidation.getInt("Choose your role mode: ",
                                i -> i == 1 || i == 2, "Only 1 or 2 allowed");

                        menu = (officerMode == 1)
                                ? new OfficerUI(officer, db)
                                : new ApplicantUI(officer, db);

                    } else if (user instanceof Applicant applicant) {
                        menu = new ApplicantUI(applicant, db);

                    } else if (user instanceof Manager manager) {
                        menu = new ManagerUI(manager, db);
                    }

                    if (menu != null) {
                        UserMenuHandler handler = new UserMenuHandler(menu);
                        handler.show();
                    }
                    break;

                case 2:
                    System.out.println("Feature to create a new applicant account is under construction.");
                    break;

                case 3:
                    System.out.println("Saving all data before exit...");

                    CSVWriter.overwriteProjects(db.projectList, "data/ProjectList.csv");
                    CSVWriter.saveApplicants(db.applicantList, "data/ApplicantList.csv");
                    CSVWriter.saveOfficers(db.officerList, "data/OfficerList.csv");
                    CSVWriter.saveManagers(db.managerList, "data/ManagerList.csv");
                    CSVWriter.saveFlatBookings(db.flatBookingList, "data/FlatBookingList.csv");
                    CSVWriter.saveApplicationHistory("data/ApplicationHistory.csv");
                    CSVWriter.saveEnquirieToCSV(db.enquiryList, "data/EnquiryList.csv");

                    System.out.println("All data saved. Goodbye!");
                    System.exit(0);

                    System.out.println("Exiting the BTO App. Goodbye!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

            System.out.println("-----------------------------");
        }
    }

    private static String getRoleFromChoice(int roleChoice) {
        switch (roleChoice) {
            case 1:
                return "applicant";
            case 2:
                return "officer";
            case 3:
                return "manager";
            default:
                return "";
        }
    }

    private static void printMainMenu() {
        String line = "+--------------------------------------------+";
        System.out.println(line);
        System.out.printf("|%s|\n", centerString(44, "Welcome to the BTO App"));
        System.out.println(line);
        System.out.printf("| %-42s |\n", "1. Login");
        System.out.printf("| %-42s |\n", "2. Create new Applicant account");
        System.out.printf("| %-42s |\n", "3. Exit");
        System.out.println(line);
    }

    private static void printRoleMenu() {
        String line = "+--------------------------------------------+";
        System.out.println(line);
        System.out.printf("|%s|\n", centerString(44, "Select Your Role"));
        System.out.println(line);
        System.out.printf("| %-42s |\n", "1. Applicant");
        System.out.printf("| %-42s |\n", "2. Officer");
        System.out.printf("| %-42s |\n", "3. Manager");
        System.out.println(line);
    }

    private static String centerString(int width, String s) {
        return String.format("%" + ((width - s.length()) / 2 + s.length()) + "s%" +
                ((width - s.length() + 1) / 2) + "s", s, "");
    }
}
