import java.io.IOException;

public class FaceGramServer extends Server {
	FaceGramServerApplication application;

	/**
	 * Constructor of Class FaceGrammServer (Server)
	 * @param port Port to run the Server on
	 * @param application The Application which data should be send to
	 */
	public FaceGramServer(int port, FaceGramServerApplication application) {
		super(port);
		this.application = application;
	}
	
	
	@Override
	/**
	 * (non-Javadoc) Method used to add a Conection.
	 * @see Server#processNewConnection(java.lang.String, int)
	 */
	public void processNewConnection(String ip, int port) {

		application.addID(ip + ":" + port);

	}
	/**
	 * (non-Javadoc) Method to Process messages. Splits Messages to their Coresponding Methods (No protocolsplitting)
	 * @see Server#processMessage(java.lang.String, int, java.lang.String)
	 */
	@Override
	public void processMessage(String ip, int port, String pMessage) {
		System.out.println(pMessage);
		try {
			int splitterPostition = pMessage.indexOf(":");
			
			String firstPart = splitterPostition >0?pMessage.substring(0,splitterPostition):pMessage; //Command
			String rest = splitterPostition >0?pMessage.substring(splitterPostition+1, pMessage.length()):""; //further Information
			if(firstPart.contains(";") || rest.contains(";")) { // Remove SQL insertion Possibility
				this.send(ip, port, "Fsql");
				firstPart = "";
				rest = "";
				return;
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
				case "Owndata":
					if(rest.equals(""))owndata(ip + ":" + port);
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
				case "Changeprofile":
					if(rest.split(":").length == 3)changeProfile(rest, ip + ":" + port);
					else this.send(ip, port, "Failed:" + firstPart + ":" + rest);
					break;
				case "Changepass":
					if(rest.split(":").length == 2)changePassword(rest, ip + ":" + port);
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
	
	/**
	 * Adapter to Facegramserverapplication: Command owndata
	 * @param id
	 */
	private void owndata(String id) {
		application.owndata(id);
		
	}


	/**
	 * Adapter to Facegramserverapplication: Command changePassword
	 * @param message 
	 * @param id
	 */
	private void changePassword(String rest, String id) {
		String[] splitMessage = rest.split(":");
		application.changePassword(splitMessage[0], splitMessage[1], id);
	}


	/**
	 * Adapter to Facegramserverapplication: Command changeProfile
	 * @param message 
	 * @param id
	 */
	private void changeProfile(String rest, String id) {
		String[] splitMessage = rest.split(":");
		application.changeProfile(splitMessage[0], splitMessage[1], splitMessage[2], id);
	}



	/**
	 * Adapter to Facegramserverapplication: Command news
	 * @param id
	 */
	private void news(String id) {
		application.news(id);
		
	}
	/**
	 * Adapter to Facegramserverapplication: Command privateData
	 * @param message 
	 * @param id
	 */
	private void privateData(String message, String id) {
		application.privateData(message, id);
		
	}

	/**
	 * Adapter to Facegramserverapplication: Command status
	 * @param message
	 * @param id
	 */
	private void status(String message, String id) {
		application.status(message, id);
		
	}

	/**
	 * Adapter to Facegramserverapplication: Command weather
	 * @param id
	 */
	private void weather(String id) {
		application.weather(id);
		
	}
	
	/**
	 * Adapter to Facegramserverapplication: Command login
	 * @param message
	 * @param id
	 */
	private void login(String message, String id) {
		String[] splitMessage = message.split(":");
		application.login(splitMessage[0], splitMessage[1], id);
	}

	/**
	 * Adapter to Facegramserverapplication: Command register
	 * @param message
	 * @param id
	 */
	private void register(String message, String id) {
		String[] splitMessage = message.split(":");
		 
		application.register(splitMessage[0], splitMessage[1], splitMessage[2], splitMessage[3], splitMessage[4], id);
	}
	
	/**
	 * Adapter to Facegramserverapplication: Command data
	 * @param name
	 * @param id
	 */
	private void data(String name, String id) {
		application.data(name, id);
		
	}

	/**
	 * Adapter to Facegramserverapplication: Command knows
	 * @param name
	 * @param id
	 */
	private void knows(String name, String id) {
		application.knows(name, id);
		
	}
 
	/**
	 * Adapter to Facegramserverapplication: Command message
	 * @param rest
	 * @param id
	 */
	private void message(String rest, String id) {
		String username = rest.split(":")[0];
		String message = rest.split(":")[1];
		application.message(username, message, id);
		
	}

	/**
	 * Adapter to Facegramserverapplication: Command chat
	 * @param name
	 * @param id
	 */
	private void chat(String name, String id) {
		application.chat(name, id);
		
	}

	/**
	 * Adapter to Facegramserverapplication: Command addFriend
	 * @param name
	 * @param id
	 */
	private void addFriend(String name, String id) {
		application.addFriend(name, id);
		
	}

	/** 
	 * Adapter to Facegramserverapplication: Command delFriend
	 * @param name
	 * @param id
	 */
	private void deleteFriend(String name, String id) {
		application.deleteFriend(name, id);
		
	}

	/**
	 * Adapter to Facegramserverapplication: Command friendlist
	 * @param id
	 */
	private void friendList(String id) {
		application.friendList(id);
		
	}

	

	/**
	 * (non-Javadoc) Method to close Connection -> will lead to logout
	 * @see Server#processClosingConnection(java.lang.String, int)
	 */
	@Override
	public void processClosingConnection(String ip, int port) {
		application.removeID(ip + ":" + port);

	}

	/**
	 * Method that will answer Register Request
	 * @param b true if succesfull, else false
	 * @param ip
	 * @param port
	 * @param username
	 */
	public void answerRegister(boolean b, String ip, int port, String username) {
		this.send(ip, port, (b?"Ok":"Failed") + ":Register:" + username);
		
	}

	/**
	 * Method that will answer login Request
	 * @param b true if succesfull, else false
	 * @param ip
	 * @param port
	 * @param username
	 */
	public void answerLogin(boolean b, String ip, int port, String username) {
		this.send(ip, port, (b?"Ok":"Failed") + ":Login:" + username);
		
	}

	/**
	 * Method that will answer Friendlist Request
	 * @param b true if succesfull, else false
	 * @param friendList
	 * @param ip
	 * @param port
	 */
	public void answerFriendList(boolean b, List<Profile> friendList, String ip, int port) {
		this.send(ip, port, (b?"Ok:Friendlist:" + toCSVbyUsername(friendList):"Failed:FriendList")); //TODO: List to String(Username)
		System.out.println(toCSVbyUsername(friendList));
		
	}

	/**
	 * Method that will return a List uf usernames with coma as seperator
	 * @param friendList in Type List<Profile>
	 * @return Usernames with "," between
	 */
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

	/**
	 * Method that will answer Data Request
	 * @param b true if succesfull, else false
	 * @param profile
	 * @param distance
	 * @param ip
	 * @param port
	 */
	public void answerData(boolean b, Profile profile, double distance, String ip, int port) {
		if(b)this.send(ip, port, "Ok:Data:" + 	profile.getUsername() + ":" + 
												profile.getName() + ":" + 
												profile.getLastname() + ":" + 
												profile.getCoordinates().getLatitude() + "," + profile.getCoordinates().getLongitude() + ":" + 
												round("" + distance, 3) );
		else this.send(ip, port, "Failed:Data" + (profile != null? ":" + profile.getUsername():""));
	}

	/**
	 * Method to round a number
	 * @param distance number as String
	 * @param i number of decimal places
	 * @return
	 */
	private String round(String distance, int i) {
		try {
			i = distance.indexOf(".") + i + 1;
			return distance.substring(0,i);
		}catch(Exception e) {
			return distance;
		}
		
	}

	/**
	 * Method that will answer knows Request
	 * @param b true if succesfull, else false
	 * @param knowsList
	 * @param ip
	 * @param port
	 */
	public void answerKnows(boolean b, String knowsList, String ip, int port) {
		if(b && knowsList != null)this.send(ip, port, "Ok:Knows:" + knowsList); 
		else this.send(ip, port, "Failed:Knows:" + knowsList);
	}

	/**
	 * Method that will answer message Request
	 * @param b true if succesfull, else false
	 * @param receipent
	 * @param ip
	 * @param port
	 */
	public void answerMessage(boolean b, String receipent, String ip, int port) {
		  if(b)this.send(ip, port, "Ok:Msg:" + receipent); 
		  else this.send(ip, port, "Failed:Msg:" + receipent); 
		
	}

	/**
	 * Method that will answer chat Request
	 * @param b true if succesfull, else false
	 * @param messages
	 * @param chatName
	 * @param ip
	 * @param port
	 */
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

	/**
	 * Method that will answer addFriend Request
	 * @param b true if succesfull, else false
	 * @param profile
	 * @param ip
	 * @param port
	 */
	public void answerAddFriend(boolean b, Profile profile, String ip, int port) {
		if(profile != null) this.send(ip, port, (b?"Ok":"Failed") + ":AddFriend:" + profile.getUsername());
		else this.send(ip, port, "Failed:AddFriend");
		
	}

	/**
	 * Method that will answer delFriend Request
	 * @param b true if succesfull, else false
	 * @param profile
	 * @param ip
	 * @param port
	 */
	public void answerDeleteFriend(boolean b, Profile profile, String ip, int port) {
		if(profile != null) this.send(ip, port, (b?"Ok":"Failed") + ":DelFriend:" + profile.getUsername());
		else this.send(ip, port, "Failed:DelFriend");
	}

	/**
	 * Method that will answer logout Request
	 * @param b true if succesfull, else false
	 * @param username
	 * @param ip
	 * @param port
	 */
	public void answerLogout(boolean b, String username, String ip, int port) {
		if(b)this.send(ip, port, "Ok:Logout:" + username); 
		else this.send(ip, port, "Failed:Logout");
	}
	
	/**
	 * Method that will answer weather Request
	 * @param b true if succesfull, else false
	 * @param weatherAPI
	 * @param ip
	 * @param port
	 */
	public void answerWeather(boolean b, WeatherAPI weatherAPI, String ip, int port) {
		if(b)this.send(ip, port, "Ok:Weather:" + weatherAPI.getMain() + ":" + weatherAPI.getDescription() + ":" + weatherAPI.getTempInCelsius() + ":" + weatherAPI.getFeelsLikeTempInCelsius() + ":" + weatherAPI.getPressure() + ":" + weatherAPI.getHumidity() + ":" + weatherAPI.getWindSpeed() + ":" + weatherAPI.getWindDirection() + ":" + weatherAPI.getCloudiness() + ":" + weatherAPI.getWeatherID() + ":" + weatherAPI.getWeatherIcon()); 
		else this.send(ip, port, "Failed:Weather");
	}

	/**
	 * Method that will answer status Request
	 * @param b true if succesfull, else false
	 * @param ip
	 * @param port
	 */
	public void answerStatus(boolean b, String ip, int port) {
		if(b)this.send(ip, port, "Ok:Setstatus" ); 
		else this.send(ip, port, "Failed:Setstatus");
	}
	
	/**
	 * Method that will answer privateData Request
	 * @param b true if succesfull, else false
	 * @param username
	 * @param profile
	 * @param ip
	 * @param port
	 */
	public void answerPrivateData(boolean b, String username, Profile profile, String ip, int port) {
		if(b)this.send(ip, port, "Ok:Privatedata:" + username + ":" + profile.getStatus() ); 
		else this.send(ip, port, "Failed:Privatedata:" + username);
	}

	/**
	 * Method that will answer News Request
	 * @param b true if succesfull, else false
	 * @param newsAPI
	 * @param ip
	 * @param port
	 */
	public void answerNews(boolean b, NewsAPI newsAPI, String ip, int port) {
		if(b && newsAPI != null)
			try {
				this.send(ip, port, "Ok:News:" + newsAPI.getTitle() + ":" + newsAPI.getDescription() + ":" + newsAPI.getPictureLink() + ":" + newsAPI.getLink());
			} catch (IOException e) {
				this.send(ip, port, "Failed:News");
			}
		else this.send(ip, port, "Failed:News");
	}

	/**
	 * Method that will answer changeProfile request
	 * @param b true if succesfull, else false
	 * @param name
	 * @param ip
	 * @param port
	 */
	public void answerProfile(boolean b, String name, String ip, int port) {
		if(b)this.send(ip, port, "Ok:Changeprofile:" + name); 
		else this.send(ip, port, "Failed:Changeprofile:" + name);
	}

	/**
	 * Method that will answer changePass request
	 * @param b true if succesfull, else false
	 * @param ip
	 * @param port
	 */
	public void answerPass(boolean b, String ip, int port) {
		if(b)this.send(ip, port, "Ok:Changepass"); 
		else this.send(ip, port, "Failed:Changepass");
	}


	public void answerOwnData(boolean b, Profile profile, String ip, int port) {
		if(b)this.send(ip, port, "Ok:Owndata:" + 	profile.getUsername() + ":" + 
													profile.getName() + ":" + 
													profile.getLastname() + ":" + 
													profile.getCoordinates().getLatitude() + "," + profile.getCoordinates().getLongitude() + ":" + 
													profile.getStatus()); 
		else this.send(ip, port, "Failed:Owndata");
	}
}
