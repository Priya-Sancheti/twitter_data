/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twitter;

/**
 *
 * @author LENOVO
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.io.InputStreamReader;
import java.util.List;
import javax.net.ssl.SSLEngineResult.Status;
import twitter4j.*;
import twitter4j.api.TweetsResources;
import twitter4j.conf.ConfigurationBuilder;

public class retweet {
    
     public static void main(String[] args) throws TwitterException,InterruptedException  {
        // TODO code application logic here
       

      try {
        
           FileInputStream in = new FileInputStream("C:/Users/LENOVO/Documents/NetBeansProjects/Twitter/src/twitter/inp.txt");
           BufferedReader br=new BufferedReader(new InputStreamReader(in));
           String line=null;
           while((line=br.readLine())!=null)
           {
               System.out.println(line); 
          
        ConfigurationBuilder cb = new ConfigurationBuilder();
           cb.setDebugEnabled(true)
               .setOAuthConsumerKey("18tG6Z8UwoOmceIDhALTD8UHQ")
               .setOAuthConsumerSecret("KbKHeVKXl3ItcEI4afTLp08h7HKcjhNSDcQpJ10ti4ijcMkKlN")
               .setOAuthAccessToken("4788856057-1cPr4YTgC5wU8H1UsppjG8vVH5eh3EJjsR51WmC")
               .setOAuthAccessTokenSecret("yITRVUuoQWydursccAgAs3Grf60D3w1EJObZpnTABDZCo");
       
           TwitterFactory tf = new TwitterFactory(cb.build());
           twitter4j.Twitter twitter = tf.getInstance();
           ResponseList<twitter4j.Status> status = twitter.getUserTimeline(line);
           
           for(twitter4j.Status st : status)
           {
              // System.out.println(st.getUser().getLocation()+st.getUser().getRetweets());
              System.out.println(st.getId()+"---"+st.getUser().getLocation());
              
               //IDs ids=twitter.getRetweeterIds(st.getId(),-1);
               //System.out.println("helo");
              
              
               System.out.println(st.getGeoLocation()+"---"+st.getRetweetCount() +"---"+st.getUser().getName()+" ---"+st.getText()+"------"+st.isRetweet()+"---"+st.getGeoLocation());
              System.out.println(st.getId());
               ResponseList<twitter4j.Status> rstatus = twitter.getRetweets(st.getId());
              
               for(twitter4j.Status rst : rstatus)
               {
                   System.out.println(rst.getId()+"--"+rst.getUser().getName()+"--"+rst.getUser().getLocation()+"---"+rst.getGeoLocation());
               }
               Thread.sleep(10000);
           }
         }
           
       
        
      }catch(Exception e){System.out.println(e.getMessage());}  
         
    }
    
}
