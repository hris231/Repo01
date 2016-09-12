package bg.ers.game_of_life.view.swing;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bg.ers.game_of_life.model.AbstractView;
import bg.ers.game_of_life.model.FileManager;
import bg.ers.game_of_life.model.Grid;
import bg.ers.game_of_life.util.SwingFactory;
import bg.ers.game_of_life.util.SwingFactory.LayoutType;

public class SwingView extends JPanel implements AbstractView {
	
	public		static	final	int		PREPARE			=	0;
	public 		static 	final 	int 	READY 			= 	1;	
	public 		static	final 	int 	GAME_STARTED 	= 	2;

	private 	static 	final 	int 	GRID_WIDTH 	= 	600;
	private 	static 	final 	int 	GRID_HEIGHT 	= 	400;
	private 	static  		int		GRID_X;
	private 	static  		int		GRID_Y;
	
	private 	Grid 			grid;
	private 	FileManager 	fileManager;
	private 	int 			state;
	
	private 	JFrame 			frame;
	
	private 	JPanel 			gridPanel;
	private		JPanel			inputParamsPanel;
	private		JPanel			buttonPanel;
	
	private		JComboBox		widthCmb;
	private		JComboBox		heightCmb;
	
	private		JLabel			widthLbl;
	private		JLabel			heightLbl;
	
	private 	JButton[][] 	gridButtons;
	private		JButton			lockUnlockSettings;
	private 	JButton 		startStopBtn;
	private		JButton			clearGridBtn;
	private 	JButton 		saveBtn;
	private 	JButton 		loadBtn;
	
	public SwingView() {
		this.fileManager = FileManager.getInstance();
		this.state = PREPARE;
		init();
	}
	
	private void init() {
		frame = new JFrame("Game of life");
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		gridPanel = SwingFactory.createPanel(this, LayoutType.GRID, 1, 1);
		createInputParamsPanel();
		createButtonPanel();
		
		transition();
		
		frame.add(this);
		frame.setSize(GRID_WIDTH, GRID_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private void createButtonPanel() {
			buttonPanel = SwingFactory.createPanel(this, LayoutType.FLOW);
			
			startStopBtn = SwingFactory.createButton("Start", buttonPanel, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(state == READY) {
					state = GAME_STARTED;
				} else {
					state = READY;
				}
				transition();
			}
		});
		
		clearGridBtn = SwingFactory.createButton("Clear", buttonPanel, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateGrid(new boolean[GRID_X][GRID_Y]);
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
	}
	
	private void createInputParamsPanel() {
		inputParamsPanel = SwingFactory.createPanel(this, LayoutType.FLOW);
		widthLbl = SwingFactory.createLabel("Width", inputParamsPanel);
		widthCmb = SwingFactory.createCombo(new Integer[]{3, 4, 5, 6, 7, 8, 9, 10}, inputParamsPanel);
		heightLbl = SwingFactory.createLabel("Height", inputParamsPanel);
		heightCmb = SwingFactory.createCombo(new Integer[]{3, 4, 5, 6, 7, 8, 9, 10}, inputParamsPanel);
		lockUnlockSettings = SwingFactory.createButton("Lock", inputParamsPanel, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (state == PREPARE) {
					state = READY;
					GRID_X = (Integer)widthCmb.getSelectedItem();
					GRID_Y = (Integer)heightCmb.getSelectedItem();
					createGrid();
				} else {
					state = PREPARE;
				}
				transition();
			}
		});
	}
	
	private void createGridPanel() {
		gridPanel.setLayout(new GridLayout(GRID_X, GRID_Y));
		for (int x = 0; x < GRID_X; x++) {
			for (int y = 0; y < GRID_Y; y++) {
				addNewCell(x, y);
			}
		}
	}
	
	private void addNewCell(int x, int y) {	
		gridButtons[x][y] = SwingFactory.createButton(null, gridPanel, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (state == READY) {
					boolean cellState = grid.at(x, y);
					setState(x, y, !cellState);
					grid.setAt(x, y, !cellState);
				}
			}
		});
		gridButtons[x][y].setBackground(Color.white);
	}
	
	private void updateGrid(boolean[][] grid) {	
		int width = grid.length;
		int height = grid[0].length;
		if (GRID_X != width || GRID_Y != height) {
			GRID_X = width;
			GRID_Y = height;
			widthCmb.setSelectedIndex(width - 3);
			heightCmb.setSelectedIndex(height - 3);
			createGrid();
		}
		this.grid = new Grid(grid);
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[0].length; y++) {
				setState(x, y, grid[x][y]);
			}
		}
	}
	
	private void createGrid() {
		this.grid = new Grid(GRID_X, GRID_Y);
		this.gridButtons = new JButton[GRID_X][GRID_Y];
		gridPanel.removeAll();
		createGridPanel();
		gridPanel.revalidate();
	}
	
	private void transition() {
		switch (this.state) {
			case PREPARE:
				lockUnlockSettings.setText("Lock");
				setButtonsState(false);
				break;
			case READY:
				startStopBtn.setText("Start");
				lockUnlockSettings.setText("Unlock");
				setButtonsState(true);
				break;
			case GAME_STARTED:
				startStopBtn.setText("Stop");
				setButtonsState(false);	
				runGame();
				break;
		}
	}
	
	private void setButtonsState(boolean state) {
		if (this.state == GAME_STARTED) {
			widthCmb.setEnabled(state);
			heightCmb.setEnabled(state);
			startStopBtn.setEnabled(!state);
		} else {
			widthCmb.setEnabled(!state);
			heightCmb.setEnabled(!state);
			startStopBtn.setEnabled(state);
		}
		clearGridBtn.setEnabled(state);
		loadBtn.setEnabled(state);
		saveBtn.setEnabled(state);
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
