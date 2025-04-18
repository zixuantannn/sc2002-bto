public class FlatBooking {
    private static int ID = 1;
    private final int bookingID;
    private String applicantName;
    private String applicantNRIC;
    private int applicantAge;
    private String applicantMaritalStatus;
    private String projectName;
    private String flatType;
    private String neighborhood;
    private int sellPrice;

    // Constructor to initialize properties and auto-generate bookingID
    public FlatBooking(String applicantName, String applicantNRIC, int applicantAge,
            String applicantMaritalStatus, String projectName, String flatType, String neighborhood, int sellPrice) {
        this.bookingID = ID++;
        this.applicantName = applicantName;
        this.applicantNRIC = applicantNRIC;
        this.applicantAge = applicantAge;
        this.applicantMaritalStatus = applicantMaritalStatus;
        this.projectName = projectName;
        this.flatType = flatType;
        this.neighborhood = neighborhood;
        this.sellPrice = sellPrice;
    }

    public FlatBooking(int bookingID, String applicantName, String applicantNRIC, int applicantAge,
            String applicantMaritalStatus, String projectName, String flatType, String neighborhood, int sellPrice) {
        this.bookingID = bookingID;
        this.applicantName = applicantName;
        this.applicantNRIC = applicantNRIC;
        this.applicantAge = applicantAge;
        this.applicantMaritalStatus = applicantMaritalStatus;
        this.projectName = projectName;
        this.flatType = flatType;
        this.neighborhood = neighborhood;
        this.sellPrice = sellPrice;

        if (bookingID >= ID) {
            ID = bookingID + 1;
        }
    }

    public int getBookingID() {
        return bookingID;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getApplicantNRIC() {
        return applicantNRIC;
    }

    public int getApplicantAge() {
        return applicantAge;
    }

    public String getApplicantMaritalStatus() {
        return applicantMaritalStatus;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getFlatType() {
        return flatType;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public void setApplicantNRIC(String applicantNRIC) {
        this.applicantNRIC = applicantNRIC;
    }

    public void setApplicantAge(int applicantAge) {
        this.applicantAge = applicantAge;
    }

    public void setApplicantMaritalStatus(String applicantMaritalStatus) {
        this.applicantMaritalStatus = applicantMaritalStatus;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void viewFlatBookingDetails() {
        String line = "+----------------------------------------------------+";
        System.out.println(line);
        System.out.printf("| %-50s |\n", "Flat Booking Details");
        System.out.println(line);
        System.out.printf("| %-20s: %-28s |\n", "Booking ID", bookingID);
        System.out.printf("| %-20s: %-28s |\n", "Applicant Name", applicantName);
        System.out.printf("| %-20s: %-28s |\n", "Applicant NRIC", applicantNRIC);
        System.out.printf("| %-20s: %-28s |\n", "Age", applicantAge);
        System.out.printf("| %-20s: %-28s |\n", "Marital Status", applicantMaritalStatus);
        System.out.printf("| %-20s: %-28s |\n", "Project Name", projectName);
        System.out.printf("| %-20s: %-28s |\n", "Neighborhood", neighborhood);
        System.out.printf("| %-20s: %-28s |\n", "Flat Type", flatType);
        System.out.printf("| %-20s: %-28s |\n", "Selling Price", sellPrice);
        System.out.println(line);
    }

}
