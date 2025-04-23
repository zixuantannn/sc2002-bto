package entity;

/**
 * The {@code WithdrawalRequest} class represents a request made by an applicant
 * to withdraw from a previously submitted application. It stores the reason for
 * withdrawal, associated application form and current status of the request
 */
public class WithdrawalRequest {
    private String reason;
    private ApplicationForm form;
    private String status; // "Pending","Approved","Rejected"
    private String managerName;

    /**
     * Constructs a {@code WithdrawalRequest} with the specified reason and
     * application form.
     * The status is set to "Pending" by default.
     *
     * @param reason the reason for the withdrawal.
     * @param form   the application form associated with this withdrawal request.
     */
    public WithdrawalRequest(String reason, ApplicationForm form) {
        this.reason = reason;
        this.form = form;
        status = "Pending";
        managerName = null;
    }

    /**
     * Returns the reason for the withdrawal.
     *
     * @return the withdrawal reason.
     */
    public String getReason() {
        return reason;

    }

    /**
     * Returns the application form associated with this withdrawal request.
     *
     * @return the application form.
     */
    public ApplicationForm getApplicationForm() {
        return form;
    }

    /**
     * Returns the current status of the withdrawal request.
     *
     * @return the request status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Approves the withdrawal request and sets the manager's name who approved it.
     *
     * @param managerName the name of the approving manager.
     */
    public void approve(String managerName) {
        this.status = "Approved";
        this.managerName = managerName;

    }

    /**
     * Rejects the withdrawal request and sets the manager's name who rejected it.
     *
     * @param managerName the name of the rejecting manager.
     */
    public void reject(String managerName) {
        this.status = "Rejected";
        this.managerName = managerName;
    }

    /**
     * Displays the details of the withdrawal request.
     */
    public void viewDetails() {
        String rowFormat = "| %-15s | %-20s | %-40s | %-15s |%n";
        System.out.format(
                "+-----------------+----------------------+------------------------------------------+-----------------+%n");
        System.out.format(rowFormat, "Application ID", "Project Name", "Reason", "Status");
        System.out.format(
                "+-----------------+----------------------+------------------------------------------+-----------------+%n");

        System.out.format(rowFormat,
                form.getApplicationID(),
                form.getAppliedProjectName(),
                reason,
                status);

        System.out.format(
                "+-----------------+----------------------+------------------------------------------+-----------------+%n");

    }
}
