package application.entity;

/**
 * Created by chathuranga on 6/18/2015.
 */

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "USERS")
public class User {

    @DatabaseField(generatedId = true, columnName = "ID")
    private Integer user_id;

    @DatabaseField(columnName = "USER_ID")
    private Integer userId;

    @DatabaseField(columnName = "USER_CODE")
    private String userCode;

    @DatabaseField(columnName = "USER_NAME")
    private String userName;

    @DatabaseField(columnName = "USER_LAST_NAME")
    private String lastName;

    @DatabaseField(columnName = "USER_FIRST_NAME")
    private String firstName;

    @DatabaseField(columnName = "USER_PASSWORD")
    private String password;

    @DatabaseField(columnName = "USER_TP")
    private String telephoneNo;

    @DatabaseField(columnName = "USER_TOKEN")
    private String token;

    @DatabaseField(columnName = "USER_ADDRESS")
    private String userAddress;

    @DatabaseField(columnName = "USER_MOBILE")
    private String mobileNo;

    @DatabaseField(columnName = "CREATED_DATE", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    @DatabaseField(columnName = "USER_ACTIVE")
    private Integer userActive;

    @DatabaseField(columnName = "USER_STATUS")
    private Integer userStatus = 1;

    @DatabaseField(columnName = "USER_SHORT_CODE")
    private String shortCode;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public String getUserToken() {
        return token;
    }

    public void setUserToken(String userToken) {
        this.token = userToken;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getUserActive() {
        return userActive;
    }

    public void setUserActive(Integer userActive) {
        this.userActive = userActive;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
}