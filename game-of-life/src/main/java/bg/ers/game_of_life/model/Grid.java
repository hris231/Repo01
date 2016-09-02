package bg.ers.game_of_life.model;

public class Grid {
	
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
	
	public Grid(boolean[][] grid) {
		this.height = grid.length;
		this.width = grid[0].length;
		this.gridHolder = grid;
		this.neighboursHolder = new byte[width + 2][height + 2];
	}

	public void calcNewGeneration(AbstractView view) {
		calcNeighboursCount();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				boolean newState = setDeadOrAlive(x, y);
				view.setState(x, y, newState);
				neighboursHolder[x + 1][y + 1] = 0;
			}
		}
	}
	
	private boolean setDeadOrAlive(int x, int y) {
		switch(neighboursHolder[x + 1][y + 1]) {
			case 2:
				if (!gridHolder[x][y]) {
					//System.out.print(DEAD_CELL);
					return false;
				}
			case 3:
				gridHolder[x][y] = true;
				//System.out.print(ALIVE_CELL);
				return true;
			default:	
				gridHolder[x][y] = false;
				//System.out.print(DEAD_CELL);
				return false;
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
	
	public void setAt(int x, int y, boolean state) {
		if (x < 0 || x > width || y < 0 || y > height) {
			throw new ArrayIndexOutOfBoundsException();
		}
		gridHolder[x][y] = state;
	}
	
	public boolean at(int x, int y) {
		if (x < 0 || x > width || y < 0 || y > height) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return gridHolder[x][y];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean[][] getGridHolder() {
		return gridHolder;
	}

}