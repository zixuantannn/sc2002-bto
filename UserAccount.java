public abstract class UserAccount {
    private String name = null;
    private String NRIC = null;
    private int age;
    private String maritalStatus = null;
    private String password = "password";
    private boolean loggedIn = false; 
    public UserAccount(String name, String NRIC, int age, String maritalStatus){
        this.name = name;
        this.NRIC = NRIC;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

    public UserAccount(String name, String NRIC, int age, String maritalStatus, String password){
        this.name = name;
        this.NRIC = NRIC;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }


    public String getNRIC() {
        return this.NRIC;
    }

    public int getAge() {
        return age;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }


    public String getPassword() {
        return password;
    }

    public void changePassword(String password) {
        this.password = password;
    }
    
    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    public boolean login(String p) {
        if (this.password.equals(p)) {
            System.out.println("Login successful!");
            this.loggedIn = true;
            return true;
        } else {
            System.out.println("Wrong password.");
            return false;
        }
    }

    public void logout() {
        this.loggedIn = false;
        System.out.println("You have logged out.");
    }
}
