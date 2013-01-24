package org.darkstorm.minecraft.gui.theme.simple;

import static org.lwjgl.opengl.GL11.*;

import java.awt.*;

import org.darkstorm.minecraft.gui.component.Component;
import org.darkstorm.minecraft.gui.component.Panel;
import org.darkstorm.minecraft.gui.layout.Constraint;
import org.darkstorm.minecraft.gui.theme.AbstractComponentUI;
import org.darkstorm.minecraft.gui.util.RenderUtil;

public class SimplePanelUI extends AbstractComponentUI<Panel> {
	@SuppressWarnings("unused")
	private final SimpleTheme theme;

	SimplePanelUI(SimpleTheme theme) {
		super(Panel.class);
		this.theme = theme;

		foreground = Color.WHITE;
		background = new Color(128, 128, 128, 128);
	}

	@Override
	protected void renderComponent(Panel component) {
		if(component.getParent() != null)
			return;
		Rectangle area = component.getArea();
		translateComponent(component, false);
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_CULL_FACE);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		RenderUtil.setColor(component.getBackgroundColor());
		glBegin(GL_QUADS);
		{
			glVertex2d(0, 0);
			glVertex2d(area.width, 0);
			glVertex2d(area.width, area.height);
			glVertex2d(0, area.height);
		}
		glEnd();
		glEnable(GL_CULL_FACE);
		glEnable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);
		translateComponent(component, true);
	}

	@Override
	protected Dimension getDefaultComponentSize(Panel component) {
		Component[] children = component.getChildren();
		Rectangle[] areas = new Rectangle[children.length];
		Constraint[][] constraints = new Constraint[children.length][];
		for(int i = 0; i < children.length; i++) {
			Component child = children[i];
			Dimension size = child.getTheme().getUIForComponent(child)
					.getDefaultSize(child);
			areas[i] = new Rectangle(0, 0, size.width, size.height);
			constraints[i] = component.getConstraints(child);
		}
		return component.getLayoutManager().getOptimalPositionedSize(areas,
				constraints);
	}
}
