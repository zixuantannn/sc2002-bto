package controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import utility.InputValidation;
import entity.Project;
import entity.Applicant;
import database.Database;
import entity.ApplicationForm;
import entity.Officer;

public class ApplicantProjectHandler extends ProjectHandler {
    public ApplicantProjectHandler(List<Project> projectList) {
        super(projectList);
    }

    public List<Project> filterAvailableProject(Database db, Applicant ap) {
        List<Project> filteredProjects = new ArrayList<>();
        for (Project project : getProjectList()) {
            if (!project.getVisibility().equals("on")) {
                continue;
            }

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
            return;
        } else { // check for project with visibility on
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

    public void applyForProject(Database db, Applicant ap) {
        if (ap.getAvailability()) {
            System.out.println("Cannot apply for multiple projects.");
            return;
        }
        List<Project> filteredProjects = filterAvailableProject(db, ap);
        viewAvailableProjects(filteredProjects);
        if (filteredProjects.isEmpty()) {
            System.out.println("No available project to apply!");
            return;
        }
        String appliedProject = InputValidation.getString("Enter your project name you want to apply: ",
                project -> !project.trim().isEmpty(),
                "Project name cannot be empty.");

        Project project = filteredProjects.stream()
                .filter(p -> p.getName().equalsIgnoreCase(appliedProject))
                .findFirst()
                .orElse(null);

        if (project == null) {
            System.out.println("Project not found !");
        } else {
            if (ap instanceof Officer) {
                Officer officer = (Officer) ap;
                Project assigned = officer.getAssignedProject();
                if (assigned != null && assigned.getName().equals(appliedProject)) {
                    System.out.println("You cannot apply for the project you are currently managing as an officer.");
                    return;
                }
            }
            ap.setApplyForm(new ApplicationForm(ap, appliedProject, "Pending"));
            ap.setAvailability();
            project.getListOfApplyForm().add(ap.getApplyForm());
            ap.addProjectList(ap.getApplyForm());
            System.out.println("Application submitted successfully !");
        }

    }

    public void viewAppliedProject(Applicant ap) {
        if (ap.getApplyForm() != null) {
            ap.getApplyForm().viewDetails();

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

    public void withdrawalApplication(Applicant ap) {
        // If there are no current project
        if (!ap.getAvailability()) {
            System.out.println("You have not applied for any project. ");

        } else {
            String reason = InputValidation.getString("What is the reason for withdrawal: ",
                    reasonInput -> !reasonInput.trim().isEmpty(),
                    "Reason for withdrawal cannot be empty.");
            ap.getApplyForm().createWithdrawalRequest(reason);
            System.out.println("You have requested withdrawal for your BTO application");
        }
    }

}
