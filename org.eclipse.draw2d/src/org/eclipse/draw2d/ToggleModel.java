/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.draw2d;

/**
 * ButtonModel that supports toggle buttons.
 */
public class ToggleModel
	extends ButtonModel
{

/**
 * Notifies any ActionListeners on this ButtonModel that an action has been performed. 
 * Sets this ButtonModel's selection to be the opposite of what it was.
 * 
 * @since 2.0
 */
public void fireActionPerformed() {
	setSelected(!isSelected());
	super.fireActionPerformed();
}

}