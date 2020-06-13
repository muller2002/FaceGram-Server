import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class HTMLGET {
	
	/**
	 * HTML GET request from Specified URL
	 * @param urlToRead Destination URL of Get request 
	 * @return Answer of Getrequest
	 * @throws IOException if URL could not get Resolved or no answer is returend from Server
	 */
	public static String getHTML(String urlToRead) throws IOException  {
	      StringBuilder result = new StringBuilder();
	      URL url = new URL(urlToRead);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      try {
			conn.setRequestMethod("GET");
		} catch (ProtocolException e) {}
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      return result.toString();
	   }
}
