package entity;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Enquiry {
    private static int count_enquiry = 1;
    private int enquiryID;
    private String senderNRIC;
    private String content;
    private String response;
    private Date date;
    private String projectName;

    public Enquiry(int enquiryID, String senderNRIC, String content, String response, Date date, String projectName) {
        this.enquiryID = enquiryID;
        this.senderNRIC = senderNRIC;
        this.content = content;
        this.response = response;
        this.date = date;
        this.projectName = projectName;
    }

    public Enquiry(String senderNRIC, String content, String projectName) {
        this.senderNRIC = senderNRIC;
        this.enquiryID = count_enquiry++;
        this.content = content;
        this.response = null;
        this.date = new Date();
        this.projectName = projectName;
    }

    public Enquiry(String senderNRIC, String content) {
        this.senderNRIC = senderNRIC;
        this.enquiryID = count_enquiry++;
        this.content = content;
        this.response = null;
        this.date = new Date(); // record current date and time
        this.projectName = "-";
    }

    public Date getDate() {
        return date;
    }

    public String getNRIC() {
        return senderNRIC;
    }

    public static int getCount() {
        return count_enquiry;
    }

    public static void setCountEnquiry(int count) {
        count_enquiry = count + 1;
    }

    public String getProjectName() {
        return projectName;
    }

    public int getID() {
        return enquiryID;
    }

    public String getSenderNRIC() {
        return this.senderNRIC;
    }

    public void setSenderNRIC(String newSender) {
        this.senderNRIC = newSender;
    }

    public String getContent() {
        return content;
    }

    public String getResponse() {
        return response;
    }

    public void updateContent(String newContent) {
        this.content = newContent;
    }

    public void updateResponse(String newResponse) {
        this.response = newResponse;
    }

    public void viewDetails() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String rowFormat = "| %-10s | %-10s | %-18s | %-30s | %-30s |%n";
        System.out.format(
                "+------------+------------+--------------------+--------------------------------+--------------------------------+%n");
        System.out.format(rowFormat, "Enquiry ID", "Created On", "Project Name", "Content", "Response");
        System.out.format(
                "+------------+------------+--------------------+--------------------------------+--------------------------------+%n");

        System.out.format(rowFormat,
                enquiryID,
                dateFormat.format(date),
                projectName,
                content,
                (response != null ? response : "No response yet"));

        System.out.format(
                "+------------+------------+--------------------+--------------------------------+--------------------------------+%n");

    }
}