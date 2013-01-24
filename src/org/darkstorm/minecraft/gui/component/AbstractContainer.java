package org.darkstorm.minecraft.gui.component;

import java.util.*;

import java.awt.Rectangle;

import org.darkstorm.minecraft.gui.layout.*;
import org.darkstorm.minecraft.gui.theme.Theme;

public abstract class AbstractContainer extends AbstractComponent implements
		Container {
	private final Map<Component, Constraint[]> children = new LinkedHashMap<Component, Constraint[]>();

	private LayoutManager layoutManager = new BasicLayoutManager();

	@Override
	public void render() {
		super.render();

		synchronized(children) {
			for(Component child : children.keySet())
				child.render();
		}
	}

	public LayoutManager getLayoutManager() {
		return layoutManager;
	}

	public void setLayoutManager(LayoutManager layoutManager) {
		if(layoutManager == null)
			layoutManager = new BasicLayoutManager();
		this.layoutManager = layoutManager;

		layoutChildren();
	}

	public Component[] getChildren() {
		synchronized(children) {
			return children.keySet().toArray(new Component[children.size()]);
		}
	}

	public void add(Component child, Constraint... constraints) {
		synchronized(children) {
			Container parent = child.getParent();
			if(parent != null && parent.hasChild(child))
				parent.remove(child);
			children.put(child, constraints);
			if(!enabled)
				child.setEnabled(false);
			if(!visible)
				child.setVisible(false);
			child.setParent(this);
			child.setTheme(getTheme());

			layoutChildren();
		}
	}

	@Override
	public Constraint[] getConstraints(Component child) {
		if(child == null)
			throw new NullPointerException();
		synchronized(children) {
			Constraint[] constraints = children.get(child);
			return constraints != null ? constraints : new Constraint[0];
		}
	}

	public Component getChildAt(int x, int y) {
		synchronized(children) {
			for(Component child : children.keySet())
				if(child.getArea().contains(x, y))
					return child;
			return null;
		}
	}

	public boolean remove(Component child) {
		synchronized(children) {
			if(children.remove(child) != null) {
				layoutChildren();
				return true;
			} else
				return false;
		}
	}

	public boolean hasChild(Component child) {
		synchronized(children) {
			return children.get(child) != null;
		}
	}

	@Override
	public void setTheme(Theme theme) {
		super.setTheme(theme);

		synchronized(children) {
			for(Component child : children.keySet())
				child.setTheme(theme);
		}
	}

	@Override
	public void layoutChildren() {
		synchronized(children) {
			Component[] components = children.keySet().toArray(
					new Component[children.size()]);
			Rectangle[] areas = new Rectangle[components.length];
			for(int i = 0; i < components.length; i++)
				areas[i] = components[i].getArea();
			Constraint[][] allConstraints = children.values().toArray(
					new Constraint[children.size()][]);
			if(getTheme() != null)
				layoutManager.reposition(ui.getChildRenderArea(this), areas,
						allConstraints);
			for(Component child : components)
				if(child instanceof Container)
					((Container) child).layoutChildren();
		}
	}

	@Override
	public void onMousePress(int x, int y, int button) {
		super.onMousePress(x, y, button);
		synchronized(children) {
			for(Component child : children.keySet()) {
				if(!child.isVisible())
					continue;
				if(!child.getArea().contains(x, y)) {
					for(Rectangle area : child.getTheme()
							.getUIForComponent(child)
							.getInteractableRegions(child)) {
						if(area.contains(x - child.getX(), y - child.getY())) {
							child.onMousePress(x - child.getX(),
									y - child.getY(), button);
							return;
						}
					}
				}
			}
			for(Component child : children.keySet()) {
				if(!child.isVisible())
					continue;
				if(child.getArea().contains(x, y)) {
					child.onMousePress(x - child.getX(), y - child.getY(),
							button);
					return;
				}

			}
		}
	}

	@Override
	public void onMouseRelease(int x, int y, int button) {
		super.onMouseRelease(x, y, button);
		synchronized(children) {
			for(Component child : children.keySet()) {
				if(!child.isVisible())
					continue;
				if(!child.getArea().contains(x, y)) {
					for(Rectangle area : child.getTheme()
							.getUIForComponent(child)
							.getInteractableRegions(child)) {
						if(area.contains(x - child.getX(), y - child.getY())) {
							child.onMouseRelease(x - child.getX(),
									y - child.getY(), button);
							return;
						}
					}
				}
			}
			for(Component child : children.keySet()) {
				if(!child.isVisible())
					continue;
				if(child.getArea().contains(x, y)) {
					child.onMouseRelease(x - child.getX(), y - child.getY(),
							button);
					return;
				}

			}
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		enabled = isEnabled();
		synchronized(children) {
			for(Component child : children.keySet())
				child.setEnabled(enabled);
		}
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		visible = isVisible();
		synchronized(children) {
			for(Component child : children.keySet())
				child.setVisible(visible);
		}
	}

	@Override
	public void update() {
		for(Component child : getChildren())
			child.update();
	}
}
