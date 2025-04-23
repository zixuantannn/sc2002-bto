package entity;

/**
 * The {@code ApplicationForm} class represents an application made by an
 * {@link Applicant}
 * for a specific project. It contains details such as the application ID, the
 * project name,
 * the status of the application, and an optional {@link WithdrawalRequest}.
 */
public class ApplicationForm {
    private static int index_application_form = 1;

    private int applicationID = -1;
    private Applicant applicant;
    private String appliedProjectName;
    private String registrationStatus;

    private WithdrawalRequest withdrawalRequest = null;

    /**
     * Constructs a new {@code ApplicationForm} with the specified applicant,
     * project name, and registration status.
     * An application ID is automatically assigned.
     *
     * @param applicant          the applicant submitting the form
     * @param appliedProjectName the name of the project being applied to
     * @param registrationStatus the current status of the application
     */
    public ApplicationForm(Applicant applicant, String appliedProjectName, String registrationStatus) {
        this.applicationID = index_application_form++;
        this.applicant = applicant;
        this.appliedProjectName = appliedProjectName;
        this.registrationStatus = registrationStatus;
    }

    /**
     * Constructs a new {@code ApplicationForm} with a specific application ID.Used
     * when loading from csv.
     *
     * @param id                 the application ID
     * @param applicant          the applicant submitting the form
     * @param appliedProjectName the name of the project being applied to
     * @param registrationStatus the current status of the application
     */
    public ApplicationForm(int id, Applicant applicant, String appliedProjectName, String registrationStatus) {
        this.applicationID = id;
        this.applicant = applicant;
        this.appliedProjectName = appliedProjectName;
        this.registrationStatus = registrationStatus;

        if (id >= index_application_form) {
            index_application_form = id + 1;
        }
    }

    /**
     * Returns the application ID.
     *
     * @return the application ID
     */
    public int getApplicationID() {
        return this.applicationID;
    }

    /**
     * Returns the name of the project applied for.
     *
     * @return the project name
     */
    public String getAppliedProjectName() {
        return appliedProjectName;
    }

    /**
     * Returns the current status of the application.
     *
     * @return the registration status
     */
    public String getApplicationStatus() {
        return registrationStatus;
    }

    /**
     * Sets the name of the project being applied to.
     *
     * @param appliedProjectName the new project name
     */
    public void setAppliedProjectName(String appliedProjectName) {
        this.appliedProjectName = appliedProjectName;
    }

    /**
     * Updates the registration status of the application.
     *
     * @param registrationStatus the new registration status
     */
    public void updateStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    /**
     * Returns the applicant who submitted the application.
     *
     * @return the {@code Applicant} object
     */
    public Applicant getApplicant() {
        return applicant;
    }

    /**
     * Displays the details of the application.
     */
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

    /**
     * Returns the name of the applicant.
     *
     * @return the applicant's name
     */
    public String getApplicantName() {
        return applicant.getName();
    }

    /**
     * Returns the NRIC of the applicant.
     *
     * @return the applicant's NRIC
     */
    public String getApplicantNRIC() {
        return applicant.getNRIC();
    }

    /**
     * Returns the withdrawal request associated with this application, if any.
     *
     * @return the {@code WithdrawalRequest} object or {@code null} if none exists
     */
    public WithdrawalRequest getWithdrawalRequest() {
        return this.withdrawalRequest;
    }

    /**
     * Creates a withdrawal request for this application with the given reason.
     *
     * @param reason the reason for the withdrawal
     */
    public void createWithdrawalRequest(String reason) {
        this.withdrawalRequest = new WithdrawalRequest(reason, this);
    }
}
