public class ThreadHandler {
    public static void add(String url, String path){
        Runnable r = new TranslateRunner(url, path);
        Thread t = new Thread(r);
        t.start();
    }
}
