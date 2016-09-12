package bg.ers.game_of_life.util;

import java.awt.Dimension;

public enum ComponentSize {
	
	COMBO_BOX	(new Dimension(50, 25), new Dimension(100, 50)),
	BUTTON		(new Dimension(150, 25), new Dimension(200, 50));
	
	public Dimension minSize;
	public Dimension maxSize;
	
	ComponentSize(Dimension minSize, Dimension maxSize) {
		this.minSize = minSize;
		this.maxSize = maxSize;
	}
	
}
