import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Parser{
    public String url;
    public Parser(String urlp){
        url = urlp;
    }

    public  void parseCategory() {


        try {
            Document doc = Jsoup.connect(url).get();

            Element nums = doc.select("a.paginator-catalog-l-link").last();
            int nums1 = Integer.parseInt(nums.ownText());
            for (int i = nums1; i > 0; i--) {
                String pg = url + String.format("page=%s/", i);
                System.out.println(pg);
                parseCategoryPage(pg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private void parseCategoryPage(String urls){
        String htmlDoc = urls;
        try {
            Document docs = Jsoup.connect(htmlDoc).get();
            Elements tiles = docs.select("div.g-i-tile-i-title");
            for (Element tile :tiles){
                parseReviews(tile.select("a").attr("href")+"comments/");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseReviews(String urls) throws IOException {
        String htmlDoc = urls;

        Document doc = Jsoup.connect(htmlDoc).get();
        //System.out.println(doc.select("a.paginator-catalog-l-link").size());
        Element nums = doc.select("a.paginator-catalog-l-link").last();
        int num = 0;
        if(doc.select("a.paginator-catalog-l-link").size() != 0){
            num = Integer.parseInt(nums.ownText());
        }


        Object[] sentiments = new Object[num +1];
        int k = 0;
        for(int i=1; i<=num; i++){
            String pg = url + String.format("page=%s/", i);
            sentiments[k]= parseReviewsPage(pg);
            k+= 1;


        }
        String[] parts =url.split("/");
        String filename = "data/" +  parts[4] + ".csv";
        CSVWriter writercsv = new CSVWriter(new FileWriter(filename), ',');

        //for (Object[] sentiment : sentiments) {
           // writercsv.writeNext(sentiment);
        //}

        writercsv.close();

        System.out.println(sentiments.length + " reviews from " + url );



    }

    public Object[] parseReviewsPage(String url) throws IOException {
        String htmlDoc = url;
        Document doc = Jsoup.connect(htmlDoc).get();

        Elements reviews = doc.select("article.pp-review-i");
        System.out.println(reviews);

        Object[] sentiments = new Object[reviews.size()];
        int k = 0;
        for (Element review :reviews){
            Element star = review.select("span.g-rating-stars-i").first();
            Element text = review.select("dev.pp-review-text").first();
            if(star.hasText()){
                Elements texts = text.select("div.pp-review-text-i");
                String tmp[] = new String[2] ;
                tmp[0]=star.attr("content");
                tmp[1]=texts.first().ownText().replaceAll(" ", "");
                sentiments[k] = tmp ;
                k+=1;
            }

        }

        return sentiments;
    }

}
