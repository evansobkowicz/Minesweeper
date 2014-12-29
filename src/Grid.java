import java.awt.Color;

/**
 * Minesweeper Game
 * CS172 Final Project
 * @author Evan Sobkowicz
 * 5/5/2014
 */

public class Grid {

	private Square[][] squares;
	private int width;
	private int height;
	private int mines;
	private boolean gameOver;
	private boolean win;
	private int step;
	private StopWatch sw;
	private double score;
	
	
	/**
	 * Grid Constructor
	 * @param w
	 * @param h
	 * @param mineCount
	 */
	public Grid(int w, int h, int mineCount) {
		this.width = w;
		this.height = h;
		this.mines = mineCount;
		this.gameOver = false;
		this.win = false;
		this.step = 1;
		this.score = 0;
		
		// Create array of squares
		squares = new Square[this.width][this.height];
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				squares[i][j] = new Square(i,j);
			}
		}
		
		// StdDraw Setup
		StdDraw.setCanvasSize(500,500);
	    StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        
        // Initialize timer
        sw = new StopWatch();
	
        // Play the game!
        play();
	}
	
	/**
	 * Update squares based on whether they are mines, flags, active, and have a number
	 */
	public void update() {
        for (int i = 0; i < this.width; i++){
	    	for (int j = 0; j < this.height; j++){
	    		
	    		Color c = new Color(30,30,30);
	    		StdDraw.setPenColor(c);
	    		StdDraw.filledSquare(i+.45, j+.45, 0.45);
	    		
	    		if (gameOver && squares[i][j].isMine()) {
	    			c = new Color(255,30,30);
	    			StdDraw.setPenColor(c);
		    		StdDraw.filledSquare(i+.45, j+.45, 0.45);
	    		} else if (squares[i][j].isFlag()) {
	    			c = new Color(30,30,255);
	    			StdDraw.setPenColor(c);
		    		StdDraw.filledSquare(i+.45, j+.45, 0.45);
	    		} else if (squares[i][j].isActive()) {
	    			int neighbors = squares[i][j].getMineNeighbors();
	    			if (neighbors == 0) {
	    				c = new Color(50,255,50);
		    			StdDraw.setPenColor(c);
			    		StdDraw.filledSquare(i+.45, j+.45, 0.45);
	    			} else {
	    				c = new Color(100,100,100);
		    			StdDraw.setPenColor(c);
			    		StdDraw.filledSquare(i+.45, j+.45, 0.45);
			    		String text = "" + neighbors;
						StdDraw.setPenColor(255,255,255);
						StdDraw.textLeft(i+.3, j+.4, text);
	    			}
	    			
	 
	    		}
	    		
	    	}
	    }
        
        // Update mine/flag count and timer
        displayMineCount();
        displayTime();
        
	}
	
	/**
	 * Get a square from its x and y coords
	 * @param x
	 * @param y
	 * @return
	 */
	public Square getSquare(double x, double y) {
		return squares[(int) x][(int) y];
	}
	
	/**
	 * Get game result
	 * @return
	 */
	public boolean getResult() {
		return this.win;
	}
	
	/** 
	 * Get the score/time for the game
	 * @return
	 */
	public double getScore() {
		return sw.elapsedTime();
	}
	
	/**
	 * Print the mine/flag count to the screen
	 */
	public void displayMineCount() {
		int count = this.mines - countFlags();
        String flags = "" + count;
        StdDraw.setPenColor(255,255,255);
        StdDraw.filledSquare(.3, this.height+.4, 0.45);
		StdDraw.setPenColor(0,0,0);
		StdDraw.textLeft(0, this.height+.2, flags);
	}
	
	/**
	 * Display the timer on the screen
	 */
	public void displayTime() {
		int dur = (int) sw.elapsedTime();
		String time = "" + dur;
        StdDraw.setPenColor(255,255,255);
        StdDraw.filledSquare(this.width-1+.3, this.height+.4, 0.45);
		StdDraw.setPenColor(0,0,0);
		StdDraw.textLeft(this.width-1, this.height+.2, time);
		sw.delay(100);
	}
	
	/**
	 * Count flagged squares
	 * @return
	 */
	public int countFlags() {
		int count = 0;
		for (int i = 0; i < this.width; i++){
	    	for (int j = 0; j < this.height; j++){
	    		if (squares[i][j].isFlag()) {
	    			count++;
	    		}
	    	}
		}
		return count;
	}
	
	/**
	 * Check if the user has beat the game.
	 * @return 
	 */
	public boolean checkWin() {
		int count = 0;
		for (int i = 0; i < this.width; i++) {
	    	for (int j = 0; j < this.height; j++) {
	    		Square current = squares[i][j];
	    		if (current.isActive() || (current.isFlag() && current.isMine())) {
	    			count++;
	    		}
	    	}
		}
		if (count == (this.width * this.height)) {
			System.out.println(count);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Place mines on the grid
	 */
	public void placeMines() {
		for (int i = 0; i < this.mines; i++) {
			int r = (int) (Math.random() * this.width);
			int c = (int) (Math.random() * this.height);
			if (!squares[r][c].isMine() && !squares[r][c].isActive() && !squares[r][c].isFlag()) {
				squares[r][c].mine();
			} else {
				i--;
			}
		}
	}
	
	/**
	 * Move a mine from x,y to a new random location
	 * @param x
	 * @param y
	 */
	public void moveMine(int x, int y) {
		boolean placed = false;
		while (!placed) {
			int r = (int) (Math.random() * this.width);
			int c = (int) (Math.random() * this.height);
			if (x != r && y != c && !squares[r][c].isMine() && !squares[r][c].isActive() && !squares[r][c].isFlag()) {
				squares[x][y].mine();
				squares[r][c].mine();
				placed = true;
			}
		}
	}
	
	/**
	 * Check the neighbors for the current square and set the current squares neighbor count.
	 * @param current
	 */
	public void checkNeighbors(Square current) {
		int x = current.getX();
		int y = current.getY();
		int count = 0;
		
		try {
			if (squares[x-1][y+1].isMine()) 	{ count++; }
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		try {
			if (squares[x][y+1].isMine())  		{ count++; }
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		try {
			if (squares[x+1][y+1].isMine())  	{ count++; }
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		try {
			if (squares[x-1][y].isMine())  		{ count++; }
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		try {
			if (squares[x+1][y].isMine()) 		{ count++; }
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		try {
			if (squares[x-1][y-1].isMine()) 	{ count++; }
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		try {
			if (squares[x][y-1].isMine())  		{ count++; }
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		try {
			if (squares[x+1][y-1].isMine())  	{ count++; }
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		squares[x][y].setNeighbors(count);
	}
	
	/**
	 * Determine if the current square has an active neighbor that has no surrounding mines
	 * @param current
	 * @return
	 */
	public boolean hasActiveZeroNeighbor(Square current) {
		int x = current.getX();
		int y = current.getY();
		int count = 0;
		
		try {
			if (squares[x-1][y+1].getMineNeighbors() == 0 && squares[x-1][y+1].isActive()) 		{ count++; }
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		try {
			if (squares[x][y+1].getMineNeighbors() == 0 && squares[x][y+1].isActive())  		{ count++; }
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		try {
			if (squares[x+1][y+1].getMineNeighbors() == 0 && squares[x+1][y+1].isActive())  	{ count++; }
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		try {
			if (squares[x-1][y].getMineNeighbors() == 0 && squares[x-1][y].isActive())  		{ count++; }
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		try {
			if (squares[x+1][y].getMineNeighbors() == 0 && squares[x+1][y].isActive())	 		{ count++; }
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		try {
			if (squares[x-1][y-1].getMineNeighbors() == 0 && squares[x-1][y-1].isActive()) 		{ count++; }
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		try {
			if (squares[x][y-1].getMineNeighbors() == 0 && squares[x][y-1].isActive())  		{ count++; }
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		try {
			if (squares[x+1][y-1].getMineNeighbors() == 0 && squares[x+1][y-1].isActive())  	{ count++; }
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		if (count > 0) {
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * Recursively activate neighbors if a square has no mine neighbors
	 * @param current
	 */
	public void activateNeighbors(Square current) {
		int x = current.getX();
		int y = current.getY();
				
		Square s;
		
		try {
			s = squares[x][y+1];
			if (!s.isMine() && !s.isActive() && !s.isFlag() && hasActiveZeroNeighbor(s)) 	{ 
				s.activate();
				activateNeighbors(s);
			}
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		try {
			s = squares[x-1][y];
			if (!s.isMine() && !s.isActive() && !s.isFlag() && hasActiveZeroNeighbor(s)) 	{ 
				s.activate();
				activateNeighbors(s);
			}
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		try {
			s = squares[x+1][y];
			if (!s.isMine() && !s.isActive() && !s.isFlag() && hasActiveZeroNeighbor(s)) 	{ 
				s.activate();
				activateNeighbors(s);
			}
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
		try {
			s = squares[x][y-1];
			if (!s.isMine() && !s.isActive() && !s.isFlag() && hasActiveZeroNeighbor(s)) 	{ 
				s.activate();
				activateNeighbors(s);
			}
		} catch (Exception e) {
			//System.out.println("Edge Square...");
		}
		
	}
	
	/**
	 * Update all squares mine neighbor count
	 */
	public void updateNeighbors() {
		for (int i = 0; i < this.width; i++) {
	    	for (int j = 0; j < this.height; j++) {
	    		Square current = squares[i][j];
	    		checkNeighbors(current);
	    	}
		}
	}
	
	/**
	 * Play the game!
	 */
	public void play() {
		// Setup gameplay
		this.gameOver = false;
		boolean justPressedMouse = false;
		placeMines();
		updateNeighbors();
		update();
		
		// User input loop to collect mouse clicks
		while (!this.gameOver) {
			displayTime();
			
			// Check if the mouse is pressed, and not just pressed (to avoid multiple presses per click)
			if (StdDraw.mousePressed()) {
				if (!justPressedMouse) {
					
					// Get the clicked on square
					int mouseX = (int) StdDraw.mouseX();
					int mouseY = (int) StdDraw.mouseY();
					Square current = getSquare(mouseX,mouseY);
					
					// If first click is a mine, move the mine!
					if (this.step == 1 && current.isMine()) {
						moveMine(mouseX,mouseY);
						updateNeighbors();
					}
					checkNeighbors(current);
					
					if (StdDraw.isKeyPressed(70)) {
						// If "F" is held while mouse is clicked, flag the square
						current.flag();
					} else if (! current.isActive()) {
						if (current.isMine()) {
							// If clicked square is a mine, end the game
							System.out.println("Game Over!");
							System.out.println("Elapsed Time: " + sw.elapsedTime());
							this.gameOver = true;
						} else {
							// If current square is not active, activate it and if current square has no mine neighbors, check neighbors
							current.activate();
							if (current.getMineNeighbors() == 0) {
								activateNeighbors(current);
							}
						}
					} else {
						System.out.println("Invalid Click.");
					}
					
					// Check if the user has beat the game
					if (checkWin()) {
						System.out.println("You Win!");
						this.score = sw.elapsedTime();
						this.win = true;
						this.gameOver = true;
					}
					
					// Draw the updated game board
					this.step++;
					update();
				}
				justPressedMouse = true;
			} else {
				justPressedMouse = false;
			}
			
		} // end while
			
	}

}
