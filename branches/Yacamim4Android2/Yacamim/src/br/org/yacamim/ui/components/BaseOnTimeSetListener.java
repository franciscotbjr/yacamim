/**
 * BaseOnTimeSetListener.java
 *
 * Copyright 2012 yacamim.org.br
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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