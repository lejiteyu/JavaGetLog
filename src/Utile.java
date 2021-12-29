import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONObject;

public class Utile {
	public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }
    
    public static String formatDate(Date date){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // HH是24小時制，hh是12小時制
    	sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
    	return sdf.format(date);
    }
    
    public static String loadTextFile(File f) {
   	 int count =0;
       try {
           BufferedReader reader = new BufferedReader(new FileReader(f));
           StringWriter w = new StringWriter();
        
           try {
               String line = reader.readLine();
               while (null != line) {
            	   w.append(line).append(",\n");
                   line = reader.readLine();
                   count++;
               }
               System.out.println("count:"+count+"行");
               return w.toString();
           } finally {
           	reader.close();
               w.close();
           }
       } catch (Exception ex) {
           ex.printStackTrace();
           System.out.println("count:\n"+count);
           return "";
       }
   }
}
