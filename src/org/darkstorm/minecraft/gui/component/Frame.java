package org.darkstorm.minecraft.gui.component;


public interface Frame extends Container, DraggableComponent {
	public String getTitle();

	public void setTitle(String title);

	public boolean isPinned();

	public void setPinned(boolean pinned);

	public boolean isPinnable();

	public void setPinnable(boolean pinnable);

	public boolean isMinimized();

	public void setMinimized(boolean minimized);

	public boolean isMinimizable();

	public void setMinimizable(boolean minimizable);

	public void close();

	public boolean isClosable();

	public void setClosable(boolean closable);
}
