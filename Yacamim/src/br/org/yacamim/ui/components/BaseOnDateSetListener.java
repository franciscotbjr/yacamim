/**
 * BaseOnDateSetListener.java
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
	 * @param txtvDate
	 * @param format
	 */
	public BaseOnDateSetListener(final TextView txtvDate, final String format) {
		super();
		this.txtvDate = txtvDate;
		this.format = format;
	}

	/**
	 *
	 *
	 * @see android.app.DatePickerDialog.OnDateSetListener#onDateSet(android.widget.DatePicker, int, int, int)
	 */
	@Override
	public void onDateSet(final DatePicker view, int year, final int monthOfYear, final int dayOfMonth) {
		try {
			Calendar calendar = YUtilDate.getFilledCleanedCalendar(0, 0, 0, 0, dayOfMonth, monthOfYear, year);
            this.txtvDate.setText(DateFormat.format(this.format, calendar));
		} catch (Exception e) {
			Log.e("BaseOnDateSetListener.onDateSet", e.getMessage());
		}
	}

	/**
	 * @return the baseDatePickerDialog
	 */
	public BaseDatePickerDialog getBaseDatePickerDialog() {
		return baseDatePickerDialog;
	}

	/**
	 * @param baseDatePickerDialog the baseDatePickerDialog to set
	 */
	public void setBaseDatePickerDialog(BaseDatePickerDialog baseDatePickerDialog) {
		this.baseDatePickerDialog = baseDatePickerDialog;
	}

}