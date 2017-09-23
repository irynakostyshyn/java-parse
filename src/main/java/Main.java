import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class Main {
    public void main(String[] args) throws IOException {


        File theDir = new File("data");
        if (!theDir.exists()) {
            try{
                theDir.mkdir();
            }
            catch(SecurityException se){
            }
        }

        String urlp = "https://bt.rozetka.com.ua/ua/air_conditioners/c80133/";
        Document doc = Jsoup.connect(urlp).get();

        Elements nums = doc.select("a.paginator-catalog-l-link");
        int nums1 = Integer.getInteger(String.valueOf(nums.get(-1)));
        for (int i = nums1; i > 0; i--) {
            String pg = urlp + String.format("page=%s/", i);
            System.out.println(pg);
            parseCategoryPage(pg);
        }

    }

    public void parseCategoryPage(String url){
        String htmlDoc = url;
        try {
            Document docs = Jsoup.connect(htmlDoc).get();
            Elements tiles = docs.select("div.g-i-tile-i-title");
            for (Element tile :tiles){
                Elements link = tile.select("a[href]");
                parseReviews(link+"comments/");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void parseReviews(String url) throws IOException {
        String htmlDoc = url;
        Document doc = Jsoup.connect(htmlDoc).get();

        Elements nums = doc.select("a.paginator-catalog-l-link");

    }

    public void parseReviewsPage(String url) throws IOException {
        String htmlDoc = url;
        Document doc = Jsoup.connect(htmlDoc).get();

        Elements nums = doc.select("article.pp-review-i");
    }


}

