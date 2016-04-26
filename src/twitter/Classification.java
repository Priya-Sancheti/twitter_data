package twitter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Classification {
    public  String  tweet_classify(String arg) throws IOException
    {
        
        String s = "{\"texts\":[";
        String s1 = "\""+arg+"\"]}";
        byte[] requestData = s.concat(s1).getBytes("UTF-8");
       
        
        URL url = new URL("https://api.uclassify.com/v1/uclassify/topics/classify");
       TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub

        }
    }};

    // Install the all-trusting trust manager
    try {
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    } catch (Exception e) {
        ;
    }
       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Content-Length", Integer.toString(requestData.length));
        conn.setRequestProperty("Authorization", "Token vQJqbQSOgDVg");
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.write(requestData);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        FileWriter f = new FileWriter(new File("rest.json"));
        BufferedWriter bw = new BufferedWriter(f);
       // bw.write(in.readLine());
       // in.read();
        for (int c = in.read(); c != -1; c = in.read()) {
            bw.write((char) c);
        }
      bw.close();
      double max=0;
      String classify="";
       JSONParser parser = new JSONParser();
 
        try {
 
            Object obj = parser.parse(new FileReader(
                    "rest.json"));
 
           // JSONObject jsonObject = (JSONObject) obj;
            JSONArray data = (JSONArray) obj;
             Iterator<JSONObject> iter = data.iterator();
             while(iter.hasNext())
             {
             JSONObject jsonObject =  iter.next();
            Double name = (Double) jsonObject.get("textCoverage");
            JSONArray companyList = (JSONArray) jsonObject.get("classification");
 
           // System.out.println(name);
            //System.out.println("\nCompany List:");
            Iterator<JSONObject> iterator = companyList.iterator();
            while(iterator.hasNext())
            {
              JSONObject jsonObj = (JSONObject) iterator.next();
                //System.out.println(jsonObj.get("className"));
                 Double pp = (Double) jsonObj.get("p");
                //System.out.println(pp);
                if(max<pp)
                {
                  max =pp;
                  classify = jsonObj.get("className").toString();
                }
                  
            }
//                System.out.println(classify+"  "+max);
        } 
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(max > 0.40)
            return classify;
        else
            return "";
    }
    }
