package usemam.clc.usercomm;

import usemam.clc.model.SearchRequest;

public interface UserNotificationService {

    void SendSearchResult(SearchRequest result);
}
