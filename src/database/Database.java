package database;

import java.util.ArrayList;
import java.util.List;

import entity.Applicant;
import entity.Manager;
import entity.Officer;
import entity.Project;
import entity.Enquiry;
import entity.FlatBooking;
import entity.ApplicationForm;

/**
 * The {@code Database} class holds static lists that store instances of
 * various entities in the application, including applicants, managers,
 * officers, projects, enquiries, flat bookings, and application history.
 * This class serves as a central repository for managing and accessing
 * all core data objects in the application.
 */
public class Database {
    /** A list that holds all applicants in the system. */
    static public List<Applicant> applicantList = new ArrayList<>();

    /** A list that holds all managers in the system. */
    static public List<Manager> managerList = new ArrayList<>();

    /** A list that holds all officers in the system. */
    static public List<Officer> officerList = new ArrayList<>();

    /** A list that holds all projects in the system. */
    static public List<Project> projectList = new ArrayList<>();

    /** A list that holds all enquiries made by applicants. */
    static public List<Enquiry> enquiryList = new ArrayList<>();

    /** A list that holds all flat bookings made by applicants. */
    static public List<FlatBooking> flatBookingList = new ArrayList<>();

    /** A list that holds the application history for all applicants. */
    public static List<ApplicationForm> applicationHistory = new ArrayList<>();
}
