import java.awt.*;
import java.net.URI;

public class TranslateRunner implements Runnable {
    String url;
    String path;
    String TEMP_DIR;

    public TranslateRunner(String url, String path){
        this.url = url;
        this.path = path;
        TEMP_DIR = "temp";
    }

    @Override
    public void run() {
        try{
            new HTMLParserTool(url, TEMP_DIR, path).run();

            if(Desktop.isDesktopSupported()){
                System.out.println(path);
                Desktop.getDesktop().browse(new URI(path));
            }
        } catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }
}