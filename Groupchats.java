import java.util.HashMap;

public class Groupchats {

	private HashMap<String, Chat> chats = new HashMap<String, Chat>();

	public void addMessage(String chatname, Profile profile, String message) {
		if(chats.containsKey(chatname)) {
			chats.get(chatname).addMessage(new ChatMessage(message, 0, profile.getUsername()));
		}else {
			chats.put(chatname, new Chat());
			chats.get(chatname).addMessage(new ChatMessage(message, 0, profile.getUsername()));
		}
		
	}
	
	public List<ChatMessage> getChat(String chatname) {
		if(chats.containsKey(chatname)) { // Users have a chat
			return chats.get(chatname).getMessages();
		} else {
			return null;
		}
	}

}
