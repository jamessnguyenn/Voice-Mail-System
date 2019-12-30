package telephone;

/***
 * Mailbox interface creates the requirements for a Mailbox if a user would like
 * to create a Mailbox for any type of voice mail system.
 * 
 * @author James Nguyen
 *
 */
public interface MailboxInterface {
	/***
	 * method which adds a message to the mailbox
	 * 
	 * @param a
	 *            the message that will be added
	 */
	public void addMessage(Message a);

	/***
	 * method which changes the current greeting to a new greeting
	 * 
	 * @param greeting
	 *            the new greeting
	 * @precondition greeting.trim().length() !=0
	 */
	public void changeCurrentGreeting(String greeting);

	/**
	 * method which changes the current greeting to another saved greeting
	 * 
	 * @precondition i>=0 && i<greetings.size()
	 */
	public void chooseCurrentGreeting(int i);

	/**
	 * method which checks the entered passcode
	 * 
	 * @param passcode
	 *            the entered passcode
	 * @return true if the entered passcode matches
	 */
	public boolean checkPasscode(String passcode);

	/***
	 * method which gets the current new message of the mailbox
	 * 
	 * @return the current new message of the mailbox
	 */
	public Message getCurrentMessage();

	/***
	 * method which removes the current message of the mailbox
	 * 
	 * @return the current message
	 * 
	 */
	public Message removeCurrentMessage();

	/***
	 * method which sets the passcode to the entered passcode
	 * 
	 * @param passcode
	 *            the new passcode
	 * @precondition passcode.matches("^[0-9]*$") && passcode.length() == 4
	 * 
	 */
	public void setPasscode(String passcode);

	/***
	 * method finds the number of greetings
	 * 
	 * @return the number of greetings
	 */
	public int numOfGreetings();

}
