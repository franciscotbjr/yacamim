/**
 * BaseOnTimeSetListener.java
 *
 * Copyright 2011 yacamim.org.br
 */
package br.org.yacamim.ui.componentes;

import java.util.Calendar;

import android.app.TimePickerDialog;
import android.util.Log;
import android.widget.TextView;
import android.widget.TimePicker;
import br.org.yacamim.util.UtilDate;

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
			Calendar calendar = UtilDate.getFilledCleanedCalendar(0, 0, _minute, _hourOfDay, 0, 0, 0);
			this.txtvTime.setText((new java.text.SimpleDateFormat(this.format, UtilDate.getLocaleBrasil())).format(calendar.getTime()));
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