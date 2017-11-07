
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class Parser extends ParsP{

    public Parser(String urlp){
        this.url = urlp;
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

    void parseCategoryPage(String urls){

        try {
            ConnectionJsoup jsoup = new ConnectionJsoup(urls);

            Document doc = jsoup.connectionJsoup();
            Elements tiles = doc.select("div.g-i-tile-i-title");
            ParseRewiews parseRewiew = new ParseRewiews();
            for (Element tile :tiles){
                parseRewiew.parseReviews(tile.select("a").attr("href")+"comments/");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
