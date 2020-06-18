import java.io.IOException;
import org.json.JSONObject;

public class NewsAPI {
	private String apiUrl = "https://www.tagesschau.de/api2/";
	JSONObject apiResponseJSON;
	private long lastUpdate;
	private boolean works;
	private int num = 1;
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
		return (apiResponseJSON.getJSONArray("news").getJSONObject(num).getString("title")).replace(":", ";");
	}
	
	public String getDescription() throws IOException {
		update();
		String s = (apiResponseJSON.getJSONArray("news").getJSONObject(num).getJSONArray("content").getJSONObject(0).getString("value")).replace(":", ";");
		s = s.replace("\r", "");
		return s.replace("\n", "");
	}
	
	public String getLink() throws IOException {//sophoraId
		update();
		return ("www.tagesschau.de/" + apiResponseJSON.getJSONArray("news").getJSONObject(num).getString("sophoraId") + ".html").replace(":", ";");
	}
	
	public String getPictureLink() throws IOException {
		update();
		try {
			return (apiResponseJSON.getJSONArray("news").getJSONObject(num).getJSONObject("teaserImage").getJSONObject("videowebl").getString("imageurl").substring(8)).replace(":", ";");
		}catch(Exception e) {
			return "www.tagesschau.de/resources/framework/img/tagesschau/banner/logo_base.png";
		}
	}
	
	public boolean isValid() {
		update();
		return works;
	}
}
