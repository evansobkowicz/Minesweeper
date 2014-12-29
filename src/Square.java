
/**
 * Minesweeper Game
 * CS172 Final Project
 * @author Evan Sobkowicz
 * 5/5/2014
 */

public class Square {
	
	private int x;
	private int y;
	private boolean active;
	private boolean flag;
	private boolean mine;
	private int mineNeighbors;
	
	/**
	 * Square constructor
	 * @param initialX
	 * @param initialY
	 */
	public Square(int initialX, int initialY) {
		this.x = initialX;
		this.y = initialY;
		this.active = false;
		this.flag = false;
		this.mine = false;
		this.mineNeighbors = 0;
	}
	
	// GETTERS
	
	/**
	 * Get the x coord of the square
	 * @return
	 */
	public int getX() { return this.x; }
	
	/**
	 * Get the y coord of the square
	 * @return
	 */
	public int getY() { return this.y; }
	
	/**
	 * Is this square active?
	 * @return
	 */
	public boolean isActive() { return this.active; }
	
	/**
	 * Is the square flagged?
	 * @return
	 */
	public boolean isFlag() { return this.flag; }
	
	/**
	 * Is the square a mine?
	 * @return
	 */
	public boolean isMine() { return this.mine; }
	
	/**
	 * Get how many neighboring mines this square has
	 * @return
	 */
	public int getMineNeighbors() { return this.mineNeighbors; }
	
	
	// SETTERS
	
	/**
	 * Activate this square
	 */
	public void activate() {
		this.active = true;
	}
	
	/**
	 * Flag or unflag this square
	 */
	public void flag() {
		if (this.flag) {
			this.flag = false;
		} else {
			this.flag = true;
		}
	}
	
	/**
	 * Place or remove a mine from this square
	 */
	public void mine() {
		if (this.mine) {
			this.mine = false;
		} else {
			this.mine = true;
		}
	}
	
	/**
	 * Set the number of neighboring mines this square has
	 */
	public void setNeighbors(int n) {
		this.mineNeighbors = n;
	}

}
