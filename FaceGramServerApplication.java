
import java.util.HashMap;

public class FaceGramServerApplication {
	HashMap<String, Profile> profileID = new HashMap<String, Profile>();
	HashMap<String, Profile> profiles = new HashMap<String, Profile>();
	Hashing hashing = new Hashing();
	FaceGramServer fgs;
	private int port;

	public FaceGramServerApplication(int port, String userdata) {
		this.port = port;
		fgs = new FaceGramServer(port, this);
		System.out.println("test2");
	}

	public void login(String username, String password, String id) {
		System.out.println(username + " " + password);
		if (profiles.containsKey(username) && profiles.get(username).testPassword(password)) {
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
		if (!profiles.containsKey(username)) {
			Profile profile = new Profile(username, hashing.generateStorngPasswordHash(password), name, lastname,
					coordinates);
			System.out.println(name + " " + lastname + " " + coordinates + " " + username + " " + password);
			profiles.put(username, profile);
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
		profileID.remove(id);

	}

	public void friendList(String id) {
		if (profileID.containsKey(id)) {
			fgs.answerFriendList(true, profileID.get(id).getFriendlist(), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerFriendList(false, null, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}

	}

	public void addFriend(String name, String id) {
		if (profileID.containsKey(id) && profiles.containsKey(name) && !profileID.get(id).getFriendlist().contains(profiles.get(name))) {
			profiles.get(profileID.get(id).getUsername()).addFriend(profiles.get(name));//Adding friend to Logged in User
			profiles.get(name).addFriend(profiles.get(profileID.get(id).getUsername()));//Adding loged in user to friend
			fgs.answerAddFriend(true, profiles.get(name), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerAddFriend(false, profiles.get(name), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}

	}

	public void deleteFriend(String name, String id) {
		if (profileID.containsKey(id) && profiles.containsKey(name) && profileID.get(id).getFriendlist().contains(profiles.get(name))) {
			profiles.get(profileID.get(id).getUsername()).deleteFriend(profiles.get(name));//Adding friend to Logged in User
			profiles.get(name).deleteFriend(profiles.get(profileID.get(id).getUsername()));//Adding loged in user to friend
			fgs.answerDeleteFriend(true, profiles.get(name), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerDeleteFriend(false, profiles.get(name), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
	}

	public void data(String username, String id) {
		if (true) {

			fgs.answerData(true, null, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerData(false, null, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
	}

	public void knows(String username, String id) {
		// TODO Auto-generated method stub
		if (true) {

			fgs.answerKnows(true, id.split(":")[0], Integer.parseInt(id.split(":")[1]), profileID.get(id));
		} else {
			fgs.answerKnows(false, id.split(":")[0], Integer.parseInt(id.split(":")[1]), profileID.get(id));
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
