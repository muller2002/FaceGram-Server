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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

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
	
	
	public boolean changePassword(String oldPassword, String newHashedPassword) {
		if(testPassword(oldPassword)) {
			hashedPass = newHashedPassword;
			return true;
		}else return false;
	}
}