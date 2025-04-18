
import java.util.ArrayList;
import java.util.List;

public class ApplicationForm {
    private static int index_application_form = 1;

    private int applicationID = -1;
    private Applicant applicant;
    private String appliedProjectName;
    private String registrationStatus;

    private WithdrawalRequest withdrawalRequest = null;

    public ApplicationForm(Applicant applicant, String appliedProjectName, String registrationStatus) {
        this.applicationID = index_application_form++;
        this.applicant = applicant;
        this.appliedProjectName = appliedProjectName;
        this.registrationStatus = registrationStatus;
    }

    public ApplicationForm(int id, Applicant applicant, String appliedProjectName, String registrationStatus) {
        this.applicationID = id;
        this.applicant = applicant;
        this.appliedProjectName = appliedProjectName;
        this.registrationStatus = registrationStatus;

        if (id >= index_application_form) {
            index_application_form = id + 1;
        }
    }

    public int getApplicationID() {
        return this.applicationID;
    }

    public String getAppliedProjectName() {
        return appliedProjectName;
    }

    public String getApplicationStatus() {
        return registrationStatus;
    }

    public void setAppliedProjectName(String appliedProjectName) {
        this.appliedProjectName = appliedProjectName;
    }

    public void updateStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public void viewDetails() {
        System.out.println("=============== Application Details ===============");
        System.out.println("Application ID: " + applicationID);
        System.out.println("Applicant Name: " + applicant.getName());
        System.out.println("Applicant NRIC: " + applicant.getNRIC());
        System.out.println("Applicant Age: " + applicant.getAge());
        System.out.println("Marital Status: " + applicant.getMaritalStatus());
        System.out.println("Applied Project Name: " + appliedProjectName);
        System.out.println("Registration Status: " + registrationStatus);
        System.out.println("===================================================");
    }

    public static void deleteApplication(ApplicationForm form) {
        // need the code to access db
        System.out.println("Your application have been cancelled.");
    }

    public String getApplicantName() {
        return applicant.getName();
    }

    public String getApplicantNRIC() {
        return applicant.getNRIC();
    }

    public WithdrawalRequest getWithdrawalRequest() {
        return this.withdrawalRequest;
    }

    public void createWithdrawalRequest(String reason) {
        this.withdrawalRequest = new WithdrawalRequest(reason, this);
    }
}
