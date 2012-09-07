/**
 * ValueTableRow.java
 *
 * Copyright 2012 yacamim.org.br
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.org.yacamim.ui.components;

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
