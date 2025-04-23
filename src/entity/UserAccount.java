package entity;

/**
 * The {@code UserAccount} class is an abstract representation of a user in the
 * system.
 * It stores common attributes such as name, NRIC, age, marital status, and
 * login credentials.
 * This class provides basic functionality for login and logout operations, and
 * is extended
 * by specific user types like applicants and manager.
 */
public abstract class UserAccount {
    private String name = null;
    private String NRIC = null;
    private int age;
    private String maritalStatus = null;
    private String password = "password";
    private boolean loggedIn = false;

    /**
     * Constructs a {@code UserAccount} with the given user details and default
     * password.
     *
     * @param name          The user's full name.
     * @param NRIC          The user's NRIC.
     * @param age           The user's age.
     * @param maritalStatus The user's marital status.
     */
    public UserAccount(String name, String NRIC, int age, String maritalStatus) {
        this.name = name;
        this.NRIC = NRIC;
        this.age = age;
        this.maritalStatus = maritalStatus;
    }

    /**
     * Constructs a {@code UserAccount} with the given user details and specified
     * password.
     *
     * @param name          The user's full name.
     * @param NRIC          The user's NRIC.
     * @param age           The user's age.
     * @param maritalStatus The user's marital status.
     * @param password      The user's password.
     */
    public UserAccount(String name, String NRIC, int age, String maritalStatus, String password) {
        this.name = name;
        this.NRIC = NRIC;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }

    /**
     * Returns the user's name.
     *
     * @return the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the user's NRIC.
     *
     * @return the NRIC.
     */
    public String getNRIC() {
        return this.NRIC;
    }

    /**
     * Returns the user's age.
     *
     * @return the age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the user's marital status.
     *
     * @return the marital status.
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Returns the user's password.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Changes the user's password to the specified value.
     *
     * @param password the new password.
     */
    public void changePassword(String password) {
        this.password = password;
    }

    /**
     * Returns whether the user is currently logged in.
     *
     * @return {@code true} if logged in, {@code false} otherwise.
     */
    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    /**
     * Attempts to log in the user using the specified password.
     *
     * @param p the password entered.
     * @return {@code true} if login is successful, {@code false} otherwise.
     */
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

    /**
     * Logs the user out and prints a confirmation message.
     */
    public void logout() {
        this.loggedIn = false;
        System.out.println("You have logged out.");
    }
}
