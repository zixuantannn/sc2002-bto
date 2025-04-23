package entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import utility.InputValidation;
import database.Database;

/**
 * The {@code Project} class represents a project with various attributes such
 * as project name,neighborhood, unit types, sell prices and many more.
 */
public class Project {
    private String name;
    private String neighborhood;
    private boolean type1 = false;
    private boolean type2 = false;
    private int numType1;
    private int sellPriceType1;
    private int numType2;
    private int sellPriceType2;
    private Date openDate;
    private Date closeDate;
    private String manager;
    private int numOfficerSlots;
    private static final int MAX_OFFICER_SLOTS = 10; // Max slots is 10
    private String[] officers = new String[MAX_OFFICER_SLOTS];
    private String visibility = "off";

    private List<Enquiry> enquiryList = new ArrayList<>();

    private List<RegistrationForm> listOfRegisterForm = new ArrayList<>();
    private List<ApplicationForm> listOfApplyForm = new ArrayList<>();

    private static String savedNeighborhoodFilter = "";
    private static Boolean savedType1Filter = null;
    private static Boolean savedType2Filter = null;

    /**
     * Constructs a {@code Project} with the specified details. Used when loading
     * from
     * csv.
     * 
     * @param name            The name of the project.
     * @param neighborhood    The neighborhood where the project is located.
     * @param numType1        The number of units for type 1 in the project.
     * @param sellPrice1      The selling price of a unit of type 1.
     * @param numType2        The number of units for type 2 in the project.
     * @param sellPrice2      The selling price of a unit of type 2.
     * @param openDate        The opening date of the project.
     * @param closeDate       The closing date of the project.
     * @param manager         The name of the manager overseeing the project.
     * @param numOfficerSlots The number of officer slots available for the project.
     * @param officers        The array of officers assigned to the project.
     * @param visibility      The visibility status of the project (e.g., public,
     *                        private).
     */
    public Project(String name, String neighborhood, int numType1, int sellPrice1, int numType2, int sellPrice2,
            Date openDate, Date closeDate, String manager, int numOfficerSlots, String[] officers, String visibility) {
        this.name = name;
        this.neighborhood = neighborhood;
        this.numType1 = numType1;
        this.sellPriceType1 = sellPrice1;
        this.numType2 = numType2;
        this.sellPriceType2 = sellPrice2;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.manager = manager;
        this.numOfficerSlots = numOfficerSlots;
        this.officers = officers;
        this.visibility = visibility;
    }

    /**
     * Gets the name of the project.
     *
     * @return The name of the project.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the project.
     *
     * @param name The name of the project.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the neighborhood of the project.
     *
     * @return The neighborhood of the project.
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     * Sets the neighborhood of the project.
     *
     * @param neighborhood The neighborhood of the project.
     */
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    /**
     * Gets the availability of type 1 (2-room) units.
     *
     * @return {@code true} if 2-room units are available, {@code false} otherwise.
     */
    public boolean getType1() {
        return type1;
    }

    /**
     * Sets the availability of type 1 (2-room) units.
     *
     * @param type1 {@code true} if 2-room units are available, {@code false}
     *              otherwise.
     */
    public void setType1(boolean type1) {
        this.type1 = type1;
    }

    /**
     * Gets the availability of type 2 (3-room) units.
     *
     * @return {@code true} if 3-room units are available, {@code false} otherwise.
     */
    public boolean getType2() {
        return type2;
    }

    /**
     * Sets the availability of type 2 (3-room) units.
     *
     * @param type2 {@code true} if 3-room units are available, {@code false}
     *              otherwise.
     */
    public void setType2(boolean type2) {
        this.type2 = type2;
    }

    /**
     * Gets the number of type 1 (2-room) units.
     *
     * @return The number of 2-room units.
     */
    public int getNumType1() {
        return numType1;
    }

    /**
     * Sets the number of type 1 (2-room) units.
     *
     * @param numType1 The number of 2-room units.
     */
    public void setNumType1(int numType1) {
        this.numType1 = numType1;
    }

    /**
     * Gets the selling price of type 1 (2-room) units.
     *
     * @return The selling price of 2-room units.
     */
    public int getSellPriceType1() {
        return sellPriceType1;
    }

    /**
     * Sets the selling price of type 1 (2-room) units.
     *
     * @param sellPriceType1 The selling price of 2-room units.
     */
    public void setSellPriceType1(int sellPriceType1) {
        this.sellPriceType1 = sellPriceType1;
    }

    /**
     * Gets the number of type 2 (3-room) units.
     *
     * @return The number of 3-room units.
     */
    public int getNumType2() {
        return numType2;
    }

    /**
     * Sets the number of type 2 (3-room) units.
     *
     * @param numType2 The number of 3-room units.
     */
    public void setNumType2(int numType2) {
        this.numType2 = numType2;
    }

    /**
     * Gets the selling price of type 2 (3-room) units.
     *
     * @return The selling price of 3-room units.
     */
    public int getSellPriceType2() {
        return sellPriceType2;
    }

    /**
     * Sets the selling price of type 2 (3-room) units.
     *
     * @param sellPriceType2 The selling price of 3-room units.
     */
    public void setSellPriceType2(int sellPriceType2) {
        this.sellPriceType2 = sellPriceType2;
    }

    /**
     * Gets the opening date of the project.
     *
     * @return The opening date of the project.
     */
    public Date getOpenDate() {
        return openDate;
    }

    /**
     * Sets the opening date of the project.
     *
     * @param openDate The opening date of the project.
     */
    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    /**
     * Gets the closing date of the project.
     *
     * @return The closing date of the project.
     */
    public Date getCloseDate() {
        return closeDate;
    }

    /**
     * Sets the closing date of the project.
     *
     * @param closeDate The closing date of the project.
     */
    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    /**
     * Gets the project manager's name.
     *
     * @return The project manager's name.
     */
    public String getManager() {
        return this.manager;
    }

    /**
     * Sets the project manager's name.
     *
     * @param newManager The new project manager's name.
     */
    public void setManager(String newManager) {
        this.manager = newManager;
    }

    /**
     * Gets the number of officer slots available for the project.
     *
     * @return The number of officer slots available.
     */
    public int getNumOfficerSlots() {
        return numOfficerSlots;
    }

    /**
     * Sets the number of officer slots available for the project.
     *
     * @param numOfficerSlots The number of officer slots available.
     * @throws IllegalArgumentException if the number of officer slots is negative.
     */
    public void setNumOfficerSlots(int numOfficerSlots) {
        if (numOfficerSlots < 0) {
            throw new IllegalArgumentException("No available officer slots.");
        }
        this.numOfficerSlots = numOfficerSlots;
    }

    /**
     * Gets the list of officers assigned to the project.
     *
     * @return An array of officer names assigned to the project.
     */
    public String[] getOfficers() {
        return officers;
    }

    /**
     * Gets the list of enquiries for the project.
     *
     * @return A list of {@link Enquiry} objects associated with the project.
     */
    public List<Enquiry> getEnquiryList() {
        return this.enquiryList;
    }

    /**
     * Sets the list of officers assigned to the project.
     *
     * @param officers An array of officer names assigned to the project.
     */
    public void setOfficers(String[] officers) {
        this.officers = officers;
    }

    /**
     * Gets the visibility status of the project.
     *
     * @return The visibility status of the project.
     */

    public String getVisibility() {
        return visibility;
    }

    /**
     * Sets the visibility status of the project.
     *
     * @param visibility The visibility status of the project.
     */
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    /**
     * Gets the list of registration forms for the project.
     *
     * @return A list of registration forms associated with the project.
     */
    public List<RegistrationForm> getListOfRegisterForm() {
        return this.listOfRegisterForm;
    }

    /**
     * Gets the list of application forms for the project.
     *
     * @return A list of application forms associated with the project.
     */
    public List<ApplicationForm> getListOfApplyForm() {
        return this.listOfApplyForm;
    }

    /**
     * Displays the project details.
     */
    public void viewProjectDetails() {
        String line = "+-----------------------------------------------------+";
        String title = "PROJECT DETAILS";

        System.out.println(line);
        System.out.printf("|%s|\n", centerString(53, title));
        System.out.println(line);

        System.out.printf("| %-20s : %-28s |\n", "Name", name);
        System.out.printf("| %-20s : %-28s |\n", "Neighborhood", neighborhood);
        System.out.printf("| %-20s : %-28s |\n", "2-Room Units", numType1 + " ($" + sellPriceType1 + ")");
        System.out.printf("| %-20s : %-28s |\n", "3-Room Units", numType2 + " ($" + sellPriceType2 + ")");
        System.out.printf("| %-20s : %-28s |\n", "Opening Date", openDate.toString());
        System.out.printf("| %-20s : %-28s |\n", "Closing Date", closeDate.toString());
        System.out.printf("| %-20s : %-28s |\n", "Manager", manager);
        System.out.printf("| %-20s : %-28s |\n", "Officer Slots", numOfficerSlots);

        StringBuilder assignedOfficers = new StringBuilder();
        boolean hasOfficer = false;

        for (String officer : officers) {
            if (officer != null && !officer.isEmpty()) {
                if (hasOfficer) {
                    assignedOfficers.append(" "); // to add spaces between Officers' names
                }
                assignedOfficers.append(officer);
                hasOfficer = true;
            }
        }

        if (!hasOfficer) {
            assignedOfficers.append("None");
        }
        System.out.printf("| %-20s : %-28s |\n", "Assigned Officers", assignedOfficers.toString());
        System.out.printf("| %-20s : %-28s |\n", "Visibility", (visibility != null ? visibility : "Not Set"));
        System.out.println(line);
    }

    /**
     * Displays a list of projects managed by a specific manager.
     *
     * @param filterManager The manager whose projects need to be filtered.
     * @param db            The database from which the projects are fetched.
     */
    public void displayProjectsByManager(Manager filterManager, Database db) {
        boolean filtered = false;
        for (Project project : db.projectList) {
            if (project.getManager().equals(filterManager)) {
                System.out.println("Project Name: " + project.getName());
                filtered = true;
            }
        }
        if (!filtered) {
            System.out.println("No projects found for the specified manager.");
        }
    }

    /**
     * Displays a list of projects filtered based on various criteria such as
     * neighborhood, unit type, and availability.
     *
     * @param db The database from which the projects are fetched.
     */
    public void displayProjectsWithFilters(Database db) {
        String neighborhoodFilter = "";
        Boolean filter2Room = null;
        Boolean filter3Room = null;

        String answer = InputValidation.getYesOrNo("Would you like to filter by neighborhood (yes/no)?\n",
                "Please enter 'yes' or 'no'.");
        if (answer.equalsIgnoreCase("yes")) {
            neighborhoodFilter = InputValidation.getString(
                    "Enter neighborhood: ",
                    input -> input != null && !input.trim().isEmpty(),
                    "Neighborhood cannot be empty.");
        }

        answer = InputValidation.getYesOrNo("Would you like to filter by 2-Room flats availability (yes/no)?\n",
                "Please enter 'yes' or 'no'.");
        if (answer.equalsIgnoreCase("yes")) {
            String boolInput = InputValidation.getString(
                    "Enter 'true' to filter for 2-Room availability, 'false' otherwise: ",
                    input -> input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false"),
                    "Please enter 'true' or 'false'.");
            filter2Room = Boolean.parseBoolean(boolInput);
        }

        answer = InputValidation.getYesOrNo("Would you like to filter by 3-Room flats availability (yes/no)?\n",
                "Please enter 'yes' or 'no'.");
        if (answer.equalsIgnoreCase("yes")) {
            String boolInput2 = InputValidation.getString(
                    "Enter 'true' to filter for projects with 3-Room flats, 'false' otherwise: ",
                    input -> input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false"),
                    "Please enter 'true' or 'false'.");
            filter3Room = Boolean.parseBoolean(boolInput2);
        }

        List<Project> filteredProjects = new ArrayList<>();
        for (Project project : db.projectList) {
            boolean matches = true;

            if (filter2Room != null && filter2Room && project.getNumType1() <= 0) {
                matches = false;
            }

            if (filter3Room != null && filter3Room && project.getNumType2() <= 0) {
                matches = false;
            }

            if (!neighborhoodFilter.isEmpty()
                    && !project.getNeighborhood().equalsIgnoreCase(neighborhoodFilter)) {
                matches = false;
            }

            if (matches) {
                filteredProjects.add(project);
            }
        }

        filteredProjects.sort(Comparator.comparing(Project::getName));

        if (filteredProjects.isEmpty()) {
            System.out.println("No projects match the selected filters.");
        } else {
            System.out.println("Filtered Projects:");
            for (Project project : filteredProjects) {
                System.out
                        .println("Project Name: " + project.getName() + ", Neighborhood: " + project.getNeighborhood());
            }
        }
    }

    /**
     * Centers a string within a specified width.
     *
     * @param width The width of the formatted string.
     * @param s     The string to be centered.
     * @return The string centered within the specified width.
     */
    private String centerString(int width, String s) {
        return String.format(
                "%" + ((width - s.length()) / 2 + s.length()) + "s%" + ((width - s.length() + 1) / 2) + "s", s, "");
    }

    /**
     * Displays the list of available registration forms for the project.
     *
     * @return {@code true} if registration forms are available and displayed,
     *         {@code false} otherwise.
     */
    public boolean viewListOfRegistration() {
        if (this.listOfRegisterForm.size() <= 0) {
            System.out.println("There is no available registration form.");
            return false;
        }
        for (int i = 0; i < this.listOfRegisterForm.size(); i++) {
            listOfRegisterForm.get(i).viewDetails();
        }
        return true;
    }

    /**
     * Displays the list of available application forms for the project.
     *
     * @return {@code true} if application forms are available and displayed,
     *         {@code false} otherwise.
     */
    public boolean viewListOfApplication() {
        if (this.listOfApplyForm.size() <= 0) {
            System.out.println("There is no available application form.");
            return false;
        }
        for (int i = 0; i < this.listOfApplyForm.size(); i++) {
            listOfApplyForm.get(i).viewDetails();
        }
        return true;
    }

    /**
     * Displays the list of enquiries for the project.
     */
    public void viewEnquiryList() {
        System.out.println("The list of enquiries in the project " + this.name);
        if (this.enquiryList.isEmpty()) {
            System.out.println("There is no available enquiry.");
            return;
        }
        for (Enquiry en : this.enquiryList) {
            en.viewDetails();
        }
    }

    /**
     * Displays any withdrawal requests for application forms in the project.
     */
    public void viewRequestWithdrawal() {
        for (ApplicationForm app : listOfApplyForm) {
            WithdrawalRequest requestWithdrawal = app.getWithdrawalRequest();
            if (requestWithdrawal != null) {
                System.out.println("Applicant " + app.getApplicantName() + " : ");
                requestWithdrawal.viewDetails();

            }

        }
    }

}
