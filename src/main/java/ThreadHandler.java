public class ThreadHandler {
    public static void add(String url, String path, String targetLang){
        Runnable r = new TranslateRunner(url, path, targetLang);
        Thread t = new Thread(r);
        t.start();
    }
}
