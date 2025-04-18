
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public boolean getType1() {
        return type1;
    }

    public void setType1(boolean type1) {
        this.type1 = type1;
    }

    public boolean getType2() {
        return type2;
    }

    public void setType2(boolean type2) {
        this.type2 = type2;
    }

    public int getNumType1() {
        return numType1;
    }

    public void setNumType1(int numType1) {
        this.numType1 = numType1;
    }

    public int getSellPriceType1() {
        return sellPriceType1;
    }

    public void setSellPriceType1(int sellPriceType1) {
        this.sellPriceType1 = sellPriceType1;
    }

    public int getNumType2() {
        return numType2;
    }

    public void setNumType2(int numType2) {
        this.numType2 = numType2;
    }

    public int getSellPriceType2() {
        return sellPriceType2;
    }

    public void setSellPriceType2(int sellPriceType2) {
        this.sellPriceType2 = sellPriceType2;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getManager() {
        return this.manager;
    }

    public void setManager(String newManager) {
        this.manager = newManager;
    }

    public int getNumOfficerSlots() {
        return numOfficerSlots;
    }

    public void setNumOfficerSlots(int numOfficerSlots) {
        if (numOfficerSlots < 0) {
            throw new IllegalArgumentException("No available officer slots.");
        }
        this.numOfficerSlots = numOfficerSlots;
    }

    public String[] getOfficers() {
        return officers;
    }

    public List<Enquiry> getEnquiryList() {
        return this.enquiryList;
    }

    public void setOfficers(String[] officers) {
        this.officers = officers;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public List<RegistrationForm> getListOfRegisterForm() {
        return this.listOfRegisterForm;
    }

    public List<ApplicationForm> getListOfApplyForm() {
        return this.listOfApplyForm;
    }

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
                System.out.println("Project Name: " + project.getName() + ", Neighborhood: " + project.getNeighborhood());
            }
        }
    }

    private String centerString(int width, String s) {
        return String.format(
                "%" + ((width - s.length()) / 2 + s.length()) + "s%" + ((width - s.length() + 1) / 2) + "s", s, "");
    }

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

    public void viewRequestWithdrawal() {
        for (ApplicationForm app : listOfApplyForm) {
            WithdrawalRequest requestWithdrawal = app.getWithdrawalRequest();
            System.out.println("Applicant " + app.getApplicantName() + " : ");
            requestWithdrawal.viewDetails();
        }
    }

}
