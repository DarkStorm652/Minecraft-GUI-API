package org.darkstorm.minecraft.gui.listener;

import org.darkstorm.minecraft.gui.component.Slider;

public interface SliderListener extends ComponentListener {
	public void onSliderValueChanged(Slider slider);
}
