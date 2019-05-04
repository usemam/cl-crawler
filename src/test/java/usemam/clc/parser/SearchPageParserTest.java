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
        Ad ad = ads.get(0);
        Assert.assertEquals("2008 Mazda Miata w. Android/Apple", ad.getTitle());
        Assert.assertEquals("$5995", ad.getPrice());
        Assert.assertEquals("(Roseville)", ad.getLocation());
        Assert.assertEquals("https://sacramento.craigslist.org/cto/d/antelope-2008-mazda-miata-android-apple/6879524822.html", ad.getUrl());
    }

    @Test
    public void parseComplexSearchPageTest() throws Exception {
        SearchPageParser parser = new SearchPageParser();
        List<Ad> ads = parser.parse("http://localhost:8080/ComplexSearchPage.html");
        Assert.assertTrue(ads.size() == 13);
        Ad ad = ads.get(4);
        Assert.assertEquals("2016 Mazda Miata Mx-5 Club Edition BBS Brembo LED Lighting", ad.getTitle());
        Assert.assertEquals("$15000", ad.getPrice());
        Assert.assertEquals("(Roseville)", ad.getLocation());
        Assert.assertEquals("https://sacramento.craigslist.org/cto/d/antelope-2016-mazda-miata-mx-5-club/6873546318.html", ad.getUrl());
    }
}
