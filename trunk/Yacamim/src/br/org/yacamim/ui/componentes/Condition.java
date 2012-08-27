/**
 * Condition.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim.ui.componentes;

import android.view.View;

/**
 * Class Condition TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public interface Condition {
	
	/**
	 * 
	 * @param _dado
	 * @return
	 */
	public boolean checkToVisibility(final Object _dado);
	
	/**
	 * 
	 * @param _dado
	 * @param _view
	 * @return
	 */
	public void handle(final Object _dado, final View _view);

}
