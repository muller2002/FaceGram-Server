import java.io.IOException;
import org.json.JSONObject;

public class NewsAPI {
	private String apiUrl = "https://www.tagesschau.de/api2/";
	JSONObject apiResponseJSON;
	private long lastUpdate;
	private boolean works;
	public NewsAPI() {

		
	}
	
	private void update() {
		if(lastUpdate < (System.currentTimeMillis() / 1000L)-(15*60) || !works) {
			lastUpdate = (System.currentTimeMillis() / 1000L);
			try {
				apiResponseJSON = new JSONObject((String)HTMLGET.getHTML(apiUrl));
				works = true;
			} catch (IOException e) {
				works = false;
			}
		}
	}
	
	public String getTitle() throws IOException {
		update();
		return apiResponseJSON.getJSONArray("news").getJSONObject(0).getString("title");
	}
	
	public String getDescription() throws IOException {
		update();
		return apiResponseJSON.getJSONArray("news").getJSONObject(0).getJSONArray("content").getJSONObject(0).getString("value");
	}
	
	public String getLink() throws IOException {//sophoraId
		update();
		return "www.tagesschau.de/" + apiResponseJSON.getJSONArray("news").getJSONObject(0).getString("sophoraId") + ".html";
	}
	
	public String getPictureLink() throws IOException {
		update();
		return apiResponseJSON.getJSONArray("news").getJSONObject(0).getJSONObject("teaserImage").getJSONObject("videowebl").getString("imageurl").substring(8);
	}
	
	public boolean isValid() {
		update();
		return works;
	}
}
