import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Comparator;

public class ApplicantProjectManager extends ProjectManager {
    public ApplicantProjectManager(List<Project> projectList) {
        super(projectList);
    }

    public List<Project> filterAvailableProject(Database db, Applicant ap) {
        List<Project> filteredProjects = new ArrayList<>();
        for (Project project : getProjectList()) {
            if (ap.getAge() > 34 && ap.getMaritalStatus().equals("Single")) {
                // If at least 1 2-room is available and visibility is on
                if (project.getNumType1() > 0) {
                    filteredProjects.add(project);
                }
            } else if (ap.getAge() > 20 && ap.getMaritalStatus().equals("Married")) {
                // If either 2-room or 3-room is available and visibility is on
                if ((project.getNumType1() > 0 || project.getNumType2() > 0)) {
                    filteredProjects.add(project);
                }
            }
        }
        filteredProjects.sort(Comparator.comparing(Project::getName));
        return filteredProjects;
    }

    public void viewAvailableProjects(List<Project> filteredProjects) {

        if (filteredProjects.isEmpty()) {
            System.out.println("No projects available.");
        } else {
            boolean check = false;
            for (Project project : filteredProjects) {
                if (project.getVisibility().equals("on")) {
                    project.viewProjectDetails();
                    check = true;
                }
            }
            if (!check) {
                System.out.println("No available project to apply!");
            }
        }
    }

    public void applyForProject(Database db, Scanner sc, Applicant ap) {
        if (ap.getAvailability()) {
            System.out.println("Cannot apply for multiple projects.");
            return;
        }
        List<Project> filteredProjects = filterAvailableProject(db, ap);
        viewAvailableProjects(filteredProjects);
        if (filteredProjects.isEmpty()) {
            System.out.println("No available project to apply!");
        }
        System.out.print("Enter your project name you want to apply: ");
        String appliedProject = sc.nextLine();

        Project project = getProject(appliedProject);

        if (project == null) {
            System.out.println("Project not found !");
        } else {
            ap.setApplyForm(new ApplicationForm(ap, appliedProject, "Pending"));
            ap.setAvailability();
            project.getListOfApplyForm().add(ap.getApplyForm());
            ap.addProjectList(ap.getApplyForm());
            System.out.println("Application submitted successfully !");
        }

    }

    public void viewAppliedProject(Applicant ap) {
        if (!ap.getProjectAppliedList().isEmpty()) { // list is not empty
            System.out.println("You applied for the following project: ");
            for (ApplicationForm form : ap.getProjectAppliedList()) {
                System.out.println("Project Name: " + form.getAppliedProjectName() + " | Application Status: "
                        + form.getApplicationStatus());
                // do we need display flat type
            }
        } else {
            System.out.println("You have not applied for any project yet.");
        }
    }

    public void viewApplicationStatus(Applicant ap) {
        if (!ap.getProjectAppliedList().isEmpty()) {
            System.out.println("Lastest Application:");
            System.out.println("Project Name: " + ap.getProjectAppliedList().get(0).getAppliedProjectName() +
                    " | Application Status: " + ap.getProjectAppliedList().get(0).getApplicationStatus());
        } else {
            System.out.println("You have no latest project.");
        }
    }

    public void withdrawalApplication(Scanner sc, Applicant ap) {
        // If there are no projects
        if (ap.getProjectAppliedList().isEmpty()) {
            System.out.println("You have not applied for any project. ");
        }
        // If no pending project in the list
        else if (!(ap.getProjectAppliedList().get(0)).getApplicationStatus().equals("Pending")) {
            System.out.println("You have no pending projects. ");
        } else {
            System.out.println("What is the reason for withdrawal: ");
            String reason = sc.nextLine();
            ap.getApplyForm().createWithdrawalRequest(reason);
            System.out.println("You have requested withdrawal for your BTO application");
        }
    }

}
