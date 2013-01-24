package org.darkstorm.minecraft.gui.theme;

import java.awt.*;

import org.lwjgl.opengl.GL11;

import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Container;

public abstract class AbstractComponentUI<T extends Component> implements
		ComponentUI {
	protected final Class<T> handledComponentClass;
	protected Color foreground, background;

	public AbstractComponentUI(Class<T> handledComponentClass) {
		this.handledComponentClass = handledComponentClass;
	}

	public void render(Component component) {
		if(component == null)
			throw new NullPointerException();
		if(!handledComponentClass.isInstance(component))
			throw new IllegalArgumentException();
		if(!component.isVisible())
			return;
		renderComponent(handledComponentClass.cast(component));
	}

	protected abstract void renderComponent(T component);

	@Override
	public Rectangle getChildRenderArea(Container container) {
		if(!Container.class.isAssignableFrom(handledComponentClass))
			throw new UnsupportedOperationException();
		if(container == null)
			throw new NullPointerException();
		if(!handledComponentClass.isInstance(container))
			throw new IllegalArgumentException();
		return getContainerChildRenderArea(handledComponentClass
				.cast(container));
	}

	protected Rectangle getContainerChildRenderArea(T container) {
		return new Rectangle(new Point(0, 0), container.getSize());
	}

	@Override
	public Dimension getDefaultSize(Component component) {
		if(component == null)
			throw new NullPointerException();
		if(!handledComponentClass.isInstance(component))
			throw new IllegalArgumentException();
		return getDefaultComponentSize(handledComponentClass.cast(component));
	}

	protected abstract Dimension getDefaultComponentSize(T component);

	protected void translateComponent(Component component, boolean reverse) {
		Component parent = component.getParent();
		while(parent != null) {
			GL11.glTranslated((reverse ? -1 : 1) * parent.getX(), (reverse ? -1
					: 1) * parent.getY(), 0);
			parent = parent.getParent();
		}
		GL11.glTranslated((reverse ? -1 : 1) * component.getX(), (reverse ? -1
				: 1) * component.getY(), 0);
	}

	@Override
	public Color getDefaultBackgroundColor(Component component) {
		if(component == null)
			throw new NullPointerException();
		if(!handledComponentClass.isInstance(component))
			throw new IllegalArgumentException();
		return getBackgroundColor(handledComponentClass.cast(component));
	}

	protected Color getBackgroundColor(T component) {
		return background;
	}

	@Override
	public Color getDefaultForegroundColor(Component component) {
		if(component == null)
			throw new NullPointerException();
		if(!handledComponentClass.isInstance(component))
			throw new IllegalArgumentException();
		return getForegroundColor(handledComponentClass.cast(component));
	}

	protected Color getForegroundColor(T component) {
		return foreground;
	}

	@Override
	public Rectangle[] getInteractableRegions(Component component) {
		if(component == null)
			throw new NullPointerException();
		if(!handledComponentClass.isInstance(component))
			throw new IllegalArgumentException();
		return getInteractableComponentRegions(handledComponentClass
				.cast(component));
	}

	protected Rectangle[] getInteractableComponentRegions(T component) {
		return new Rectangle[0];
	}

	@Override
	public void handleInteraction(Component component, Point location,
			int button) {
		if(component == null)
			throw new NullPointerException();
		if(!handledComponentClass.isInstance(component))
			throw new IllegalArgumentException();
		handleComponentInteraction(handledComponentClass.cast(component),
				location, button);
	}

	protected void handleComponentInteraction(T component, Point location,
			int button) {

	}
}
