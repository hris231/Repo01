package bg.ers.app.grid;

public class Grid {
	
	private static final char ALIVE_CELL = '+';
	private static final char DEAD_CELL = '-';
	
	private int gridX;
	private int gridY;
	private boolean[][] gridHolder;
	private byte[][] neighboursHolder;
	private char[] gridRow;
	
	public Grid(int x, int y) {
		if (x < 3 || y < 3) {
			throw new IllegalArgumentException("x and y must be >= 3");
		}
		this.gridX = x;
		this.gridY = y;
		this.gridHolder = new boolean[x][y];
		this.neighboursHolder = new byte[x][y];
		this.gridRow = new char[x];
	}

	private void remapGrid() {	
		calcNeighboursCount();
		for (int x = 0; x < gridX; x++) {
			for (int y = 0; y < gridY; y++) {
				setDeadOrAlive(x, y);
				neighboursHolder[x][y] = 0;
			}
			System.out.println(gridRow);
		}
		System.out.println("\n");
	}
	
	private void setDeadOrAlive(int x, int y) {
		if (gridHolder[x][y]) {
			if (neighboursHolder[x][y] < 2 || neighboursHolder[x][y] > 3) {
				gridHolder[x][y] = false;
				gridRow[y] = DEAD_CELL;
			} else {
				gridRow[y] = ALIVE_CELL;
			}
		} else {
			if (neighboursHolder[x][y] == 3) {
				gridHolder[x][y] = true;
				gridRow[y] = ALIVE_CELL;
			} else {
				gridRow[y] = DEAD_CELL;
			}
		}
	}
	
	void updateCellCounter(int x, int y) {
		if (gridHolder[x][y]) {
			//first row
			neighboursHolder[x - 1][y - 1]++;
			neighboursHolder[x][y - 1]++;
			neighboursHolder[x + 1][y - 1]++;
			//middle row
			neighboursHolder[x - 1][y]++;
			neighboursHolder[x + 1][y]++;
			//last row
			neighboursHolder[x - 1][y + 1]++;
			neighboursHolder[x][y + 1]++;
			neighboursHolder[x+ 1][y + 1]++;
		}
	}
	
	private void calcNeighboursCount() {
		if (gridHolder[0][0]) {
			neighboursHolder[0][1]++;
			neighboursHolder[1][0]++;
			neighboursHolder[1][1]++;
		}
		
		if (gridHolder[gridX - 1][0]) {
			neighboursHolder[gridX - 2][0]++;
			neighboursHolder[gridX - 1][1]++;
			neighboursHolder[gridX - 2][1]++;
		}
		
		if (gridHolder[0][gridY - 1]) {
			neighboursHolder[0][gridY - 2]++;
			neighboursHolder[1][gridY - 1]++;
			neighboursHolder[1][gridY - 2]++;
		}
		
		if (gridHolder[gridX - 1][gridY - 1]) {
			neighboursHolder[gridX - 2][gridY - 1]++;
			neighboursHolder[gridX - 1][gridY - 2]++;
			neighboursHolder[gridX - 2][gridY - 2]++;
		}
		
		for (int x = 1; x < gridX - 1; x++) {
			if (gridHolder[x][0]) {
				neighboursHolder[x - 1][0]++;
				neighboursHolder[x + 1][0]++;
				neighboursHolder[x][1]++;
			}
		}
		
		for (int x = 1; x < gridX - 1; x++) {
			if (gridHolder[x][gridY - 1]) {
				neighboursHolder[x - 1][gridY - 1]++;
				neighboursHolder[x + 1][gridY - 1]++;
				neighboursHolder[x][gridY - 2]++;
			}
		}
		
		for (int y = 1; y < gridY - 1; y++) {
			if (gridHolder[0][y]) {
				neighboursHolder[0][y - 1]++;
				neighboursHolder[0][y + 1]++;
				neighboursHolder[1][y]++;
			}
		}
		
		for (int y = 1; y < gridY - 1; y++) {
			if (gridHolder[0][y]) {
				neighboursHolder[0][y - 1]++;
				neighboursHolder[0][y + 1]++;
				neighboursHolder[1][y]++;
			}
		}
		
		for (int x = 1; x < gridX - 1; x++) {
			for (int y = 1; y < gridY - 1; y++) {
				updateCellCounter(x, y);
			}
		}
	}
	
	public void startGame() {
		while (true) {
			remapGrid();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setAt(int x, int y, boolean value) {
		if (x < 0 || x > gridX || y < 0 || y > gridY) {
			throw new ArrayIndexOutOfBoundsException();
		}
		gridHolder[x][y] = value;
	}

}