import java.io.IOException;
import org.json.JSONObject;


public class WeatherAPI {
	private String apiKey = "a4bd4fab9acede655267268cf4b742d7";
	private String apiUrl;
	private String response;
	private String tempInCelsius, feelsLikeTempInCelsius, pressure, humidity, windSpeed, windDirection, cloudiness, main, description, weatherID, weatherIcon;
	private long timeOfCreation;
	
	public WeatherAPI(double lat, double lon) throws IOException{
		apiUrl = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey + "&lang=" + "de" + "&units=metric";

		response = HTMLGET.getHTML(apiUrl);

		JSONObject apiResponseJSON = new JSONObject(response);
		tempInCelsius = 			apiResponseJSON.getJSONObject("main").getDouble("temp") + "";
		feelsLikeTempInCelsius = 	apiResponseJSON.getJSONObject("main").getDouble("feels_like") + "";
		pressure = 					apiResponseJSON.getJSONObject("main").getInt("pressure") + "";
		humidity = 					apiResponseJSON.getJSONObject("main").getInt("humidity") + "";
		
		windSpeed = 	apiResponseJSON.getJSONObject("wind").getDouble("speed") + "";
		windDirection = apiResponseJSON.getJSONObject("wind").getInt("deg") + "";
		
		cloudiness = apiResponseJSON.getJSONObject("clouds").getInt("all") + "";
		
		main = 			apiResponseJSON.getJSONArray("weather").getJSONObject(0).getString("main") + "";
		description = 	apiResponseJSON.getJSONArray("weather").getJSONObject(0).getString("description") + "";
		weatherID = 	apiResponseJSON.getJSONArray("weather").getJSONObject(0).getInt("id") + "";
		weatherIcon =	apiResponseJSON.getJSONArray("weather").getJSONObject(0).getString("icon") + "";
		timeOfCreation = System.currentTimeMillis() / 1000L;;
	}
	
	
	 
	 
	/**
	 * Returns Temperature in Clesius
	 * @return Temperature in Celsius
	 */
	public String getTempInCelsius() {
		return tempInCelsius;
	}
	
	/**
	 * Returns feels like Temperature in Clesius
	 * @return feels like temperature in Celsius
	 */
	public String getFeelsLikeTempInCelsius() {
		return feelsLikeTempInCelsius;
	}
	/**
	 * Returns atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
	 * @return Atmospheric pressure (on the sea level, if there is no sea_level or grnd_level data), hPa
	 */
	public String getPressure() {
		return pressure;
	}
	
	/**
	 * Returns humidity in %
	 * @return Humidity in %
	 */
	public String getHumidity() {
		return humidity;
	}
	
	/**
	 * Returns wind speed. Unit Default: meter/sec
	 * @return Wind speed. Unit Default: meter/sec
	 */
	public String getWindSpeed() {
		return windSpeed;
	}
	
	/**
	 * Returns wind direction, degrees (meteorological)
	 * @return Wind direction, degrees (meteorological)
	 */
	public String getWindDirection() {
		return windDirection;
	}
	/**
	 * Returns cloudiness, %
	 * @return Cloudiness, %
	 */
	public String getCloudiness() {
		return cloudiness;
	}
	
	/**
	 * Returns group of weather parameters (Rain, Snow, Extreme etc.)
	 * @return Group of weather parameters (Rain, Snow, Extreme etc.)
	 */
	public String getMain() {
		return main;
	}
	
	/**
	 * Returns Weather condition within the group in German
	 * @return Weather condition within the group in German
	 */
	public String getDescription() {
		return description;
	}
	
	public String getWeatherID() {
		return weatherID;
	}

	/**
	 * @return the weatherIcon
	 */
	public String getWeatherIcon() {
		return weatherIcon;
	}


	public long timeOfCreation() {
		return timeOfCreation ;
	}

}
