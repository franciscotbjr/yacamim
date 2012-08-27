/**
 * BaseTimePickerDialog.java
 *
 * Copyright 2011 yacamim.org.br
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
package br.org.yacamim.ui.componentes;

import java.util.Calendar;

import android.app.TimePickerDialog;
import android.content.Context;
import br.org.yacamim.util.UtilDate;

/**
 * Class BaseTimePickerDialog TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class BaseTimePickerDialog extends TimePickerDialog {
	
	/**
	 * 
	 */
	private boolean updateTime;
	
	/**
	 * @param context
	 * @param callBack
	 * @param hourOfDay
	 * @param minute
	 * @param is24HourView
	 */
	public BaseTimePickerDialog(final Context _context, final BaseOnTimeSetListener _callBack,
			final int _hourOfDay, final int _minute, final boolean _is24HourView, 
			final int _idResourceTituloJanela, final boolean _updateTime) {
		super(_context, _callBack, _hourOfDay, _minute, _is24HourView);
		_callBack.setTimePickerDialog(this);
		this.setTitle(_context.getText(_idResourceTituloJanela));
		this.updateTime = _updateTime;
	}

	/**
	 * @param _context
	 * @param _theme
	 * @param _callBack
	 * @param _hourOfDay
	 * @param _minute
	 * @param _is24HourView
	 */
	public BaseTimePickerDialog(Context _context, int _theme,
			BaseOnTimeSetListener _callBack, int _hourOfDay, int _minute,
			boolean _is24HourView, final int _idResourceTituloJanela, final boolean _updateTime) {
		super(_context, _theme, _callBack, _hourOfDay, _minute, _is24HourView);
		_callBack.setTimePickerDialog(this);
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
		if(this.updateTime) {
			final Calendar calendarHoraria = UtilDate.getCalendarBrasil();
			super.updateTime(calendarHoraria.get(Calendar.HOUR_OF_DAY), calendarHoraria.get(Calendar.MINUTE));
		}
	}
	
}
