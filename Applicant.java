
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Applicant extends UserAccount{
    private boolean appliedForProject;
    private ApplicationForm applyForm = null;

    public Applicant(String name, String NRIC, int age, String maritalStatus){
        super(name, NRIC, age, maritalStatus);
        this.appliedForProject = false;
    }

    public Applicant(String name, String NRIC, int age, String maritalStatus, String password){
        super(name, NRIC, age, maritalStatus, password);
        this.appliedForProject = false;
    }
    public ApplicationForm getApplyForm(){
        return this.applyForm;
    }
    public void setApplyForm(ApplicationForm form){
        this.applyForm = form;
    }
    
    public List<Project> viewAvailableProjects(Database db, boolean ok){
        System.out.println("Displaying available projects...");
        List <Project> allProjects = db.projectList;
        List <Project> filteredProjects = new ArrayList<>();
    
        for (Project project: allProjects){
            // Singles, 35 years old and above, can only view and apply 2-room 
            if (this.getAge()>34 && this.getMaritalStatus().equals("Single")){
                // If at least 1 2-room is available and visibility is on
                if (project.getNumType1()>0){ 
                    filteredProjects.add(project);
                }
            }    
            else if (super.getAge()>20 && super.getMaritalStatus().equals("Married")){
                // If either 2-room or 3-room is available and visibility is on
                if ((project.getNumType1()>0 || project.getNumType2()>0 )){
                    filteredProjects.add(project);
                }
            }      // then how about people who fall out of this range 
        }

        filteredProjects.sort(Comparator.comparing(Project::getName));
        if (filteredProjects.isEmpty()){
            System.out.println("No projects available.");
        }
        else{
            if(ok) return filteredProjects;
            System.out.println("List of Projects: ");
            boolean check = false;
            for (Project project: filteredProjects){
                if(project.getVisibility().equals("on")){
                    project.viewProjectDetails();
                    check = true;
                }
            }
            if(!check){
                System.out.println("No available project to apply!");
            }
        }
        return filteredProjects;
    }
    
    public void applyForProject(Database db, Scanner sc){
        if (!appliedForProject){
            System.out.print("Enter your project name you want to apply: ");
            String appliedProject = sc.nextLine();
            List<Project> filteredProjects = this.viewAvailableProjects(db, true);

            boolean found = false;
            for(int i=0;i<filteredProjects.size();i++){
                if(appliedProject.equalsIgnoreCase(filteredProjects.get(i).getName())){
                    found = true;
                    this.applyForm = new ApplicationForm(
                            this.getName(),
                            this.getNRIC(),
                            this.getAge(),
                            this.getMaritalStatus(),
                            appliedProject,
                            "Pending"
                    );

                    System.out.println(" Application submitted successfully!");
                    this.appliedForProject = true;
                    filteredProjects.get(i).getListOfApplyForm().add(this.applyForm);
                    break;
                }
            }
            if(!found){
                System.out.println("Not found!!!");
            }
        }else{
            System.out.println("Cannot apply for multiple projects.");
        }
    }    


    public void viewAppliedProject() {
        if (appliedForProject && applyForm != null) {
            System.out.println("You applied for project: " + applyForm.getAppliedProjectName() +
                               " | Status: " + applyForm.getApplicationStatus());
        } else {
            System.out.println("You have not applied for any project yet.");
        }
    }

    public void viewApplicationStatus(){ //should this be different from the appliedProjects
    	if(this.appliedForProject){
            System.out.println("Application Status: " + applyForm.getApplicationStatus());
        }else{
            System.out.println("You have no applied project.");
        }
    }

    public void  withdrawalApplication(){
        this.applyForm.setWithdrawalEnquiry("I want to cancel my application.");
        System.out.println("You have requested withdrawal for your BTO application");
    }

    public void sendEnquiry(Database db, Scanner sc){
    	if (applyForm != null) {
            String appliedProject = applyForm.getAppliedProjectName();
            Project applyProject = null;
            for(int i=0;i<db.projectList.size();i++){
                if(appliedProject.equals(db.projectList.get(i).getName())){
                    applyProject = db.projectList.get(i);
                    break;
                }
            }
		
     		System.out.print("Please enter your enquiry details: ");
    		String content = sc.nextLine();

    		Enquiry enquiry = new Enquiry (this.getNRIC(), content);

    		applyProject.getEnquiryList().add(enquiry);
    		System.out.println("New Enquiry submitted.");
    	}
    	else {
    		System.out.println("No application found.");
    	}
    }

    public void displayMyEnquiries(Database db) {
        if (applyForm != null) {
            String projectName = applyForm.getAppliedProjectName();
            Project appliedProject = null;
            for (Project p : db.projectList) {
                if (p.getName().equalsIgnoreCase(projectName)) {
                    appliedProject = p;
                    break;
                }
            }
            if (appliedProject == null) {
                System.out.println("Project not found.");
                return;
            }
            boolean found = false;
            System.out.println("Your Enquiries:");
            for (Enquiry en : appliedProject.getEnquiryList()) {
                if (en.getSenderNRIC().equals(this.getNRIC())) {
                    en.viewDetails();
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No enquiries found.");
            }
        } else {
            System.out.println("You have not applied for any project.");
        }
    }


    public void modifyEnquiry(Database db, Scanner sc) {
        if (applyForm != null) {
            String projectName = applyForm.getAppliedProjectName();
            Project appliedProject = null;
            for (Project p : db.projectList) {
                if (p.getName().equalsIgnoreCase(projectName)) {
                    appliedProject = p;
                    break;
                }
            }
            if (appliedProject == null) {
                System.out.println("Project not found.");
                return;
            }
            List<Enquiry> myEnquiries = new ArrayList<>();
            for (Enquiry en : appliedProject.getEnquiryList()) {
                if (en.getSenderNRIC().equals(this.getNRIC())) {
                    myEnquiries.add(en);
                }
            }
            if (myEnquiries.isEmpty()) {
                System.out.println("No enquiries to modify.");
                return;
            }

            System.out.print("Enter the enquiry ID: ");
            int ID = sc.nextInt();
            sc.nextLine();
            Enquiry selected = null;
            for(Enquiry en : myEnquiries){
                if(ID == en.getID()){
                    selected = en;
                    break;
                }
            }
            if(selected == null){
                System.out.println("The enquiry ID is invalid!");
                return ;
            }
            System.out.println("Enter new content for the enquiry: ");
            String newContent = sc.nextLine();
            selected.updateContent(newContent);  
            System.out.println("Enquiry updated successfully.");
        } else {
            System.out.println("No applied project found.");
        }
    }

    public void removeEnquiry(Database db, Scanner sc) {
        if (applyForm != null) {
            String projectName = applyForm.getAppliedProjectName();
            Project appliedProject = null;
            for (Project p : db.projectList) {
                if (p.getName().equalsIgnoreCase(projectName)) {
                    appliedProject = p;
                    break;
                }
            }
            if (appliedProject == null) {
                System.out.println("Project not found.");
                return;
            }
            List<Enquiry> myEnquiries = new ArrayList<>();
            for (Enquiry en : appliedProject.getEnquiryList()) {
                if (en.getSenderNRIC().equals(this.getNRIC())) {
                    myEnquiries.add(en);
                }
            }
            if (myEnquiries.isEmpty()) {
                System.out.println("No enquiries to remove.");
                return;
            }
            System.out.println("Select an enquiry to remove by index:");
            for (int i = 0; i < myEnquiries.size(); i++) {
                System.out.println((i + 1) + ". ");
                myEnquiries.get(i).viewDetails();
            }
            int choice = sc.nextInt();
            sc.nextLine(); 
            if (choice < 1 || choice > myEnquiries.size()) {
                System.out.println("Invalid choice.");
                return;
            }
            Enquiry selected = myEnquiries.get(choice - 1);
            appliedProject.getEnquiryList().remove(selected);
            System.out.println("Enquiry removed successfully.");
        } else {
            System.out.println("No applied project found.");
        }
    }




   

}