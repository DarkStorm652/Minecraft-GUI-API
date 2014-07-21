package org.darkstorm.minecraft.gui.util;

import java.awt.Rectangle;

import net.minecraft.client.gui.GuiScreen;

import org.darkstorm.minecraft.gui.GuiManager;
import org.darkstorm.minecraft.gui.component.*;

public class GuiManagerDisplayScreen extends GuiScreen {
	private final GuiManager guiManager;

	public GuiManagerDisplayScreen(GuiManager guiManager) {
		this.guiManager = guiManager;
	}

	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		for(Frame frame : guiManager.getFrames()) {
			if(!frame.isVisible())
				continue;
			if(!frame.isMinimized() && !frame.getArea().contains(x, y)) {
				for(Component component : frame.getChildren()) {
					for(Rectangle area : component.getTheme().getUIForComponent(component).getInteractableRegions(component)) {
						if(area.contains(x - frame.getX() - component.getX(), y - frame.getY() - component.getY())) {
							frame.onMousePress(x - frame.getX(), y - frame.getY(), button);
							guiManager.bringForward(frame);
							return;
						}
					}
				}
			}
		}
		for(Frame frame : guiManager.getFrames()) {
			if(!frame.isVisible())
				continue;
			if(!frame.isMinimized() && frame.getArea().contains(x, y)) {
				frame.onMousePress(x - frame.getX(), y - frame.getY(), button);
				guiManager.bringForward(frame);
				break;
			} else if(frame.isMinimized()) {
				for(Rectangle area : frame.getTheme().getUIForComponent(frame).getInteractableRegions(frame)) {
					if(area.contains(x - frame.getX(), y - frame.getY())) {
						frame.onMousePress(x - frame.getX(), y - frame.getY(), button);
						guiManager.bringForward(frame);
						return;
					}
				}
			}
		}
	}

	@Override
	public void mouseMovedOrUp(int x, int y, int button) {
		super.mouseMovedOrUp(x, y, button);
		for(Frame frame : guiManager.getFrames()) {
			if(!frame.isVisible())
				continue;
			if(!frame.isMinimized() && !frame.getArea().contains(x, y)) {
				for(Component component : frame.getChildren()) {
					for(Rectangle area : component.getTheme().getUIForComponent(component).getInteractableRegions(component)) {
						if(area.contains(x - frame.getX() - component.getX(), y - frame.getY() - component.getY())) {
							frame.onMouseRelease(x - frame.getX(), y - frame.getY(), button);
							guiManager.bringForward(frame);
							return;
						}
					}
				}
			}
		}
		for(Frame frame : guiManager.getFrames()) {
			if(!frame.isVisible())
				continue;
			if(!frame.isMinimized() && frame.getArea().contains(x, y)) {
				frame.onMouseRelease(x - frame.getX(), y - frame.getY(), button);
				guiManager.bringForward(frame);
				break;
			} else if(frame.isMinimized()) {
				for(Rectangle area : frame.getTheme().getUIForComponent(frame).getInteractableRegions(frame)) {
					if(area.contains(x - frame.getX(), y - frame.getY())) {
						frame.onMouseRelease(x - frame.getX(), y - frame.getY(), button);
						guiManager.bringForward(frame);
						return;
					}
				}
			}
		}
	}

	@Override
	public void drawScreen(int par2, int par3, float par4) {
		guiManager.render();
		super.drawScreen(par2, par3, par4);
	}
}