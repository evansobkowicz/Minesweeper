
/**
 * Minesweeper Game
 * CS172 Final Project
 * @author Evan Sobkowicz
 * 5/5/2014
 */

public class StopWatch { 
	
    private final long start;
    
    /**
     * Constructor - start the stopwatch
     */
    public StopWatch() {
        start = System.currentTimeMillis();
    } 
    
    /**
     * Get the elapsed time
     * @return
     */
    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    } 
    
    /**
     * Set a delay
     * @param millsec
     */
    public void delay(int millsec) {
		try {
			Thread.sleep(millsec);
		} catch (Exception e) {
			System.out.println("Error");
		}
	}
    
} 