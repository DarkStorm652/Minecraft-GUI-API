package org.darkstorm.minecraft.gui.component;

import org.darkstorm.minecraft.gui.layout.*;

public interface Container extends Component {
	public LayoutManager getLayoutManager();

	public void setLayoutManager(LayoutManager layoutManager);

	public Component[] getChildren();

	public void add(Component child, Constraint... constraints);

	public Constraint[] getConstraints(Component child);

	public Component getChildAt(int x, int y);

	public boolean hasChild(Component component);

	public boolean remove(Component child);

	public void layoutChildren();
}
