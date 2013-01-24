package org.darkstorm.minecraft.gui.theme.simple;

import static org.lwjgl.opengl.GL11.*;

import java.awt.*;

import org.lwjgl.input.Mouse;

import org.darkstorm.minecraft.gui.component.*;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
import org.darkstorm.minecraft.gui.util.RenderUtil;

public class SimpleCheckButtonUI extends AbstractComponentUI<CheckButton> {
	private final SimpleTheme theme;

	SimpleCheckButtonUI(SimpleTheme theme) {
		super(CheckButton.class);
		this.theme = theme;

		foreground = Color.WHITE;
		background = new Color(128, 128, 128, 128 + 128 / 2);
	}

	@Override
	protected void renderComponent(CheckButton button) {
		translateComponent(button, false);
		Rectangle area = button.getArea();
		glEnable(GL_BLEND);
		glDisable(GL_CULL_FACE);

		glDisable(GL_TEXTURE_2D);
		RenderUtil.setColor(button.getBackgroundColor());
		int size = area.height - 4;
		glBegin(GL_QUADS);
		{
			glVertex2d(2, 2);
			glVertex2d(size + 2, 2);
			glVertex2d(size + 2, size + 2);
			glVertex2d(2, size + 2);
		}
		glEnd();
		if(button.isSelected()) {
			glColor4f(0.0f, 0.0f, 0.0f, 0.5f);
			glBegin(GL_QUADS);
			{
				glVertex2d(3, 3.5);
				glVertex2d(size + 0.5, 3.5);
				glVertex2d(size + 0.5, size + 1);
				glVertex2d(3, size + 1);
			}
			glEnd();
		}
		glLineWidth(1.0f);
		glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
		glBegin(GL_LINE_LOOP);
		{
			glVertex2d(2, 2);
			glVertex2d(size + 2, 2);
			glVertex2d(size + 2, size + 2);
			glVertex2d(2 - 0.5, size + 2);
		}
		glEnd();
		Point mouse = RenderUtil.calculateMouseLocation();
		Component parent = button.getParent();
		while(parent != null) {
			mouse.x -= parent.getX();
			mouse.y -= parent.getY();
			parent = parent.getParent();
		}
		if(area.contains(mouse)) {
			glColor4f(0.0f, 0.0f, 0.0f, Mouse.isButtonDown(0) ? 0.5f : 0.3f);
			glBegin(GL_QUADS);
			{
				glVertex2d(0, 0);
				glVertex2d(area.width, 0);
				glVertex2d(area.width, area.height);
				glVertex2d(0, area.height);
			}
			glEnd();
		}
		glEnable(GL_TEXTURE_2D);

		String text = button.getText();
		theme.getFontRenderer().drawString(text, size + 4,
				area.height / 2 - theme.getFontRenderer().FONT_HEIGHT / 2,
				RenderUtil.toRGBA(button.getForegroundColor()));

		glEnable(GL_CULL_FACE);
		glDisable(GL_BLEND);
		translateComponent(button, true);
	}

	@Override
	protected Dimension getDefaultComponentSize(CheckButton component) {
		return new Dimension(theme.getFontRenderer().getStringWidth(
				component.getText())
				+ theme.getFontRenderer().FONT_HEIGHT + 6,
				theme.getFontRenderer().FONT_HEIGHT + 4);
	}

	@Override
	protected Rectangle[] getInteractableComponentRegions(CheckButton component) {
		return new Rectangle[] { new Rectangle(0, 0, component.getWidth(),
				component.getHeight()) };
	}

	@Override
	protected void handleComponentInteraction(CheckButton component,
			Point location, int button) {
		if(location.x <= component.getWidth()
				&& location.y <= component.getHeight() && button == 0)
			component.press();
	}
}