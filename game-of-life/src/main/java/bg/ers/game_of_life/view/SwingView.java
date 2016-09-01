package bg.ers.game_of_life.view;

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
	
	private 	JButton[][] 	buttons;
	private 	JButton 		startBtn;
	private 	JButton 		stopButton;
	private		JButton			clearGridButton;
	private 	JButton 		saveButton;
	private 	JButton 		loadButton;
	
	public SwingView() {
		grid = new Grid(MAX_SIZE, MAX_SIZE);
		buttons = new JButton[MAX_SIZE][MAX_SIZE];
		fileManager = FileManager.getInstance();
		this.state = IDLE;
		init();
	}
	
	private void init() {
		frame = new JFrame("Game of life");
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		gridPanel = SwingFactory.createPanel(this, LayoutType.GRID, MAX_SIZE, MAX_SIZE);
		buttonPanel = SwingFactory.createPanel(this, LayoutType.FLOW);
		
		startBtn = new JButton("Start");
		startBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				state = GAME_STARTED;
				runGame();
			}
			
		});
		
		buttonPanel.add(startBtn);
		
		stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				state = IDLE;
			}
			
		});
		
		buttonPanel.add(stopButton);
		
		clearGridButton = new JButton("Clear");
		clearGridButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				state = IDLE;
				updateGrid(new boolean[MAX_SIZE][MAX_SIZE]);
			}
			
		});
		
		buttonPanel.add(clearGridButton);
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (state == GAME_STARTED) {
					return;
				}
				fileManager.save("Record01", grid.getGridHolder());
			}
			
		});
		
		buttonPanel.add(saveButton);
		
		loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (state == GAME_STARTED) {
					return;
				}
				boolean[][] loadedGrid = (boolean[][]) fileManager.load("Record01");
				updateGrid(loadedGrid);
			}
			
		});
		
		buttonPanel.add(loadButton);
		
		drawGrid();
		
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
	
	private void addNewCell(final int x, final int y) {
		final JButton gridBtn = new JButton();
		gridBtn.setBackground(Color.white);
		
		gridBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (state != GAME_STARTED) {
					boolean isAlive = grid.at(x, y);
					if (isAlive) {
						gridBtn.setBackground(Color.white);
					} else {
						gridBtn.setBackground(Color.black);
					}
					grid.setAt(x, y, !isAlive);
				}
			}
			
		});
		
		buttons[x][y] = gridBtn;
		gridPanel.add(gridBtn);
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
	
	@Override
	public void setState(int x, int y, boolean state) {
		if(state) {
			buttons[x][y].setBackground(Color.black);
		} else {
			buttons[x][y].setBackground(Color.white);
		}
	}
	
	public static void main(String[] args) {
		new SwingView();
	}
	
}
