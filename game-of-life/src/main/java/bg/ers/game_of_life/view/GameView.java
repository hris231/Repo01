package bg.ers.game_of_life.view;

import java.util.Scanner;
import java.util.function.BiConsumer;

import bg.ers.game_of_life.model.Grid;

public class GameView {
	
	private static final int INPUT_ERROR 		= 	0;
	private static final int INPUT_OK 			= 	1;
	private static final int INPUT_TERMINATE 	= 	2;
	
	private Grid gameGrid;
	
	private void readUserInput(String msg, int inputCode, Scanner sc, BiConsumer<Integer, Integer> func) {
		int returnCode = INPUT_ERROR;
		int[] inputHolder = new int[2];
		do {
			try {
				System.out.println(msg);
				returnCode = readLine(sc, inputHolder);
				if (returnCode == INPUT_OK) {
					func(inputHolder[0], inputHolder[1]);
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Invalid position.");
			} catch (IllegalArgumentException e) {
				System.out.println("X and Y must be >= 3.");
			}
		} while (returnCode != inputCode);
	}
	
	private void performAction(int inputCode, int inputHolder[]) {
		if (inputCode == INPUT_TERMINATE) {
			gameGrid.setAt(inputHolder[0], inputHolder[1]);
		} else {
			gameGrid = new Grid(inputHolder[0], inputHolder[1]);
		}
	}
	
	private int readLine(Scanner sc, int[] inputHolder) {
		int errCode = INPUT_ERROR;
		String[] props = null;
		String line;
		try {
			line = sc.nextLine();
			props = line.split(" ");
			inputHolder[0] = Integer.parseInt(props[0]);
			inputHolder[1] = Integer.parseInt(props[1]);
			errCode = INPUT_OK;
		} catch(NumberFormatException e) {	
			if (props[0].toUpperCase().equals("N")) {
				return INPUT_TERMINATE;
			}
			System.out.println("Wrong input.");
		}
		return errCode;
	}
	
	public void startGame() {
		System.out.println("Welcome to Game of Life.");
		Scanner sc = new Scanner(System.in);
		String gridInputMsg = "Input the desired grid size (width and height).";
		String cellInputMsg = "Input x and y of the cell (press \"N\" to terminate): ";
		readUserInput(gridInputMsg, INPUT_OK, sc);
		readUserInput(cellInputMsg, INPUT_TERMINATE, sc);
		sc.close();
		gameGrid.startGame();
	}
	
}
