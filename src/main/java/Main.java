import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {


        File theDir = new File("data");
        if (!theDir.exists()) {
            try {
                theDir.mkdir();
            } catch (SecurityException se) {
            }
        }

        Parser parser = new Parser("https://bt.rozetka.com.ua/ua/air_conditioners/c80133/");
        parser.parseCategory();
    }

}

