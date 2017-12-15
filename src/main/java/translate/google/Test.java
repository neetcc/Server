package translate.google;

/**
 * Created by ChengCe on 2017/12/14.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        // Set the HTTP referrer to your website address.
        GoogleAPI.setHttpReferrer("");

        // Set the Google Translate API key
        // See: http://code.google.com/apis/language/translate/v2/getting_started.html
        GoogleAPI.setKey("");

        String translatedText = Translate.DEFAULT.execute("Bonjour le monde", Language.FRENCH, Language.ENGLISH);

        System.out.println(translatedText);
    }
}
