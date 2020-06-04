import java.util.HashMap;

public class ChatModel {
	HashMap<String, HashMap<String, Chat>> chats = new HashMap<String, HashMap<String, Chat>>();
	
	
	public ChatModel(String string) {
		// TODO Auto-generated constructor stub
	}


	public void addMessage(String source, String destination, String message){
		System.out.println(source + destination + message);
		String prior = (source.compareTo(destination) < 0?source:destination); // Alphebatical First:  Key to outer HashMap
		String minor = (source.compareTo(destination) > 0?source:destination); // Alphebatical Second: Key to inner HashMap
		if(chats.containsKey(prior) && chats.get(prior).containsKey(minor)) { // Users have a chat
			chats.get(prior).get(minor).addMessage(new ChatMessage(message, 0, source));
		}else if(chats.containsKey(prior)) {
			chats.get(prior).put(minor, new Chat());
			addMessage(source, destination, message);
		}else {
			chats.put(prior, new HashMap<String, Chat>() );
			chats.get(prior).put(minor, new Chat());
			addMessage(source, destination, message);
		}
	}


	public List<ChatMessage> getChat(String user1, String user2) {
		String prior = (user1.compareTo(user2) < 0?user1:user2); // Alphebatical First:  Key to outer HashMap
		String minor = (user1.compareTo(user2) > 0?user1:user2); // Alphebatical Second: Key to inner HashMap
		if(chats.containsKey(prior) && chats.get(prior).containsKey(minor)) { // Users have a chat
			return chats.get(prior).get(minor).getMessages();
			
		}
		else return null;
		
	}
}
