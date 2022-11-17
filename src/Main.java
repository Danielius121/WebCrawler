import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {


        String url = "https://en.wikipedia.org/wiki/Elon_Musk";
        Crawler crawler = new Crawler();
        crawler.crawl(url);

    }

}