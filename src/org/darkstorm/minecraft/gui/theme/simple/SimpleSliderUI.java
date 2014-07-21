package org.darkstorm.minecraft.gui.theme.simple;

import static org.lwjgl.opengl.GL11.*;

import java.awt.*;

import net.minecraft.client.gui.FontRenderer;

import org.darkstorm.minecraft.gui.component.*;
import org.darkstorm.minecraft.gui.component.Container;
import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
import org.darkstorm.minecraft.gui.util.RenderUtil;
import org.lwjgl.input.Mouse;

public class SimpleSliderUI extends AbstractComponentUI<Slider> {
	private SimpleTheme theme;

	public SimpleSliderUI(SimpleTheme theme) {
		super(Slider.class);
		this.theme = theme;

		foreground = Color.LIGHT_GRAY;
		background = new Color(128, 128, 128, 128 + 128 / 2);
	}

	@Override
	protected void renderComponent(Slider component) {
		translateComponent(component, false);
		glEnable(GL_BLEND);
		glDisable(GL_CULL_FACE);
		Rectangle area = component.getArea();
		int fontSize = theme.getFontRenderer().FONT_HEIGHT;
		FontRenderer fontRenderer = theme.getFontRenderer();
		fontRenderer.drawString(component.getText(), 0, 0, RenderUtil.toRGBA(component.getForegroundColor()));
		String content = null;
		switch(component.getValueDisplay()) {
		case DECIMAL:
			content = String.format("%,.3f", component.getValue());
			break;
		case INTEGER:
			content = String.format("%,d", Long.valueOf(Math.round(component.getValue())));
			break;
		case PERCENTAGE:
			int percent = (int) Math.round((component.getValue() - component.getMinimumValue()) / (component.getMaximumValue() - component.getMinimumValue()) * 100D);
			content = String.format("%d%%", percent);
		default:
		}
		if(content != null) {
			String suffix = component.getContentSuffix();
			if(suffix != null && !suffix.trim().isEmpty())
				content = content.concat(" ").concat(suffix);
			fontRenderer.drawString(content, component.getWidth() - fontRenderer.getStringWidth(content), 0, RenderUtil.toRGBA(component.getForegroundColor()));
		}
		glDisable(GL_TEXTURE_2D);

		RenderUtil.setColor(component.getBackgroundColor());
		glLineWidth(0.9f);
		glBegin(GL_LINE_LOOP);
		{
			glVertex2d(0, fontSize + 2D);
			glVertex2d(area.width, fontSize + 2D);
			glVertex2d(area.width, area.height);
			glVertex2d(0, area.height);
		}
		glEnd();

		double sliderPercentage = (component.getValue() - component.getMinimumValue()) / (component.getMaximumValue() - component.getMinimumValue());
		RenderUtil.setColor(component.getForegroundColor());
		glBegin(GL_QUADS);
		{
			glVertex2d(0, fontSize + 2D);
			glVertex2d(area.width * sliderPercentage, fontSize + 2D);
			glVertex2d(area.width * sliderPercentage, area.height);
			glVertex2d(0, area.height);
		}
		glEnd();

		glEnable(GL_TEXTURE_2D);
		translateComponent(component, true);
	}

	@Override
	protected Dimension getDefaultComponentSize(Slider component) {
		return new Dimension(100, 8 + theme.getFontRenderer().FONT_HEIGHT);
	}

	@Override
	protected Rectangle[] getInteractableComponentRegions(Slider component) {
		return new Rectangle[] { new Rectangle(0, theme.getFontRenderer().FONT_HEIGHT + 2, component.getWidth(), component.getHeight() - theme.getFontRenderer().FONT_HEIGHT) };
	}

	@Override
	protected void handleComponentInteraction(Slider component, Point location, int button) {
		if(getInteractableComponentRegions(component)[0].contains(location) && button == 0)
			if(Mouse.isButtonDown(button) && !component.isValueChanging())
				component.setValueChanging(true);
			else if(!Mouse.isButtonDown(button) && component.isValueChanging())
				component.setValueChanging(false);
	}

	@Override
	protected void handleComponentUpdate(Slider component) {
		if(component.isValueChanging()) {
			if(!Mouse.isButtonDown(0)) {
				component.setValueChanging(false);
				return;
			}
			Point mouse = RenderUtil.calculateMouseLocation();
			Container parent = component.getParent();
			if(parent != null)
				mouse.translate(-parent.getX(), -parent.getY());
			double percent = (double) mouse.x / (double) component.getWidth();
			double value = component.getMinimumValue() + (percent * (component.getMaximumValue() - component.getMinimumValue()));
			component.setValue(value);
		}
	}
}
