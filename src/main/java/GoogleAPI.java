

// Imports the Google Cloud client library
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.util.LinkedList;
import java.util.Queue;

public class GoogleAPI {
    String srcLang;
    public GoogleAPI(String srcLang) {
        this.srcLang = srcLang;
    }

    public Queue<String> getTranslations(Queue<String> wordsList) {
        Queue<String> rv = new LinkedList<>();
        for(String block : wordsList){
            rv.add(translation(block));
        }
        return rv;
    }

    public String translation(String srcTxt){
        // Instantiates a client
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        // Translates some text into Russian
        Translation translation =
                translate.translate(
                        srcTxt,
                        TranslateOption.sourceLanguage(this.srcLang),
                        TranslateOption.targetLanguage("de"));


        return translation.getTranslatedText();
    }
}
