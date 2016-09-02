package bg.ers.game_of_life;

import bg.ers.game_of_life.view.console.ConsoleView;

public class GameOfLife {
	
    public static void main(String[] args) {
		try {
			ConsoleView gv = new ConsoleView();
			gv.startGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
