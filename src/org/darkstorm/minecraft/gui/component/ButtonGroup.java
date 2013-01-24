package org.darkstorm.minecraft.gui.component;

public interface ButtonGroup {
	public void addButton(Button button);

	public void removeButton(Button button);

	public Button[] getButtons();
}
