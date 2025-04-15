public class RegistrationForm {
    private static int index_registration_form = 1;

    private int registerID;
    private String officerName;
    private String officerNRIC;
    private int officerAge;
    private String maritalStatus;
    private String registeredProjectName;
    private String registrationStatus;

    public RegistrationForm(String officerName, String officerNRIC, int officerAge, String maritalStatus, String registeredProjectName) {
        this.registerID = index_registration_form++;
        this.officerName = officerName;
        this.officerNRIC = officerNRIC;
        this.officerAge = officerAge;
        this.maritalStatus = maritalStatus;
        this.registeredProjectName = registeredProjectName;
        this.registrationStatus = "Pending";
    }

    public RegistrationForm(int registerID, String officerName, String officerNRIC, int officerAge, String maritalStatus, String registeredProjectName, String registrationStatus) {
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

    public int getRegistrationID() {
        return registerID;
    }

    public String getOfficerName() {
        return officerName;
    }

    public String getOfficerNRIC() {
        return officerNRIC;
    }

    public int getOfficerAge() {
        return officerAge;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getRegisteredProjectName() {
        return registeredProjectName;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(String status) {
        this.registrationStatus = status;
    }

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
