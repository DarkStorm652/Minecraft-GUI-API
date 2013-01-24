package org.darkstorm.minecraft.gui.component.basic;

import org.darkstorm.minecraft.gui.component.*;
import org.darkstorm.minecraft.gui.listener.*;

public class BasicComboBox extends AbstractComponent implements ComboBox {
	private String[] elements;
	private int selectedIndex;
	private boolean selected;

	public BasicComboBox() {
		elements = new String[0];
	}

	public BasicComboBox(String... elements) {
		this.elements = elements;
	}

	@Override
	public String[] getElements() {
		return elements;
	}

	@Override
	public void setElements(String... elements) {
		selectedIndex = 0;
		this.elements = elements;
	}

	@Override
	public int getSelectedIndex() {
		return selectedIndex;
	}

	@Override
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
		for(ComponentListener listener : getListeners()) {
			if(listener instanceof ComboBoxListener) {
				try {
					((ComboBoxListener) listener)
							.onComboBoxSelectionChanged(this);
				} catch(Exception exception) {
					exception.printStackTrace();
				}
			}
		}
	}

	@Override
	public String getSelectedElement() {
		return elements[selectedIndex];
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
	public void addComboBoxListener(ComboBoxListener listener) {
		addListener(listener);
	}

	@Override
	public void removeComboBoxListener(ComboBoxListener listener) {
		removeListener(listener);
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
