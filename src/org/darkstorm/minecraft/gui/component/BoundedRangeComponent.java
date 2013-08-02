package org.darkstorm.minecraft.gui.component;

public interface BoundedRangeComponent extends Component {
	public enum ValueDisplay {
		DECIMAL,
		INTEGER,
		PERCENTAGE,
		NONE
	}

	public double getValue();

	public double getMinimumValue();

	public double getMaximumValue();

	public double getIncrement();

	public ValueDisplay getValueDisplay();

	public void setValue(double value);

	public void setMinimumValue(double minimumValue);

	public void setMaximumValue(double maximumValue);

	public void setIncrement(double increment);

	public void setValueDisplay(ValueDisplay display);
}
