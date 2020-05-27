package faceGram_Server;

public class Profil {
	
	private String username;
	private String hashedPass;
	private String name;
	private String lastname;
	private String coordinates;
	private List<Profil> friendlist;
	
	public Profil(String username, String password, String name, String lastname, String coordinates) {
		friendlist = new List<Profil>();
		this.setUsername(username);
		this.setName(name);
		this.setLastname(lastname);
		this.setCoordinates(coordinates);
	}
	
	public boolean testPassword(String passwort) {
		return true;
	}
	
	public boolean addFriend(Profil profil) {
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
	
	public boolean deleteFriend(Profil profil) {
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

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public List<Profil> getFriendlist() {
		return friendlist;
	}
}
