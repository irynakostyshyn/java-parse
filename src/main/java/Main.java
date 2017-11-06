import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {


        CreateDir dir = new CreateDir();

        Parser parser = new Parser("https://bt.rozetka.com.ua/ua/air_conditioners/c80133/");
        parser.parseCategory();
    }

}

