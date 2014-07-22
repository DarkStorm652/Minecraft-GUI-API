package org.darkstorm.minecraft.gui.theme.simple;

import static org.lwjgl.opengl.GL11.*;

import java.awt.*;

import net.minecraft.client.gui.FontRenderer;

import org.darkstorm.minecraft.gui.component.ProgressBar;
import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
import org.darkstorm.minecraft.gui.util.RenderUtil;

public class SimpleProgressBarUI extends AbstractComponentUI<ProgressBar> {
	private SimpleTheme theme;

	public SimpleProgressBarUI(SimpleTheme theme) {
		super(ProgressBar.class);
		this.theme = theme;

		foreground = Color.LIGHT_GRAY;
		background = new Color(128, 128, 128, 128 + 128 / 2);
	}

	@Override
	protected void renderComponent(ProgressBar component) {
		Rectangle area = component.getArea();
		int fontSize = theme.getFontRenderer().FONT_HEIGHT;
		FontRenderer fontRenderer = theme.getFontRenderer();

		translateComponent(component, false);
		glEnable(GL_BLEND);
		glDisable(GL_CULL_FACE);
		glDisable(GL_TEXTURE_2D);

		RenderUtil.setColor(component.getBackgroundColor());
		glLineWidth(0.9f);
		glBegin(GL_LINE_LOOP);
		{
			glVertex2d(0, 0);
			glVertex2d(area.width, 0);
			glVertex2d(area.width, area.height);
			glVertex2d(0, area.height);
		}
		glEnd();

		double barPercentage = (component.getValue() - component.getMinimumValue()) / (component.getMaximumValue() - component.getMinimumValue());
		RenderUtil.setColor(component.getForegroundColor());
		glBegin(GL_QUADS);
		{
			glVertex2d(0, 0);
			glVertex2d(area.width * barPercentage, 0);
			glVertex2d(area.width * barPercentage, area.height);
			glVertex2d(0, area.height);
		}
		glEnd();

		glEnable(GL_TEXTURE_2D);
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
			glBlendFunc(GL_ONE_MINUS_DST_COLOR, GL_ONE_MINUS_SRC_COLOR);
			fontRenderer.drawString(content, component.getWidth() / 2 - fontRenderer.getStringWidth(content) / 2, component.getHeight() / 2 - fontSize / 2, RenderUtil.toRGBA(component.getForegroundColor()));
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		}
		glEnable(GL_CULL_FACE);
		glDisable(GL_BLEND);
		translateComponent(component, true);
	}

	@Override
	protected Dimension getDefaultComponentSize(ProgressBar component) {
		return new Dimension(100, 4 + theme.getFontRenderer().FONT_HEIGHT);
	}

	@Override
	protected Rectangle[] getInteractableComponentRegions(ProgressBar component) {
		return new Rectangle[0];
	}
}
