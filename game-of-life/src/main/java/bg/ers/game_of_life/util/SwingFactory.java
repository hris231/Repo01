package bg.ers.game_of_life.util;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SwingFactory {
	
	public enum LayoutType {
		FLOW, GRID
	}
	
	public static JComboBox<Object> createCombo (Object[] list, JPanel panel) {
		JComboBox<Object> combo = new JComboBox<>(list);
		setSize(combo, ComponentSize.COMBO_BOX, 75, 25);
		centerAlingment(combo);
		panel.add(combo);
		return combo;
	}
	
	public static JLabel createLabel(String name, JPanel panel) {
		JLabel lbl = new JLabel(name);
		panel.add(lbl);
		return lbl;
	}
	
	public static JButton createButton(String name, JPanel panel) {
		JButton btn = new JButton(name);
		btn.setPreferredSize(new Dimension(100, 25));
		setSize(btn, ComponentSize.BUTTON, 150, 25);
		centerAlingment(btn);
		panel.add(btn);
		return btn;
	}
	
	public static JButton createButton(String name, JPanel panel, ActionListener actionListener) {
		JButton btn = new JButton(name);
		btn.addActionListener(actionListener);
		panel.add(btn);
		return btn;
	}
	
	public static JPanel createPanel() {
		JPanel panel = new JPanel();
		centerAlingment(panel);
		return panel;
	}
	
	public static JPanel createPanel(JPanel parent, LayoutType layoutType, int... args) {
		JPanel panel = new JPanel();
		LayoutManager layout = null;
		switch(layoutType) {	
			case FLOW:
				layout = new FlowLayout();
				break;
			case GRID:
				if (args.length != 2) {
					throw new IllegalArgumentException();
				}
				layout = new GridLayout(args[0], args[1]);
				break;
			default:
				break;
		}
		panel.setLayout(layout);
		centerAlingment(panel);
		if (parent != null) {
			parent.add(panel);
		}
		return panel;
	}
	
	private static void centerAlingment(JComponent component) {
		component.setAlignmentX(JFrame.CENTER_ALIGNMENT);
		component.setAlignmentY(JFrame.CENTER_ALIGNMENT);
	}
	
	private static void setSize(JComponent component, ComponentSize compType, int width, int height) {
		component.setMinimumSize(compType.minSize);
		component.setMaximumSize(compType.maxSize);
		component.setPreferredSize(new Dimension(width, height));
	}
}
