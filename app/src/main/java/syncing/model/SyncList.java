package syncing.model;

/**
 * Created by Chathun on 7/6/2016.
 */
public class SyncList {
        /* Result Pack
        *
        *  Object[0] - Type of element (name)
        *  Object[1] - success of fail (1/0)
        *  Object[2] - data insert count
        *  Object[3] - data updated count
        *
        * */

    private String typeOfElement;
    private String successOrFail;
    private String dataInsertCount;
    private String dataUpdatedCount;
    private String dataPreview;
    private String totalCount;

    public String getTypeOfElement() {
        return typeOfElement;
    }

    public void setTypeOfElement(String typeOfElement) {
        this.typeOfElement = typeOfElement;
    }

    public String getSuccessOrFail() {
        return successOrFail;
    }

    public void setSuccessOrFail(String successOrFail) {
        this.successOrFail = successOrFail;
    }

    public String getDataInsertCount() {
        return dataInsertCount;
    }

    public void setDataInsertCount(String dataInsertCount) {
        this.dataInsertCount = dataInsertCount;
    }

    public String getDataUpdatedCount() {
        return dataUpdatedCount;
    }

    public void setDataUpdatedCount(String dataUpdatedCount) {
        this.dataUpdatedCount = dataUpdatedCount;
    }

    public String getDataPreview() {
        return dataPreview;
    }

    public void setDataPreview(String dataPreview) {
        this.dataPreview = dataPreview;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }
}
