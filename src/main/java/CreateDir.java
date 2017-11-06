import java.io.File;

public class CreateDir {
    public  CreateDir(){
        File theDir = new File("data");
        if (!theDir.exists()) {
            try {
                theDir.mkdir();
            } catch (SecurityException se) {
            }
        }
    }
}
