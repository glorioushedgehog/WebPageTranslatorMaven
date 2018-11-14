

// Imports the Google Cloud client library
import com.google.cloud.translate.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GoogleAPI {
    String targetLang;
    public GoogleAPI(String targetLang) {
        this.targetLang = targetLang;
    }

    public Queue<String> getTranslations(Queue<String> wordsList) {
        Queue<String> rv = new LinkedList<>();
        for(String block : wordsList){
            System.out.println(block);
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
                        TranslateOption.sourceLanguage("en"),
                        TranslateOption.targetLanguage(this.targetLang));


        return translation.getTranslatedText();
    }
    public static void main(String[] args){
        Translate translate = TranslateOptions.getDefaultInstance().getService();
        List<Language> languages = translate.listSupportedLanguages();

        for (Language language : languages) {
            System.out.printf("Name: %s, Code: %s\n", language.getName(), language.getCode());
        }
    }
}
