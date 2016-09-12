package bg.ers.game_of_life.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class FileManager {
	
	private static final String SAVE_DIRECTORY = "D:\\Game of Life\\Save\\";
	private static FileManager instance = new FileManager();
	
	private FileManager() {
		
	}
	
	public static FileManager getInstance() {
		return instance;
	}
	
	public void save(String record, Object obj) {
		boolean[][] grid = (boolean[][])obj;
		if (grid[0] == null) {
			throw new IllegalArgumentException();
		}
		String entryRow = rowToString(grid);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new File(SAVE_DIRECTORY + record + ".txt"));
			pw.write(entryRow);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}	
	}
	
	public Object load(String record) {
		BufferedReader br = null;
		String line = null;
		String[] row = null;
		boolean[][] grid = null;
		try {
			br = new BufferedReader(new FileReader(SAVE_DIRECTORY + record + ".txt"));
			line = br.readLine();
			row = line.split("]");
			grid = new boolean[row.length][];
			for (int i = 0; i < row.length; i++) {
				grid[i] = (parseRow(row[i]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return grid;
	}
	
	private String rowToString(boolean[][] grid) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < grid.length; i++) {
			sb.append(Arrays.toString(grid[i]));
		}
		return sb.toString();
	}
	
	private boolean[] parseRow(String row) {
		row = row.substring(1);
		String[] values = row.split(", ");
		boolean[] result = new boolean[values.length];
		for (int i = 0; i < values.length; i++) {
			result[i] = (Boolean.parseBoolean(values[i]));
		}
		return result;
	}

}
