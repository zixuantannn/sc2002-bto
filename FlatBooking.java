public class FlatBooking {
    private static int ID = 1;
    private final int bookingID;
    private String applicantName;
    private String applicantNRIC;
    private int applicantAge;
    private String applicantMaritalStatus;
    private String projectName;
    private String flatType;
    
    // Constructor to initialize properties and auto-generate bookingID
    public FlatBooking(String applicantName, String applicantNRIC, int applicantAge, 
                       String applicantMaritalStatus, String projectName, String flatType) {
        this.bookingID = ID++;
        this.applicantName = applicantName;
        this.applicantNRIC = applicantNRIC;
        this.applicantAge = applicantAge;
        this.applicantMaritalStatus = applicantMaritalStatus;
        this.projectName = projectName;
        this.flatType = flatType;
    }
    public FlatBooking(int bookingID, String applicantName, String applicantNRIC, int applicantAge, 
                    String applicantMaritalStatus, String projectName, String flatType) {
        this.bookingID = bookingID;
        this.applicantName = applicantName;
        this.applicantNRIC = applicantNRIC;
        this.applicantAge = applicantAge;
        this.applicantMaritalStatus = applicantMaritalStatus;
        this.projectName = projectName;
        this.flatType = flatType;

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
    public void viewFlatBookingDetails() {
        String line = "+------------------------------------------------------+";
        System.out.println(line);
        System.out.printf("| %-50s |\n", "Flat Booking Details");
        System.out.println(line);
        System.out.printf("| %-20s: %-25s |\n", "Booking ID", bookingID);
        System.out.printf("| %-20s: %-25s |\n", "Applicant Name", applicantName);
        System.out.printf("| %-20s: %-25s |\n", "Applicant NRIC", applicantNRIC);
        System.out.printf("| %-20s: %-25d |\n", "Age", applicantAge);
        System.out.printf("| %-20s: %-25s |\n", "Marital Status", applicantMaritalStatus);
        System.out.printf("| %-20s: %-25s |\n", "Project Name", projectName);
        System.out.printf("| %-20s: %-25s |\n", "Flat Type", flatType);
        System.out.println(line);
    }
    
    
    
}
