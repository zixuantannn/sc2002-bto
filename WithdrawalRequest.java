public class WithdrawalRequest {
    private String reason;
    private ApplicationForm form;
    private String status; // "Pending","Approved","Rejected"
    private String managerName;

    public WithdrawalRequest(String reason, ApplicationForm form) {
        this.reason = reason;
        this.form = form;
        status = "Pending";
        managerName = null;
    }

    public String getReason() {
        return reason;

    }

    public ApplicationForm getApplicationForm() {
        return form;
    }

    public String getStatus() {
        return status;
    }

    public void approve(String managerName) {
        this.status = "Approved";
        this.managerName = managerName;

    }

    public void reject(String managerName) {
        this.status = "Rejected";
        this.managerName = managerName;
    }

    public void viewDetails() {
        // Define a format string for the table rows
        String rowFormat = "| %-15s | %-20s | %-40s | %-15s |%n";

        // Print the table header
        System.out.format(
                "+-----------------+----------------------+------------------------------------------+-----------------+%n");
        System.out.format(rowFormat, "Application ID", "Project Name", "Reason", "Status");
        System.out.format(
                "+-----------------+----------------------+------------------------------------------+-----------------+%n");

        // Print the withdrawal details in a row
        System.out.format(rowFormat,
                form.getApplicationID(),
                form.getAppliedProjectName(),
                reason,
                status);

        // Print the closing border
        System.out.format(
                "+-----------------+----------------------+------------------------------------------+-----------------+%n");

    }
}