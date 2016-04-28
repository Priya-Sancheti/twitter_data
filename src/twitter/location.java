package twitter;


import com.sun.xml.internal.bind.v2.util.FlattenIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author LENOVO
 */
public class location {
    public String getCountry(String loc) throws JSONException{
        JSONParser jsonParser=new JSONParser();
        String url_create_product = "https://maps.googleapis.com/maps/api/geocode/json";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("address",loc));
            params.add(new BasicNameValuePair("key","AIzaSyDu2ViqMXoKDhA_f0qaJlyqRj172R53JYw"));
             String country="";
           
            JSONObject json = jsonParser.makeHttpRequest(url_create_product, "GET", params);
            //System.out.println(json);
            String  status=(String) json.get("status");
            if(!status.equals("ZERO_RESULTS")){
            JSONArray j=(JSONArray)json.get("results");
            if(j.length()!= 0)
            {
            JSONObject jobject=(JSONObject)j.get(0);
            JSONArray address=(JSONArray)jobject.get("address_components");
            System.out.println(address);
            for(int i=0;i<address.length();i++)
            {
                JSONObject jobj=address.getJSONObject(i);
               System.out.println(jobj);
                JSONArray jarray=(JSONArray) jobj.get("types");
                if(jarray.length() != 0)
                {
               System.out.println(jarray.get(0));
               System.out.println(jobj.get("long_name"));
                if(jarray.get(0).equals("premise"))
                {
                country="";
                break;
                }
                if(jarray.get(0).equals("country"))
                {
                country=(String) jobj.get("long_name");
                break;
                }
                }
                 
            }
            System.out.println(country);
            }
            }
            
            return country;
     
    }
    
}
