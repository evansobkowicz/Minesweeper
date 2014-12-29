
/**
 * Minesweeper Game
 * CS172 Final Project
 * @author Evan Sobkowicz
 * 5/5/2014
 */

public class User {

	private String name;
	private int games;
	private double lowscore;
	
	/**
	 * User Constructor
	 * @param n
	 * @param g
	 * @param hs
	 */
	public User(String n, int g, double hs) {
		this.name = n;
		this.games = g;
		this.lowscore = hs;
	}
	
	/**
	 * Get user's name
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Get # games played
	 * @return
	 */
	public int getGames() {
		return this.games;
	}
	
	/**
	 * Get high score
	 * @return
	 */
	public double getLowScore() {
		return this.lowscore;
	}
	
	/** 
	 * Set high score
	 * @param n
	 */
	public void setLowScore(double n) {
		this.lowscore = n;
	}
	
	/**
	 * Increase played games
	 */
	public void plusGame() {
		this.games++;
	}
	
	/** 
	 * Print user data
	 */
	public String toString() {
		return this.name + ", " + this.games + ", " + this.lowscore;
	}
	
}
