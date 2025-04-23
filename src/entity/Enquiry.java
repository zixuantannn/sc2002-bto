package entity;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * The {@code Enquiry} class represents a question or message submitted by an
 * applicant
 * regarding a specific project or general enquiry.
 */
public class Enquiry {
    private static int count_enquiry = 1;
    private int enquiryID;
    private String senderNRIC;
    private String content;
    private String response;
    private Date date;
    private String projectName;

    /**
     * Constructs an {@code Enquiry} with all attributes specified. Used when
     * loading from csv.
     *
     * @param enquiryID   the unique ID of the enquiry
     * @param senderNRIC  the NRIC of the sender
     * @param content     the content of the enquiry
     * @param response    the response to the enquiry (can be null)
     * @param date        the date the enquiry was made
     * @param projectName the name of the project related to the enquiry
     */
    public Enquiry(int enquiryID, String senderNRIC, String content, String response, Date date, String projectName) {
        this.enquiryID = enquiryID;
        this.senderNRIC = senderNRIC;
        this.content = content;
        this.response = response;
        this.date = date;
        this.projectName = projectName;
    }

    /**
     * Constructs a new {@code Enquiry} for a specific project. An enquiry ID is
     * automatically assigned.
     *
     * @param senderNRIC  the NRIC of the sender
     * @param content     the content of the enquiry
     * @param projectName the name of the project
     */
    public Enquiry(String senderNRIC, String content, String projectName) {
        this.senderNRIC = senderNRIC;
        this.enquiryID = count_enquiry++;
        this.content = content;
        this.response = null;
        this.date = new Date();
        this.projectName = projectName;
    }

    /**
     * Constructs a new {@code Enquiry} with no associated project, representing
     * general enquiry. Project name is set to '-'.
     *
     * @param senderNRIC the NRIC of the sender
     * @param content    the content of the enquiry
     */
    public Enquiry(String senderNRIC, String content) {
        this.senderNRIC = senderNRIC;
        this.enquiryID = count_enquiry++;
        this.content = content;
        this.response = null;
        this.date = new Date(); // record current date and time
        this.projectName = "-";
    }

    /**
     * Returns the date the enquiry was created.
     *
     * @return the creation date
     */

    public Date getDate() {
        return date;
    }

    /**
     * Returns the NRIC of the sender.
     *
     * @return the sender's NRIC
     */
    public String getNRIC() {
        return senderNRIC;
    }

    /**
     * Returns the current count used to generate enquiry IDs.
     *
     * @return the enquiry count
     */
    public static int getCount() {
        return count_enquiry;
    }

    /**
     * Updates the static enquiry count. This is used when reloading data from csv.
     *
     * @param count the last used enquiry ID
     */
    public static void setCountEnquiry(int count) {
        count_enquiry = count + 1;
    }

    /**
     * Returns the name of the project this enquiry is related to.
     *
     * @return the project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Returns the unique ID of the enquiry.
     *
     * @return the enquiry ID
     */
    public int getID() {
        return enquiryID;
    }

    /**
     * Returns the NRIC of the sender.
     *
     * @return the sender's NRIC
     */
    public String getSenderNRIC() {
        return this.senderNRIC;
    }

    /**
     * Updates the sender's NRIC.
     *
     * @param newSender the new NRIC to set
     */
    public void setSenderNRIC(String newSender) {
        this.senderNRIC = newSender;
    }

    /**
     * Returns the content of the enquiry.
     *
     * @return the enquiry content
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the response to the enquiry, or {@code null} if not yet responded.
     *
     * @return the response
     */
    public String getResponse() {
        return response;
    }

    /**
     * Updates the content of the enquiry.
     *
     * @param newContent the new content to set
     */
    public void updateContent(String newContent) {
        this.content = newContent;
    }

    /**
     * Updates the response to the enquiry.
     *
     * @param newResponse the response to set
     */
    public void updateResponse(String newResponse) {
        this.response = newResponse;
    }

    /**
     * Displays the enquiry details.
     */
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