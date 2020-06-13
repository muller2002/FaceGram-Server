import java.util.HashMap;

public class ChatModel {
	HashMap<String, HashMap<String, Chat>> chats = new HashMap<String, HashMap<String, Chat>>();
	
	/**
	 * Constructor of Class ChatModel. 
	 * @param data Path to Chatdata to restore after programmstart
	 */
	public ChatModel(String data) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Method to add A Message to a Chat. need to Specifie Source (Author), Destination and Message
	 * @param source Source as String
	 * @param destination Destination as String
	 * @param message Message as String
	 */
	public void addMessage(String source, String destination, String message){
		String prior = (source.compareTo(destination) < 0?source:destination); // Alphebatical First:  Key to outer HashMap
		String minor = (source.compareTo(destination) > 0?source:destination); // Alphebatical Second: Key to inner HashMap
		if(chats.containsKey(prior) && chats.get(prior).containsKey(minor)) { // Users have a chat
			chats.get(prior).get(minor).addMessage(new ChatMessage(message, 0, source)); // add message to chat
		}else if(chats.containsKey(prior)) { // users dont have a Chat. Prior User has Chattet
			chats.get(prior).put(minor, new Chat()); // add a Hashmap<String, Chat> to user who has chattet before (has a hashmap not containing the Hashmap with key minor
			addMessage(source, destination, message);
		}else { // Users dont have a Chat. Prior user doesnt has a chat. Creating both HashMaps
			chats.put(prior, new HashMap<String, Chat>() );
			chats.get(prior).put(minor, new Chat());
			addMessage(source, destination, message);
		}
	}


	/**
	 * Returning the Caht between users. 
	 * @param user1 First User
	 * @param user2 Second User
	 * @return List of Chatmessages. If no chat exists null
	 */
	public List<ChatMessage> getChat(String user1, String user2) {
		String prior = (user1.compareTo(user2) < 0?user1:user2); // Alphebatical First:  Key to outer HashMap
		String minor = (user1.compareTo(user2) > 0?user1:user2); // Alphebatical Second: Key to inner HashMap
		if(chats.containsKey(prior) && chats.get(prior).containsKey(minor)) { // Users have a chat
			return chats.get(prior).get(minor).getMessages();
			
		}
		else return null;
		
	}
}
