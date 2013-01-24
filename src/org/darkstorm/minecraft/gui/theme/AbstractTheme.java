package org.darkstorm.minecraft.gui.theme;

import java.util.*;

import org.darkstorm.minecraft.gui.component.Component;

public abstract class AbstractTheme implements Theme {
	protected final Map<Class<? extends Component>, ComponentUI> uis;

	public AbstractTheme() {
		uis = new HashMap<Class<? extends Component>, ComponentUI>();
	}

	protected void installUI(AbstractComponentUI<?> ui) {
		uis.put(ui.handledComponentClass, ui);
	}

	public ComponentUI getUIForComponent(Component component) {
		if(component == null || !(component instanceof Component))
			throw new IllegalArgumentException();
		return getComponentUIForClass(component.getClass());
	}

	@SuppressWarnings("unchecked")
	public ComponentUI getComponentUIForClass(
			Class<? extends Component> componentClass) {
		for(Class<?> componentInterface : componentClass.getInterfaces()) {
			ComponentUI ui = uis.get(componentInterface);
			if(ui != null)
				return ui;
		}
		if(componentClass.getSuperclass().equals(Component.class))
			return uis.get(componentClass);
		else if(!Component.class.isAssignableFrom(componentClass
				.getSuperclass()))
			return null; // WTF?
		return getComponentUIForClass((Class<? extends Component>) componentClass
				.getSuperclass());
	}

}
