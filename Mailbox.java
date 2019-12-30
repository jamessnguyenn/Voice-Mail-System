package telephone;

import java.util.ArrayList;
import java.util.Comparator;

/***
 * Class in which users are able to create a Mailbox which will store the
 * messages of the users and up to three greetings
 * 
 * @author James Nguyen
 *
 */
public class Mailbox implements MailboxInterface {
	private int currentGreeting;// the index of the current greeting
	private String passcode;// the passcode of the mailbox
	private ArrayList<String> greetings;// the array to keep the different
	private MessageQueue savedQueue;// Messsage Queue to keep the saved messages
	private MessageQueue newQueue; // Message Queue to keep the new messages

	/***
	 * Constructor which allows users to create a Mailbox based on an entered
	 * passcode, and sets the greeting to a default greeting.
	 * 
	 * @param passcode
	 *            the passcode of the Mailbox that is created
	 * @precondition passcode.matches("^[0-9]*$") && passcode.length() == 4
	 */
	public Mailbox(String passcode) {
		assert passcode.matches("^[0-9]*$") && passcode.length() == 4 : "Passcode must be four digits long";
		this.passcode = passcode;
		this.currentGreeting = 0;
		greetings = new ArrayList<String>();
		greetings.add("Please leave a messsage for the Mailbox.");
		savedQueue = new MessageQueue();
		newQueue = new MessageQueue();
	}

	/***
	 * Method which checks if the new message queue is empty
	 * 
	 * @return true if the new message queue is empty
	 */
	public boolean newMessageisEmpty() {
		return newQueue.isEmpty();
	}

	/***
	 * Method which checks if the saved message queue is empty
	 * 
	 * @return true if the saved message queue
	 */
	public boolean savedMessageisEmpty() {
		return savedQueue.isEmpty();
	}

	@Override
	/**
	 * method which checks the entered passcode
	 * 
	 * @param passcode
	 *            the entered passcode
	 * @return true if the entered passcode matches
	 */
	public boolean checkPasscode(String passcode) {
		return this.passcode.equals(passcode);
	}

	/***
	 * method which add a new greeting to be saved
	 * 
	 * @param greeting
	 *            the new greeting that will be saved
	 * @precondition !greetingIsFull()
	 */
	public void addGreeting(String greeting) {
		assert !greetingIsFull() : "There are already three greetings.";
		greetings.add(greeting);
	}

	@Override
	/***
	 * method which adds the message as a new message to the mailbox
	 * 
	 * @param a
	 *            the message that will be added
	 */
	public void addMessage(Message message) {
		newQueue.addMessage(message);

	}

	/***
	 * method which saves the message to the mailbox
	 * 
	 * @param a
	 *            the message that will be added
	 */
	public void saveMessage(Message message) {
		savedQueue.addMessage(message);
	}

	/***
	 * method which resets the passcode to the default passcode of 0000
	 */
	public void resetPasscode() {
		passcode = "0000";
	}

	@Override
	/***
	 * method which gets the current new message of the mailbox
	 * 
	 * @return the current new message of the mailbox
	 */
	public Message getCurrentMessage() {
		return newQueue.peek();
	}

	@Override
	/***
	 * method which removes the current message of the mailbox
	 * 
	 * @return the current message
	 * 
	 */
	public Message removeCurrentMessage() {
		return newQueue.remove();

	}

	@Override
	/***
	 * method which sets the passcode to the entered passcode
	 * 
	 * @param passcode
	 *            the new passcode
	 * @precondition passcode.matches("^[0-9]*$") && passcode.length() == 4
	 * 
	 */
	public void setPasscode(String passcode) {
		assert passcode.matches("^[0-9]*$") && passcode.length() == 4 : "Passcode must be four digits long";
		this.passcode = passcode;

	}

	/***
	 * method which gets the current greeting from the array that has the saved
	 * greetings
	 * 
	 * @return the current greeting
	 */
	public String getCurrentGreeting() {
		return greetings.get(currentGreeting);
	}

	@Override
	/**
	 * method which returns the number of greetings that are currently saved
	 */
	public int numOfGreetings() {
		return greetings.size();
	}

	/***
	 * method which gets the saved greeting based on index
	 * 
	 * @param i
	 *            the index of the saved greeting
	 * @return the saved greeting at the index
	 * @precondition i>=0 && i<greetings.size()
	 */
	public String getGreeting(int i) {
		assert i < greetings.size() && i >= 0 : "Index is out of bounds";
		return greetings.get(i);
	}

	/***
	 * method which gets the saved message based on the index
	 * 
	 * @param i
	 *            the index of the saved message
	 * @return the saved message at the index
	 * @precondition i<savedQueue.getSize() && i>=0
	 */
	public Message getSavedMessage(int i) {
		assert i < savedQueue.getSize() && i >= 0 : "Index is out of bounds";
		return savedQueue.getMessage(i);
	}

	/***
	 * method which get the new message based on the index
	 * 
	 * @param i
	 *            the index of the new message
	 * @return the new message at the index
	 * @precondition i<newQueue.getSize() && i>=0
	 */
	public Message getNewMessage(int i) {
		assert i < newQueue.getSize() && i >= 0 : "Index is out of bounds";
		return newQueue.getMessage(i);
	}

	/***
	 * method which removes a saved message based on the index
	 * 
	 * @param i
	 *            the index of the saved message
	 * @precondition i<savedQueue.getSize() && i>=0
	 */
	public void removeSavedMessage(int i) {
		assert i < savedQueue.getSize() && i >= 0 : "Index is out of bounds";
		savedQueue.remove(i);
	}

	/***
	 * method which removes a new message based on the index
	 * 
	 * @param i
	 *            the index of the new message
	 * @precondition i<newQueue.getSize() && i>=0
	 */
	public void removeNewMessage(int i) {
		assert i < newQueue.getSize() && i >= 0 : "Index is out of bounds";
		newQueue.remove(i);
	}

	/***
	 * method which returns the size of the savedQueue
	 * 
	 * @return the size of the saved queue
	 */
	public int getSavedSize() {
		return savedQueue.getSize();
	}

	/***
	 * method whihc returns the size of the newQueue
	 * 
	 * @return the size of the new Queue
	 */
	public int getNewSize() {
		return newQueue.getSize();
	}

	@Override
	/***
	 * method which changes the current greeting to a new greeting
	 * 
	 * @param greeting
	 *            the new greeting
	 * @precondition greeting.trim().length() !=0
	 */
	public void changeCurrentGreeting(String greeting) {
		assert greeting.trim().length() != 0;
		greetings.set(currentGreeting, greeting);

	}

	@Override
	/**
	 * method which changes the current greeting to another saved greeting
	 * 
	 * @precondition i>=0 && i<greetings.size()
	 */
	public void chooseCurrentGreeting(int i) {
		assert i >= 0 && i < greetings.size() : "Index is out of bounds";
		currentGreeting = i;

	}

	/***
	 * Method which returns a compartor which allows the Mailbox to compare itself
	 * with other mailboxes based on the number of messages
	 * 
	 * @return comparator to compare the mailboxes based on number of messsages
	 */
	public static Comparator<Mailbox> comparatorByMessage() {
		return new Comparator<Mailbox>() {
			public int compare(Mailbox mailbox1, Mailbox mailbox2) {
				return (new Integer (mailbox1.getNewSize() + mailbox1.getSavedSize()))
						.compareTo(new Integer (mailbox2.getNewSize() + mailbox2.getSavedSize()));
			}
		};
	}

	/***
	 * method which checks if the number of saved greetings is full
	 * 
	 * @return true if the number of greetings is full
	 */
	public boolean greetingIsFull() {
		return greetings.size() == 3;
	}

}
