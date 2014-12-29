import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Minesweeper Game -- Main Class
 * CS172 Final Project
 * @author Evan Sobkowicz
 * 5/5/2014
 */

public class Minesweeper {
	
	public static User[] users;
	public static User current;
	
	/**
	 * Get an integer and reject anything else
	 * @param prompt
	 * @param limit
	 * @return
	 */
	public static int getInt(String prompt, int limit) {
		boolean valid = false;
		while (!valid) {
			System.out.print(prompt);
			String input = StdIn.readString();
			try {
				int response = Integer.parseInt(input);
				if (response > limit || response < 1) {
					throw new Exception("Please enter a vaid number between 1 and " + limit);
				}
				return response;
			} catch (Exception e) {
				System.out.println("ERROR: Input was invalid!");
			}
		}
		return -1;
	}
	
	/**
	 * Load the high scores and user data from the text file
	 * @throws IOException
	 */
	public static void loadScores() throws IOException {
		// Read Data
		String file = "data.txt";
		String line = "";
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(file));
		String firstline = br.readLine();
		int usersLength = Integer.parseInt(firstline);
		users = new User[usersLength];
		int i = 0;
		while ((line = br.readLine()) != null) {
 			String[] data = line.split(", ");
			users[i] = new User(data[0], Integer.parseInt(data[1]), Double.parseDouble(data[2]));
			i++;
		}
		br.close();
	}
	
	/**
	 * Save user data and high scores to the text file
	 */
	public static void saveScores() {
		String output = users.length + "\n";
		for (int i = 0; i < users.length; i++) {
			output += users[i].toString() + "\n";
		}
		
		try {
            FileWriter fileWriter = new FileWriter("data.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(output);            
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch(Exception  e){
            System.out.println("An error occured while writing files.");
        }
	}
	
	/**
	 * Match an entered username to an existing user or create a new user
	 * @param username
	 * @return
	 */
	public static User matchUser(String username) {
		for (int i = 0; i < users.length; i++) {
			if (username.equals(users[i].getName())) {
				return users[i];
			}
		}
		newUser(1);
		users[users.length-1] = new User(username,0,0);
		return users[users.length-1];
	}
	
	/**
	 * Expand the users array
	 * @param add
	 */
	public static void newUser(int add) {
		User[] tempUsers = new User[users.length];
		for (int i = 0; i < users.length; i++) {
			tempUsers[i] = users[i];
		}
		int newLength = users.length + add;
		users = new User[newLength];
		for (int j = 0; j < newLength-1; j++) {
			users[j] = tempUsers[j];
		}
	}
	
	/**
	 * Main function - get username, choose game to play, save data
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			loadScores();
		} catch (IOException e) {
			System.out.println("ERROR");
		}
		
		System.out.println("Welcome to Minesweeper!");
		System.out.print("Enter your username: ");
		String username = StdIn.readString();
		current = matchUser(username);
		System.out.println("----------------------");
		
		System.out.println("Welcome " + current.getName() + "! You have played " + current.getGames() + " games, and your low score is " + current.getLowScore() + "!");

		System.out.println("----------------------");
		System.out.println("Controls:");
		System.out.println("Click - Select Square");
		System.out.println("F Key - Flag Square");
		System.out.println("----------------------");
		System.out.println("1) Beginner");
		System.out.println("2) Intermediate");
		System.out.println("3) Expert");
		System.out.println("4) Custom");
		
		int choice = getInt("Please select a difficulty: ", 4);
		
		// Create a grid and play the game
		Grid g = null;
		
		if (choice == 4) {
			System.out.println("Create a Custom Game");
			int width = getInt("Please enter a width [1-30]: ", 30);
			int height = getInt("Please enter a height [1-30]: ", 30);
			int mines = (width*height)/5;
			g = new Grid(width,height,mines);
		} else if (choice == 3) {
			g = new Grid(16,30,99);
		} else if (choice == 2) {
			g = new Grid(16,16,40);
		} else if (choice == 1) {
			g = new Grid(9,9,10);
		} else {
			System.out.println("ERROR: Invalid Choice!");
		}
		
		
		System.out.println("");
		System.out.println("----------------------");
		
		
		// After the game
		boolean result = g.getResult();
		double score = g.getScore();
		if (result) {
			current.setLowScore(score);
			System.out.println("Your new low score is " + score);
		}
		current.plusGame();
		saveScores();
		
		
		
	}

}
