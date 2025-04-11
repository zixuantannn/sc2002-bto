import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSVImporter {

    public static void importApplicants(Database db, String filepath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String name = tokens[0];
                String nric = tokens[1];
                int age = Integer.parseInt(tokens[2]);
                String maritalStatus = tokens[3];
                String password = tokens.length > 4 ? tokens[4] : "";

                Applicant applicant = new Applicant(name, nric, age, maritalStatus, password);
                db.applicantList.add(applicant);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void importManagers(Database db, String filepath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String name = tokens[0];
                String nric = tokens[1];
                int age = Integer.parseInt(tokens[2]);
                String maritalStatus = tokens[3];
                String password = tokens.length > 4 ? tokens[4] : "";

                Manager manager = new Manager(name, nric, age, maritalStatus, password);
                db.managerList.add(manager);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void importOfficers(Database db, String filepath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String name = tokens[0];
                String nric = tokens[1];
                int age = Integer.parseInt(tokens[2]);
                String maritalStatus = tokens[3];
                String password = tokens.length > 4 ? tokens[4] : "";

                Officer officer = new Officer(name, nric, age, maritalStatus, password);
                db.officerList.add(officer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void importProjects(Database db, String filepath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            br.readLine();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String name = tokens[0];
                String neighborhood = tokens[1];
                int numType1 = Integer.parseInt(tokens[3]);
                int sellPriceType1 = Integer.parseInt(tokens[4]);
                int numType2 = Integer.parseInt(tokens[6]);
                int sellPriceType2 = Integer.parseInt(tokens[7]);
                Date openDate = sdf.parse(tokens[8]);
                Date closeDate = sdf.parse(tokens[9]);
                String manager = tokens[10];
                int officerSlots = Integer.parseInt(tokens[11]);

                String[] officers = new String[tokens.length - 12];
                for (int i = 12; i < tokens.length; i++) {
                    officers[i - 12] = tokens[i].trim();
                }

                Project project = new Project(name, neighborhood, numType1, sellPriceType1, numType2, sellPriceType2,
                        openDate, closeDate, manager, officerSlots, officers);
                db.projectList.add(project);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void importEnquiry(Database db, String filepath) {
        int maxID = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            br.readLine();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String nric = tokens[0];
                int enquiryId = Integer.parseInt(tokens[1]);
                String content = tokens[2];
                String response = tokens[3].isEmpty() ? null : tokens[3];
                String projectName = tokens[4].isEmpty() ? "-" : tokens[4];
                Date date = sdf.parse(tokens[5]);

                Enquiry enquiry = new Enquiry(enquiryId, nric, content, response, date, projectName);
                db.enquiryList.add(enquiry);

                if (enquiryId > maxID) {
                    maxID = enquiryId;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Enquiry.setCountEnquiry(maxID);
    }
}
