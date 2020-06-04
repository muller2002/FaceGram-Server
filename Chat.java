
public class Chat {
	private List<ChatMessage> messages = new List<ChatMessage>();
	
	public Chat() {

	}

	
	public void addMessage(ChatMessage msg) {
		messages.append(msg);
	}


	/**
	 * @return the messages
	 */
	public List<ChatMessage> getMessages() {
		return messages;
	}
	
	

}
