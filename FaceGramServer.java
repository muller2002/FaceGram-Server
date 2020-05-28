
public class FaceGramServer extends Server {
	FaceGramServerApplication application;

	public FaceGramServer(int port, FaceGramServerApplication application) {
		super(port);
		this.application = application;
	}

	@Override
	public void processNewConnection(String ip, int port) {

		application.addID(ip + ":" + port);

	}

	@Override
	public void processMessage(String ip, int port, String pMessage) {

		int splitterPostition = pMessage.indexOf(":");
		try {
		String firstPart = splitterPostition >0?pMessage.substring(0,splitterPostition):pMessage;
		String rest = splitterPostition >0?pMessage.substring(splitterPostition+1, pMessage.length()):"";
		
			switch (firstPart) {
				case "Login":
					login(rest, ip + ":" + port);
					break;
				case "Logout":
					processClosingConnection(ip, port);
					break;
				case "Register":
					register(rest, ip + ":" + port);
					break;
				case "Friendlist":
					friendList(rest, ip + ":" + port);
					break;
				case "AddFriend":
					addFriend(rest, ip + ":" + port);
					break;
				case "DelFriend":
					deleteFriend(rest, ip + ":" + port);
					break;
				case "Chat":
					chat(rest, ip + ":" + port);
					break;
				case "Msg":
					message(rest, ip + ":" + port);
					break;
				case "Knows":
					knows(rest, ip + ":" + port);
					break;
				case "Data":
					data(rest, ip + ":" + port);
					break;
				default:
					send(ip, port, "Error Command not found: " + pMessage);
					break;
			}
		}catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			send(ip, port, "Error on: " + pMessage + " " + e.getLocalizedMessage());
		}catch (StringIndexOutOfBoundsException e) {
			send(ip, port, "Error on: " + pMessage + " " + e.getLocalizedMessage());
			e.printStackTrace();
		}

	}
	
	private void login(String message, String id) {
		String[] splitMessage = message.split(":");
		application.login(splitMessage[0], splitMessage[1], id);
	}

	private void register(String message, String id) {
		String[] splitMessage = message.split(":");
		application.register(splitMessage[0], splitMessage[1], splitMessage[2], splitMessage[3], splitMessage[4], id);
	}
	

	private void data(String name, String id) {
		application.data(name, id);
		
	}

	private void knows(String name, String id) {
		application.knows(name, id);
		
	}

	private void message(String rest, String id) {
		String username = rest.split(":")[0];
		String message = rest.split(":")[0];
		application.message(username, message, id);
		
	}

	private void chat(String name, String id) {
		application.chat(name, id);
		
	}

	private void addFriend(String name, String id) {
		application.addFriend(name, id);
		
	}

	private void deleteFriend(String name, String id) {
		application.deleteFriend(name, id);
		
	}

	private void friendList(String rest, String id) {
		application.friendList(id);
		
	}

	

	
	@Override
	public void processClosingConnection(String ip, int port) {
		application.removeID(ip + ":" + port);

	}

	public void answerRegister(boolean b, String ip, int port, String username) {
		this.send(ip, port, (b?"Ok":"Failed") + ":Register:" + username);
		
	}

	public void answerLogin(boolean b, String ip, int port, String username) {
		this.send(ip, port, (b?"Ok":"Failed") + ":Login:" + username);
		
	}

	public void answerFriendList(boolean b, List<Profile> friendList, String ip, int port) {
		this.send(ip, port, (b?"Ok:FriendList:" + toCSVbyUsername(friendList):"Failed:FriendList")); //TODO: List to String(Username)
		
	}

	private String toCSVbyUsername(List<Profile> friendList) {
		String returnString = "";
		friendList.toFirst();
		while(friendList.hasAccess()) {
			returnString += friendList.getContent().getUsername() + ",";
			friendList.next();
		}
		if(returnString.length() > 0)return returnString.substring(0,returnString.length()-1);
		return returnString;
	}

	public void answerData(boolean b, Profile profile, String ip, int port) {
		this.send(ip, port, (b?"Ok:Data:" + profile.getUsername() + ":" + profile.getName() + ":" + profile.getLastname() + ":" + profile.getCoordinates() :"Failed:Data") ); //TODO: List to String(Username)
		
	}

	public void answerKnows(boolean b, String ip, int port, Profile profile) {
		// TODO Auto-generated method stub
		
	}

	  void answerMessage(boolean b, String ip, int port, Profile profile) {
		// TODO Auto-generated method stub
		
	}

	public void answerChat(boolean b, String ip, int port, Profile profile) {
		// TODO Auto-generated method stub
		
	}

	public void answerAddFriend(boolean b, Profile profile, String ip, int port) {
		if(profile != null) this.send(ip, port, (b?"Ok":"Failed") + ":AddFriend:" + profile.getUsername());
		else this.send(ip, port, "Failed:AddFriend");
		
	}

	public void answerDeleteFriend(boolean b, Profile profile, String ip, int port) {
		if(profile != null) this.send(ip, port, (b?"Ok":"Failed") + ":DelFriend:" + profile.getUsername());
		else this.send(ip, port, "Failed:DelFriend");
	}
}
