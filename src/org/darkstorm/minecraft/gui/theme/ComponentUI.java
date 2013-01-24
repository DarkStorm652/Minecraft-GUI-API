package org.darkstorm.minecraft.gui.theme;

import java.awt.*;

import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Container;

public interface ComponentUI {
	public void render(Component component);

	public Rectangle getChildRenderArea(Container container);

	public Dimension getDefaultSize(Component component);

	public Color getDefaultBackgroundColor(Component component);

	public Color getDefaultForegroundColor(Component component);

	public Rectangle[] getInteractableRegions(Component component);

	public void handleInteraction(Component component, Point location,
			int button);
}
