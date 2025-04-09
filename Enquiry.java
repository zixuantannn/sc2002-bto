
public class Enquiry {
    private static int count_enquiry = 1;
    private int enquiryID;
    private String senderNRIC;
    private String content;
    private String response;
    public Enquiry(){
        enquiryID = count_enquiry++;
    }
    public Enquiry(String senderNRIC, String content) {
        this.senderNRIC = senderNRIC;
        this.enquiryID = count_enquiry++;
        this.content = content;
    }

    public int getID() {
        return enquiryID;
    }

    public String getSenderNRIC(){
        return this.senderNRIC;
    }

    public void setSenderNRIC(String newSender){
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
        // Define a format string for the table rows
        String rowFormat = "| %-10s | %-15s | %-40s | %-40s |%n";
        
        // Print the table header
        System.out.format("+------------+-----------------+------------------------------------------+------------------------------------------+%n");
        System.out.format(rowFormat, "Enquiry ID", "Sender NRIC", "Content", "Response");
        System.out.format("+------------+-----------------+------------------------------------------+------------------------------------------+%n");
        
        // Print the enquiry details in a row
        System.out.format(rowFormat, 
                          enquiryID, 
                          senderNRIC, 
                          (content != null ? content : "No content provided"), 
                          (response != null ? response : "No response yet"));
        
        // Print the closing border
        System.out.format("+------------+-----------------+------------------------------------------+------------------------------------------+%n");
    }

}