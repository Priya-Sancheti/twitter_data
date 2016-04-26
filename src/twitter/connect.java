package twitter;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.sql.Connection;
import java.sql.Statement;
 
 
public class connect {
 
    /**
     * Java Program to connect to remote database through SSH using port forwarding
     * @author Pankaj@JournalDev
     * @throws SQLException 
     */
    Connection conn = null;
        Session session1= null;
        
    public Connection db_connect() throws SQLException {
 
        int lport=3303;
        String rhost="127.0.0.1";
        String host="ec2-52-77-226-177.ap-southeast-1.compute.amazonaws.com";
        int rport=3306;
        String user="ubuntu";
        String password="root";
        String dbuserName = "root";
        String dbpassword = "root";
        String url = "jdbc:mysql://localhost:"+lport+"/twitter_data";
        String driverName="com.mysql.jdbc.Driver";
        Statement stmt=null;
       
        try{
            //Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
            java.util.Properties config = new java.util.Properties(); 
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            session1=jsch.getSession(user, host, 22);
            session1.setPassword(password);
            jsch.addIdentity("F:/gis/sample.pem");
            session1.setConfig(config);
            session1.connect();
            System.out.println("Connected");
            //mysql database connectivity
            session1.setPortForwardingL(lport,rhost,rport);
               

                //mysql database connectivity
                Class.forName(driverName).newInstance();
                conn = DriverManager.getConnection(url, dbuserName, dbpassword);
               }catch(Exception e){
            e.printStackTrace();
        }
        return conn;
    }
    public void conn_close() throws SQLException
    {
        if(conn != null && !conn.isClosed()){
        //System.out.println("Closing Database Connection");
        conn.close();
        }
if(session1 !=null && session1.isConnected()){
       //System.out.println("Closing SSH Connection");
       session1.disconnect();
}
    }
 
}