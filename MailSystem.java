package telephone;

import java.util.ArrayList;
import java.util.Comparator;

/***
 * The class will be used to create a Mail System to hold multiple mailboxes of
 * different users
 * 
 * @author James Nguyen
 *
 */
public class MailSystem {
	private ArrayList<Mailbox> mailboxes;// the array of mailboxes
	private static final String DEFAULT_PASSCODE = "0000";// the default passcode of the mailboxes

	/***
	 * constructor which initializes the mailbox array
	 */
	public MailSystem() {
		mailboxes = new ArrayList<Mailbox>();
	}

	/***
	 * method which will find a mailbox at the specified index
	 * 
	 * @param i
	 *            the index of the mailbox
	 * @return null if there is no mailbox found and the mailbox if its found
	 * @precondition i>0
	 */
	public Mailbox findMailbox(int i) {
		assert i>0: "No Negative Index";
		if (i > mailboxes.size() || (mailboxes.size() == 0 && i == 0)) {
			return null;
		} else {
			return mailboxes.get(i - 1);
		}
	}

	/***
	 * method which will add a new mailbox with a default passcode to the system
	 */
	public void addNewMailbox() {
		Mailbox mailbox = new Mailbox(DEFAULT_PASSCODE);
		mailboxes.add(mailbox);

	}

	/**
	 * method which checks if the mailbox system is empty
	 * 
	 * @return true if the mailbox system is empty
	 */
	public boolean isEmpty() {
		return mailboxes.isEmpty();
	}

	/***
	 * method which finds the last mailbox number of the mail system
	 * 
	 * @return the last mailbox number of the mail system
	 */
	public int getLastMailboxNumber() {
		return mailboxes.size();
	}
	/***
	 * Method which returns a comparator which allows the message to compare itself
	 * with other messages based on the text
	 * 
	 * @return comparator to compare the mailboxes based on the messsages
	 */
	public static Comparator<MailSystem> comparatorByMailboxes() {
		return new Comparator<MailSystem>() {
			public int compare(MailSystem mailSystem1, MailSystem mailSystem2) {
				return ((new Integer(mailSystem1.getLastMailboxNumber())).compareTo(new Integer(mailSystem2.getLastMailboxNumber())));
			}
		};
	}
}
