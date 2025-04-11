import java.util.List;
import java.util.ArrayList;

public class ProjectManager {
    private List<Project> projectList;

    public ProjectManager(List<Project> projectList) {
        this.projectList = projectList;
    }

    public Project getProject(String name) {
        for (Project project : projectList) {
            if (project.getName().equals(name)) {
                return project;
            }
        }
        return null;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

}
