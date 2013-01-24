package org.darkstorm.minecraft.gui.listener;

import org.darkstorm.minecraft.gui.component.SelectableComponent;

public interface SelectableComponentListener extends ComponentListener {
	public void onSelectedStateChanged(SelectableComponent component);
}
