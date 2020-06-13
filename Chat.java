
public class Chat {
	private List<ChatMessage> messages = new List<ChatMessage>();

	/**
	 * Method to add a Message to Chat
	 * @param msg
	 */
	public void addMessage(ChatMessage msg) {
		messages.append(msg);
	}


	/**
	 * Method that returns a List of all Chatmessages
	 * @return the messages
	 */
	public List<ChatMessage> getMessages() {
		messages.toFirst();
		return messages;
	}
	
	

}
