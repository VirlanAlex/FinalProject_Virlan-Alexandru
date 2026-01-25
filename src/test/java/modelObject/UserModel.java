package modelObject;

public class UserModel {

    private final String email;
    private final String password;

    private final String currentPassword;
    private final String newPassword;

    // LOGIN
    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
        this.currentPassword = null;
        this.newPassword = null;
    }

    // CHANGE PASSWORD
    public UserModel(String email,
                     String password,
                     String currentPassword,
                     String newPassword) {
        this.email = email;
        this.password = password;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
