package usemam.clc.dataaccess;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

import org.junit.*;

import usemam.clc.model.*;

import java.time.Instant;
import java.util.*;

public class DynamoSearchRequestRepositoryTest {

    private static AmazonDynamoDB amazonDynamoDB;

    private static DynamoDBMapper dynamoDBMapper;

    @ClassRule
    public static LocalDynamoCreationRule localDb = new LocalDynamoCreationRule();

    private DynamoSearchRequestRepository repository;

    @BeforeClass
    public static void startDynamoAndCreateTable() {
        AwsClientBuilder.EndpointConfiguration endpointConfiguration =
            new AwsClientBuilder.EndpointConfiguration("http://localhost:"+ LocalDynamoCreationRule.port, "us-east-2");
        amazonDynamoDB = AmazonDynamoDBClientBuilder
            .standard()
            .withEndpointConfiguration(endpointConfiguration)
            .build();
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest createTableRequest = dynamoDBMapper.generateCreateTableRequest(SearchRequest.class);
        createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
        amazonDynamoDB.createTable(createTableRequest);
    }

    @Before
    public void beforeTest() {
        repository = new DynamoSearchRequestRepository(dynamoDBMapper);
    }

    @After
    public void afterTest() {
        try {
            SearchRequest request = createRequest();
            repository.deleteRequest(request.getSearchId(), request.getUserEmail());
        }
        catch (IllegalArgumentException e) {
            // do nothing - request has already been deleted
        }
    }

    @Test
    public void saveTest() {
        SearchRequest request = createRequest();
        repository.saveOrUpdateRequest(request);
        Optional<SearchRequest> found = repository.findRequestByIdAndEmail(request.getSearchId(), request.getUserEmail());
        Assert.assertTrue("Saved request not found", found.isPresent());
        assertRequestsEqual(request, found.get());
    }

    @Test
    public void updateTest() {
        SearchRequest request = createRequest();
        repository.saveOrUpdateRequest(request);
        request.setActive(false);
        repository.saveOrUpdateRequest(request);
        Optional<SearchRequest> found = repository.findRequestByIdAndEmail(request.getSearchId(), request.getUserEmail());
        assertRequestsEqual(request, found.get());
    }

    @Test
    public void deleteTest() {
        SearchRequest request = createRequest();
        repository.saveOrUpdateRequest(request);
        repository.deleteRequest(request.getSearchId(), request.getUserEmail());
        Optional<SearchRequest> found = repository.findRequestByIdAndEmail(request.getSearchId(), request.getUserEmail());
        Assert.assertFalse("Request has not been deleted", found.isPresent());
    }

    @Test
    public void findByUserTest() {
        SearchRequest request = createRequest();
        repository.saveOrUpdateRequest(request);
        List<SearchRequest> found = repository.findRequestsByUser(request.getUserEmail());
        Assert.assertEquals(1, found.size());
        assertRequestsEqual(request, found.get(0));
    }

    @Test
    public void adsTest() {
        SearchRequest request = createRequest();
        Ad ad = new Ad();
        ad.setUrl("http://localhost");
        ad.setTitle("Very interesting ad");
        ad.setLocation("Folsom, CA");
        ad.setPrice(100);
        List<Ad> ads = new ArrayList<>();
        ads.add(ad);
        request.setAds(ads);
        repository.saveOrUpdateRequest(request);
        Optional<SearchRequest> found = repository.findRequestByIdAndEmail(request.getSearchId(), request.getUserEmail());
        List<Ad> loadedAds = found.get().getAds();
        Assert.assertNotNull("Ads collection is not loaded", loadedAds);
        Assert.assertEquals(1, loadedAds.size());
        Ad loadedAd = loadedAds.get(0);
        Assert.assertEquals(ad.getLocation(), loadedAd.getLocation());
        Assert.assertEquals(ad.getUrl(), loadedAd.getUrl());
        Assert.assertEquals(ad.getTitle(), loadedAd.getTitle());
        Assert.assertEquals(ad.getPrice(), loadedAd.getPrice());
    }

    private void assertRequestsEqual(SearchRequest r1, SearchRequest r2) {
        Assert.assertEquals(r1.getSearchId(), r2.getSearchId());
        Assert.assertEquals(r1.getUserEmail(), r2.getUserEmail());
        Assert.assertEquals(r1.getActive(), r2.getActive());
        Assert.assertEquals(r1.getUrl(), r2.getUrl());
        Assert.assertEquals(r1.getCreated(), r2.getCreated());
        Assert.assertEquals(r1.getUpdated(), r2.getUpdated());
    }

    private SearchRequest createRequest() {
        Date now = Date.from(Instant.now());
        SearchRequest request = new SearchRequest();
        request.setUserEmail("useinm@gmail.com");
        request.setSearchId("1");
        request.setActive(true);
        request.setUrl("https://google.com");
        request.setCreated(now);
        request.setUpdated(now);
        return request;
    }
}
