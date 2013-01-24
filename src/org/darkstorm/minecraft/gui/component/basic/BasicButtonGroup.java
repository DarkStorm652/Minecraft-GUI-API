package org.darkstorm.minecraft.gui.component.basic;

import java.util.*;

import org.darkstorm.minecraft.gui.component.*;

public class BasicButtonGroup implements ButtonGroup {
	private List<Button> buttons = new ArrayList<Button>();

	@Override
	public void addButton(Button button) {
		if(button == null)
			throw new NullPointerException();
		synchronized(buttons) {
			buttons.add(button);
		}
	}

	@Override
	public void removeButton(Button button) {
		if(button == null)
			throw new NullPointerException();
		synchronized(buttons) {
			buttons.remove(button);
		}
	}

	@Override
	public Button[] getButtons() {
		synchronized(buttons) {
			return buttons.toArray(new Button[buttons.size()]);
		}
	}

}
