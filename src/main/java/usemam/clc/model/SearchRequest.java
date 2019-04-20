package usemam.clc.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

@DynamoDBTable(tableName = "SearchRequests")
public class SearchRequest implements Serializable {
    @DynamoDBRangeKey
    public String searchId;

    @DynamoDBHashKey
    public String userEmail;

    @DynamoDBAttribute
    public String url;

    @DynamoDBAttribute
    public boolean isActive;

    @DynamoDBAttribute
    public Date created;

    @DynamoDBAttribute
    public Date updated;

    @DynamoDBAttribute
    public List<Ad> ads;
}
