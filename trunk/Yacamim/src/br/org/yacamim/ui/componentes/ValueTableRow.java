/**
 * ValueTableRow.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim.ui.componentes;

import java.io.Serializable;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableRow;

/**
 * 
 * Class ValueTableRow TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class ValueTableRow extends TableRow {
	
	/**
	 * 
	 */
	private Serializable value;

	/**
	 * @param context
	 */
	public ValueTableRow(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param _attrs
	 */
	public ValueTableRow(final Context context, final AttributeSet _attrs) {
		super(context, _attrs);
	}

	/**
	 * 
	 * @return
	 */
	public Serializable getValue() {
		return value;
	}

	/**
	 * 
	 * @param _value
	 */
	public void setValue(final Serializable _value) {
		this.value = _value;
	}

}
