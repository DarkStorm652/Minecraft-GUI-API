package org.darkstorm.minecraft.gui.component.basic;

import org.darkstorm.minecraft.gui.component.CheckButton;
import org.darkstorm.minecraft.gui.listener.*;

public class BasicCheckButton extends BasicButton implements CheckButton {
	private boolean selected = false;

	public BasicCheckButton() {
	}

	public BasicCheckButton(String text) {
		this.text = text;
	}

	@Override
	public void press() {
		selected = !selected;
		super.press();
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
		for(ComponentListener listener : getListeners()) {
			if(listener instanceof SelectableComponentListener) {
				try {
					((SelectableComponentListener) listener).onSelectedStateChanged(this);
				} catch(Exception exception) {
					exception.printStackTrace();
				}
			}
		}
	}

	@Override
	public void addSelectableComponentListener(SelectableComponentListener listener) {
		addListener(listener);
	}

	@Override
	public void removeSelectableComponentListener(SelectableComponentListener listener) {
		removeListener(listener);
	}
}
