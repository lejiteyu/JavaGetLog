import java.util.ArrayList;

import org.json.JSONObject;

public class WatchData {
	public long sid =-1;
	public long ts =-1;
	public String startTime = "";
	public String endTime = "";
	public ArrayList<JSONObject> bufferZeroList = new ArrayList<>();
	public int otherCount =0;
}
