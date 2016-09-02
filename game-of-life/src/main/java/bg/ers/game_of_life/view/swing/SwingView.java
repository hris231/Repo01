package bg.ers.game_of_life.view.swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import bg.ers.game_of_life.model.AbstractView;
import bg.ers.game_of_life.model.FileManager;
import bg.ers.game_of_life.model.Grid;
import bg.ers.game_of_life.util.SwingFactory;
import bg.ers.game_of_life.util.SwingFactory.LayoutType;

public class SwingView extends JPanel implements AbstractView {
	
	public 		static 	final 	int 	IDLE 			= 	0;
	public 		static	final 	int 	GAME_STARTED 	= 	1;

	private 	static 	final 	int 	GRID_WIDTH 	= 	600;
	private 	static 	final 	int 	GRID_HEIGHT 	= 	400;
	private 	static 	final 	int 	MAX_SIZE 		= 	10;
	
	private 	Grid 			grid;
	private 	FileManager 	fileManager;
	private 	int 			state;
	
	private 	JFrame 			frame;
	
	private 	JPanel 			gridPanel;
	private		JPanel			buttonPanel;
	
	private 	JButton[][] 	gridButtons;
	private 	JButton 		startStopBtn;
	private		JButton			clearGridBtn;
	private 	JButton 		saveBtn;
	private 	JButton 		loadBtn;
	
	public SwingView() {
		this.grid = new Grid(MAX_SIZE, MAX_SIZE);
		this.gridButtons = new JButton[MAX_SIZE][MAX_SIZE];
		this.fileManager = FileManager.getInstance();
		this.state = IDLE;
		init();
	}
	
	private void init() {
		frame = new JFrame("Game of life");
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		gridPanel = SwingFactory.createPanel(this, LayoutType.GRID, MAX_SIZE, MAX_SIZE);
		buttonPanel = SwingFactory.createPanel(this, LayoutType.FLOW);
		
		startStopBtn = SwingFactory.createButton("Start", buttonPanel, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(state == IDLE) {
					state = GAME_STARTED;
				} else {
					state = IDLE;
				}
				transition();
			}
		});
		
		clearGridBtn = SwingFactory.createButton("Clear", buttonPanel, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateGrid(new boolean[MAX_SIZE][MAX_SIZE]);
			}
		});
		
		saveBtn = SwingFactory.createButton("Save", buttonPanel, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				fileManager.save("Record01", grid.getGridHolder());
			}
		});
		
		loadBtn = SwingFactory.createButton("Load", buttonPanel, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean[][] loadedGrid = (boolean[][]) fileManager.load("Record01");
				updateGrid(loadedGrid);
			}
		});
		
		drawGrid();
		transition();
		
		frame.add(this);
		frame.setSize(GRID_WIDTH, GRID_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private void runGame() {
		new Thread(() -> {
			while (state == GAME_STARTED) {
				grid.calcNewGeneration(this);
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private void addNewCell(int x, int y) {	
		gridButtons[x][y] = SwingFactory.createButton(null, gridPanel, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean cellState = grid.at(x, y);
				setState(x, y, !cellState);
				grid.setAt(x, y, !cellState);
			}
		});
		gridButtons[x][y].setBackground(Color.white);
	}
	
	private void drawGrid() {
		for (int x = 0; x < MAX_SIZE; x++) {
			for (int y = 0; y < MAX_SIZE; y++) {
				addNewCell(x, y);
			}
		}
	}
	
	private void updateGrid(boolean[][] grid) {
		this.grid = new Grid(grid);
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				setState(x, y, grid[x][y]);
			}
		}
	}
	
	private void transition() {
		switch (this.state) {
			case IDLE:
				startStopBtn.setText("Start");
				buttonsState(true);
				break;
			case GAME_STARTED:
				startStopBtn.setText("Stop");
				buttonsState(false);
				runGame();
				break;
		}
	}
	
	private void buttonsState(boolean state) {
		clearGridBtn.setEnabled(state);
		loadBtn.setEnabled(state);
		saveBtn.setEnabled(state);
	}
	
	@Override
	public void setState(int x, int y, boolean state) {
		if(state) {
			gridButtons[x][y].setBackground(Color.black);
		} else {
			gridButtons[x][y].setBackground(Color.white);
		}
	}
	
	public static void main(String[] args) {
		new SwingView();
	}
	
}
