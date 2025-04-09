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
        String rowFormat = "| %-10s | %-15s | %-40s | %-40s |%n";

        // Print the table header
        System.out.format(
                "+------------+-----------------+------------------------------------------+------------------------------------------+%n");
        System.out.format(rowFormat, "Project Name", "Reason", "Response", "ManagerName");
        System.out.format(
                "+------------+-----------------+------------------------------------------+------------------------------------------+%n");

        // Print the enquiry details in a row
        System.out.format(rowFormat,
                form.getAppliedProjectName(),
                reason,
                status,
                (managerName != null ? managerName : "-"));

        // Print the closing border
        System.out.format(
                "+------------+-----------------+------------------------------------------+------------------------------------------+%n");
    }
}