package boundary.menu;

/**
 * Interface for manager menu options in the BTO system.
 * Managers can manage projects, officers, applications, and enquiries.
 */
public interface ManagerMenu extends CommonMenu {

    /**
     * Add new BTO projects.
     */
    public void addNewProjects();

    /**
     * Edit details of existing projects.
     */
    public void modifyExistingProjectDetails();

    /**
     * Remove a project from the system.
     */
    public void removeProjectFromSystem();

    /**
     * Show or hide a project from applicants.
     */
    public void toggleVisibility();

    /**
     * Displays all projects or filter based on certain criteria.
     */
    public void viewAllOrFilteredProjectListings();

    /**
     * Manage Officer registration (approve / reject).
     */
    public void manageOfficerRegistration();

    /**
     * Handles applications submitted by applicants.
     */
    public void manageBTOApplication();

    /**
     * Handles application withdrawals.
     */
    public void manageBTOWithdrawal();

    /**
     * View enquiries sent by applicants.
     */
    public void viewAllEnquiry();

    /**
     * Reply to enquiries.
     */
    public void ReplyEnquiry();

    /**
     * Display list of projects created by the manager.
     */
    public void viewProjectsManagerCreated();

    /**
     * Generate reports for flat booking.
     */
    public void generateReportFlatBooking();
}
