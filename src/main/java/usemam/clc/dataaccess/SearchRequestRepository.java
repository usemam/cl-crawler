package usemam.clc.dataaccess;

import usemam.clc.model.SearchRequest;

import java.util.List;
import java.util.Optional;

public interface SearchRequestRepository {

    List<SearchRequest> findRequestsByUser(String userEmail);

    Optional<SearchRequest> findRequestByIdAndEmail(String searchId, String userEmail);

    void saveOrUpdateRequest(SearchRequest request);

    void deleteRequest(String searchId, String userEmail);
}
