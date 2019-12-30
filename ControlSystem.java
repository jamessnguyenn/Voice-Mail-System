package telephone;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

/***
 * Class which is in charge on controlling the different classes in order for
 * the phone to work. The Class will process the different inputs from the phone
 * and perform actions based on the state.
 * 
 * @author James Nguyen
 *
 */
public class ControlSystem {
	private final MailSystem mailSystem;// the mail system
	private final Phone phone;// the phone that will be used
	private Mailbox currentMailbox;// the currentMailbox that is found
	private String currentMessage;// the currentMessage that is found
	// the different strings for the distinct menus used
	private static final String ADMIN_CHOICE = "<html><body>Enter 1 to create a new user.<br>Enter 2 to change a user’s passcode.<br>Enter 3 to reset a user’s passcode.</body></html>";
	private static final String MAILBOX_CHOICE = "<html><body>Enter 1 to retrieve your messages.<br>Enter 2 to change your passcode.<br>Enter 3 to change your greeting.</body></html>";
	private static final String MESSAGE_CHOICE = "<html><body>Enter 1 to listen to your current new message. <br>Enter 2 to save and move on to the next message.<br>Enter 3 to delete and move on to the next message.<br>Enter 4 to select a new or saved message to delete.<br>Enter 5 to go back to the Mailbox Menu.</body></html>";
	private static final String GREETING_CHOICE = "<html><body>Enter 1 to choose a saved greeting.<br>Enter 2 to change the current greeting.<br>Enter 3 to create a new saved greeting.<br>Enter 4 to return to the Mailbox Menu.</body></html>";
	// the different states the control system will be in
	private static final int CONNECTED = 1;
	private static final int RECORDING = 2;
	private static final int ADMIN_MENU = 3;
	private static final int GREETING_MENU = 4;
	private static final int MAILBOX_MENU = 5;
	private static final int MESSAGE_MENU = 6;
	private static final int ADMIN_CHANGE_PASSCODE = 7;
	private static final int ADMIN_FIND_MAILBOX = 8;
	private static final int ADMIN_RESET_PASSCODE = 9;
	private static final int CHANGE_PASSCODE = 10;
	private static final int SELECTED_MESSAGE = 11;
	private static final int SELECTED_GREETING = 12;
	private static final int CHANGE_CURRENT_GREETING = 13;
	private static final int CREATE_GREETING = 14;
	private int state = 0; // intialize the state to 0

	/***
	 * Constructor which connects the control system to the phone and initializes
	 * the mail System, phone, and reset the connection
	 * 
	 * @param phone
	 */
	public ControlSystem(Phone phone) {
		mailSystem = new MailSystem();
		this.phone = phone;
		resetConnection();
		currentMessage = "";
	}

	/***
	 * method which will perform multiple types of actions on the inputted string
	 * depending on the state that the control system is currently in
	 * 
	 * @param entered
	 *            the inputted string that is sent from the phone
	 */
	public void dial(String entered) {
		if (state == CONNECTED) {
			connect(entered);
		} else if (state == RECORDING) {
			login(entered);
		} else if (state == ADMIN_MENU) {
			adminMenu(entered);
		} else if (state == ADMIN_FIND_MAILBOX) {
			adminMenuFind(entered);
		} else if (state == ADMIN_RESET_PASSCODE) {
			adminMenuFind(entered);
		} else if (state == ADMIN_CHANGE_PASSCODE) {
			changePasscode(entered);
		} else if (state == GREETING_MENU) {
			greetingMenu(entered);
		} else if (state == CHANGE_PASSCODE) {
			changePasscode(entered);
		} else if (state == MAILBOX_MENU) {
			mailboxMenu(entered);
		} else if (state == MESSAGE_MENU) {
			messageMenu(entered);
		} else if (state == SELECTED_MESSAGE) {
			deleteMessage(entered);
		} else if (state == SELECTED_GREETING) {
			chooseGreeting(entered);
		} else if (state == CHANGE_CURRENT_GREETING) {
			changeGreeting(entered);
		} else if (state == CREATE_GREETING) {
			createGreeting(entered);
		}
	}

	/***
	 * method which will create a simple pop up window which will contain a title
	 * and message
	 * 
	 * @param title
	 *            the title of the window
	 * @param text
	 *            the text of the window
	 */
	private void createPopUpWindow(String title, String text) {
		JFrame popUpWindow = new JFrame(title);
		JLabel label = new JLabel(text);
		popUpWindow.add(label);
		popUpWindow.setLayout(new FlowLayout());
		popUpWindow.pack();
		popUpWindow.setVisible(true);
	}

	/***
	 * Method which connects the phone to a mailbox or the admin menu based on what
	 * is entered into the text box. It will enter the admin menu if the entered number is '0#'
	 * 
	 * @param entered
	 *            the text that was sent into the text box
	 */
	private void connect(String entered) {
		if (!(entered.indexOf('#') >= 1)) {
			phone.speak("Invalid mailbox number. Try again and enter a mailbox number.");
		} else {
			if ((!(entered.substring(0, entered.indexOf('#')).matches("^[0-9]*$"))) || (entered.length() > 9)) {
				phone.speak("Invalid mailbox number. Try again and enter a mailbox number.");
			} else {
				int number = Integer.parseInt(entered.substring(0, entered.indexOf('#')));
				if (number == 0) {
					state = ADMIN_MENU;
					phone.speak(ADMIN_CHOICE);
					phone.setSize(400, 150);
				} else {
					currentMailbox = mailSystem.findMailbox(number);
					if (currentMailbox == null) {
						phone.speak("Invalid mailbox number. Try again and enter a mailbox number.");
					} else {
						state = RECORDING;
						phone.speak("<html><body>" + currentMailbox.getCurrentGreeting()
								+ "<br>  Enter the passcode to access the mailbox. </body></html>");
					}
				}

			}
		}

	}

	/***
	 * method which will login the user if the entered text matches the passcode or
	 * will record the text that the user enters as a message
	 * 
	 * @param entered
	 *            what the user enters
	 */
	private void login(String entered) {
		if (currentMailbox.checkPasscode(currentMessage + entered)) {
			state = MAILBOX_MENU;
			phone.speak(MAILBOX_CHOICE);
			phone.setSize(350, 200);
		} else {
			currentMessage = currentMessage + entered;
		}
	}

	/**
	 * Method which will perform different actions based on what the user enters for
	 * the admin menu. The method will check if the text matches the preconditions
	 * of the different methods that will be called.
	 * 
	 * @param entered
	 *            what the user enters for the admin menu
	 */
	private void adminMenu(String entered) {
		if (entered.matches("^[0-9]*$") && (entered.trim().length() != 0) && entered.length() <= 9) {
			int choice = Integer.parseInt(entered);
			if (choice >= 1 && choice <= 3) {
				if (choice == 1) {
					createNewUser();
				} else if (choice == 2) {
					if (mailSystem.isEmpty()) {
						createPopUpWindow("Error", "There are no mailboxes. Create a new User or hang up.");
					} else {
						phone.speak("Enter the extension number of the user to change the password.");
						state = ADMIN_FIND_MAILBOX;
					}

				} else if (choice == 3) {
					if (mailSystem.isEmpty()) {
						createPopUpWindow("Error", "There are no mailboxes. Create a new User or hang up.");
					} else {
						phone.speak("Enter the extension number of the user to reset the password.");
						state = ADMIN_RESET_PASSCODE;
					}
				}
			} else {
				createPopUpWindow("Error", "Invalid choice. Choose a valid choice or hang up.");
			}
		} else {
			createPopUpWindow("Error", "Invalid choice. Choose a valid choice or hang up.");
		}
	}

	/***
	 * Method which will perform different actions based on what the user enters on
	 * the greeting menu. The method will check if the text matches the
	 * preconditions of the different methods that will be called.
	 * 
	 * @param event
	 *            what the user enters for the greeting menu
	 */
	private void greetingMenu(String entered) {
		if (entered.matches("^[0-9]*$") && (entered.trim().length() != 0) && entered.length() <= 9) {
			int choice = Integer.parseInt(entered);
			if (choice >= 1 && choice <= 4) {
				if (choice == 1) {
					if (currentMailbox.numOfGreetings() == 1) {
						createPopUpWindow("Error",
								"You cannot choose another greeting since there is only 1 greeting.");
					} else {
						state = SELECTED_GREETING;
						String greetings = "<html><body>Choose the greeting by entering the number corresponding to the greeting. ";
						for (int i = 0; i < currentMailbox.numOfGreetings(); i++) {
							greetings = greetings + "<br>" + (i + 1) + ". " + currentMailbox.getGreeting(i);
						}
						phone.setSize(700, 200);
						greetings = greetings + "</body></html>";
						phone.speak(greetings);
					}
				} else if (choice == 2) {
					state = CHANGE_CURRENT_GREETING;
					phone.setSize(600, 135);
					phone.speak(
							"Enter to the machine what you would like current greeting in used to be followed by the '#' sign.");
				} else if (choice == 3) {
					if (currentMailbox.greetingIsFull()) {
						createPopUpWindow("Error",
								"Max amount of greetings reached. Change the current greeting or choose a different greeting to change");
					} else {
						phone.speak("Enter the new greeting you would like to have followed by the '#' sign.");
						state = CREATE_GREETING;
						phone.setSize(600, 150);
					}
				} else if (choice == 4) {
					state = MAILBOX_MENU;
					phone.speak(MAILBOX_CHOICE);
					phone.setSize(350, 200);
				}
			} else {
				createPopUpWindow("Error", "Invalid choice. Choose a valid choice or hang up.");
			}
		} else {
			createPopUpWindow("Error", "Invalid choice. Choose a valid choice or hang up.");
		}
	}

	/***
	 * Method which will allow the user to choose a saved greeting they would like.
	 * The method will check to make sure the methods are called with the
	 * preconditions met
	 * 
	 * @param entered
	 *            the text entered by the user
	 */
	private void chooseGreeting(String entered) {
		if (entered.matches("^[0-9]*$") && (entered.trim().length() != 0) && entered.length() <= 9) {
			int choice = Integer.parseInt(entered);
			if (choice >= 1 && choice <= currentMailbox.numOfGreetings()) {
				currentMailbox.chooseCurrentGreeting(choice - 1);
				state = GREETING_MENU;
				phone.speak(GREETING_CHOICE);
				phone.setSize(350, 200);
				createPopUpWindow("Sucess", "The Greeting has been chosen");
			} else {
				createPopUpWindow("Error", "Invalid choice. Choose a valid choice or hang up.");
			}
		} else {
			createPopUpWindow("Error", "Invalid choice. Choose a valid choice or hang up.");
		}
	}

	/***
	 * Method which will allow the user to create a saved greeting they would like.
	 * The method will check to make sure the methods are called with the
	 * preconditions met
	 * 
	 * @param entered
	 *            the text entered by the user
	 */
	private void createGreeting(String entered) {
		if (!(entered.trim().indexOf('#') >= 1)) {
			phone.speak("Invalid greeting. Make sure there is a '#' sign. Try again.");
			phone.setSize(350, 200);
		} else {
			String greeting = entered.substring(0, entered.indexOf('#'));
			currentMailbox.addGreeting(greeting);
			currentMailbox.chooseCurrentGreeting(currentMailbox.numOfGreetings() - 1);
			state = GREETING_MENU;
			phone.speak(GREETING_CHOICE);
			phone.setSize(350, 200);
			createPopUpWindow("Sucess", "The Greeting has been created and set as the current greeting.");

		}

	}

	/***
	 * Method which will allow the user to change the current greeting. The method
	 * will check to make sure the methods are called with the preconditions met
	 * 
	 * @param entered
	 *            the text entered by the user
	 */
	private void changeGreeting(String entered) {
		if (!(entered.trim().indexOf('#') >= 1)) {
			phone.speak("Invalid greeting. Make sure there is a '#' sign. Try again.");
			phone.setSize(350, 200);
		} else {
			String greeting = entered.substring(0, entered.indexOf('#'));
			currentMailbox.changeCurrentGreeting(greeting);
			state = GREETING_MENU;
			phone.speak(GREETING_CHOICE);
			phone.setSize(350, 200);
			createPopUpWindow("Sucess", "The current greeting has been changed");

		}

	}

	/***
	 * method which will create a new user and print out a pop window confirming
	 * that it is created
	 */
	private void createNewUser() {
		mailSystem.addNewMailbox();
		createPopUpWindow("Success", "User has been created with Mailbox Extension Number "
				+ mailSystem.getLastMailboxNumber() + " with default pasccode '0000'");
	}

	/***
	 * Method which will allow the user to change the passcode. The method will
	 * check to make sure the methods are called with the preconditions met
	 * 
	 * @param entered
	 *            the text entered by the user
	 */
	private void changePasscode(String entered) {
		if (entered.matches("^[0-9]*$") && entered.length() == 4) {
			currentMailbox.setPasscode(entered);
			createPopUpWindow("Success", "The Mailbox Passcode has been set to " + entered);
			if (state == ADMIN_CHANGE_PASSCODE) {
				state = ADMIN_MENU;
				phone.speak(ADMIN_CHOICE);
			} else {
				state = MAILBOX_MENU;
				phone.speak(MAILBOX_CHOICE);
				phone.setSize(350, 200);
			}
		} else {
			phone.speak("Passcode must be numerical and is four digits. Try Again");
		}
	}

	/***
	 * Method which will find a mailbox while the Control system is in the admin
	 * state.The method will check to make sure the methods are called with the
	 * preconditions met
	 * 
	 * @param entered
	 *            the text entered by the user
	 */
	private void adminMenuFind(String entered) {
		if (!(entered.indexOf('#') >= 1)) {
			phone.speak("Invalid mailbox number. Try again and enter a mailbox number.");
		} else {
			if (!(entered.substring(0, entered.indexOf('#')).matches("^[0-9]*$"))) {
				phone.speak("Invalid mailbox number. Try again and enter a mailbox number.");
			} else {
				int number = Integer.parseInt(entered.substring(0, entered.indexOf('#')));
				currentMailbox = mailSystem.findMailbox(number);
				if (currentMailbox == null) {
					phone.speak("Invalid mailbox number. Try again and enter a mailbox number.");
				} else {
					if (state == ADMIN_RESET_PASSCODE) {
						currentMailbox.resetPasscode();
						createPopUpWindow("Success", "The Mailbox Passcode has been reset to '0000'");
						phone.speak(ADMIN_CHOICE);
						state = ADMIN_MENU;

					} else {
						state = ADMIN_CHANGE_PASSCODE;
						phone.speak("Enter the passcode you would like Mailbox " + number + " to have");
					}
				}
			}

		}
	}

	/***
	 * Method which will perform different actions based on what the user enters on
	 * the mailbox menu. The method will check if the text matches the preconditions
	 * of the different methods that will be called.
	 * 
	 * @param event
	 *            what the user enters for the greeting menu
	 */
	private void mailboxMenu(String entered) {
		if (entered.matches("^[0-9]*$") && (entered.trim().length() != 0) && entered.length() <= 9) {
			int choice = Integer.parseInt(entered);
			if (choice >= 1 && choice <= 3) {
				if (choice == 1) {

					state = MESSAGE_MENU;
					phone.speak(MESSAGE_CHOICE);
					phone.setSize(350, 215);

				} else if (choice == 2) {
					phone.speak("Enter the passcode you would like the Mailbox to have");
					state = CHANGE_PASSCODE;
					phone.setSize(350, 150);

				} else if (choice == 3) {
					state = GREETING_MENU;
					phone.speak(GREETING_CHOICE);
					phone.setSize(350, 200);
				}
			} else {
				createPopUpWindow("Error", "Invalid choice. Choose a valid choice or hang up.");
			}
		} else {
			createPopUpWindow("Error", "Invalid choice. Choose a valid choice or hang up.");
		}
	}

	/***
	 * Method which creates a window containing all the messages
	 */
	private void createMessageWindow() {
		int messageNumber = 1;
		JFrame messageWindow = new JFrame("Your Messages");
		messageWindow.setLayout(new GridLayout(currentMailbox.getNewSize() + currentMailbox.getSavedSize(), 1));
		for (int i = 0; i < currentMailbox.getNewSize(); i++) {
			JLabel message = new JLabel(messageNumber + ". " + currentMailbox.getNewMessage(i).getText(),
					JLabel.CENTER);
			messageNumber++;
			messageWindow.add(message);
		}
		for (int i = 0; i < currentMailbox.getSavedSize(); i++) {
			JLabel message = new JLabel(messageNumber + ". " + currentMailbox.getSavedMessage(i).getText(),
					JLabel.CENTER);
			messageNumber++;
			messageWindow.add(message);
		}
		messageWindow.pack();
		messageWindow.setVisible(true);
	}

	/***
	 * Method which will delete the message based on the integer entered by the
	 * user. The method will check if the text matches the preconditions of the
	 * different methods that will be called.
	 * 
	 * @param entered
	 *            the integer entered
	 */
	private void deleteMessage(String entered) {
		if (entered.matches("^[0-9]*$") && (entered.trim().length() != 0) && entered.length() <= 9) {
			int choice = Integer.parseInt(entered);
			if (choice <= currentMailbox.getNewSize() + currentMailbox.getSavedSize() && choice != 0) {
				if (currentMailbox.newMessageisEmpty()) {
					currentMailbox.removeSavedMessage(choice - 1);
				} else if (choice > currentMailbox.getNewSize()) {
					currentMailbox.removeSavedMessage(choice - 1 - currentMailbox.getNewSize());
				} else {
					currentMailbox.removeNewMessage(choice - 1);
				}
				createPopUpWindow("Sucess", "The selected message has been deleted");
				state = MESSAGE_MENU;
				phone.speak(MESSAGE_CHOICE);
				phone.setSize(350, 215);
			} else {
				createPopUpWindow("Error", "Invalid choice. Choose a valid choice or hang up.");
			}
		} else {
			createPopUpWindow("Error", "Invalid choice. Choose a valid choice or hang up.");
		}

	}

	/***
	 * Method which will perform different actions based on what the user enters on
	 * the message menu. The method will check if the text matches the preconditions
	 * of the different methods that will be called.
	 * 
	 * @param event
	 *            what the user enters for the greeting menu
	 */
	public void messageMenu(String entered) {
		if (entered.matches("^[0-9]*$") && (entered.trim().length() != 0) && entered.length() <= 9) {
			int choice = Integer.parseInt(entered);
			if (choice >= 1 && choice <= 5) {
				if (currentMailbox.newMessageisEmpty() && (choice == 1 || choice == 2 || choice == 3)) {
					createPopUpWindow("Message", "There are currently no new messsage");
				} else {
					if (choice == 1) {
						createPopUpWindow("Message", currentMailbox.getCurrentMessage().getText());
					} else if (choice == 2) {
						currentMailbox.saveMessage(currentMailbox.removeCurrentMessage());
						createPopUpWindow("Message", "The new message has been saved.");
					} else if (choice == 3) {
						currentMailbox.removeCurrentMessage();
						createPopUpWindow("Message", "The message has been removed.");
					} else if (choice == 4) {
						if (currentMailbox.newMessageisEmpty() && currentMailbox.savedMessageisEmpty()) {
							createPopUpWindow("Message", "There are no saved or new messages to view.");
						} else {
							state = SELECTED_MESSAGE;
							phone.speak(
									"Select a Message to delete by providing the number corresponding to the message.");
							phone.setSize(500, 150);
							createMessageWindow();
						}
					} else if (choice == 5) {
						state = MAILBOX_MENU;
						phone.speak(MAILBOX_CHOICE);
						phone.setSize(350, 200);
					}
				}
			} else {
				createPopUpWindow("Error", "Invalid choice. Choose a valid choice or hang up.");
			}
		} else {
			createPopUpWindow("Error", "Invalid choice. Choose a valid choice or hang up.");
		}
	}

	/***
	 * method which will essentially hang up the phone and reset the connection and
	 * state of the phone
	 */
	public void hangUp() {
		if (state == RECORDING) {
			if (currentMessage != null && currentMessage.trim().length() != 0) {
				currentMailbox.addMessage(new Message(currentMessage));
			}
		}
		resetConnection();
		createPopUpWindow("Hanged Up", "You have hanged up");
	}

	/***
	 * method which resets the connection by restarting the state and clearing the
	 * current message
	 */
	private void resetConnection() {
		state = CONNECTED;
		currentMessage = "";
		phone.speak("Enter numbers followed by the '#' sign to go to a specific Mailbox Number");
		phone.setSize(500, 150);
	}
}
