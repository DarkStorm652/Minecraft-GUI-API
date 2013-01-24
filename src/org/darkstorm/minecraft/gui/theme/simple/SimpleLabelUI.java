package org.darkstorm.minecraft.gui.theme.simple;

import static org.lwjgl.opengl.GL11.*;

import java.awt.*;

import org.darkstorm.minecraft.gui.component.Label;
import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
import org.darkstorm.minecraft.gui.util.RenderUtil;

public class SimpleLabelUI extends AbstractComponentUI<Label> {
	private final SimpleTheme theme;

	SimpleLabelUI(SimpleTheme theme) {
		super(Label.class);
		this.theme = theme;

		foreground = Color.WHITE;
		background = new Color(128, 128, 128, 128);
	}

	@Override
	protected void renderComponent(Label label) {
		translateComponent(label, false);
		int x = 0, y = 0;
		switch(label.getHorizontalAlignment()) {
		case CENTER:
			x += label.getWidth() / 2
					- theme.getFontRenderer().getStringWidth(label.getText())
					/ 2;
			break;
		case RIGHT:
			x += label.getWidth()
					- theme.getFontRenderer().getStringWidth(label.getText())
					- 2;
			break;
		default:
			x += 2;
		}
		switch(label.getVerticalAlignment()) {
		case TOP:
			y += 2;
			break;
		case BOTTOM:
			y += label.getHeight() - theme.getFontRenderer().FONT_HEIGHT - 2;
			break;
		default:
			y += label.getHeight() / 2 - theme.getFontRenderer().FONT_HEIGHT
					/ 2;
		}
		glEnable(GL_BLEND);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_CULL_FACE);
		theme.getFontRenderer().drawString(label.getText(), x, y,
				RenderUtil.toRGBA(label.getForegroundColor()));
		glEnable(GL_CULL_FACE);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		translateComponent(label, true);
	}

	@Override
	protected Dimension getDefaultComponentSize(Label component) {
		return new Dimension(theme.getFontRenderer().getStringWidth(
				component.getText()) + 4,
				theme.getFontRenderer().FONT_HEIGHT + 4);
	}
}