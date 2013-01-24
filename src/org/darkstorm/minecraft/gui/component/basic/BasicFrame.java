package org.darkstorm.minecraft.gui.component.basic;

import java.awt.Point;

import org.lwjgl.input.Mouse;

import org.darkstorm.minecraft.gui.component.*;
import org.darkstorm.minecraft.gui.util.RenderUtil;

public class BasicFrame extends AbstractContainer implements Frame {
	private String title;
	private Point dragOffset;

	private boolean pinned, pinnable = true;
	private boolean minimized, minimizable = true;
	private boolean closable = true;

	@Override
	public void render() {
		if(isDragging()) {
			if(Mouse.isButtonDown(0)) {
				Point mouseLocation = RenderUtil.calculateMouseLocation();
				setX(mouseLocation.x - dragOffset.x);
				setY(mouseLocation.y - dragOffset.y);
			} else
				setDragging(false);
		}
		if(minimized) {
			if(ui != null)
				ui.render(this);
		} else
			super.render();
	}

	public BasicFrame() {
		this("");
	}

	public BasicFrame(String title) {
		setVisible(false);
		this.title = title;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public boolean isDragging() {
		return dragOffset != null;
	}

	@Override
	public void setDragging(boolean dragging) {
		if(dragging) {
			Point mouseLocation = RenderUtil.calculateMouseLocation();
			dragOffset = new Point(mouseLocation.x - getX(), mouseLocation.y
					- getY());
		} else
			dragOffset = null;
	}

	@Override
	public boolean isPinned() {
		return pinned;
	}

	@Override
	public void setPinned(boolean pinned) {
		if(!pinnable)
			pinned = false;
		this.pinned = pinned;
	}

	@Override
	public boolean isPinnable() {
		return pinnable;
	}

	@Override
	public void setPinnable(boolean pinnable) {
		if(!pinnable)
			pinned = false;
		this.pinnable = pinnable;
	}

	@Override
	public boolean isMinimized() {
		return minimized;
	}

	@Override
	public void setMinimized(boolean minimized) {
		if(!minimizable)
			minimized = false;
		this.minimized = minimized;
	}

	@Override
	public boolean isMinimizable() {
		return minimizable;
	}

	@Override
	public void setMinimizable(boolean minimizable) {
		if(!minimizable)
			minimized = false;
		this.minimizable = minimizable;
	}

	@Override
	public void close() {
		if(closable)
			setVisible(false);
	}

	@Override
	public boolean isClosable() {
		return closable;
	}

	@Override
	public void setClosable(boolean closable) {
		this.closable = closable;
	}
}
