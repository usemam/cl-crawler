package usemam.clc.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import usemam.clc.model.Ad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchPageParser implements PageParser<List<Ad>> {

    @Override
    public List<Ad> parse(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements items = doc.select("#searchform #sortable-results ul.rows li");
        List<Ad> ads = new ArrayList<>(items.size());
        for (Element item : items) {
            Element titleElement = item.selectFirst("a.result-title");
            Element priceElement = item.selectFirst("span.result-meta span.result-price");
            Element hoodElement = item.selectFirst("span.result-meta span.result-hood");

            Ad ad = new Ad();
            ad.setTitle(titleElement.text());
            ad.setUrl(titleElement.attr("href"));
            ad.setPrice(priceElement.text());
            ad.setLocation(hoodElement.text());

            ads.add(ad);
        }

        return ads;
    }
}
