public class Profile {
	
	private String username;
	private String hashedPass;
	private String name;
	private String lastname;
	private String status;
	private Coordinates coordinates;
	private List<Profile> friendlist;
	
	/**
	 * 
	 * @param username
	 * @param hashedPass the hashed Password. hashing with class Hashing.java
	 * @param name
	 * @param lastname
	 * @param coordinates
	 * @param status
	 */
	public Profile(String username, String hashedPass, String name, String lastname, Coordinates coordinates, String status) {
		friendlist = new List<Profile>();
		this.hashedPass = hashedPass;
		this.setUsername(username);
		this.setName(name);
		this.setLastname(lastname);
		this.setCoordinates(coordinates);
		this.setStatus(status);
		
	}
	/**
	 * Method to test if the Password is correct
	 * @param password in Plaintext
	 * @return true if Password is correct
	 */
	
	public boolean testPassword(String password) {
		Hashing hashing = new Hashing();
		String toTestHash = hashing.generateStrongPasswordHash(password, hashedPass.split(":")[1]);
		return toTestHash.equals(hashedPass);
	}
	
	/**
	 * Method to add a Friend to the friendlist. Will only add Friends who wasnt add yet
	 * @param profil
	 * @return true if succesful
	 */
	public boolean addFriend(Profile profil) {
		if(profil != null) {
			friendlist.toFirst();
			while(friendlist.hasAccess()) {
				if(friendlist.getContent() == profil) {
					return false;
				}
				friendlist.next();
			}
			friendlist.append(profil);
		}
		return false;
	}
	
	/**
	 * Method to remove a Friend from the friendlist
	 * @param profil
	 * @return true if succesful
	 */
	public boolean deleteFriend(Profile profil) {
		if(profil != null) {
			friendlist.toFirst();
			while(friendlist.hasAccess()) {
				if(friendlist.getContent() == profil) {
					friendlist.remove();
					return true;
				}
				friendlist.next();
			}
			friendlist.append(profil);
		}
		return false;
	}


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * @return the coordinates
	 */
	public Coordinates getCoordinates() {
		return coordinates;
	}
	/**
	 * @param coordinates the coordinates to set
	 */
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	/**
	 * @return the hashedPass
	 */
	public String getHashedPass() {
		return hashedPass;
	}
	
	/**
	 * Method to get the Friendlist type Profile
	 * @return Friendlist<Profile>
	 */
	public List<Profile> getFriendlist() {
		return friendlist;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * Method to Change the Password
	 * @param oldPassword the OldPassword in Plaintext
	 * @param newHashedPassword new Password in Hashed Variant
	 * @return true if succesfull (oldPassword is right), else false
	 */
	public boolean changePassword(String oldPassword, String newHashedPassword) {
		if(testPassword(oldPassword)) {
			hashedPass = newHashedPassword;
			return true;
		}else return false;
	}
}