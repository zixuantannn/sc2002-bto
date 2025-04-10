import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EnquiryHandler {
    public static final String FILTER_BY_NRIC = "by_nric";
    public static final String FILTER_BY_PROJECT = "by_project_name";

    private List<Enquiry> allEnquries;

    public EnquiryHandler(String filter, List<Enquiry> enquiryList, String filterType) {
        this.allEnquries = new ArrayList<>();
        for (Enquiry enquiry : enquiryList) {
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
        System.out.println("New Enquiry submitted.");

    }

    // general enquiries
    public void createEnquiry(String nric, String content) {
        Enquiry newEnquiry = new Enquiry(nric, content);
        allEnquries.add(newEnquiry);
        System.out.println("New Enquiry submitted.");

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

    public void modifyEnquiry(Scanner sc) {
        if (allEnquries.isEmpty()) {
            System.out.println("You have no enquiries to edit.");
            return;
        }

        displayMyEnquiries();

        System.out.print("Please enter the Enquiry ID to edit: ");
        int id = sc.nextInt();
        sc.nextLine();
        boolean found = false;
        for (Enquiry enquiry : allEnquries) {
            if (enquiry.getID() == id) {
                found = true;
                if (enquiry.getResponse() == null) { // Enquiry have not been answered
                    System.out.println("Please enter the new enquiry: ");
                    String newContent = sc.nextLine();
                    enquiry.updateContent(newContent);
                    System.out.println("Enquiry content updated successfully.");
                } else { // Enquiry have been answered
                    System.out.println("Enquiry has been answered. No further changes available.");
                }
                break;
            }
            if (!found) {
                System.out.println("Enquiry ID not found. Please check again.");
            }
        }

    }

    public void removeEnquriy(Scanner sc) {
        if (allEnquries.isEmpty()) {
            System.out.println("You have no enquiries to edit.");
            return;
        }

        // Display all enquiries
        displayMyEnquiries();

        System.out.print("Please enter the Enquiry ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine(); // To remove newline character left by nextInt()

        boolean found = false;
        Enquiry enquiryToRemove = findEnquiryByID(id);
        if (enquiryToRemove != null) {
            if (enquiryToRemove.getResponse() == null) {
                System.out.println("Chosen Enquiry: \nEnquiry ID:" + enquiryToRemove.getID() + " | Content: "
                        + enquiryToRemove.getContent() + "  | Response: " + enquiryToRemove.getResponse());
                String choice;
                do {
                    System.out.print("Are you sure want to delete this enquiry (yes/no):");
                    choice = sc.next().toLowerCase();
                } while (!choice.equals("yes") && !choice.equals("no"));

                if (choice.equals("yes")) {
                    allEnquries.remove(enquiryToRemove);
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

    public void viewProjectEnquiries() {
        for (Enquiry enquiry : allEnquries) {
            enquiry.viewDetails();
        }
    }
}
