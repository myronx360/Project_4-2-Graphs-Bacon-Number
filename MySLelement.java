import edu.uncc.cs.bridgesV2.base.SLelement;
import edu.uncc.cs.bridgesV2.base.Edge;
import edu.uncc.cs.bridgesV2.base.GraphList;
import edu.uncc.cs.bridgesV2.connect.Bridges;

public class MySLelement<E> extends SLelement<E> {
	private int distance;
	private MySLelement<E> prev = null;
	public MySLelement(E e, SLelement<E> next) {
		super(e, next);
		// TODO Auto-generated constructor stub
	}

	public MySLelement(SLelement<E> next) {
		super(next);
		// TODO Auto-generated constructor stub
	}

	public MySLelement(String label, E e) {
		super(label, e);
		// TODO Auto-generated constructor stub
	}

	public MySLelement(String label, E e, int distance, MySLelement<E> prev) {
		super(label, e);
		this.distance = distance;
		this.prev = prev;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the distance
	 */
	public int getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}

	/**
	 * @return the prev
	 */
	public MySLelement<E> getPrev() {
		return prev;
	}

	/**
	 * @param prev the prev to set
	 */
	public void setPrev(MySLelement<E> prev) {
		this.prev = prev;
	}
	
	
	
}
