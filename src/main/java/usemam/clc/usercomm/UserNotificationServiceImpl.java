package usemam.clc.usercomm;

import usemam.clc.model.SearchRequest;

public class UserNotificationServiceImpl implements UserNotificationService {

    private final MessageBodyComposer<SearchRequest> messageBodyComposer;

    private final Sender sender;

    public UserNotificationServiceImpl(MessageBodyComposer<SearchRequest> composer, Sender sender) {
        this.messageBodyComposer = composer;
        this.sender = sender;
    }

    @Override
    public void SendSearchResult(SearchRequest result) {
    }
}
