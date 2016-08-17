
public class GameOfLifes {
	
	private static final int GRID_X = 6;
	private static final int GRID_Y = 6;
	
	private static final char ALIVE_CELL = '+';
	private static final char DEAD_CELL = '-';
	
	private static final long INTERVAL = 1000;
	
	private static boolean[][] gridHolder = new boolean[GRID_X][GRID_Y];
	private static byte[][] neighboursHolder = new byte[GRID_X][GRID_Y];
	private static char[] gridRow = new char[GRID_X];
	
	private static void remapGrid() {
		
		updateNeighboursCount();
		
		for (int x = 0; x < GRID_X; x++) {
			for (int y = 0; y < GRID_Y; y++) {
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
				neighboursHolder[x][y] = 0;
			}
			System.out.println(gridRow);
		}
		System.out.println("\n");
	}
	
	public static void updateNeighboursCount() {
		
		if (gridHolder[0][0]) {
			neighboursHolder[0][1]++;
			neighboursHolder[1][0]++;
			neighboursHolder[1][1]++;
		}
		
		if (gridHolder[GRID_X - 1][0]) {
			neighboursHolder[GRID_X - 2][0]++;
			neighboursHolder[GRID_X - 1][1]++;
			neighboursHolder[GRID_X - 2][1]++;
		}
		
		if (gridHolder[0][GRID_Y - 1]) {
			neighboursHolder[0][GRID_Y - 2]++;
			neighboursHolder[1][GRID_Y - 1]++;
			neighboursHolder[1][GRID_Y - 2]++;
		}
		
		if (gridHolder[GRID_X - 1][GRID_Y - 1]) {
			neighboursHolder[GRID_X - 2][GRID_Y - 1]++;
			neighboursHolder[GRID_X - 1][GRID_Y - 2]++;
			neighboursHolder[GRID_X - 2][GRID_Y - 2]++;
		}
		
		for (int x = 1; x < GRID_X - 1; x++) {
			if (gridHolder[x][0]) {
				neighboursHolder[x - 1][0]++;
				neighboursHolder[x + 1][0]++;
				neighboursHolder[x][1]++;
			}
		}
		
		for (int x = 1; x < GRID_X - 1; x++) {
			if (gridHolder[x][GRID_Y - 1]) {
				neighboursHolder[x - 1][GRID_Y - 1]++;
				neighboursHolder[x + 1][GRID_Y - 1]++;
				neighboursHolder[x][GRID_Y - 2]++;
			}
		}
		
		for (int y = 1; y < GRID_Y - 1; y++) {
			if (gridHolder[0][y]) {
				neighboursHolder[0][y - 1]++;
				neighboursHolder[0][y + 1]++;
				neighboursHolder[1][y]++;
			}
		}
		
		for (int y = 1; y < GRID_Y - 1; y++) {
			if (gridHolder[0][y]) {
				neighboursHolder[0][y - 1]++;
				neighboursHolder[0][y + 1]++;
				neighboursHolder[1][y]++;
			}
		}
		
		for (int x = 1; x < GRID_X - 1; x++) {
			for (int y = 1; y < GRID_Y - 1; y++) {
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
		}
		
	}
	
	public static void initGrid() {
		gridHolder[2][2] = true;
		gridHolder[2][3] = true;
		gridHolder[2][4] = true;
		gridHolder[3][1] = true;
		gridHolder[3][2] = true;
		gridHolder[3][3] = true;
	}
	
	public static void main(String[] args) {
		initGrid();
		
		while (true) {
			remapGrid();
			try {
				Thread.sleep(INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
