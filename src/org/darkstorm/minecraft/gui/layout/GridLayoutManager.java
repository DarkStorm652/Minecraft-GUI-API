package org.darkstorm.minecraft.gui.layout;

import java.awt.*;

public class GridLayoutManager implements LayoutManager {
	private int columns, rows;

	public GridLayoutManager(int columns, int rows) {
		this.columns = columns;
		this.rows = rows;
	}

	@Override
	public void reposition(Rectangle area, Rectangle[] componentAreas,
			Constraint[][] constraints) {
		if(componentAreas.length == 0)
			return;
		int componentsPerColumn, componentsPerRow;
		if(columns == 0) {
			if(rows == 0) {
				double square = Math.sqrt(componentAreas.length);
				componentsPerColumn = (int) square;
				componentsPerRow = (int) square;
				if(square - (int) square > 0)
					componentsPerColumn++;
			} else {
				componentsPerRow = componentAreas.length / rows;
				if(componentAreas.length % rows > 0)
					componentsPerRow++;
				componentsPerColumn = rows;
			}
		} else if(rows == 0) {
			componentsPerColumn = componentAreas.length / columns;
			if(componentAreas.length % columns > 0)
				componentsPerColumn++;
			componentsPerRow = columns;
		} else {
			componentsPerRow = columns;
			componentsPerColumn = rows;
		}
		double elementWidth = (double) area.width / (double) componentsPerRow;
		double elementHeight = (double) area.height
				/ (double) componentsPerColumn;
		rowLabel: for(int row = 0; row < componentsPerColumn; row++) {
			for(int element = 0; element < componentsPerRow; element++) {
				int index = (row * componentsPerRow) + element;
				if(index >= componentAreas.length)
					break rowLabel;
				Rectangle componentArea = componentAreas[index];
				Constraint[] componentConstraints = constraints[index];
				HorizontalGridConstraint horizontalAlign = HorizontalGridConstraint.LEFT;
				VerticalGridConstraint verticalAlign = VerticalGridConstraint.CENTER;
				for(Constraint constraint : componentConstraints) {
					if(constraint instanceof HorizontalGridConstraint)
						horizontalAlign = (HorizontalGridConstraint) constraint;
					else if(constraint instanceof VerticalGridConstraint)
						verticalAlign = (VerticalGridConstraint) constraint;
				}
				switch(horizontalAlign) {
				case FILL:
					componentArea.width = (int) elementWidth;
				case LEFT:
					componentArea.x = (int) (area.x + element * elementWidth);
					break;
				case RIGHT:
					componentArea.x = (int) (area.x
							+ ((element + 1) * elementWidth) - componentArea.width);
					break;
				case CENTER:
					componentArea.x = (int) (area.x + (element * elementWidth)
							+ elementWidth / 2 - componentArea.width / 2);
					break;
				}
				switch(verticalAlign) {
				case FILL:
					componentArea.height = (int) elementHeight;
				case TOP:
					componentArea.y = (int) (area.y + row * elementHeight);
					break;
				case BOTTOM:
					componentArea.y = (int) (area.y
							+ ((row + 1) * elementHeight) - componentArea.height);
					break;
				case CENTER:
					componentArea.y = (int) (area.y + (row * elementHeight)
							+ elementHeight / 2 - componentArea.height / 2);
					break;
				}
			}
		}
	}

	@Override
	public Dimension getOptimalPositionedSize(Rectangle[] componentAreas,
			Constraint[][] constraints) {
		if(componentAreas.length == 0)
			return new Dimension(0, 0);
		int componentsPerColumn, componentsPerRow;
		if(columns == 0) {
			if(rows == 0) {
				double square = Math.sqrt(componentAreas.length);
				componentsPerColumn = (int) square;
				componentsPerRow = (int) square;
				if(square - (int) square > 0)
					componentsPerColumn++;
			} else {
				componentsPerRow = componentAreas.length / rows;
				if(componentAreas.length % rows > 0)
					componentsPerRow++;
				componentsPerColumn = rows;
			}
		} else if(rows == 0) {
			componentsPerColumn = componentAreas.length / columns;
			if(componentAreas.length % columns > 0)
				componentsPerColumn++;
			componentsPerRow = columns;
		} else {
			componentsPerRow = columns;
			componentsPerColumn = rows;
		}
		int maxElementWidth = 0, maxElementHeight = 0;
		for(Rectangle component : componentAreas) {
			maxElementWidth = Math.max(maxElementWidth, component.width);
			maxElementHeight = Math.max(maxElementHeight, component.height);
		}
		return new Dimension(maxElementWidth * componentsPerRow,
				maxElementHeight * componentsPerColumn);
	}

	public int getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public enum HorizontalGridConstraint implements Constraint {
		CENTER,
		LEFT,
		RIGHT,
		FILL
	}

	public enum VerticalGridConstraint implements Constraint {
		CENTER,
		TOP,
		BOTTOM,
		FILL
	}
}
