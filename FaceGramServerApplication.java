
import java.util.HashMap;


public class FaceGramServerApplication {
	private HashMap<String, Profile> profileID = new HashMap<String, Profile>();
	private ProfileModel profiles;
	private ChatModel chats;
	private Hashing hashing = new Hashing();
	private FaceGramServer fgs;
	private WeatherAPIHandler weatherAPIHandler = new WeatherAPIHandler();
	NewsAPI newsAPI = new NewsAPI();

	public FaceGramServerApplication(int port, String userdata) {
		profiles = new ProfileModel(userdata + "/profiles");
		chats = new ChatModel(userdata + "/chats");
		fgs = new FaceGramServer(port, this);
	}
	

	public void login(String username, String password, String id) {
		if (profiles.exists(username) && profiles.get(username).testPassword(password)) {
			profileID.put(id, profiles.get(username));
			fgs.answerLogin(true, id.split(":")[0], Integer.parseInt(id.split(":")[1]), username);
		} else {
			fgs.answerLogin(false, id.split(":")[0], Integer.parseInt(id.split(":")[1]), username);
		}

	}

	public void register(String name, String lastname, String coordinates, String username, String password, String id) {
		if (!username.equals("chatbot") && !profiles.exists(username) && !(name.equals("") || lastname.equals("") || coordinates.equals("") || username.equals("") || password.equals(""))) {
			Profile profile = new Profile(username, hashing.generateStrongPasswordHash(password), name, lastname, new Coordinates(coordinates), "Hey there! Im using Facegram.");
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
		if (profileID.containsKey(id) && profiles.exists(username) && !profileID.get(id).getFriendlist().contains(profiles.get(username)) && !profileID.get(id).getUsername().equals(username)) {
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
		if (profileID.containsKey(id) && profiles.exists(username) ) {
			String answer = profiles.knows(profileID.get(id), profiles.get(username));
			if(answer != null)fgs.answerKnows(true, answer, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
			else fgs.answerKnows(false, username, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerKnows(false, username, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}

	}

	public void message(String username, String message, String id) {
		// TODO Auto-generated method stub
		if (profileID.containsKey(id) && profiles.exists(username)) {
			chats.addMessage(profileID.get(id).getUsername(), username, message);
			fgs.answerMessage(true, username, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerMessage(false, username, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}

	}

	public void chat(String username, String id) {
		if (profileID.containsKey(id) && profiles.exists(username)) {
			
			fgs.answerChat(true, chats.getChat(profileID.get(id).getUsername(), username), username, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerChat(false, null, username, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}

	}


	public void weather(String id) {
		if (profileID.containsKey(id) && weatherAPIHandler.get(profileID.get(id)) != null) {
			fgs.answerWeather(true, weatherAPIHandler.get(profileID.get(id)), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerWeather(false, null, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
	}


	public void status(String status, String id) {
		if (profileID.containsKey(id)) {
			profileID.get(id).setStatus(status);
			fgs.answerStatus(true, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerStatus(false, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
		
	}


	public void privateData(String username, String id) {
		if (profileID.containsKey(id) && profiles.exists(username)  && profileID.get(id).getFriendlist().contains(profiles.get(username))) {
			fgs.answerPrivateData(true, username, profiles.get(username), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}else {
			fgs.answerPrivateData(true, username, null, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
		
	}


	public void news(String id) {
		if (profileID.containsKey(id) && newsAPI.isValid()) {
			fgs.answerNews(true, newsAPI, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerNews(false, null, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
	}




}
