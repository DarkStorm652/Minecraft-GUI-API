package org.darkstorm.minecraft.gui.theme.simple;

import static org.lwjgl.opengl.GL11.*;

import java.awt.*;

import org.lwjgl.input.Mouse;

import org.darkstorm.minecraft.gui.component.*;
import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
import org.darkstorm.minecraft.gui.util.RenderUtil;

public class SimpleComboBoxUI extends AbstractComponentUI<ComboBox> {
	private final SimpleTheme theme;

	SimpleComboBoxUI(SimpleTheme theme) {
		super(ComboBox.class);
		this.theme = theme;

		foreground = Color.WHITE;
		background = new Color(128, 128, 128, 128 + 128 / 2);
	}

	@Override
	protected void renderComponent(ComboBox component) {
		translateComponent(component, false);
		Rectangle area = component.getArea();
		glEnable(GL_BLEND);
		glDisable(GL_CULL_FACE);

		glDisable(GL_TEXTURE_2D);
		int maxWidth = 0;
		for(String element : component.getElements())
			maxWidth = Math.max(maxWidth, theme.getFontRenderer()
					.getStringWidth(element));
		int extendedHeight = 0;
		if(component.isSelected()) {
			String[] elements = component.getElements();
			for(int i = 0; i < elements.length - 1; i++)
				extendedHeight += theme.getFontRenderer().FONT_HEIGHT + 2;
			extendedHeight += 2;
		}

		RenderUtil.setColor(component.getBackgroundColor());
		glBegin(GL_QUADS);
		{
			glVertex2d(0, 0);
			glVertex2d(area.width, 0);
			glVertex2d(area.width, area.height + extendedHeight);
			glVertex2d(0, area.height + extendedHeight);
		}
		glEnd();
		Point mouse = RenderUtil.calculateMouseLocation();
		Component parent = component.getParent();
		while(parent != null) {
			mouse.x -= parent.getX();
			mouse.y -= parent.getY();
			parent = parent.getParent();
		}
		glColor4f(0.0f, 0.0f, 0.0f, Mouse.isButtonDown(0) ? 0.5f : 0.3f);
		if(area.contains(mouse)) {
			glBegin(GL_QUADS);
			{
				glVertex2d(0, 0);
				glVertex2d(area.width, 0);
				glVertex2d(area.width, area.height);
				glVertex2d(0, area.height);
			}
			glEnd();
		} else if(component.isSelected() && mouse.x >= area.x
				&& mouse.x <= area.x + area.width) {
			int offset = component.getHeight();
			String[] elements = component.getElements();
			for(int i = 0; i < elements.length; i++) {
				if(i == component.getSelectedIndex())
					continue;
				int height = theme.getFontRenderer().FONT_HEIGHT + 2;
				if((component.getSelectedIndex() == 0 ? i == 1 : i == 0)
						|| (component.getSelectedIndex() == elements.length - 1 ? i == elements.length - 2
								: i == elements.length - 1))
					height++;
				if(mouse.y >= area.y + offset
						&& mouse.y <= area.y + offset + height) {
					glBegin(GL_QUADS);
					{
						glVertex2d(0, offset);
						glVertex2d(0, offset + height);
						glVertex2d(area.width, offset + height);
						glVertex2d(area.width, offset);
					}
					glEnd();
					break;
				}
				offset += height;
			}
		}
		int height = theme.getFontRenderer().FONT_HEIGHT + 4;
		glColor4f(0.0f, 0.0f, 0.0f, 0.3f);
		glBegin(GL_TRIANGLES);
		{
			if(component.isSelected()) {
				glVertex2d(maxWidth + 4 + height / 2d, height / 3d);
				glVertex2d(maxWidth + 4 + height / 3d, 2d * height / 3d);
				glVertex2d(maxWidth + 4 + 2d * height / 3d, 2d * height / 3d);
			} else {
				glVertex2d(maxWidth + 4 + height / 3d, height / 3d);
				glVertex2d(maxWidth + 4 + 2d * height / 3d, height / 3d);
				glVertex2d(maxWidth + 4 + height / 2d, 2d * height / 3d);
			}
		}
		glEnd();
		glLineWidth(1.0f);
		glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
		// glBegin(GL_LINE_LOOP);
		// {
		// glVertex2d(0, 0);
		// glVertex2d(area.width, 0);
		// glVertex2d(area.width, area.height + extendedHeight);
		// glVertex2d(-0.5, area.height + extendedHeight);
		// }
		// glEnd();
		if(component.isSelected()) {
			glBegin(GL_LINES);
			{
				glVertex2d(2, area.height);
				glVertex2d(area.width - 2, area.height);
			}
			glEnd();
		}
		glBegin(GL_LINES);
		{
			glVertex2d(maxWidth + 4, 2);
			glVertex2d(maxWidth + 4, area.height - 2);
		}
		glEnd();
		glBegin(GL_LINE_LOOP);
		{
			if(component.isSelected()) {
				glVertex2d(maxWidth + 4 + height / 2d, height / 3d);
				glVertex2d(maxWidth + 4 + height / 3d, 2d * height / 3d);
				glVertex2d(maxWidth + 4 + 2d * height / 3d, 2d * height / 3d);
			} else {
				glVertex2d(maxWidth + 4 + height / 3d, height / 3d);
				glVertex2d(maxWidth + 4 + 2d * height / 3d, height / 3d);
				glVertex2d(maxWidth + 4 + height / 2d, 2d * height / 3d);
			}
		}
		glEnd();
		glEnable(GL_TEXTURE_2D);

		String text = component.getSelectedElement();
		theme.getFontRenderer().drawString(text, 2,
				area.height / 2 - theme.getFontRenderer().FONT_HEIGHT / 2,
				RenderUtil.toRGBA(component.getForegroundColor()));
		if(component.isSelected()) {
			int offset = area.height + 2;
			String[] elements = component.getElements();
			for(int i = 0; i < elements.length; i++) {
				if(i == component.getSelectedIndex())
					continue;
				theme.getFontRenderer().drawString(elements[i], 2, offset,
						RenderUtil.toRGBA(component.getForegroundColor()));
				offset += theme.getFontRenderer().FONT_HEIGHT + 2;
			}
		}

		glEnable(GL_CULL_FACE);
		glDisable(GL_BLEND);
		translateComponent(component, true);
	}

	@Override
	protected Dimension getDefaultComponentSize(ComboBox component) {
		int maxWidth = 0;
		for(String element : component.getElements())
			maxWidth = Math.max(maxWidth, theme.getFontRenderer()
					.getStringWidth(element));
		return new Dimension(
				maxWidth + 8 + theme.getFontRenderer().FONT_HEIGHT,
				theme.getFontRenderer().FONT_HEIGHT + 4);
	}

	@Override
	protected Rectangle[] getInteractableComponentRegions(ComboBox component) {
		int height = component.getHeight();
		if(component.isSelected()) {
			String[] elements = component.getElements();
			for(int i = 0; i < elements.length; i++)
				height += theme.getFontRenderer().FONT_HEIGHT + 2;
			height += 2;
		}
		return new Rectangle[] { new Rectangle(0, 0, component.getWidth(),
				height) };
	}

	@Override
	protected void handleComponentInteraction(ComboBox component,
			Point location, int button) {
		if(button != 0)
			return;
		if(location.x <= component.getWidth()
				&& location.y <= component.getHeight())
			component.setSelected(!component.isSelected());
		else if(location.x <= component.getWidth() && component.isSelected()) {
			int offset = component.getHeight() + 2;
			String[] elements = component.getElements();
			for(int i = 0; i < elements.length; i++) {
				if(i == component.getSelectedIndex())
					continue;
				if(location.y >= offset
						&& location.y <= offset
								+ theme.getFontRenderer().FONT_HEIGHT) {
					component.setSelectedIndex(i);
					component.setSelected(false);
					break;
				}
				offset += theme.getFontRenderer().FONT_HEIGHT + 2;
			}
		}
	}
}