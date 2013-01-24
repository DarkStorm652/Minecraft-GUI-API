package org.darkstorm.minecraft.gui.component;

public interface Label extends TextComponent {
	public enum TextAlignment {
		CENTER,
		LEFT,
		RIGHT,
		TOP,
		BOTTOM
	}

	public TextAlignment getHorizontalAlignment();

	public TextAlignment getVerticalAlignment();

	public void setHorizontalAlignment(TextAlignment alignment);

	public void setVerticalAlignment(TextAlignment alignment);
}
