
public class Coordinates {
	/**
	 * 
	 * @param coordinates in Format: ±ddd.ddddd,±ddd.ddddd meaning Lat=46.235197° Long=8.015445°
	 */
	private double latitude;
	private double longitude;
	public Coordinates(String coordinates) {
		String slatitude = coordinates.split(",")[0];
		String slongitude = coordinates.split(",")[1];
		latitude = Double.parseDouble(slatitude);
		longitude = Double.parseDouble(slongitude);
		
	}
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getDistance(Coordinates coordinates) {
	 	int radius = 6371;

	 	double lat1 = latitude;
	 	double lon1 = longitude;
	 	double lat2 = coordinates.getLatitude();
	 	double lon2 = coordinates.getLongitude();
		 	
		 	
		double lat = Math.toRadians(lat2 - lat1);
		double lon = Math.toRadians(lon2- lon1);

		double a = Math.sin(lat / 2) * Math.sin(lat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lon / 2) * Math.sin(lon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = radius * c;

		return Math.abs(d);
	}

}
