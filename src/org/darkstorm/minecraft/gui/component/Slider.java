package org.darkstorm.minecraft.gui.component;

import org.darkstorm.minecraft.gui.listener.SliderListener;

public interface Slider extends Component, TextComponent, BoundedRangeComponent {
	public String getContentSuffix();

	public boolean isValueChanging();

	public void setContentSuffix(String suffix);

	public void setValueChanging(boolean changing);

	public void addSliderListener(SliderListener listener);

	public void removeSliderListener(SliderListener listener);
}
