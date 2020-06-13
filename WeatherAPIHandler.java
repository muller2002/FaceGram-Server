import java.io.IOException;
import java.util.HashMap;

public class WeatherAPIHandler {
	HashMap<Profile, WeatherAPI> weathers = new HashMap<Profile, WeatherAPI>();

	/**
	 * Returns a WeatherAPI.object containing the Weatherfor the User. Updated every
	 * 15 minutes
	 * 
	 * @param profile
	 * @return
	 */
	public WeatherAPI get(Profile profile) {
		if (weathers.containsKey(profile)
				&& weathers.get(profile).timeOfCreation() > (System.currentTimeMillis() / 1000L) - (15 * 60)) {
			return weathers.get(profile);

		}
		try {
			weathers.put(profile,
					new WeatherAPI(profile.getCoordinates().getLatitude(), profile.getCoordinates().getLatitude()));
			return weathers.get(profile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
