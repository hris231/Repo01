package bg.ers.game_of_life.model;

public class Grid {
	
	private static final char ALIVE_CELL = '+';
	private static final char DEAD_CELL = '-';
	
	private int width;
	private int height;
	private boolean[][] gridHolder;
	private byte[][] neighboursHolder;
	
	public Grid(int width, int height) {
		if (width < 3 || height < 3) {
			throw new IllegalArgumentException("width and height must be >= 3");
		}
		this.width = width;
		this.height = height;
		this.gridHolder = new boolean[width][height];
		this.neighboursHolder = new byte[width + 2][height + 2];
	}

	private void remapGrid() {
		calcNeighboursCount();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				setDeadOrAlive(x, y);
				neighboursHolder[x + 1][y + 1] = 0;
			}
			System.out.println();
		}
		System.out.println("\n");
	}
	
	private void setDeadOrAlive(int x, int y) {
		switch(neighboursHolder[x + 1][y + 1]) {
			case 2:
				if (!gridHolder[x][y]) {
					System.out.print(DEAD_CELL);
					break;
				}
			case 3:
				gridHolder[x][y] = true;
				System.out.print(ALIVE_CELL);
				break;
			default:	
				gridHolder[x][y] = false;
				System.out.print(DEAD_CELL);
		}
	}
	
	private void updateCellCounter(int x, int y) {
		for (int i = x; i < x + 3; i++) {
			for (int j = y; j < y + 3; j++) {
				neighboursHolder[i][j]++;
			}
		}
		neighboursHolder[x + 1][y + 1]--;
	}
	
	private void calcNeighboursCount() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (gridHolder[x][y]) {
					updateCellCounter(x, y);
				}
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
	
	public void setAt(int x, int y) {
		if (x < 0 || x > width || y < 0 || y > height) {
			throw new ArrayIndexOutOfBoundsException();
		}
		gridHolder[x][y] = true;
	}
}