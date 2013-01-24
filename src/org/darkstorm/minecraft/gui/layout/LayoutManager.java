package org.darkstorm.minecraft.gui.layout;

import java.awt.*;

public interface LayoutManager {
	public void reposition(Rectangle area, Rectangle[] componentAreas,
			Constraint[][] constraints);

	public Dimension getOptimalPositionedSize(Rectangle[] componentAreas,
			Constraint[][] constraints);
}
