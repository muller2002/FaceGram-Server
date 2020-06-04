public class Profile {
	
	private String username;
	private String hashedPass;
	private String name;
	private String lastname;
	private Coordinates coordinates;
	private List<Profile> friendlist;
	
	public Profile(String username, String hashedPass, String name, String lastname, Coordinates coordinates) {
		friendlist = new List<Profile>();
		this.setHashedPass(hashedPass);
		this.setUsername(username);
		this.setName(name);
		this.setLastname(lastname);
		this.setCoordinates(coordinates);
	}
	
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

	public String getHashedPass() {
		return hashedPass;
	}

	public void setHashedPass(String hashedPass) {
		this.hashedPass = hashedPass;
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
}