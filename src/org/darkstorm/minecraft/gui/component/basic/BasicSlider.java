package org.darkstorm.minecraft.gui.component.basic;

import org.darkstorm.minecraft.gui.component.*;
import org.darkstorm.minecraft.gui.listener.*;

public class BasicSlider extends AbstractComponent implements Slider {
	private String text, suffix;
	private double value, minimum, maximum, increment;
	private ValueDisplay display;
	private boolean changing = false;
	private double startValue;

	public BasicSlider() {
		this("");
	}

	public BasicSlider(String text) {
		this(text, 0);
	}

	public BasicSlider(String text, double value) {
		this(text, value, 0, 100);
	}

	public BasicSlider(String text, double value, double minimum, double maximum) {
		this(text, value, minimum, maximum, 1);
	}

	public BasicSlider(String text, double value, double minimum, double maximum, int increment) {
		this(text, value, minimum, maximum, increment, ValueDisplay.DECIMAL);
	}

	public BasicSlider(String text, double value, double minimum, double maximum, double increment, ValueDisplay display) {
		this.text = text != null ? text : "";
		this.minimum = Math.max(0, Math.min(minimum, maximum));
		this.maximum = Math.max(0, Math.max(minimum, maximum));
		value = Math.max(minimum, Math.min(maximum, value));
		this.value = value - (Math.round((value % increment) / increment) * increment);
		this.increment = Math.min(maximum, Math.max(0.0005, increment));
		this.display = display != null ? display : ValueDisplay.DECIMAL;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String text) {
		this.text = text != null ? text : "";
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public double getMinimumValue() {
		return minimum;
	}

	@Override
	public double getMaximumValue() {
		return maximum;
	}

	public double getIncrement() {
		return increment;
	}

	@Override
	public ValueDisplay getValueDisplay() {
		return display;
	}

	@Override
	public boolean isValueChanging() {
		return changing;
	}

	@Override
	public String getContentSuffix() {
		return suffix;
	}

	@Override
	public void setValue(double value) {
		double oldValue = this.value;
		value = Math.max(minimum, Math.min(maximum, value));
		this.value = value - (Math.round((value % increment) / increment) * increment);
		if(!changing && oldValue != this.value)
			fireChange();
	}

	@Override
	public void setMinimumValue(double minimum) {
		this.minimum = Math.max(0, Math.min(maximum, minimum));
		setValue(value);
	}

	@Override
	public void setMaximumValue(double maximum) {
		this.maximum = Math.max(maximum, minimum);
		setValue(value);
	}

	public void setIncrement(double increment) {
		this.increment = Math.min(maximum, Math.max(0.0005, increment));
		setValue(value);
	}

	public void setValueDisplay(ValueDisplay display) {
		this.display = display != null ? display : ValueDisplay.DECIMAL;
	}

	@Override
	public void setValueChanging(boolean changing) {
		if(changing != this.changing) {
			this.changing = changing;
			if(changing)
				startValue = value;
			else if(startValue != value)
				fireChange();
		}
	}

	@Override
	public void setContentSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public void addSliderListener(SliderListener listener) {
		addListener(listener);
	}

	@Override
	public void removeSliderListener(SliderListener listener) {
		removeListener(listener);
	}

	private void fireChange() {
		for(ComponentListener listener : getListeners())
			if(listener instanceof SliderListener)
				((SliderListener) listener).onSliderValueChanged(this);
	}
}
