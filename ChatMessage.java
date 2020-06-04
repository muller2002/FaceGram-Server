
public class ChatMessage {
	private String msg;
	private long utc;
	private String author;
	



	public ChatMessage(String msg, long utc, String author) {
		this.msg = msg;
		this.utc = utc;
		this.author = author;
	}
	
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @return the utc
	 */
	public long getUtc() {
		return utc;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
}
