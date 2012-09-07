/**
 * BaseDatePickerDialog.java
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

import java.util.Calendar;

import br.org.yacamim.util.UtilDate;
import android.app.DatePickerDialog;
import android.content.Context;

/**
 * Class BaseDatePickerDialog TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class BaseDatePickerDialog extends DatePickerDialog {
	
	/**
	 * 
	 */
	private boolean updateDate;

	/**
	 * 
	 * @param _context
	 * @param _callBack
	 * @param _year
	 * @param _monthOfYear
	 * @param _dayOfMonth
	 * @param _idResourceTituloJanela
	 */
	public BaseDatePickerDialog(final Context _context, final BaseOnDateSetListener _callBack,
			final int _year, final int _monthOfYear, final int _dayOfMonth, 
			final int _idResourceTituloJanela, final boolean _updateDate) {
		super(_context, _callBack, _year, _monthOfYear, _dayOfMonth);
		_callBack.setBaseDatePickerDialog(this);
		this.setTitle(_context.getText(_idResourceTituloJanela));
		this.updateDate = _updateDate;
	}

	/**
	 * 
	 * @param _context
	 * @param _theme
	 * @param _callBack
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @param _idResourceTituloJanela
	 */
	public BaseDatePickerDialog(final Context _context, final int _theme,
			final BaseOnDateSetListener _callBack, final int year, final int monthOfYear,
			final int dayOfMonth, final int _idResourceTituloJanela, final boolean _updateDate) {
		super(_context, _theme, _callBack, year, monthOfYear, dayOfMonth);
		_callBack.setBaseDatePickerDialog(this);
		this.setTitle(_context.getText(_idResourceTituloJanela));
	}
	
	/**
	 * 
	 *
	 * @see android.app.Dialog#onAttachedToWindow()
	 */
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		if(this.updateDate) {
			final Calendar timeCalendar = UtilDate.getCalendarBrasil();
			super.updateDate(timeCalendar.get(Calendar.YEAR), timeCalendar.get(Calendar.MONTH), timeCalendar.get(Calendar.DAY_OF_MONTH));
		}
	}
	
}