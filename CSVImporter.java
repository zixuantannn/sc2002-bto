import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Date;
import java.text.SimpleDateFormat;

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
                int numType1 = Integer.parseInt(tokens[2]);
                int sellPriceType1 = Integer.parseInt(tokens[3]);
                int numType2 = Integer.parseInt(tokens[4]);
                int sellPriceType2 = Integer.parseInt(tokens[5]);
                Date openDate = sdf.parse(tokens[6]);
                Date closeDate = sdf.parse(tokens[7]);
                String manager = tokens[8];
                int officerSlots = Integer.parseInt(tokens[9]);

                String[] officers = new String[10];
                if (tokens.length > 10) {
                    for (int i = 10; i < tokens.length && i - 10 < 10; i++) {
                        officers[i - 10] = tokens[i];
                    }
                }

                Project project = new Project(name, neighborhood, numType1, sellPriceType1, numType2, sellPriceType2, openDate, closeDate, manager, officerSlots, officers);
                db.projectList.add(project);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
