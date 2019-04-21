package usemam.clc.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class Ad {
    @DynamoDBAttribute
    private String url;

    @DynamoDBAttribute
    private String title;

    @DynamoDBAttribute
    private String location;

    @DynamoDBAttribute
    private int price;

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public int getPrice() {
        return price;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
