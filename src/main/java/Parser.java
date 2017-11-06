import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

public class Parser extends ParsP{
    public String url;
    public Parser(String urlp){
        url = urlp;
    }

    public  void parseCategory() {


        try {
            ConnectionJsoup jsoup = new ConnectionJsoup(url);

            Document doc = jsoup.connectionJsoup();

            Element nums = doc.select("a.paginator-catalog-l-link").last();
            int nums1 = Integer.parseInt(nums.ownText());
            for (int i = nums1; i > 0; i--) {
                String pg = url + String.format("page=%s/", i);
                //System.out.println(pg);
                parseCategoryPage(pg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private void parseCategoryPage(String urls){

        try {
            ConnectionJsoup jsoup = new ConnectionJsoup(urls);

            Document doc = jsoup.connectionJsoup();
            Elements tiles = doc.select("div.g-i-tile-i-title");
            for (Element tile :tiles){
                parseReviews(tile.select("a").attr("href")+"comments/");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseReviews(String urls) throws IOException {
        ConnectionJsoup jsoup = new ConnectionJsoup(urls);

        Document doc = jsoup.connectionJsoup();

        //System.out.println(urls);
        //System.out.println(doc.select("a.paginator-catalog-l-link").size());
        Element nums = doc.select("a.paginator-catalog-l-link").last();
        int num = 0;
        //System.out.println(doc.select("a.paginator-catalog-l-link").size());
        if(doc.select("a.paginator-catalog-l-link").size() != 0){
            num = Integer.parseInt(nums.ownText());
        }


        ArrayList<String[]> sentiments = new ArrayList<String[]>();
        //System.out.println(num);
        if(num==0){
            sentiments.addAll(parseReviewsPage(urls));
        }
        for(int i=1; i<=num; i++){
            String pg = urls + String.format("page=%s/", i);
            sentiments.addAll(parseReviewsPage(pg));

        }
        if (sentiments.size()!=0) {
            String[] parts = urls.split("/");
            String filename = "data/" + parts[4] + ".csv";
            CSVWriter writercsv = new CSVWriter(new FileWriter(filename), ',');

            for (String[] sentiment : sentiments) {
                // System.out.println(sentiment);
                writercsv.writeNext(sentiment);
            }

            writercsv.close();
        }

       System.out.println(sentiments.size() + " reviews from " + urls );



    }

    public ArrayList<String[]> parseReviewsPage(String urls) throws IOException {

        ConnectionJsoup jsoup = new ConnectionJsoup(urls);

        Document doc = jsoup.connectionJsoup();

        Elements reviews = doc.select("article.pp-review-i");
        // System.out.println(reviews.size());

        ArrayList<String[]> sentiments = new ArrayList<String[]>();

        for (Element review :reviews){
            Elements star = review.select("span.g-rating-stars-i");
            Element text = review.select("div.pp-review-text").first();

            if(star.size()!=0){
                Elements texts = text.select("div.pp-review-text-i");
                sentiments.add(new String[]{star.first().attr("content"), texts.first().html().replaceAll(" ", "")});

            }

        }

        return sentiments;
    }



}
