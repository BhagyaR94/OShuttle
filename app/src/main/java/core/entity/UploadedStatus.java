package core.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by chathuranga on 6/26/2015. SFAAndroid
 * chathunbandara@gmail.com
 */

/*
 * query last list_milage_row_item for get last sync time
  * */

@DatabaseTable(tableName = "UPLOAD_STATUS")
public class UploadedStatus {

    @DatabaseField(generatedId = true,columnName = "ID")
    private Integer downloadStatus_id;

    @DatabaseField(columnName = "CREATED_DATE", dataType = DataType.DATE_STRING, format = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    @DatabaseField(columnName = "TIMESTAMP")
    private String downTimestamp;

    @DatabaseField(columnName = "TYPE")
    private String downloadType;


    public Integer getDownloadStatus_id() {
        return downloadStatus_id;
    }

    public void setDownloadStatus_id(Integer downloadStatus_id) {
        this.downloadStatus_id = downloadStatus_id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDownTimestamp() {
        return downTimestamp;
    }

    public void setDownTimestamp(String downTimestamp) {
        this.downTimestamp = downTimestamp;
    }

    public String getDownloadType() {
        return downloadType;
    }

    public void setDownloadType(String downloadType) {
        this.downloadType = downloadType;
    }
}
