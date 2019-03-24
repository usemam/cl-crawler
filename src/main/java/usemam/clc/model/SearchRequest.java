package usemam.clc.model;

import java.util.Date;
import java.util.List;

public class SearchRequest {
    public String searchId;
    public String userEmail;
    public String url;
    public boolean isActive;
    public Date created;
    public Date updated;
    public List<Ad> ads;
}
