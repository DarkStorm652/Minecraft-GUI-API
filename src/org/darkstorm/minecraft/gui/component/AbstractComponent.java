package org.darkstorm.minecraft.gui.component;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.darkstorm.minecraft.gui.listener.ComponentListener;
import org.darkstorm.minecraft.gui.theme.*;

public abstract class AbstractComponent implements Component {
	private Container parent = null;
	private Theme theme;

	protected Rectangle area = new Rectangle(0, 0, 0, 0);
	protected ComponentUI ui;
	protected Color foreground, background;
	protected boolean enabled = true, visible = true;

	private List<ComponentListener> listeners = new CopyOnWriteArrayList<ComponentListener>();

	public void render() {
		if(ui == null)
			return;
		ui.render(this);
	}

	@Override
	public void update() {
		if(ui == null)
			return;
		ui.handleUpdate(this);
	}

	protected ComponentUI getUI() {
		return theme.getUIForComponent(this);
	}

	@Override
	public void onMousePress(int x, int y, int button) {
		if(ui != null) {
			for(Rectangle area : ui.getInteractableRegions(this)) {
				if(area.contains(x, y)) {
					ui.handleInteraction(this, new Point(x, y), button);
					break;
				}
			}
		}
	}

	@Override
	public void onMouseRelease(int x, int y, int button) {
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		Theme oldTheme = this.theme;
		this.theme = theme;
		if(theme == null) {
			ui = null;
			foreground = null;
			background = null;
			return;
		}

		ui = getUI();
		boolean changeArea;
		if(oldTheme != null) {
			Dimension defaultSize = oldTheme.getUIForComponent(this).getDefaultSize(this);
			changeArea = area.width == defaultSize.width && area.height == defaultSize.height;
		} else
			changeArea = area.equals(new Rectangle(0, 0, 0, 0));
		if(changeArea) {
			Dimension defaultSize = ui.getDefaultSize(this);
			area = new Rectangle(area.x, area.y, defaultSize.width, defaultSize.height);
		}
		foreground = ui.getDefaultForegroundColor(this);
		background = ui.getDefaultBackgroundColor(this);
	}

	public int getX() {
		return area.x;
	}

	public int getY() {
		return area.y;
	}

	public int getWidth() {
		return area.width;
	}

	public int getHeight() {
		return area.height;
	}

	public void setX(int x) {
		area.x = x;
	}

	public void setY(int y) {
		area.y = y;
	}

	public void setWidth(int width) {
		area.width = width;
	}

	public void setHeight(int height) {
		area.height = height;
	}

	@Override
	public Color getBackgroundColor() {
		return background;
	}

	@Override
	public Color getForegroundColor() {
		return foreground;
	}

	@Override
	public void setBackgroundColor(Color color) {
		background = color;
	}

	@Override
	public void setForegroundColor(Color color) {
		foreground = color;
	}

	public Point getLocation() {
		return area.getLocation();
	}

	public Dimension getSize() {
		return area.getSize();
	}

	public Rectangle getArea() {
		return area;
	}

	public Container getParent() {
		return parent;
	}

	public void setParent(Container parent) {
		if(!parent.hasChild(this) || (this.parent != null && this.parent.hasChild(this)))
			throw new IllegalArgumentException();
		this.parent = parent;
	}

	public void resize() {
		Dimension defaultDimension = ui.getDefaultSize(this);
		setWidth(defaultDimension.width);
		setHeight(defaultDimension.height);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		if(parent != null && !parent.isEnabled())
			this.enabled = false;
		else
			this.enabled = enabled;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		if(parent != null && !parent.isVisible())
			this.visible = false;
		else
			this.visible = visible;
	}

	protected void addListener(ComponentListener listener) {
		synchronized(listeners) {
			listeners.add(listener);
		}
	}

	protected void removeListener(ComponentListener listener) {
		synchronized(listeners) {
			listeners.remove(listener);
		}
	}

	protected ComponentListener[] getListeners() {
		synchronized(listeners) {
			return listeners.toArray(new ComponentListener[listeners.size()]);
		}
	}
}
