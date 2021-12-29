import java.util.ArrayList;
import java.util.Date;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;









public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 System.out.println("hello world!");
		// long list[] = {
	        //     8535241,
	        //     8815611,
	        //     665531,
	        //     670423,
	        //     3321068,
	        //     4835657,
	        //     5346159,
	        //     5351112,
	        //     5351112,
	        //     6285100,
	        //     6425190,
	        //     6425217,
	        //     6895706,
	        //     7526287,
	        //     7526300,
	        //     7526338,
	            
	        // };
	        // for(int i=0;i<list.length;i++){
	        //     System.out.println("list["+i+"]:"+list[i]+" = "+generateTime(list[i]));
	        // }
	        
	        String sysTime[] ={
	            "1640533293638",
	            "1640534538880",
	            "1640536480884",
	            "1640537104969",
	            "1640537810558",
	            "1640538711849",
	            "1640538714145",
	            "1640539595826"
	        };
	         for(int i=0;i<sysTime.length;i++){
	             Long lo = Long.parseLong(sysTime[i]);
	             Date date = new Date(lo);
	            System.out.println("ts:"+sysTime[i]+" = "+Utile.formatDate(date));
	        }
	         JSONArray jsonArray =  loadFile();
	         checkData(jsonArray);
	    }
	    
	
		public static void checkData(JSONArray jsonArray) {
			  int size = jsonArray.length();
		
			  System.out.println("JSONArray size:"+size);
			  long oldSid = -1;
			  WatchData watchData = new WatchData();
			  ArrayList<WatchData> arrayList = new ArrayList<>();
			  for(int i=0;i<size;i++) {
				  try {
					  JSONObject jsObject = jsonArray.getJSONObject(i);
						 // System.out.println("["+i+"]JSONArray jsObject:"+object.toString());
						  if(jsObject!=null) {
							  Long sid = Long.parseLong(jsObject.optString("sid"));
							  Long ts = Long.parseLong(jsObject.optString("ts"));
							  String buf = jsObject.optString("buf");
							  
							  if(sid != oldSid) {
								 
								  if(oldSid!=-1)
									  arrayList.add(watchData);
								  oldSid = sid;
								  System.out.println("["+i+"]JSONArray oldSid="+oldSid+" sid:"+sid+"="+Utile.formatDate(new Date(sid))+" ts:"+ts+"="+Utile.formatDate(new Date(ts))+" buf:"+buf);
								  watchData = new WatchData();
								  watchData.sid = sid;
								  watchData.ts = ts;
								  watchData.startTime = Utile.formatDate(new Date(sid));
										 
							  }
							  if(watchData!=null) {
								  watchData.sid = sid;
								  watchData.ts = ts;
								  watchData.endTime = Utile.formatDate(new Date(ts));
								  if(buf.contains("0;0")||buf.contains(";;")){
									  //System.out.println("["+i+"]JSONArray jsObject:"+jsObject);
									  watchData.bufferZeroList.add(jsObject);
								  }else if(jsObject.has("buf")){
									  watchData.otherCount++;
								  }
							  }
						  }
				  }catch (JSONException e) {
					// TODO: handle exception
					 //System.out.println("["+i+"]JSONArray JSONException");
				}
			  }
			  arrayList.add(watchData);
			  int aSize = arrayList.size();
			  System.out.println("arrayList:"+aSize);
			  for(int i=0;i<aSize;i++) {
				  System.out.println("\n\n["+i+"]");
				  WatchData watch = arrayList.get(i);
				  System.out.println("sid :"+watch.sid+ ",ts:"+watch.ts);
				  System.out.println("開始播放時間 :"+watch.startTime+ " ~ 停止播放時間:"+watch.endTime);
				  ArrayList<JSONObject> bufferList = watch.bufferZeroList;
				  for(int j=0;j<bufferList.size();j++) {
					  JSONObject jsObject = bufferList.get(j);
					  if(jsObject!=null) {
						  Long ts = Long.parseLong(jsObject.optString("ts"));
						  String buf = jsObject.optString("buf");
						  System.out.println("buffer 99 發生時間 :"+Utile.formatDate(new Date(ts))+ ",buf:"+"\""+buf+"\"");
					  }
				  }
				  System.out.println("其他buffer 數量 :"+watch.otherCount);
			  }
		}
	    
	    
	    public static JSONArray loadFile() {
	    	File file = new File("/Users/i_hfuhsu/Desktop/cj.log");
			String loadString = Utile.loadTextFile(file);
			loadString = "["+loadString+"]";
			//System.out.println("JSONArray loadString:"+loadString);
			try {
				JSONArray modFamilyJSONArray = new JSONArray(loadString);
				
				  return modFamilyJSONArray;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
//			System.out.println("loadString:\n"+loadString);
		}
	    
	    

}
