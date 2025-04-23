package boundary.menu;

/**
 * Interface for officer menu actions in the BTO system.
 * Officers can manage project registration, enquiries, and applications.
 */
public interface OfficerMenu extends CommonMenu {

    /**
     * Register officer for a project.
     */
    public void registerProjectAsOfficer();

    /**
     * Display available projects for officer application
     */
    public void viewProjectsForApplyOfficer();

    /**
     * Check the status of officer project registration.
     */
    public void checkRegistrationStatus();

    /**
     * Display the project assigned to the officer
     */
    public void viewHandledProject();

    /**
     * Displays all received enquiries and allows the officer to respond to them.
     */
    public void viewAndReplyEnquiryList();

    /**
     * Manage the flat booking process for applicants.
     */
    public void handleFlatBook();

    /**
     * Display all application forms submitted by applicants.
     */
    public void viewApplicationForms();
}
