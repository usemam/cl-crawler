package usemam.clc.parser;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import usemam.clc.model.Ad;

import java.util.List;

public class SearchPageParserTest {

    @Rule
    public final HttpServer server = new HttpServer(8080);

    @Test
    public void parseSimpleSearchPageTest() throws Exception {
        SearchPageParser parser = new SearchPageParser();
        List<Ad> ads = parser.parse("http://localhost:8080/SimpleSearchPage.html");
        Assert.assertTrue(ads.size() == 1);
    }

    @Test
    public void parseComplexSearchPageTest() throws Exception {
        SearchPageParser parser = new SearchPageParser();
        List<Ad> ads = parser.parse("http://localhost:8080/ComplexSearchPage.html");
        Assert.assertTrue(ads.size() == 13);
    }
}
