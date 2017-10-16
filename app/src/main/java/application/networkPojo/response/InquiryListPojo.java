package application.networkPojo.response;


/**
 * Created by tharindumac on 9/19/17.
 */
public class InquiryListPojo {

   private String timestamp;
    private int result;
//    private List<InquiryListModel> inquiries;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

//    public List<InquiryListModel> getInquiries() {
//        return inquiries;
//    }
//
//    public void setInquiries(List<InquiryListModel> inquiries) {
//        this.inquiries = inquiries;
//    }
}
