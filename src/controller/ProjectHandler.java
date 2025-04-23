package controller;

import java.util.List;
import java.util.ArrayList;
import entity.Project;

/**
 * The {@code ProjectHandler} class is responsible for managing a list of
 * projects.
 */
public class ProjectHandler {
    private List<Project> projectList;

    /**
     * Constructs a new {@code ProjectHandler} instance with the provided list of
     * projects.
     *
     * @param projectList The list of projects to be managed by this handler.
     */
    public ProjectHandler(List<Project> projectList) {
        this.projectList = projectList;
    }

    /**
     * Retrieves a project from the list by its name.
     *
     * @param name The name of the project to retrieve.
     * @return The {@link Project} instance with the specified name, or {@code null}
     *         if no project is found.
     */
    public Project getProject(String name) {
        for (Project project : projectList) {
            if (project.getName().toLowerCase().equalsIgnoreCase(name)) {
                return project;
            }
        }
        return null;
    }

    /**
     * Retrieves the list of all projects.
     *
     * @return A list of {@link Project} instances.
     */
    public List<Project> getProjectList() {
        return projectList;
    }

}
