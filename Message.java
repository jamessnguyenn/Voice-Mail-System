package telephone;

import java.util.Comparator;

/***
 * Class which allows users to create a message that can be stored into a
 * mailbox
 * 
 * @author James Nguyen
 *
 */
public class Message {
	private final String text; // the text of the message

	/***
	 * constructor which creates a message with a specific text
	 * 
	 * @param text
	 *            the text of the message
	 * @precondition text.trim().length !=0
	 */
	public Message(String text) {
		assert text.trim().length() !=0: "The text is empty";
		this.text = text;
	}
	/***
	 * method which returns the text of the message
	 * @return
	 */
	public String getText() {
		return text;
	}
	/***
	 * Method which returns a comparator which allows the message to compare itself
	 * with other messages based on the text
	 * 
	 * @return comparator to compare the mailboxes based on the messsages
	 */
	public static Comparator<Message> comparatorByMessage() {
		return new Comparator<Message>() {
			public int compare(Message message1, Message message2) {
				return message1.getText().compareTo(message2.getText());
			}
		};
	}
}
