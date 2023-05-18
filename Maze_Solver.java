import java.util.Scanner;
import java.io.File;
import java.util.*;
import java.util.Stack;


 /**
 *Three sample mazes are included.
 *maze1.txt solves easily
 *maze2.txt is larger, but also solves
 *maze3.txt is unsolveable
 */

public class MazeSolver {
	char[][] charmap = null; // im guessing this is the maze
	int startRow = -1;//Starting row
	int startCol = -1;//Starting column
	int width;
	int height;
	char p; // stores whether the direction will be North South East or West  N,S,E,W respectively
	Stack<Character> directions= new Stack<>();// the old stack that stored the data from the end to the start
	Stack<Character> directional= new Stack<>(); // the new stack that is being printed and has the directions in the right order
	//You can add extra stuff here if you like!

	public MazeSolver() {

	}


	private void loadMaze() {
		String filename;
		Scanner console = new Scanner(System.in);
		Scanner file;
		String temp;

		System.out.print("Enter maze filename: ");
		filename = console.nextLine();
		try {
			file = new Scanner(new File(filename));

			height = file.nextInt();
			width = file.nextInt();
			charmap = new char[height][width];
			file.nextLine();
			for (int i = 0; i < height; i++) {
				temp = file.nextLine();
				charmap[i] = temp.toCharArray();
				if (temp.indexOf('S') >= 0) {
					startRow = i;
					startCol = temp.indexOf('S');
					System.out.println("Start at " + startRow + "," + startCol + ".");
				}
			}

			System.out.println("File transcription complete!\n");
		} catch (Exception exn) {
			System.out.println("\nFile transcription problem. Exiting now.");
			System.exit(0);
		}

		solve();
	}

	public void solve() {
		System.out.println("Initial State:");
		printMap();

		if (recursiveSolve(startRow, startCol)) {
			System.out.println("\nFinal Maze:");
			printMap();
			System.out.print("Solution Path: ");
			order();
		} else System.out.println("Oops! No solution found!");
	}

	//Put your recursive solution here.
	//Feel free to add parameters if you'd like it to keep track of your solution path
	/**
	 * This method solves the maze recursively and returns true when it finds a solution,
	 * @param rows it is the rows of the maze
	 * @param cols it is the columns of the maze
	 * **/
	public boolean recursiveSolve(int rows, int cols) {
		boolean solved=false;
		// our base case to end the recursion by returning true hence showing the method found an ending to the maze
			if (charmap[rows][cols] == 'E') {
				charmap[rows][cols] = 'O';
				return true;
			}
			if(charmap[rows][cols]=='S'){charmap[rows][cols]='#';}
		// the conditions to mark whether a cell is valid to enter
		// i.e the cell is not a wall and the cell has not been marked and it is not the ending
		if (charmap[rows][cols] != 'X' && charmap[rows][cols] != '-' && charmap[rows][cols]!='E') {
			// marks the cell as visited by changing it into a -
			charmap[rows][cols] = '-';
			// moves down and checks if it returns either true or false
			solved = recursiveSolve(rows + 1, cols);
			p='S';
				// if it returns false then it moves to the left
				// and checks if it is either true or false
				// and sets p to W
				if (!solved) {
					solved = recursiveSolve(rows, cols - 1);
					charmap[rows][cols] = ' ';
					p='W';
				}
				// moves up if the boolean solved is false
				// and then checks to see if solved is either true or false
				// and sets char p to that direction
				if (!solved) {
					solved = recursiveSolve(rows - 1, cols);
					charmap[rows][cols] = ' ';
					p='N';
				}
				// same thing as the previous if statements but just moves to the right
				// and p is set to E
				if (!solved) {
					solved = recursiveSolve(rows, cols + 1);
					charmap[rows][cols] = ' ';
					p='E';
				}
				// finally if solved is true it adds the direction of where it returned true to a stack and changes the char to a '*'
				if (solved) {
						charmap[rows][cols] = '*';
					directions.add(p);
				}
			}

	return solved;
	}
// this method basically orders the solution path so it appears in teh right order and prints it
	public void order(){
		while(!directions.isEmpty()){
			directional.push(directions.pop());
		}
		System.out.println(directional);

	}
	private void printMap() {
		for (char[] row:charmap) {
			for (char c:row) System.out.print(c);
			System.out.println();
		}
	}
	
	public static void main(String args[]) {new MazeSolver().loadMaze();}
}
