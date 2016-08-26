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
		int x, y;
		String[] props = null;
		String line;
		do {
			try {
				System.out.println(msg);
				line = sc.nextLine();
				props = line.split(" ");
				x = Integer.parseInt(props[0]);
				y = Integer.parseInt(props[1]);
				func.accept(x, y);
				returnCode = INPUT_OK;
			} catch(NumberFormatException e) {	
				if (props[0].toUpperCase().equals("N")) {
					return;
				}
				System.out.println("Wrong input.");
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("Invalid position.");
			} catch (IllegalArgumentException e) {
				System.out.println("X and Y must be >= 3.");
			}
		} while (returnCode != inputCode);
	}
	
	public void startGame() {
		System.out.println("Welcome to Game of Life.");
		Scanner sc = new Scanner(System.in);
		String gridInputMsg = "Input the width followed by space(\" \") and height of the grid.";
		String cellInputMsg = "Input x followed by space(\" \") and y of the cell (input \"N or n\" to terminate): ";
		BiConsumer<Integer, Integer> createGrid = (x, y) -> gameGrid = new Grid(x, y);
		BiConsumer<Integer, Integer> setGridCell = (x, y) -> gameGrid.setAt(x, y);
		readUserInput(gridInputMsg, INPUT_OK, sc, createGrid);
		readUserInput(cellInputMsg, INPUT_TERMINATE, sc, setGridCell);
		sc.close();
		gameGrid.startGame();
	}
	
}
