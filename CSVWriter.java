import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CSVWriter {
    public static void saveProject(Project project, String filepath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Construct the CSV row manually
            StringBuilder sb = new StringBuilder();
            sb.append(project.getName()).append(",");
            sb.append(project.getNeighborhood()).append(",");
            sb.append("2-Room").append(",");
            sb.append(project.getNumType1()).append(",");
            sb.append(project.getSellPriceType1()).append(",");
            sb.append("3-Room").append(",");
            sb.append(project.getNumType2()).append(",");
            sb.append(project.getSellPriceType2()).append(",");
            sb.append(sdf.format(project.getOpenDate())).append(",");
            sb.append(sdf.format(project.getCloseDate())).append(",");
            sb.append(project.getManager()).append(",");
            sb.append(project.getNumOfficerSlots()).append(",");

            String[] officers = project.getOfficers();
            if (officers != null && officers.length > 0) {
                sb.append(String.join(",", officers)); // Join officers with commas
            } else {
                sb.append(""); // Empty field if no officers
            }
            sb.append(",");

            sb.append(project.getVisibility()).append(",");

            bw.write(sb.toString());
            bw.newLine();
            System.out.println("Project saved to " + filepath);
        } catch (IOException e) {
            System.out.println("Error writing project to CSV file.");
            e.printStackTrace();
        }
    }

    public static void overwriteProjects(List<Project> projects, String filepath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, false))) { // false = overwrite
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            bw.write(
                    "Project Name,Neighborhood,Type 1,Number of units for Type 1,Selling price for Type 1,Type 2,Number of units for Type 2,Selling price for Type 2,Application opening date,Application closing date,Manager,Officer Slot,Officer,Visibility");
            bw.newLine();

            for (Project project : projects) {
                StringBuilder sb = new StringBuilder();
                sb.append(project.getName()).append(",");
                sb.append(project.getNeighborhood()).append(",");
                sb.append("2-Room").append(",");
                sb.append(project.getNumType1()).append(",");
                sb.append(project.getSellPriceType1()).append(",");
                sb.append("3-Room").append(",");
                sb.append(project.getNumType2()).append(",");
                sb.append(project.getSellPriceType2()).append(",");
                sb.append(sdf.format(project.getOpenDate())).append(",");
                sb.append(sdf.format(project.getCloseDate())).append(",");
                sb.append(project.getManager()).append(",");
                sb.append(project.getNumOfficerSlots()).append(",");
                sb.append(String.join(",", project.getOfficers())).append(",");
                sb.append(project.getVisibility()).append(",");

                bw.write(sb.toString());
                bw.newLine();
            }

            System.out.println("All projects overwritten to CSV file.");
        } catch (IOException e) {
            System.out.println("Error overwriting project CSV.");
            e.printStackTrace();
        }
    }

    public static void deleteProject(String projectNameToDelete) {
        File inputFile = new File("ProjectList.csv");
        File tempFile = new File("ProjectList_temp.csv");

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String currentLine;
            boolean found = false;

            while ((currentLine = reader.readLine()) != null) {
                String[] fields = currentLine.split(",", -1);

                if (fields.length > 0 && fields[0].equalsIgnoreCase(projectNameToDelete)) {
                    found = true;
                    continue;
                }

                writer.write(currentLine);
                writer.newLine();
            }

            if (!found) {
                System.out.println("No matching project found to delete.");
            } else {
                System.out.println("Project deleted successfully.");
            }

        } catch (IOException e) {
            System.out.println("An error occurred while deleting the project: " + e.getMessage());
        }

        // Replace the old file with the updated one
        if (!inputFile.delete()) {
            System.out.println("Could not delete original project file.");
            return;
        }

        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename temp file to project file.");
        }
    }

    public static void submitEnquiry(Enquiry enquiry, String filepath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Construct the CSV row manually
            StringBuilder sb = new StringBuilder();
            sb.append(enquiry.getNRIC()).append(","); // NRIC
            sb.append(enquiry.getID()).append(","); // Enquiry ID
            sb.append(enquiry.getContent()).append(","); // content
            sb.append(","); // response
            sb.append(enquiry.getProjectName().equals("-") ? "" : enquiry.getProjectName()).append(","); // project name

            sb.append(sdf.format(enquiry.getDate())).append(",");
            ;// date created

            bw.write(sb.toString());
            bw.newLine();
            System.out.println("Enquiry saved to " + filepath);
        } catch (IOException e) {
            System.out.println("Error sending enquiry.");
            e.printStackTrace();
        }
    }

    public static void saveEnquirieToCSV(List<Enquiry> enquiryList, String filepath) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, false))) { // false = overwrite
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            bw.write(
                    "NRIC,Enquiry ID,content,response,Project Name,date created");
            bw.newLine();

            for (Enquiry e : enquiryList) {
                StringBuilder sb = new StringBuilder();
                sb.append(e.getNRIC()).append(",");
                sb.append(e.getID()).append(",");
                sb.append(e.getContent()).append(",");
                sb.append(e.getResponse() == null ? "" : e.getResponse()).append(",");
                sb.append(e.getProjectName().equals("-") ? null : e.getProjectName()).append(",");
                sb.append(sdf.format(e.getDate()));

                bw.write(sb.toString());
                bw.newLine();
            }

            System.out.println("All projects overwritten to CSV file.");
        } catch (IOException e) {
            System.out.println("Error overwriting project CSV.");
            e.printStackTrace();
        }
    }
}