/** Linked queue implementation */
class LQueue<E>  implements Queue<E>{
	private Link<E> front; // Pointer to front queue node
	private Link<E> rear; // Pointer to rear queuenode
	private int size; // Number of elements in queue
	/** Constructors */
	public LQueue() { init(); }
	public LQueue(int size) { init(); } // Ignore size
	/** Initialize queue */
	private void init() {
		front = rear = new Link<E>(null);
		size = 0;
	}
	/** Reinitialize queue */
	public void clear() { init(); }
	/** Put element on rear */
	public void enqueue(E it) {
		rear.setNext(new Link<E>(it, null));
		rear = rear.next();
		size++;
	}
	/** Remove and return element from front */
	public E dequeue() {
		assert size != 0 : "Queue is empty";
		E it = front.next().element(); // Store dequeued value
		front.setNext(front.next().next()); // Advance front
		if (front.next() == null) rear = front; // Last Object
		size--;
		return it; // Return Object
	}
	/** @return Front element */
	public E frontValue() {
		assert size != 0 : "Queue is empty";
		return front.next().element();
	}
	/** @return Queue size */
	public int length() { return size; }

	/** print elements in Queue */
	public String print(){
		// create a temporary queue
		LQueue temp = new LQueue();
		// get the size of queue
		int size = length();
		String queueString = "";
		// loop through the entire queue
		for(int i = 0; i<size;i++){
			// add the front value of this queue to the temporary queue
			temp.enqueue(this.frontValue());
			// print the front value to the console
			System.out.print(frontValue()+"; ");
			queueString = queueString.concat(frontValue().toString()+"; ");
			// remove the first element
			this.dequeue();
			// loop back and print and remove the next element of the queue
		}

		// create an empty line in the console
		System.out.println();

		for(int i = 0; i<size;i++){
			// add the elements in the temporary queue back to this queue
			this.enqueue((E)temp.frontValue());
			// remove first element in queue so that the next element can be added back
			temp.dequeue();
		}
		return (queueString);
	}

	/** @return if (E it) is inside of the Queue */
	public boolean contains(E it){
		// create a temporary queue
		LQueue temp = new LQueue();
		// get the size of queue
		int size = length();
		// set isContained to false
		boolean isContained = false;
		// loop through the entire queue
		for(int i = 0; i<size;i++){
			// add the front value of this queue to the temporary queue
			temp.enqueue(this.frontValue());
			// check if the current fron value is equal to it
			if(this.frontValue().equals(it)){
				isContained = true;
			}
			this.dequeue();
			// loop back and check the next element of the queue
		}


		for(int i = 0; i<size;i++){
			// add the elements in the temporary queue back to this queue
			this.enqueue((E)temp.frontValue());
			// remove first element in queue so that the next element can be added back
			temp.dequeue();
		}
		return isContained;
	}

}