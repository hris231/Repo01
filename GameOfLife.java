package bg.ers.game_of_life;

import bg.ers.game_of_life.model.Grid;

public class GameOfLife
{
    public static void main(String[] args) {
		try {
			int y = 2;
			Grid grid = new Grid(6, 6);
			for (int x = 2; x < 4; x++) {
				for (int size = y + 3; y < size; y++) {
					grid.setAt(x, y, true);
				}
				y = 1;
			}
			grid.startGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
