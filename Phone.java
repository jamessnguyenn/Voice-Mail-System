package telephone;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/***
 * Class which allows users to create a phone that has access to the voice mail
 * system. The user will be able to enter different strings into the phone and
 * perform different actions
 * 
 * @author James Nguyen
 *
 */
public class Phone extends JFrame {
	private JTextField textField; // the text field in the interface type
	private JLabel text; // the text that is used to speak to the users
	private String enteredText;// the text enterd by the user
	private static final int FIELD_WIDTH = 20;
	private ControlSystem controlSystem;// control system that will be used

	/***
	 * Constructor which initializes the phone and adds different buttons and fields
	 * within the phone in order for the user to interact with the phone
	 */
	public Phone() {
		super("Voice Mail System");
		textField = new JTextField(FIELD_WIDTH);
		JButton button = new JButton("Enter to Machine");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {// action that occurs if the Enter to Machine button is
															// clicked
				enteredText = textField.getText();
				textField.setText("");
				run();

			}
		});
		text = new JLabel("Enter numbers followed by the '#' sign to go to a specific Mailbox Number");
		JLabel hangUpText = new JLabel("To end the phone call, send 'H' to the machine.");
		this.setLayout(new FlowLayout());
		this.add(text);
		this.add(hangUpText);
		this.add(textField);
		this.add(button);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 150);
		this.setVisible(true);
		controlSystem = new ControlSystem(this);
	}

	/**
	 * changes the text of the window to speak to the user
	 * 
	 * @param text
	 */
	public void speak(String text) {
		this.text.setText(text);
	}

	/***
	 * method which will be called every time the button is clicked and will send
	 * the entered text into the control system
	 */
	public void run() {
		if (enteredText == null) {
			// do nothing
		} else if (enteredText.equalsIgnoreCase("H")) {// hangs up if the text is equal to h
			controlSystem.hangUp();
		} else {
			controlSystem.dial(enteredText);
		}
	}

	
}
