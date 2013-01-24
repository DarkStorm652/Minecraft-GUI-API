package org.darkstorm.minecraft.gui.component;

import org.darkstorm.minecraft.gui.listener.SelectableComponentListener;

public interface SelectableComponent extends Component {
	public boolean isSelected();

	public void setSelected(boolean selected);

	public void addSelectableComponentListener(SelectableComponentListener listener);

	public void removeSelectableComponentListener(SelectableComponentListener listener);
}
