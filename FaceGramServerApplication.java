
import java.util.HashMap;
import java.util.Random;


public class FaceGramServerApplication {
	private HashMap<String, Profile> profileID = new HashMap<String, Profile>();
	private ProfileModel profiles;
	private ChatModel chats;
	private Hashing hashing = new Hashing();
	private FaceGramServer fgs;
	private WeatherAPIHandler weatherAPIHandler = new WeatherAPIHandler();
	private Groupchats groupchats = new Groupchats();
	private Random random = new Random();
	NewsAPI newsAPI = new NewsAPI();

	public FaceGramServerApplication(int port, String userdata) {
		profiles = new ProfileModel(userdata + "/profiles");
		chats = new ChatModel(userdata + "/chats");
		fgs = new FaceGramServer(port, this);
	}
	
	/**
	 * Method to login in 
	 * @param username
	 * @param password
	 * @param id
	 */
	public void login(String username, String password, String id) {
		if (profiles.exists(username) && profiles.get(username).testPassword(password) && !username.equals("cc") && !username.equals("cp")) {
			profileID.put(id, profiles.get(username));
			fgs.answerLogin(true, id.split(":")[0], Integer.parseInt(id.split(":")[1]), username);
		} else {
			fgs.answerLogin(false, id.split(":")[0], Integer.parseInt(id.split(":")[1]), username);
		}

	}

	/**
	 * method to register
	 * @param name
	 * @param lastname
	 * @param coordinates
	 * @param username
	 * @param password
	 * @param id
	 */
	public void register(String name, String lastname, String coordinates, String username, String password, String id) {
		if (!username.equals("chatbot") && !profiles.exists(username) && !(name.equals("") || lastname.equals("") || coordinates.equals("") || username.equals("") || password.equals("")) && !(username.substring(0, 2).equals("gc") || username.substring(0, 2).equals("cc"))) {
			Profile profile = new Profile(username, hashing.generateStrongPasswordHash(password), name, lastname, new Coordinates(coordinates), "Hey there! Im using Facegram.");
			profiles.add(profile);
			profileID.put(id, profile);
			fgs.answerRegister(true, id.split(":")[0], Integer.parseInt(id.split(":")[1]), username);
		} else {
			fgs.answerRegister(false, id.split(":")[0], Integer.parseInt(id.split(":")[1]), username);
		}

	}

	/**
	 * methopd to add a ID. will lead to logout of user with same id. Called when new connection is established
	 * @param id
	 */
	public void addID(String id) {
		profileID.remove(id);

	}

	/**
	 * method to remove id. will lead to logout. Called when connection is lost
	 * @param id
	 */
	public void removeID(String id) {
		if(profileID.containsKey(id)) {
			fgs.answerLogout(true, profileID.get(id).getUsername(), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}else {
			fgs.answerLogout(false, "", id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
		profileID.remove(id);

	}

	/**
	 * method to return friendlist over Server to user
	 * @param id
	 */
	public void friendList(String id) {  
		if (profileID.containsKey(id)) {
			fgs.answerFriendList(true, profileID.get(id).getFriendlist(), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerFriendList(false, null, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}

	}

	/**
	 * Method to add a friend
	 * @param username
	 * @param id
	 */
	public void addFriend(String username, String id) {
		if (profileID.containsKey(id) && profiles.exists(username) && !profileID.get(id).getFriendlist().contains(profiles.get(username)) && !profileID.get(id).getUsername().equals(username)) {
			profiles.addFriend(profileID.get(id).getUsername(), username);
			fgs.answerAddFriend(true, profiles.get(username), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerAddFriend(false, profiles.get(username), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}

	}

	/**
	 * method to delete a friend
	 * @param username
	 * @param id
	 */
	public void deleteFriend(String username, String id) {
		if (profileID.containsKey(id) && profiles.exists(username) && profileID.get(id).getFriendlist().contains(profiles.get(username))) {
			profiles.deleteFriend(profileID.get(id).getUsername(), username);
			fgs.answerDeleteFriend(true, profiles.get(username), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerDeleteFriend(false, profiles.get(username), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
	}

	/**
	 * Method to get personal data of specified user
	 * @param username
	 * @param id
	 */
	public void data(String username, String id) {
		if (profileID.containsKey(id) && profiles.exists(username)) {
			fgs.answerData(true, profiles.get(username), profiles.get(username).getCoordinates().getDistance(profileID.get(id).getCoordinates()), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerData(false, null, 0, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
	}

	/**
	 * Method to get knows
	 * @param username
	 * @param id
	 */
	public void knows(String username, String id) {
		if (profileID.containsKey(id) && profiles.exists(username) ) {
			String answer = profiles.knows(profileID.get(id), profiles.get(username));
			if(answer != null)fgs.answerKnows(true, answer, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
			else fgs.answerKnows(false, username, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerKnows(false, username, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}

	}
	/**
	 * method to write a message
	 * @param chatname
	 * @param message
	 * @param id
	 */
	public void message(String chatname, String message, String id) {
		if(message.equals("!coin")) {
			message = "Münze zeigt " + (random.nextBoolean()?"Kopf":"Zahl");
		}else if(message.equals("!w6")) {
			message = "Würfel zeigt " + (int)(random.nextDouble()*6);
		}
		if(profileID.containsKey(id) && chatname.substring(0, 2).equals("gc")) {
			groupchats.addMessage(chatname, profileID.get(id), message);
			fgs.answerMessage(true, chatname, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}else if (profileID.containsKey(id) && profiles.exists(chatname)){
			chats.addMessage(profileID.get(id).getUsername(), chatname, message);
			fgs.answerMessage(true, chatname, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerMessage(false, chatname, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}

	}
	/**
	 * method to get chat chronik
	 * @param chatname
	 * @param id
	 */
	public void chat(String chatname, String id) {
		if(profileID.containsKey(id) && chatname.substring(0, 2).equals("gc")) {
			fgs.answerChat(true, groupchats.getChat(chatname), chatname, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}else if(profileID.containsKey(id) && profiles.exists(chatname)) {
			fgs.answerChat(true, chats.getChat(profileID.get(id).getUsername(), chatname), chatname, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerChat(false, null, chatname, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}

	}

	/**
	 * metzhod to get weather for user 
	 * @param id
	 */
	public void weather(String id) {
		if (profileID.containsKey(id) && weatherAPIHandler.get(profileID.get(id)) != null) {
			fgs.answerWeather(true, weatherAPIHandler.get(profileID.get(id)), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerWeather(false, null, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
	}

	/**
	 * Method to set Status
	 * @param status
	 * @param id
	 */
	public void status(String status, String id) {
		if (profileID.containsKey(id)) {
			profileID.get(id).setStatus(status);
			fgs.answerStatus(true, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerStatus(false, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
		
	}

	/**
	 * method to get Private data of user (only possible if friends)
	 * @param username
	 * @param id
	 */
	public void privateData(String username, String id) {
		if (profileID.containsKey(id) && profiles.exists(username)  && profileID.get(id).getFriendlist().contains(profiles.get(username))) {
			fgs.answerPrivateData(true, username, profiles.get(username), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}else {
			fgs.answerPrivateData(true, username, null, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
		
	}

	/**
	 * method to get active news from tagesschau.de
	 * @param id
	 */
	public void news(String id) {
		if (profileID.containsKey(id) && newsAPI.isValid()) {
			fgs.answerNews(true, newsAPI, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		} else {
			fgs.answerNews(false, null, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
	}

	/**
	 * method to change userdata
	 * @param name
	 * @param lastname
	 * @param oldPassword
	 * @param id
	 */
	public void changeProfile(String name, String lastname, String oldPassword, String id) {
		if (profileID.containsKey(id) && profileID.get(id).testPassword(oldPassword)) {
			profileID.get(id).setName(name);
			profileID.get(id).setLastname(lastname);
			fgs.answerProfile(true, name, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}else {
			fgs.answerProfile(true, name, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
		
	}

	/**
	 * method to change password
	 * @param newPassword
	 * @param oldPassword
	 * @param id
	 */
	public void changePassword(String newPassword, String oldPassword, String id) {
		if (profileID.containsKey(id) && profileID.get(id).changePassword(oldPassword, hashing.generateStrongPasswordHash(newPassword))) {
			fgs.answerPass(true, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}else {
			fgs.answerPass(false, id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
	}

	/** 
	 * method to get own data 
	 * @param id
	 */
	public void owndata(String id) {
		if (profileID.containsKey(id)) {
			fgs.answerOwnData(true, profileID.get(id), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}else {
			fgs.answerOwnData(false, profileID.get(id), id.split(":")[0], Integer.parseInt(id.split(":")[1]));
		}
	}




}
