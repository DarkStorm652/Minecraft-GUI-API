package org.darkstorm.minecraft.gui.component;

import java.awt.*;

import org.darkstorm.minecraft.gui.theme.Theme;

public interface Component {
	public Theme getTheme();

	public void setTheme(Theme theme);

	public void render();

	public void update();

	public int getX();

	public int getY();

	public int getWidth();

	public int getHeight();

	public void setX(int x);

	public void setY(int y);

	public void setWidth(int width);

	public void setHeight(int height);

	public Point getLocation();

	public Dimension getSize();

	public Rectangle getArea();

	public Container getParent();

	public Color getBackgroundColor();

	public Color getForegroundColor();

	public void setBackgroundColor(Color color);

	public void setForegroundColor(Color color);

	public void setParent(Container parent);

	public void onMousePress(int x, int y, int button);

	public void onMouseRelease(int x, int y, int button);

	public void resize();

	public boolean isVisible();

	public void setVisible(boolean visible);

	public boolean isEnabled();

	public void setEnabled(boolean enabled);
}
