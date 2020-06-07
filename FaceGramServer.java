import java.io.IOException;

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
		System.out.println(pMessage);
		try {
			int splitterPostition = pMessage.indexOf(":");
			
			String firstPart = splitterPostition >0?pMessage.substring(0,splitterPostition):pMessage;
			String rest = splitterPostition >0?pMessage.substring(splitterPostition+1, pMessage.length()):"";
			if(firstPart.contains(";") || rest.contains(";")) {
				firstPart = "";
				rest = "";
			}
			switch (firstPart) {
				case "Login":
					if(rest.split(":").length == 2)login(rest, ip + ":" + port);
					else this.send(ip, port, "Failed:" + firstPart + ":" + rest);
					break;
				case "Logout":
					if(rest.equals(""))processClosingConnection(ip, port);
					else this.send(ip, port, "Failed:" + firstPart + ":" + rest);
					break;
				case "Register":
					if(rest.split(":").length == 5)register(rest, ip + ":" + port);
					else this.send(ip, port, "Failed:" + firstPart + ":" + rest);
					break;
				case "Friendlist":
					if(rest.equals(""))friendList(ip + ":" + port);
					else this.send(ip, port, "Failed:" + firstPart + ":" + rest);
					break;
				case "AddFriend":
					if(rest.split(":").length == 1)addFriend(rest, ip + ":" + port);
					else this.send(ip, port, "Failed:" + firstPart + ":" + rest);
					break;
				case "DelFriend":
					if(rest.split(":").length == 1)deleteFriend(rest, ip + ":" + port);
					else this.send(ip, port, "Failed:" + firstPart + ":" + rest);
					break;
				case "Chat":
					if(rest.split(":").length == 1)chat(rest, ip + ":" + port);
					else this.send(ip, port, "Failed:" + firstPart + ":" + rest);
					break;
				case "Msg":
					if(rest.split(":").length == 2)message(rest, ip + ":" + port);
					else this.send(ip, port, "Failed:" + firstPart + ":" + rest);
					break;
				case "Knows":
					if(rest.split(":").length == 1)knows(rest, ip + ":" + port);
					else this.send(ip, port, "Failed:" + firstPart + ":" + rest);
					break;
				case "Data":
					if(rest.split(":").length == 1)data(rest, ip + ":" + port);
					else this.send(ip, port, "Failed:" + firstPart + ":" + rest);
					break;
				case "Weather":
					if(rest.equals(""))weather(ip + ":" + port);
					else this.send(ip, port, "Failed:" + firstPart + ":" + rest);
					break;
				case "News":
					if(rest.equals(""))news(ip + ":" + port);
					else this.send(ip, port, "Failed:" + firstPart + ":" + rest);
					break;
				case "Setstatus":
					if(rest.split(":").length == 1)status(rest, ip + ":" + port);
					else this.send(ip, port, "Failed:" + firstPart + ":" + rest);
					break;
				case "Privatedata":
					if(rest.split(":").length == 1)privateData(rest, ip + ":" + port);
					else this.send(ip, port, "Failed:" + firstPart + ":" + rest);
					break;
				default:
					send(ip, port, "Error Command not found: " + pMessage);
					break;
			}
		}catch (Exception e) {
			System.out.println("cought Error:" );
			e.printStackTrace();
			send(ip, port, "Error:" + pMessage + ":" + e.getLocalizedMessage());
		}

	}
	
	private void news(String id) {
		application.news(id);
		
	}

	private void privateData(String rest, String id) {
		application.privateData(rest, id);
		
	}

	private void status(String rest, String id) {
		application.status(rest, id);
		
	}

	private void weather(String id) {
		application.weather(id);
		
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
		String message = rest.split(":")[1];
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

	private void friendList(String id) {
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
		this.send(ip, port, (b?"Ok:Friendlist:" + toCSVbyUsername(friendList):"Failed:FriendList")); //TODO: List to String(Username)
		System.out.println(toCSVbyUsername(friendList));
		
	}

	private String toCSVbyUsername(List<Profile> friendList) {
		String returnString = "";
		friendList.toFirst();
		while(friendList.hasAccess()) {
			returnString += friendList.getContent().getUsername() + ",";
			friendList.next();
		}
		if(returnString.length() > 0)return returnString.substring(0,returnString.length()-1);
		return " ";
	}

	public void answerData(boolean b, Profile profile, double distance, String ip, int port) {
		if(b)this.send(ip, port, "Ok:Data:" + 	profile.getUsername() + ":" + 
												profile.getName() + ":" + 
												profile.getLastname() + ":" + 
												profile.getCoordinates().getLatitude() + "," + profile.getCoordinates().getLongitude() + ":" + 
												round("" + distance, 3) );
		else this.send(ip, port, "Failed:Data" + (profile != null? ":" + profile.getUsername():""));
	}

	private String round(String distance, int i) {
		try {
			i = distance.indexOf(".") + i + 1;
			return distance.substring(0,i);
		}catch(Exception e) {
			return distance;
		}
		
	}

	public void answerKnows(boolean b, String knowsList, String ip, int port) {
		if(b && knowsList != null)this.send(ip, port, "Ok:Knows:" + knowsList); 
		else this.send(ip, port, "Failed:Knows:" + knowsList);
	}

	public void answerMessage(boolean b, String receipent, String ip, int port) {
		  if(b)this.send(ip, port, "Ok:Msg:" + receipent); 
		  else this.send(ip, port, "Failed:Msg:" + receipent); 
		
	}

	public void answerChat(boolean b, List<ChatMessage> messages, String chatName, String ip, int port) {
		if(b) {
			if(messages != null) {
				messages.toFirst();
				String toSend = "Ok:Chat:" + chatName + ":";
				while(messages.hasAccess()) {
					toSend += messages.getContent().getAuthor() + "\t" + messages.getContent().getMsg() + "\f";
					messages.next();
				}
				this.send(ip, port, toSend.substring(0, toSend.length()-1));
			}else {
				this.send(ip, port, "Ok:Chat:" + chatName + ":null");
			}
				
			
		}else this.send(ip, port, "Failed:Chat:" + chatName);
		
		
	}

	public void answerAddFriend(boolean b, Profile profile, String ip, int port) {
		if(profile != null) this.send(ip, port, (b?"Ok":"Failed") + ":AddFriend:" + profile.getUsername());
		else this.send(ip, port, "Failed:AddFriend");
		
	}

	public void answerDeleteFriend(boolean b, Profile profile, String ip, int port) {
		if(profile != null) this.send(ip, port, (b?"Ok":"Failed") + ":DelFriend:" + profile.getUsername());
		else this.send(ip, port, "Failed:DelFriend");
	}

	public void answerLogout(boolean b, String username, String ip, int port) {
		if(b)this.send(ip, port, "Ok:Logout:" + username); 
		else this.send(ip, port, "Failed:Logout");
	}

	public void answerWeather(boolean b, WeatherAPI weatherAPI, String ip, int port) {
		if(b)this.send(ip, port, "Ok:Weather:" + weatherAPI.getMain() + ":" + weatherAPI.getDescription() + ":" + weatherAPI.getTempInCelsius() + ":" + weatherAPI.getFeelsLikeTempInCelsius() + ":" + weatherAPI.getPressure() + ":" + weatherAPI.getHumidity() + ":" + weatherAPI.getWindSpeed() + ":" + weatherAPI.getWindDirection() + ":" + weatherAPI.getCloudiness() + ":" + weatherAPI.getWeatherID() + ":" + weatherAPI.getWeatherIcon()); 
		else this.send(ip, port, "Failed:Weather");
	}

	public void answerStatus(boolean b, String ip, int port) {
		if(b)this.send(ip, port, "Ok:Setstatus" ); 
		else this.send(ip, port, "Failed:Setstatus");
	}

	public void answerPrivateData(boolean b, String username, Profile profile, String ip, int port) {
		if(b)this.send(ip, port, "Ok:Privatedata:" + username + ":" + profile.getStatus() ); 
		else this.send(ip, port, "Failed:Privatedata:" + username);
	}

	public void answerNews(boolean b, NewsAPI newsAPI, String ip, int port) {
		if(b && newsAPI != null)
			try {
				this.send(ip, port, "Ok:News:" + newsAPI.getTitle() + ":" + newsAPI.getDescription() + ":" + newsAPI.getPictureLink() + ":" + newsAPI.getLink());
			} catch (IOException e) {
				this.send(ip, port, "Failed:News");
			}
		else this.send(ip, port, "Failed:News");
	}
}
