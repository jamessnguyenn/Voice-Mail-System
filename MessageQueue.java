package telephone;

import java.util.LinkedList;

/***
 * Class which will be used by the user to hold the different messages
 * 
 * @author James Nguyen
 *
 */
public class MessageQueue {
	private LinkedList<Message> queue; // the linked list which will hold the different messages

	/***
	 * constructor which will intialize the queue that holds the messages
	 */
	public MessageQueue() {
		queue = new LinkedList<Message>();
	}

	/**
	 * method which will add the message to the queue
	 * 
	 * @param message
	 *            the message that will be added
	 * 
	 */
	public void addMessage(Message message) {

		queue.push(message);
	}

	/***
	 * method which will remove the newest message that was added to the queue
	 * 
	 * @return the newest message
	 * @precondition !isEmpty()
	 */
	public Message remove() {
		assert !isEmpty() : "The queue is empty";
		return queue.pop();
	}

	/***
	 * method which remove the message based on the index
	 * 
	 * @param i
	 * @precondition i>=0 && i<queue.size()
	 */
	public void remove(int i) {
		assert i >= 0 && i < queue.size();
		queue.remove(i);
	}

	/**
	 * method which finds the size of the queue
	 * 
	 * @return the size of the queue
	 */
	public int getSize() {
		return queue.size();
	}

	/***
	 * method which peeks at the Message at the top of the queue
	 * 
	 * @return the message at the top of the queue
	 */
	public Message peek() {
		return queue.peek();
	}

	/***
	 * method which checks if the queue is empty
	 * 
	 * @return true if the queue is empty
	 */
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	/**
	 * method which gets the message at the specified index
	 * 
	 * @param i
	 *            the index of the message
	 * @return the message at the specified index
	 * @precondition i>=0 && i<queue.size()
	 */
	public Message getMessage(int i) {
		assert i>=0 && i<queue.size(): "Index is out of bounds";
		return queue.get(i);
	}

}
