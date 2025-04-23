package entity;

/**
 * The {@code RegistrationForm} class represents a registration form submitted
 * by an officer
 * to register for a housing project. It contains information such as the
 * officer's name,
 * NRIC, age, marital status, project details, and registration status.
 */
public class RegistrationForm {
    private static int index_registration_form = 1;

    private int registerID;
    private String officerName;
    private String officerNRIC;
    private int officerAge;
    private String maritalStatus;
    private String registeredProjectName;
    private String registrationStatus;

    /**
     * Constructs a {@code RegistrationForm} with the given officer details and
     * project information. The registration ID is automatically assigned
     * sequentially.
     *
     * @param officerName           The name of the officer.
     * @param officerNRIC           The NRIC of the officer.
     * @param officerAge            The age of the officer.
     * @param maritalStatus         The marital status of the officer.
     * @param registeredProjectName The name of the project the officer is
     *                              registered for.
     */
    public RegistrationForm(String officerName, String officerNRIC, int officerAge, String maritalStatus,
            String registeredProjectName) {
        this.registerID = index_registration_form++;
        this.officerName = officerName;
        this.officerNRIC = officerNRIC;
        this.officerAge = officerAge;
        this.maritalStatus = maritalStatus;
        this.registeredProjectName = registeredProjectName;
        this.registrationStatus = "Pending";
    }

    /**
     * Constructs a {@code RegistrationForm} with the given officer details, project
     * information, and registration status. Used when loading from csv.
     *
     * @param registerID            The registration ID.
     * @param officerName           The name of the officer.
     * @param officerNRIC           The NRIC of the officer.
     * @param officerAge            The age of the officer.
     * @param maritalStatus         The marital status of the officer.
     * @param registeredProjectName The name of the project the officer is
     *                              registered for.
     * @param registrationStatus    The current registration status.
     */
    public RegistrationForm(int registerID, String officerName, String officerNRIC, int officerAge,
            String maritalStatus, String registeredProjectName, String registrationStatus) {
        this.registerID = registerID;
        this.officerName = officerName;
        this.officerNRIC = officerNRIC;
        this.officerAge = officerAge;
        this.maritalStatus = maritalStatus;
        this.registeredProjectName = registeredProjectName;
        this.registrationStatus = registrationStatus;

        if (registerID >= index_registration_form) {
            index_registration_form = registerID + 1;
        }
    }

    /**
     * Gets the unique registration ID for the registration form.
     *
     * @return The registration ID.
     */
    public int getRegistrationID() {
        return registerID;
    }

    /**
     * Gets the name of the officer.
     *
     * @return The name of the officer.
     */
    public String getOfficerName() {
        return officerName;
    }

    /**
     * Gets the NRIC of the officer.
     *
     * @return The NRIC of the officer.
     */
    public String getOfficerNRIC() {
        return officerNRIC;
    }

    /**
     * Gets the age of the officer.
     *
     * @return The age of the officer.
     */
    public int getOfficerAge() {
        return officerAge;
    }

    /**
     * Gets the marital status of the officer.
     *
     * @return The marital status of the officer.
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Gets the name of the project the officer is registered for.
     *
     * @return The name of the registered project.
     */
    public String getRegisteredProjectName() {
        return registeredProjectName;
    }

    /**
     * Gets the current registration status.
     *
     * @return The registration status.
     */
    public String getRegistrationStatus() {
        return registrationStatus;
    }

    /**
     * Sets the registration status.
     *
     * @param status The new registration status.
     */
    public void setRegistrationStatus(String status) {
        this.registrationStatus = status;
    }

    /**
     * Displays the details of the registration form.
     */
    public void viewDetails() {
        System.out.println("===== Registration Details =====");
        System.out.println("Registration ID: " + registerID);
        System.out.println("Officer Name: " + officerName);
        System.out.println("Officer NRIC: " + officerNRIC);
        System.out.println("Officer Age: " + officerAge);
        System.out.println("Marital Status: " + maritalStatus);
        System.out.println("Registered Project Name: " + registeredProjectName);
        System.out.println("Registration Status: " + registrationStatus);
        System.out.println("================================");
    }
}
