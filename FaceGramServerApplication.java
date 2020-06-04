
import java.util.HashMap;


public class FaceGramServerApplication {
	HashMap<String, Profile> profileID = new HashMap<String, Profile>();
	ProfileModel profiles;
	Hashing hashing = new Hashing();
	FaceGramServer fgs;
	private int port;


	public FaceGramServerApplication(int port, String userdata) {
		this.port = port;
		profiles = new ProfileModel(userdata);
		fgs = new FaceGramServer(port, this);
		System.out.println("test2");
	}
	

	public void login(String username, String password, String id) {
		System.out.println(username + " " + password);
		if (profiles.exists(username) && profiles.get(username).testPassword(password)) {
			profileID.put(id, profiles.get(username));
			System.out.println(username + " " + password);
			fgs.answerLogin(true, id.split(":")[0], Integer.parseInt(id.split(":")[1]), username);
		} else {
			fgs.answerLogin(false, id.split(":")[0], Integer.parseInt(id.split(":")[1]), username);
		}

	}

	public void register(String name, String lastname, String coordinates, String username, String password,
			String id) {
		System.out.println("Register : " + username);
		if (!profiles.exists(username)) {
			Profile profile = new Profile(username, hashing.generateStrongPasswordHash(password), name, lastname,
					new Coordinates(coordinates));
			System.out.println(name + " " + lastname + " " + coordinates + " " + username + " " + password);
			profiles.add(profile);
			profileID.put(id, profile);
			fgs.answerRegister(true, id.split(":")[0], Integer.parseInt(id.split(":")[1]), username);
		} else {
			fgs.answerRegister(false, id.split(":")[0], Integer.parseInt(id.split(":")[1]), username);
		}

	}

	public void addID(String id) {
		//profileID.put(id, null);

	}

	public void removeID(String id) {
		if(profileID.containsKey(id)) {
			fgs.answerLogout(true, profileID.get(id).getUsername(), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}else {
			fgs.answerLogout(false, "", id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
		profileID.remove(id);

	}

	public void friendList(String id) {  
		if (profileID.containsKey(id)) {
			fgs.answerFriendList(true, profileID.get(id).getFriendlist(), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerFriendList(false, null, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}

	}

	public void addFriend(String username, String id) {
		if (profileID.containsKey(id) && profiles.exists(username) && !profileID.get(id).getFriendlist().contains(profiles.get(username))) {
			profiles.addFriend(profileID.get(id).getUsername(), username);
			fgs.answerAddFriend(true, profiles.get(username), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerAddFriend(false, profiles.get(username), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}

	}

	public void deleteFriend(String username, String id) {
		if (profileID.containsKey(id) && profiles.exists(username) && profileID.get(id).getFriendlist().contains(profiles.get(username))) {
			profiles.deleteFriend(profileID.get(id).getUsername(), username);
			fgs.answerDeleteFriend(true, profiles.get(username), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerDeleteFriend(false, profiles.get(username), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
	}

	public void data(String username, String id) {
		if (profileID.containsKey(id) && profiles.exists(username)) {

			fgs.answerData(true, profiles.get(username), profiles.get(username).getCoordinates().getDistance(profileID.get(id).getCoordinates()), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerData(false, null, 0, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
	}

	public void knows(String username, String id) {
		if (profileID.containsKey(id) && profiles.exists(username)) {
			
			fgs.answerKnows(true, profiles.knows(profileID.get(id), profiles.get(username)), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerKnows(false, username, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}

	}

	public void message(String username, String message, String id) {
		// TODO Auto-generated method stub
		if (true) {

			fgs.answerMessage(true, id.split(":")[0], Integer.parseInt(id.split(":")[1]), profileID.get(id));
		} else {
			fgs.answerMessage(false, id.split(":")[0], Integer.parseInt(id.split(":")[1]), profileID.get(id));
		}

	}

	public void chat(String name, String id) {
		// TODO Auto-generated method stub
		if (true) {

			fgs.answerChat(true, id.split(":")[0], Integer.parseInt(id.split(":")[1]), profileID.get(id));
		} else {
			fgs.answerChat(false, id.split(":")[0], Integer.parseInt(id.split(":")[1]), profileID.get(id));
		}

	}




}
