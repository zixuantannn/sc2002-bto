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

/**
 * The {@code ApplicantProjectHandler} class extends the {@link ProjectHandler}
 * class
 * and handles operations related to applicants and their interaction with
 * projects.
 */
public class ApplicantProjectHandler extends ProjectHandler {
    /**
     * Constructs an {@code ApplicantProjectHandler} instance with the given project
     * list.
     *
     * @param projectList The list of projects to be managed.
     */
    public ApplicantProjectHandler(List<Project> projectList) {
        super(projectList);
    }

    /**
     * Filters available projects based on the applicant's age and marital status.
     * If the applicant meets the criteria, projects with available slots are added
     * to the filtered list.
     *
     * @param db The database instance.
     * @param ap The applicant for whom the projects are filtered.
     * @return A list of available projects for the applicant.
     */
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

    /**
     * Displays the details of available projects for the applicant to apply for.
     *
     * @param filteredProjects The list of available projects to display.
     */
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

    /**
     * Allows the applicant to apply for a project, if available.
     * The applicant can only apply for one project at a time.
     *
     * @param db The database instance.
     * @param ap The applicant who is applying for a project.
     */
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

    /**
     * Displays the details of the project the applicant has applied for.
     *
     * @param ap The applicant whose application details are being displayed.
     */
    public void viewAppliedProject(Applicant ap) {
        if (ap.getApplyForm() != null) {
            ap.getApplyForm().viewDetails();

        } else {
            System.out.println("You have not applied for any project yet.");
        }
    }

    /**
     * Displays the status of the applicant's most recent project application.
     *
     * @param ap The applicant whose application status is being displayed.
     */
    public void viewApplicationStatus(Applicant ap) {
        if (!ap.getProjectAppliedList().isEmpty()) {
            System.out.println("Lastest Application:");
            System.out.println("Project Name: " + ap.getProjectAppliedList().get(0).getAppliedProjectName() +
                    " | Application Status: " + ap.getProjectAppliedList().get(0).getApplicationStatus());
        } else {
            System.out.println("You have no latest project.");
        }
    }

    /**
     * Allows the applicant to withdraw their application for a project.
     * The applicant must provide a reason for withdrawal.
     *
     * @param ap The applicant who wishes to withdraw their application.
     */
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
