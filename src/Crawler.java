import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Crawler {
    private Map<Integer, String> map = new HashMap<Integer, String>();
    private int currentLevel = 1;
    private ArrayList<String> visited = new ArrayList<>();
    Crawler(){

    }


    public void crawl(String url)
    {   this.map.put(this.currentLevel,url);
        if( this.currentLevel >=1 && this.visited.size() < 10000)
        {
            System.out.println(this.currentLevel);
            Document doc = request(url);
            if(doc==null)
            {   this.visited.add(url);
                this.currentLevel-=1;
                crawl(this.map.get(this.currentLevel));
            }

            String cssQuery = String.format("a:containsOwn(%s)", "Tesla");
            Elements elements = doc.select(cssQuery);
            System.out.println("Tesla count : " + elements.size());
            for(Element link: elements)
                {
                    String next_link = link.absUrl("href");

                    if(this.currentLevel==10){
                        this.visited.add(next_link);
                    }

                    if(!this.visited.contains(next_link) && next_link.length()>0)
                    {   this.currentLevel+=1;
                        crawl(next_link);
                    }
                }
                this.currentLevel-=1;
                crawl(this.map.get(this.currentLevel));
            }

    }
    private Document request(String url)
    {
        try{
            Connection con = Jsoup.connect(url);
            Document doc = con.get();

            if(con.response().statusCode() == 200)
            {
               System.out.println("Link: " + url);

               this.visited.add(url);
                return doc;
            }

            return null;
        }
        catch(IOException e){

            return null;
        }
    }

}
