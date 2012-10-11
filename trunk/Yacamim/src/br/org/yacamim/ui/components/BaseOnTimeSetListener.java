/**
 * BaseOnTimeSetListener.java
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

import android.app.TimePickerDialog;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;
import br.org.yacamim.util.YUtilDate;

/**
 * Class BaseOnTimeSetListener TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class BaseOnTimeSetListener implements android.app.TimePickerDialog.OnTimeSetListener {
	
	/**
	 * 
	 */
	private TimePickerDialog timePickerDialog;
	
	/**
	 * 
	 */
	private TextView txtvTime;
	
	/**
	 * 
	 */
	private String format;

	/**
	 * 
	 * @param _txtvTime
	 * @param _format
	 */
	public BaseOnTimeSetListener(final TextView _txtvTime, final String _format) {
		super();
		this.txtvTime = _txtvTime;
		this.format = _format;
	}
	
	/**
	 *
	 * @see android.app.TimePickerDialog.OnTimeSetListener#onTimeSet(android.widget.TimePicker, int, int)
	 */
	@Override
	public void onTimeSet(TimePicker _view, int _hourOfDay, int _minute) {
		try {
			Calendar calendar = YUtilDate.getFilledCleanedCalendar(0, 0, _minute, _hourOfDay, 0, 0, 0);
			this.txtvTime.setText((new java.text.SimpleDateFormat(this.format, YUtilDate.getLocaleBrasil())).format(calendar.getTime()));
			this.timePickerDialog.dismiss();
		} catch (Throwable _e) {
			Log.e("BaseOnTimeSetListener.onTimeSet", _e.getMessage());
		}
    }

	/**
	 * @return the timePickerDialog
	 */
	public TimePickerDialog getTimePickerDialog() {
		return timePickerDialog;
	}

	/**
	 * @param timePickerDialog the timePickerDialog to set
	 */
	void setTimePickerDialog(TimePickerDialog timePickerDialog) {
		this.timePickerDialog = timePickerDialog;
	}

}