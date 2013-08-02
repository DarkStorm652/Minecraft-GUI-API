package org.darkstorm.minecraft.gui.component;

public interface ProgressBar extends Component, BoundedRangeComponent {
	public boolean isIndeterminate();

	public void setIndeterminate(boolean indeterminate);
}
