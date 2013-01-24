package org.darkstorm.minecraft.gui.component.basic;

import org.darkstorm.minecraft.gui.component.*;

public class BasicLabel extends AbstractComponent implements
		Label {
	protected String text;
	protected TextAlignment horizontalAlignment = TextAlignment.LEFT,
			verticalAlignment = TextAlignment.CENTER;

	public BasicLabel() {
	}

	public BasicLabel(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public TextAlignment getHorizontalAlignment() {
		return horizontalAlignment;
	}

	public TextAlignment getVerticalAlignment() {
		return verticalAlignment;
	}

	public void setHorizontalAlignment(TextAlignment alignment) {
		horizontalAlignment = alignment;
	}

	public void setVerticalAlignment(TextAlignment alignment) {
		verticalAlignment = alignment;
	}
}
