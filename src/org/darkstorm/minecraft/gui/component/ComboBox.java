package org.darkstorm.minecraft.gui.component;

import org.darkstorm.minecraft.gui.listener.ComboBoxListener;

public interface ComboBox extends Component, SelectableComponent {
	public String[] getElements();

	public void setElements(String... elements);

	public int getSelectedIndex();

	public void setSelectedIndex(int selectedIndex);

	public String getSelectedElement();

	public void addComboBoxListener(ComboBoxListener listener);

	public void removeComboBoxListener(ComboBoxListener listener);
}
