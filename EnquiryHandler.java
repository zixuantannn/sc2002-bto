import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnquiryHandler {
    public static final String FILTER_BY_NRIC = "by_nric";
    public static final String FILTER_BY_PROJECT = "by_project_name";
    public static final String FILTER_BY_MANAGER = "by_manager";

    private List<Enquiry> allEnquiries;
    private String filterCriteria = null;

    public EnquiryHandler(String filter, List<Enquiry> enquiryList, String filterType) {

        this.filterCriteria = filterType;
        this.allEnquiries = filterEnquiries(enquiryList, filterType, filter);

    }

    public EnquiryHandler(List<Enquiry> enquiryList, String filtertype) {
        // Only manager can access general enquiries (project name = "-")
        if (filtertype.equals(FILTER_BY_MANAGER)) {
            this.allEnquiries = Database.enquiryList.stream()
                    .filter(enquiry -> enquiry.getProjectName() != null && enquiry.getProjectName().equals("-"))
                    .collect(Collectors.toList());
        } else {
            this.allEnquiries = enquiryList;
        }
        this.filterCriteria = filtertype;
    }

    private List<Enquiry> filterEnquiries(List<Enquiry> allEnquiries, String filterCriteria, String filterValue) {
        if (filterCriteria.equals(FILTER_BY_NRIC)) {
            return allEnquiries.stream().filter(enquiry -> enquiry.getNRIC().equalsIgnoreCase(filterValue))
                    .collect(Collectors.toList());

        } else if (filterCriteria.equals(FILTER_BY_PROJECT)) {
            return allEnquiries.stream()
                    .filter(enquiry -> enquiry.getProjectName() != null
                            && enquiry.getProjectName().equalsIgnoreCase(filterValue))
                    .collect(Collectors.toList());

        } else if (filterCriteria.equals(FILTER_BY_MANAGER)) {
            return allEnquiries.stream()
                    .filter(enquiry -> enquiry.getProjectName() != null &&
                            (enquiry.getProjectName().equals("-")
                                    || enquiry.getProjectName().equalsIgnoreCase(filterValue)))
                    .collect(Collectors.toList());
        } else
            return new ArrayList<>();
    }

    public void reloadFilteredEnquiries(List<Enquiry> allEnquiries, String filterValue) {
        this.allEnquiries = filterEnquiries(allEnquiries, filterCriteria, filterValue);
    }

    // project enquiries
    public void createEnquiry(String nric, String content, String projectName) {
        Enquiry newEnquiry = new Enquiry(nric, content, projectName);
        allEnquiries.add(newEnquiry);
        Database.enquiryList.add(newEnquiry);
        System.out.println("New Enquiry submitted.");

    }

    // general enquiries
    public void createEnquiry(String nric, String content) {
        Enquiry newEnquiry = new Enquiry(nric, content);
        allEnquiries.add(newEnquiry);
        // CSVWriter.submitEnquiry(newEnquiry, "EnquiryList.csv");
        Database.enquiryList.add(newEnquiry);
        System.out.println("New Enquiry submitted.");

    }

    public List<Enquiry> getEnquiryList() {
        return this.allEnquiries;
    }

    public void displayMyEnquiries() {
        if (!allEnquiries.isEmpty()) {
            for (Enquiry each : allEnquiries) {
                each.viewDetails();
            }
        } else {
            System.out.println("You have no enquires to display");
        }

    }

    public void modifyEnquiry() {
        if (allEnquiries.isEmpty()) {
            System.out.println("You have no enquiries to edit.");
            return;
        }

        displayMyEnquiries();
        int id = InputValidation.getInt(
                "Please enter the Enquiry ID to edit: ", input -> input > 0,
                "Enquiry ID must be a positive integer.");

        boolean found = false;
        for (Enquiry enquiry : allEnquiries) {
            if (enquiry.getID() == id) {
                found = true;
                if (enquiry.getResponse() == null) { // Enquiry have not been answered
                    String newContent = InputValidation.getString(
                            "Please enter the new enquiry: ", input -> !input.trim().isEmpty(),
                            "Enquiry content cannot be empty.");
                    enquiry.updateContent(newContent);
                    System.out.println("Enquiry content updated successfully.");
                } else { // Enquiry have been answered
                    System.out.println("Enquiry has been answered. No further changes available.");
                }
                break;
            }

        }
        if (!found) {
            System.out.println("Enquiry ID not found. Please check again.");
        }

    }

    public void removeEnquiry() {
        if (allEnquiries.isEmpty()) {
            System.out.println("You have no enquiries to edit.");
            return;
        }

        // Display all enquiries
        displayMyEnquiries();

        int id = InputValidation.getInt(
                "Please enter the Enquiry ID to delete: ", input -> input > 0,
                "Enquiry ID must be a positive integer.");

        Enquiry enquiryToRemove = findEnquiryByID(id);

        if (enquiryToRemove != null) {
            if (enquiryToRemove.getResponse() == null) {
                System.out.println("Chosen Enquiry: \nEnquiry ID:" + enquiryToRemove.getID() + " | Content: "
                        + enquiryToRemove.getContent() + "  | Response: " + enquiryToRemove.getResponse());
                String choice = InputValidation.getYesOrNo(
                        "Are you sure you want to delete this enquiry (yes/no): ",
                        "Please enter 'yes' or 'no'. ");

                if (choice.equals("yes")) {
                    allEnquiries.remove(enquiryToRemove);
                    Database.enquiryList.remove(enquiryToRemove);
                    System.out.println("Enquiry have been removed.");
                } else {
                    System.out.println("No changes have been done");
                }
            } else {
                System.out.println("Enquiry has been answered. No further changes available.");
            }

        } else {
            System.out.println("Enquiry ID not found. Please check again.");
        }
    }

    public Enquiry findEnquiryByID(int id) {
        for (Enquiry each : allEnquiries) {
            if (each.getID() == id) {
                return each;
            }
        }
        return null;
    }

    public void viewProjectEnquiries(String projectName) {
        List<Enquiry> projectEnquiries = getProjectEnquiries(projectName);
        if (projectEnquiries.isEmpty()) {
            System.out.println("No enquiries for the project: " + projectName);
        } else {
            for (Enquiry enquiry : projectEnquiries) {
                enquiry.viewDetails();
            }
        }
    }

    public List<Enquiry> getProjectEnquiries(String projectName) {
        List<Enquiry> projectEnquiries = new ArrayList<>();

        // Filter enquiries by project name
        for (Enquiry enquiry : allEnquiries) {
            if (enquiry.getProjectName().equalsIgnoreCase(projectName)) {
                projectEnquiries.add(enquiry);
            }
        }
        return projectEnquiries;
    }

    public void ReplyEnquiry() {
        Enquiry chosen = null;
        List<Enquiry> enqList = this.allEnquiries;
        if (enqList == null || enqList.isEmpty()) {
            System.out.println("There are no enquiries to reply to.");
            return;
        }

        for (Enquiry enquiry : enqList) {
            enquiry.viewDetails();
        }

        boolean allAnswered = enqList.stream().allMatch(enquiry -> enquiry.getResponse() != null);
        if (allAnswered) { // All enquiries have been answered so no action can be done.
            System.out.println("All enquiries have been answered. No further replies can be made.");
            return;
        }

        int enquiryID = InputValidation.getInt(
                "Please enter the Enquiry ID to reply: ", input -> input > 0,
                "Enquiry ID must be a positive integer.");

        chosen = findEnquiryByID(enquiryID);

        // If the enquiry id does not exist
        if (chosen == null) {
            System.out.println("Enquiry ID not found. No reply has been made.");
            return;
        }

        // If the enquiry have been answered
        if (chosen.getResponse() != null) {
            System.out.println("This enquiry has already been answered. No reply can be made.");
            return;
        }

        // Proceed to reply enquiries
        if (chosen.getProjectName().equals("-")) {
            System.out.println("=== General Enquiry ===");

        } else {
            System.out.println("=== Project Enquiry ===");
            System.out.println("Project Name: " + chosen.getProjectName());

        }
        System.out.println("Enquiry: " + chosen.getContent());
        String reply = InputValidation.getString("Response: ",
                input -> !input.trim().isEmpty(),
                "Response cannot be empty");
        chosen.updateResponse(reply);
        System.out.println("Response saved.");
    }

    public void viewProjectEnquiries() {
        for (Enquiry enquiry : Database.enquiryList) {
            enquiry.viewDetails();
        }
    }

    public static void syncEnquiriesOnLogout(List<Enquiry> allEnquiries, List<Enquiry> filteredList) {
        if (filteredList == null || filteredList.isEmpty()) {
            return;
        }
        for (Enquiry updated : filteredList) {
            for (int i = 0; i < allEnquiries.size(); i++) {
                Enquiry original = allEnquiries.get(i);
                if (original.getID() == updated.getID()) {
                    allEnquiries.set(i, updated);
                    break;
                }
            }
        }
    }

}
