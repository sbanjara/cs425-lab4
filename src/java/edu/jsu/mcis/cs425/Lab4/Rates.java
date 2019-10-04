package edu.jsu.mcis.cs425.Lab4;

import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Rates {
    
    public static final String RATE_FILENAME = "rates.csv";
    
    public static List<String[]> getRates(String path) {
        
        StringBuilder s = new StringBuilder();
        List<String[]> data = null;
        String line;
        
        try {
            
            /* Open Rates File; Attach BufferedReader */

            BufferedReader reader = new BufferedReader(new FileReader(path));
            
            /* Get File Data */
            
            while((line = reader.readLine()) != null) {
                s.append(line).append('\n');
            }
            
            reader.close();
            
            /* Attach CSVReader; Parse File Data to List */
            
            CSVReader csvreader = new CSVReader(new StringReader(s.toString()));
            data = csvreader.readAll();
            
        }
        catch (Exception e) { System.err.println( e.toString() ); }
        
        /* Return List */
        
        return data;
        
    }
    
    public static String getRatesAsTable(List<String[]> csv) {
        
        StringBuilder s = new StringBuilder();
        String[] row;
        
        try {
            
            /* Create Iterator */
            
            Iterator<String[]> iterator = csv.iterator();
            
            /* Create HTML Table */
            
            s.append("<table>");
            
            while (iterator.hasNext()) {
                
                /* Create Row */
            
                row = iterator.next();
                s.append("<tr>");
                
                for (int i = 0; i < row.length; ++i) {
                    s.append("<td>").append(row[i]).append("</td>");
                }
                
                /* Close Row */
                
                s.append("</tr>");
            
            }
            
            /* Close Table */
            
            s.append("</table>");
            
        }
        catch (Exception e) { System.err.println( e.toString() ); }
        
        /* Return Table */
        
        return (s.toString());
        
    }
    
    public static String getRatesAsJson(List<String[]> csv) {
        
        String results = "";
        String[] row;
        
        try {
            
            /* Create Iterator */
            
            Iterator<String[]> iterator = csv.iterator();
            
            /* Create JSON Containers */
            
            JSONObject json = new JSONObject();
            JSONObject rates = new JSONObject();            
            
            /* 
             * Add rate data to "rates" container and add "date" and "base"
             * values to "json" container.  See the "getRatesAsTable()" method
             * for an example of how to get the CSV data from the list, and
             * don't forget to skip the header row!s
             */
            
            String[] header = iterator.next();
            while( iterator.hasNext() ) {
                
                row = iterator.next();
                rates.put(row[1], Double.parseDouble(row[2]));
                
            }
            
            json.put("rates", rates);
            json.put("base", "USD");
            json.put("date", "2019-09-20");
            
            /* Parse top-level container to a JSON string */
            
            results = JSONValue.toJSONString(json);
            
        }
        catch (Exception e) { System.err.println( e.toString() ); }
        
        /* Return JSON string */
        System.err.println(results);
        return (results.trim());
        
    }
    
    public static String getRateAsJson(String Code) throws NamingException {
        
        Context envContext = null, initContext = null;
        DataSource ds = null;
        Connection conn = null;
        
         try {
            
            envContext = new InitialContext();
            initContext  = (Context)envContext.lookup("java:/comp/env");
            ds = (DataSource)initContext.lookup("jdbc/db_pool");
            conn = ds.getConnection();
            
        }
        
        catch (SQLException e) {}
         
        return "";
    }

}