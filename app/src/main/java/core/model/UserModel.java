package core.model;

/**
 * Created by Bhagya Rathnayake on 9/21/17.
 * Email: rathnayake.bhagya94@gmail.com
 * Contact No.: +94 778170779
 * Company: Orange IT Solution(PVT.) LTD
 * Project Name: olead-app
 */

public class UserModel {
    private String userName;
    private String emailAddress;
    private String password;
    private String confirmedPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }
}
