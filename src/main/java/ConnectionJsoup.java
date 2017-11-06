import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ConnectionJsoup {
    public String urls;

    public ConnectionJsoup(String urls) {
        this.urls = urls;

    }

    public Document connectionJsoup() throws IOException {

        return Jsoup.connect(urls).get();
    }
}