package entity;

/**
 * The {@code FlatBooking} class represents a booking made by an applicant for a
 * housing flat.
 * It contains details such as the applicant's personal information, the flat's
 * location and type,
 * the selling price, and the project associated with the booking.
 */
public class FlatBooking {
    private static int ID = 1;
    private final int bookingID;
    private String applicantName;
    private String applicantNRIC;
    private int applicantAge;
    private String applicantMaritalStatus;
    private String projectName;
    private String flatType;
    private String neighborhood;
    private int sellPrice;

    /**
     * Constructs a new {@code FlatBooking} with the given details.
     * The booking ID is automatically generated.
     *
     * @param applicantName          the full name of the applicant
     * @param applicantNRIC          the NRIC of the applicant
     * @param applicantAge           the age of the applicant
     * @param applicantMaritalStatus the marital status of the applicant
     * @param projectName            the name of the housing project
     * @param flatType               the type of flat booked (e.g., 3-room, 4-room)
     * @param neighborhood           the neighborhood of the flat
     * @param sellPrice              the selling price of the flat
     */
    public FlatBooking(String applicantName, String applicantNRIC, int applicantAge,
            String applicantMaritalStatus, String projectName, String flatType, String neighborhood, int sellPrice) {
        this.bookingID = ID++;
        this.applicantName = applicantName;
        this.applicantNRIC = applicantNRIC;
        this.applicantAge = applicantAge;
        this.applicantMaritalStatus = applicantMaritalStatus;
        this.projectName = projectName;
        this.flatType = flatType;
        this.neighborhood = neighborhood;
        this.sellPrice = sellPrice;
    }

    /**
     * Constructs a {@code FlatBooking} using a specific booking ID. Used when
     * loading from csv.
     *
     * @param bookingID              the manually assigned booking ID
     * @param applicantName          the full name of the applicant
     * @param applicantNRIC          the NRIC of the applicant
     * @param applicantAge           the age of the applicant
     * @param applicantMaritalStatus the marital status of the applicant
     * @param projectName            the name of the housing project
     * @param flatType               the type of flat booked
     * @param neighborhood           the neighborhood of the flat
     * @param sellPrice              the selling price of the flat
     */
    public FlatBooking(int bookingID, String applicantName, String applicantNRIC, int applicantAge,
            String applicantMaritalStatus, String projectName, String flatType, String neighborhood, int sellPrice) {
        this.bookingID = bookingID;
        this.applicantName = applicantName;
        this.applicantNRIC = applicantNRIC;
        this.applicantAge = applicantAge;
        this.applicantMaritalStatus = applicantMaritalStatus;
        this.projectName = projectName;
        this.flatType = flatType;
        this.neighborhood = neighborhood;
        this.sellPrice = sellPrice;

        if (bookingID >= ID) {
            ID = bookingID + 1;
        }
    }

    /**
     * Returns the booking ID.
     *
     * @return the booking ID
     */
    public int getBookingID() {
        return bookingID;
    }

    /**
     * Returns the name of the applicant.
     *
     * @return the applicant's name
     */
    public String getApplicantName() {
        return applicantName;
    }

    /**
     * Returns the NRIC of the applicant.
     *
     * @return the applicant's NRIC
     */
    public String getApplicantNRIC() {
        return applicantNRIC;
    }

    /**
     * Returns the age of the applicant.
     *
     * @return the applicant's age
     */
    public int getApplicantAge() {
        return applicantAge;
    }

    /**
     * Returns the marital status of the applicant.
     *
     * @return the applicant's marital status
     */
    public String getApplicantMaritalStatus() {
        return applicantMaritalStatus;
    }

    /**
     * Returns the name of the housing project.
     *
     * @return the project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Returns the type of the flat.
     *
     * @return the flat type
     */
    public String getFlatType() {
        return flatType;
    }

    /**
     * Returns the neighborhood of the flat.
     *
     * @return the neighborhood
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     * Returns the selling price of the flat.
     *
     * @return the selling price
     */
    public int getSellPrice() {
        return sellPrice;
    }

    /**
     * Updates the name of the applicant.
     *
     * @param applicantName the new applicant name
     */
    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    /**
     * Updates the NRIC of the applicant.
     *
     * @param applicantNRIC the new applicant NRIC
     */
    public void setApplicantNRIC(String applicantNRIC) {
        this.applicantNRIC = applicantNRIC;
    }

    /**
     * Updates the age of the applicant.
     *
     * @param applicantAge the new applicant age
     */
    public void setApplicantAge(int applicantAge) {
        this.applicantAge = applicantAge;
    }

    /**
     * Updates the marital status of the applicant.
     *
     * @param applicantMaritalStatus the new marital status
     */
    public void setApplicantMaritalStatus(String applicantMaritalStatus) {
        this.applicantMaritalStatus = applicantMaritalStatus;
    }

    /**
     * Updates the name of the housing project.
     *
     * @param projectName the new project name
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Updates the flat type.
     *
     * @param flatType the new flat type
     */
    public void setFlatType(String flatType) {
        this.flatType = flatType;
    }

    /**
     * Updates the neighborhood of the flat.
     *
     * @param neighborhood the new neighborhood
     */
    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    /**
     * Updates the selling price of the flat.
     *
     * @param sellPrice the new selling price
     */
    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    /**
     * Displays all flat booking details.
     */
    public void viewFlatBookingDetails() {
        String line = "+----------------------------------------------------+";
        System.out.println(line);
        System.out.printf("| %-50s |\n", "Flat Booking Details");
        System.out.println(line);
        System.out.printf("| %-20s: %-28s |\n", "Booking ID", bookingID);
        System.out.printf("| %-20s: %-28s |\n", "Applicant Name", applicantName);
        System.out.printf("| %-20s: %-28s |\n", "Applicant NRIC", applicantNRIC);
        System.out.printf("| %-20s: %-28s |\n", "Age", applicantAge);
        System.out.printf("| %-20s: %-28s |\n", "Marital Status", applicantMaritalStatus);
        System.out.printf("| %-20s: %-28s |\n", "Project Name", projectName);
        System.out.printf("| %-20s: %-28s |\n", "Neighborhood", neighborhood);
        System.out.printf("| %-20s: %-28s |\n", "Flat Type", flatType);
        System.out.printf("| %-20s: $%-27s |\n", "Selling Price", sellPrice);
        System.out.println(line);
    }

}
