package org.eclipse.gef.palette;
/*
 * Licensed Material - Property of IBM
 * (C) Copyright IBM Corp. 2001, 2002 - All Rights Reserved.
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp.
 */

import org.eclipse.jface.resource.ImageDescriptor;

public class PaletteDrawer 
	extends PaletteContainer 
{

public static final Object PALETTE_TYPE_DRAWER = "$Palette Drawer"; //$NON-NLS-1$
public static final String PROPERTY_INITIAL_STATUS = "Initial status"; //$NON-NLS-1$
public static final int INITIAL_STATE_OPEN = 0;
public static final int INITIAL_STATE_CLOSED = 1;
public static final int INITIAL_STATUS_PINNED_OPEN = 2;

private int initialState;
private Object drawerType;

public PaletteDrawer(String label) {
	this(label, (ImageDescriptor)null);
}

public PaletteDrawer(String label, ImageDescriptor icon) {
	super(label, null, icon, PALETTE_TYPE_DRAWER);
	setUserModificationPermission(PERMISSION_LIMITED_MODIFICATION);
}

public Object getDrawerType() {
	if (drawerType != null) {
		return drawerType;
	}
	for (int i = 0; i < children.size(); i++) {
		PaletteEntry child = (PaletteEntry)children.get(i);
		Object type = child.getType();
		if (type != PaletteSeparator.PALETTE_TYPE_SEPARATOR)
			return type;
	}
	return PaletteEntry.PALETTE_TYPE_UNKNOWN;
}

public int getInitialState() {
	return initialState;
}

public boolean isInitiallyOpen() {
	return (getInitialState() == INITIAL_STATE_OPEN 
			|| getInitialState() == INITIAL_STATUS_PINNED_OPEN);
}

public boolean isInitiallyPinned() {
	return (getInitialState() == INITIAL_STATUS_PINNED_OPEN);
}

/**
 * This method does not fire a property change.
 * 
 * @param	s	PALETTE_TYPE_TOOL or PALETTE_TYPE_TEMPLATE
 */
public void setDrawerType(Object obj) {
	drawerType = obj;
}

public void setInitialState(int state) {
	if (initialState == state)
		return;
	int oldState = initialState;
	initialState = state;
	listeners.firePropertyChange(PROPERTY_INITIAL_STATUS, oldState, state);
}
	
}