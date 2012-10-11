/**
 * BaseOnDateSetListener.java
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

import android.app.DatePickerDialog;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import br.org.yacamim.util.YUtilDate;

/**
 * Class BaseOnDateSetListener TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class BaseOnDateSetListener implements DatePickerDialog.OnDateSetListener {
	
	/**
	 * 
	 */
	private BaseDatePickerDialog baseDatePickerDialog;
	
	/**
	 * 
	 */
	private TextView txtvDate;
	
	/**
	 * 
	 */
	private String format;

	/**
	 * 
	 * @param _txtvDate
	 * @param _format
	 */
	public BaseOnDateSetListener(final TextView _txtvDate, final String _format) {
		super();
		this.txtvDate = _txtvDate;
		this.format = _format;
	}

	/**
	 * 
	 *
	 * @see android.app.DatePickerDialog.OnDateSetListener#onDateSet(android.widget.DatePicker, int, int, int)
	 */
	@Override
	public void onDateSet(final DatePicker _view, int _year, final int _monthOfYear, final int _dayOfMonth) {
		try {
			Calendar calendar = YUtilDate.getFilledCleanedCalendar(0, 0, 0, 0, _dayOfMonth, _monthOfYear, _year);
            this.txtvDate.setText(DateFormat.format(this.format, calendar));
		} catch (Exception _e) {
			Log.e("BaseOnDateSetListener.onDateSet", _e.getMessage());
		}
	}

	/**
	 * @return the baseDatePickerDialog
	 */
	public BaseDatePickerDialog getBaseDatePickerDialog() {
		return baseDatePickerDialog;
	}

	/**
	 * @param _baseDatePickerDialog the baseDatePickerDialog to set
	 */
	public void setBaseDatePickerDialog(BaseDatePickerDialog _baseDatePickerDialog) {
		this.baseDatePickerDialog = _baseDatePickerDialog;
	}

}