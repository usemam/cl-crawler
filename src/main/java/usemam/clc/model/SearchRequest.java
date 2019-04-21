package usemam.clc.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

@DynamoDBTable(tableName = "SearchRequests")
public class SearchRequest implements Serializable {
    @DynamoDBRangeKey
    private String searchId;

    @DynamoDBHashKey
    private String userEmail;

    @DynamoDBAttribute
    private String url;

    @DynamoDBAttribute
    private boolean active;

    @DynamoDBAttribute
    private Date created;

    @DynamoDBAttribute
    private Date updated;

    @DynamoDBAttribute
    private List<Ad> ads;

    public String getSearchId() {
        return searchId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUrl() {
        return url;
    }

    public boolean getActive() {
        return active;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public List<Ad> getAds(){
        return ads;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }
}
