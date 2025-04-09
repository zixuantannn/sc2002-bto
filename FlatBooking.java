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
        System.out.println("BookingID: " + bookingID);
        System.out.println("Applicant Name: " + applicantName);
        System.out.println("Applicant NRIC: " + applicantNRIC);
        System.out.println("Age: " + applicantAge);
        System.out.println("Marital Status: " + applicantMaritalStatus);
        System.out.println("Project Name: " + projectName);
        System.out.println("Flat Type: " + flatType);
    }
    
    
}
