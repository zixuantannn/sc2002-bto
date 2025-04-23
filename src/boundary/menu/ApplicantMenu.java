package boundary.menu;

/**
 * Interface representing the menu options available to applicant.
 */
public interface ApplicantMenu extends CommonMenu {

    /**
     * Displays all available BTO projects to the applicant.
     */
    void viewAllAvailableProjects();

    /**
     * Allows the applicant to apply for a selected BTO project.
     */
    void applyForBTOProject();

    /**
     * Allows the applicant to view the status of their application form.
     */
    void viewApplyFormStatus();

    /**
     * Displays details of the project that the applicant has applied for.
     */
    void viewTheAppliedProject();

    /**
     * Allows the applicant to withdraw their application form.
     */
    void withdrawalApplyForm();

    /**
     * Enables the applicant to submit an enquiry
     */
    void submitEnquiry();

    /**
     * Displays all enquiries submitted by the applicant.
     */
    void displayEnquiries();

    /**
     * Allows the applicant to edit a previously submitted enquiry.
     */
    void editEnquiries();

    /**
     * Allows the applicant to remove a previously submitted enquiry.
     */
    void removeEnquiries();

    /**
     * Displays flat booking information associated with the applicant.
     */
    void viewFlatBooking();

    /**
     * Displays all application forms submitted by the applicant.
     */
    void viewAllApplicationForms();
}
