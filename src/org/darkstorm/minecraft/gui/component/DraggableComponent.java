package org.darkstorm.minecraft.gui.component;


public interface DraggableComponent extends Component {
	public boolean isDragging();

	public void setDragging(boolean dragging);
}
