import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.awt.*;
import java.net.URI;

public class HTMLParserTool {

    private String url;
    private String tempFile;
    private String destFile;
    private String sourceCode;
    private String targetLang;

    public HTMLParserTool(String url,
                          String tempFile,
                          String destFile,
                          String targetLang) {
        this.url = url;
        this.tempFile = tempFile;
        this.destFile = destFile;
        this.targetLang = targetLang;
    }

    public void convert() throws Exception {
        GoogleAPI googleAPI = new GoogleAPI(this.targetLang);
        PrintWriter writer = new PrintWriter(destFile);
        int ind = 1;
        StringBuilder toTrans = new StringBuilder();
        StringBuilder code = new StringBuilder("<");
        int state = 1;
        while (ind < sourceCode.length()) {
            char current = sourceCode.charAt(ind);
            if (current == '<') {
                String textToTranslate = toTrans.toString();
                if (!textToTranslate.trim().equals("")) {
                    String translatedString = googleAPI.translation(textToTranslate);
                    code.append(translatedString);
                }
                code.append("\n<");
                toTrans = new StringBuilder();
                state++;
            } else if (current == '>') {
                code.append(current);
                state--;
            } else {
                if (state == 0) {
                    toTrans.append(current);
                } else {
                    code.append(current);
                }
            }
            ind++;
        }
        writer.print(code.toString());
        writer.close();
    }

    public void run() throws Exception {
        FileExtractor extractor = new FileExtractor(tempFile);
        SourceCodeFetcher fetcher = new SourceCodeFetcher(url, tempFile);

        fetcher.saveSourceCodeToFile();
        sourceCode = extractor.readFile(tempFile);
        convert();
    }

    public static void main(String[] args) throws Exception {
        new HTMLParserTool("https://projecteuler.net/archives", "temp", "new4.html", "en").run();
    }
}
