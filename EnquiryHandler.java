import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EnquiryHandler {
    public static final String FILTER_BY_NRIC = "by_nric";
    public static final String FILTER_BY_PROJECT = "by_project_name";

    private List<Enquiry> allEnquries;

    public EnquiryHandler(String filter, List<Enquiry> enquiryList, String filterType) {
        this.allEnquries = new ArrayList<>();
        for (Enquiry enquiry : Database.enquiryList) {
            if (filterType.equals(FILTER_BY_NRIC) && enquiry.getNRIC().equalsIgnoreCase(filter)) {
                this.allEnquries.add(enquiry);
            } else if (filterType.equals(FILTER_BY_PROJECT) && enquiry.getProjectName().equalsIgnoreCase(filter)) {
                this.allEnquries.add(enquiry);
            }
        }

    }

    public EnquiryHandler(List<Enquiry> enquiryList) {
        this.allEnquries = enquiryList;
    }

    // project enquiries
    public void createEnquiry(String nric, String content, String projectName) {
        Enquiry newEnquiry = new Enquiry(nric, content, projectName);
        allEnquries.add(newEnquiry);
        Database.enquiryList.add(newEnquiry);
        System.out.println("New Enquiry submitted.");

    }

    // general enquiries
    public void createEnquiry(String nric, String content) {
        Enquiry newEnquiry = new Enquiry(nric, content);
        allEnquries.add(newEnquiry);
        // CSVWriter.submitEnquiry(newEnquiry, "EnquiryList.csv");
        Database.enquiryList.add(newEnquiry);
        System.out.println("New Enquiry submitted.");

    }

    public List<Enquiry> getEnquiryList() {
        return this.allEnquries;
    }

    public void displayMyEnquiries() {
        if (!allEnquries.isEmpty()) {
            for (Enquiry each : allEnquries) {
                each.viewDetails();
            }
        } else {
            System.out.println("You have no enquires to display");
        }

    }

    public void modifyEnquiry() {
        if (allEnquries.isEmpty()) {
            System.out.println("You have no enquiries to edit.");
            return;
        }

        displayMyEnquiries();
        int id = InputValidation.getInt(
                "Please enter the Enquiry ID to edit: ", input -> input > 0,
                "Enquiry ID must be a positive integer.");

        boolean found = false;
        for (Enquiry enquiry : allEnquries) {
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

    public void removeEnquriy() {
        if (allEnquries.isEmpty()) {
            System.out.println("You have no enquiries to edit.");
            return;
        }

        // Display all enquiries
        displayMyEnquiries();

        int id = InputValidation.getInt(
                "Please enter the Enquiry ID to delete: ", input -> input > 0,
                "Enquiry ID must be a positive integer.");

        boolean found = false;
        Enquiry enquiryToRemove = findEnquiryByID(id);
        if (enquiryToRemove != null) {
            if (enquiryToRemove.getResponse() == null) {
                System.out.println("Chosen Enquiry: \nEnquiry ID:" + enquiryToRemove.getID() + " | Content: "
                        + enquiryToRemove.getContent() + "  | Response: " + enquiryToRemove.getResponse());
                String choice = InputValidation.getYesOrNo(
                        "Are you sure you want to delete this enquiry (yes/no): ",
                        "Please enter 'yes' or 'no'. ");

                if (choice.equals("yes")) {
                    allEnquries.remove(enquiryToRemove);
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

    public static void syncEnquiriesOnLogout(List<Enquiry> allEnquries, List<Enquiry> filteredList) {
        if (filteredList == null || filteredList.isEmpty()) {
            return;
        }
        for (Enquiry updated : filteredList) {
            for (int i = 0; i < allEnquries.size(); i++) {
                Enquiry original = allEnquries.get(i);
                if (original.getID() == updated.getID()) {
                    allEnquries.set(i, updated);
                    break;
                }
            }
        }
    }

    public Enquiry findEnquiryByID(int id) {
        for (Enquiry each : allEnquries) {
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
        for (Enquiry enquiry : allEnquries) {
            if (enquiry.getProjectName().equalsIgnoreCase(projectName)) {
                projectEnquiries.add(enquiry);
            }
        }
        return projectEnquiries;
    }

    public void ReplyEnquiry() {
        Enquiry chosen = null;
        List<Enquiry> enqList = this.allEnquries;
        if (enqList == null || enqList.isEmpty()) {
            System.out.println("There are no enquiries to reply to.");
            return;
        }

        for (Enquiry enquiry : enqList) {
            enquiry.viewDetails();
        }

        boolean allAnswered = enqList.stream().allMatch(enquiry -> enquiry.getResponse() != null);
        if (allAnswered) {
            System.out.println("All enquiries have been answered. No further replies can be made.");
        }
        int enquiryID = InputValidation.getValidatedInput("Which enquiry would you like to reply to: ",
                Integer::parseInt, id -> {
                    Enquiry found = findEnquiryByID(id);
                    if (found == null) {
                        System.out.println("Enquiry ID not found. Please try again.");
                        return false;
                    } else if (found.getResponse() != null) {
                        System.out.println("This enquiry has already been answered.");
                        return false;
                    }
                    return true;
                }, "Invalid input. Please enter a valid enquiry ID.");
        chosen = findEnquiryByID(enquiryID);
        if (chosen.getProjectName().equals("-")) {
            System.out.println("=== General Enquiry ===");

        } else {
            System.out.println("=== Project Enquiry ===");
            System.out.println("Project Name: " + chosen.getProjectName());

        }
        System.out.println("Enquiry: " + chosen.getContent());
        System.out.print("Response: ");
        String reply = InputValidation.getString("Response: ",
                input -> !input.trim().isEmpty(),
                "Response cannot be empty");
        chosen.updateResponse(reply);
        System.out.println("Response saved.");
    }

    public void viewProjectEnquiries() {
        for (Enquiry enquiry : allEnquries) {
            enquiry.viewDetails();
        }
    }

}
