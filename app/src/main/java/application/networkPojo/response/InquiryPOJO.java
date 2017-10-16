package application.networkPojo.response;

import java.util.Date;

/**
 * Created by bhagyar on 9/18/17.
 */

public class InquiryPOJO {

    private String inquiryTitle;
    private String inquiryType;
    private String inquiryRemarks;
    private Date inquiryCreatedDate;
    private Date inquiryUpdatedDate;
    private Date inquiryDeletedDate;
    private int inquiryStatus;

    public String getInquiryTitle() {
        return inquiryTitle;
    }

    public void setInquiryTitle(String inquiryTitle) {
        this.inquiryTitle = inquiryTitle;
    }

    public String getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(String inquiryType) {
        this.inquiryType = inquiryType;
    }

    public String getInquiryRemarks() {
        return inquiryRemarks;
    }

    public void setInquiryRemarks(String inquiryRemarks) {
        this.inquiryRemarks = inquiryRemarks;
    }

    public Date getInquiryCreatedDate() {
        return inquiryCreatedDate;
    }

    public void setInquiryCreatedDate(Date inquiryCreatedDate) {
        this.inquiryCreatedDate = inquiryCreatedDate;
    }

    public Date getInquiryUpdatedDate() {
        return inquiryUpdatedDate;
    }

    public void setInquiryUpdatedDate(Date inquiryUpdatedDate) {
        this.inquiryUpdatedDate = inquiryUpdatedDate;
    }

    public Date getInquiryDeletedDate() {
        return inquiryDeletedDate;
    }

    public void setInquiryDeletedDate(Date inquiryDeletedDate) {
        this.inquiryDeletedDate = inquiryDeletedDate;
    }

    public int getInquiryStatus() {
        return inquiryStatus;
    }

    public void setInquiryStatus(int inquiryStatus) {
        this.inquiryStatus = inquiryStatus;
    }
}
