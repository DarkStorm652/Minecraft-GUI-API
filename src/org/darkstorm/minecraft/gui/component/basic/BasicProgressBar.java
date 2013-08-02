package org.darkstorm.minecraft.gui.component.basic;

import org.darkstorm.minecraft.gui.component.*;

public class BasicProgressBar extends AbstractComponent implements ProgressBar {
	private double value, minimum, maximum, increment;
	private ValueDisplay display;
	private boolean indeterminate;

	public BasicProgressBar() {
		this(0);
	}

	public BasicProgressBar(double value) {
		this(value, 0, 100);
	}

	public BasicProgressBar(double value, double minimum, double maximum) {
		this(value, minimum, maximum, 1);
	}

	public BasicProgressBar(double value, double minimum, double maximum, int increment) {
		this(value, minimum, maximum, increment, ValueDisplay.NONE);
	}

	public BasicProgressBar(double value, double minimum, double maximum, double increment, ValueDisplay display) {
		this.minimum = Math.max(0, Math.min(minimum, maximum));
		this.maximum = Math.max(0, Math.max(minimum, maximum));
		value = Math.max(minimum, Math.min(maximum, value));
		this.value = value - (Math.round((value % increment) / increment) * increment);
		this.increment = Math.min(maximum, Math.max(0.0005, increment));
		this.display = display != null ? display : ValueDisplay.NONE;
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
	public boolean isIndeterminate() {
		return indeterminate;
	}

	@Override
	public void setValue(double value) {
		value = Math.max(minimum, Math.min(maximum, value));
		this.value = value - (Math.round((value % increment) / increment) * increment);
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
		this.display = display != null ? display : ValueDisplay.NONE;
	}

	@Override
	public void setIndeterminate(boolean indeterminate) {
		this.indeterminate = indeterminate;
	}
}
