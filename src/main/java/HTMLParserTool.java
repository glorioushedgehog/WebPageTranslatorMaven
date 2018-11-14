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

    String getBaseUrl() {
        StringBuilder baseURL = new StringBuilder();
        if (url.startsWith("https://")) {
            baseURL.append("https://");
            for (int i = 8; i < url.length(); i++) {
                if (url.substring(i, i + 1).equals("/")) {
                    break;
                }
                baseURL.append(url, i, i + 1);
            }
        } else {
            for (int i = 0; i < url.length(); i++) {
                if (url.substring(i, i + 1).equals("/")) {
                    break;
                }
                baseURL.append(url, i, i + 1);
            }
        }
        baseURL.append("/");
        return String.valueOf(baseURL);
    }

    String getSourceWithFullLinks(){
        String baseURL = getBaseUrl();
        StringBuilder sourceWithFullLinks = new StringBuilder();
        int i = 0;
        while (i < sourceCode.length() - 5) {
            if (sourceCode.substring(i, i + 6).equals("href=\"")) {
                sourceWithFullLinks.append(sourceCode, i, i + 6);
                sourceWithFullLinks.append(baseURL);
                i += 5;
            } else if (sourceCode.substring(i, i + 5).equals("src=\"")) {
                sourceWithFullLinks.append(sourceCode, i, i + 5);
                sourceWithFullLinks.append(baseURL);
                i += 4;
            } else {
                sourceWithFullLinks.append(sourceCode, i, i + 1);
            }
            i++;
        }
        return String.valueOf(sourceWithFullLinks);
    }

    public void convert() throws Exception {
        String sourceWithFullLinks = getSourceWithFullLinks();
        GoogleAPI googleAPI = new GoogleAPI(this.targetLang);
        PrintWriter writer = new PrintWriter(destFile);
        int ind = 1;
        StringBuilder toTrans = new StringBuilder();
        StringBuilder code = new StringBuilder("<");
        int state = 1;
        while (ind < sourceWithFullLinks.length()) {
            char current = sourceWithFullLinks.charAt(ind);
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
