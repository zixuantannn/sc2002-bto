
import java.util.ArrayList;
import java.util.List;

public class ApplicationForm {
    private static int index_application_form = 1;

    private int applicationID = -1;
    private String applicantName;
    private String applicantNRIC;
    private int applicantAge;
    private String maritalStatus;
    private String appliedProjectName;
    private String registrationStatus;

    private Enquiry withdrawEnquiry = new Enquiry();

    public ApplicationForm(String applicantName, String applicantNRIC, int applicantAge, String maritalStatus,
                           String appliedProjectName, String registrationStatus) {
        this.applicationID = index_application_form++;
        this.applicantName = applicantName;
        this.applicantNRIC = applicantNRIC;
        this.applicantAge = applicantAge;
        this.maritalStatus = maritalStatus;
        this.appliedProjectName = appliedProjectName;
        this.registrationStatus = registrationStatus;
    }

    public int getApplicationID() {
        return this.applicationID;
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

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getAppliedProjectName() {
        return appliedProjectName;
    }

    public String getApplicationStatus() {
        return registrationStatus;
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

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public void setAppliedProjectName(String appliedProjectName) {
        this.appliedProjectName = appliedProjectName;
    }

    public void updateStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public void answerWithdrawalEnquiry(String response){
        this.withdrawEnquiry.updateResponse(response);
    }

    public void setWithdrawalEnquiry(String content){
        this.withdrawEnquiry.updateContent(content);
    }

    public Enquiry getWithdrawalEnquiry(){
        return this.withdrawEnquiry;
    }

    public void viewDetails() {
        System.out.println("===== Application Details =====");
        System.out.println("Application ID: " + applicationID);
        System.out.println("Applicant Name: " + applicantName);
        System.out.println("Applicant NRIC: " + applicantNRIC);
        System.out.println("Applicant Age: " + applicantAge);
        System.out.println("Marital Status: " + maritalStatus);
        System.out.println("Applied Project Name: " + appliedProjectName);
        System.out.println("Registration Status: " + registrationStatus);
        System.out.println("================================");
    }
}
