import java.util.List;
import java.util.ArrayList;

public class ProjectHandler {
    private List<Project> projectList;

    public ProjectHandler(List<Project> projectList) {
        this.projectList = projectList;
    }

    public Project getProject(String name) {
        for (Project project : projectList) {
            if (project.getName().equalsIgnoreCase(name)) {
                return project;
            }
        }
        return null;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

}
