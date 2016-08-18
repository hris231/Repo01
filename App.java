package bg.ers.app;

import bg.ers.app.grid.*;

public class App 
{
    public static void main( String[] args )
    {
		try {
			Grid grid = new Grid(6, 6);
			grid.setAt(2, 2, true);
			grid.setAt(2, 3, true);
			grid.setAt(2, 4, true);
			grid.setAt(3, 1, true);
			grid.setAt(3, 2, true);
			grid.setAt(3, 3, true);
			grid.startGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
