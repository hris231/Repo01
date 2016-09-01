package bg.ers.game_of_life;

import bg.ers.game_of_life.view.GameView;

public class GameOfLife {
	
    public static void main(String[] args) {
		try {
			GameView gv = new GameView();
			gv.startGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
