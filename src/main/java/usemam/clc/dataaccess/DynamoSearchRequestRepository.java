package usemam.clc.dataaccess;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import usemam.clc.model.SearchRequest;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class DynamoSearchRequestRepository implements SearchRequestRepository {

    private static final Logger log =
            Logger.getLogger(DynamoSearchRequestRepository.class);

    private static final DynamoDBMapper mapper = createMapper(Regions.US_EAST_2);

    public List<SearchRequest> findRequestsByUser(String userEmail) {
        DynamoDBQueryExpression<SearchRequest> query = new DynamoDBQueryExpression<>();
        SearchRequest requestKey = new SearchRequest();
        requestKey.userEmail = userEmail;
        query.setHashKeyValues(requestKey);
        return mapper.query(SearchRequest.class, query);
    }

    public Optional<SearchRequest> findRequestByIdAndEmail(String searchId, String userEmail) {
        SearchRequest request = mapper.load(SearchRequest.class, userEmail, searchId);
        return Optional.ofNullable(request);
    }

    public void saveOrUpdateRequest(SearchRequest request) {
        mapper.save(request);
    }

    public void deleteRequest(String searchId, String userEmail) {
        Optional<SearchRequest> requestOption = findRequestByIdAndEmail(searchId, userEmail);
        if (requestOption.isPresent()) {
            mapper.delete(requestOption.get());
        }
        else {
            String errorMessage = String.format(
                    "Couldn't delete search request [searchId=%s][userEmail=%s]", searchId, userEmail);
            log.error(errorMessage);
            throw new IllegalArgumentException("Delete failed for nonexistent search request");
        }
    }

    private static DynamoDBMapper createMapper(Regions regions) {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(Region.getRegion(regions));
        return new DynamoDBMapper(client);
    }
}
