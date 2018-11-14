import java.awt.*;
import java.net.URI;

public class TranslateRunner implements Runnable {
    String url;
    String path;
    String targetLang;
    String TEMP_DIR;

    public TranslateRunner(String url, String path, String targetLang){
        this.url = url;
        this.path = path;
        this.targetLang = targetLang;
        TEMP_DIR = "temp";
    }

    @Override
    public void run() {
        try{
            new HTMLParserTool(url, TEMP_DIR, path, targetLang).run();

            if(Desktop.isDesktopSupported()){
                System.out.println(path);
                Desktop.getDesktop().browse(new URI(path));
            }
        } catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }
}