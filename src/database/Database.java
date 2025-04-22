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

public class Database {
    static public List<Applicant> applicantList = new ArrayList<>();
    static public List<Manager> managerList = new ArrayList<>();
    static public List<Officer> officerList = new ArrayList<>();
    static public List<Project> projectList = new ArrayList<>();
    static public List<Enquiry> enquiryList = new ArrayList<>();
    static public List<FlatBooking> flatBookingList = new ArrayList<>();
    public static List<ApplicationForm> applicationHistory = new ArrayList<>();
}
