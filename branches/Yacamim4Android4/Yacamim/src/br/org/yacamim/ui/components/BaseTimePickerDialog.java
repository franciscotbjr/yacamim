/**
 * BaseTimePickerDialog.java
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
import android.content.Context;
import br.org.yacamim.util.YUtilDate;

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
	public BaseTimePickerDialog(final Context context, final BaseOnTimeSetListener callBack,
			final int hourOfDay, final int minute, final boolean is24HourView,
			final int idResourceTituloJanela, final boolean updateTime) {
		super(context, callBack, hourOfDay, minute, is24HourView);
		callBack.setTimePickerDialog(this);
		this.setTitle(context.getText(idResourceTituloJanela));
		this.updateTime = updateTime;
	}

	/**
	 * @param context
	 * @param theme
	 * @param callBack
	 * @param hourOfDay
	 * @param minute
	 * @param is24HourView
	 */
	public BaseTimePickerDialog(Context context, int theme,
			BaseOnTimeSetListener callBack, int hourOfDay, int minute,
			boolean is24HourView, final int idResourceTituloJanela, final boolean updateTime) {
		super(context, theme, callBack, hourOfDay, minute, is24HourView);
		callBack.setTimePickerDialog(this);
		this.setTitle(context.getText(idResourceTituloJanela));
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
			final Calendar calendarHoraria = YUtilDate.getCalendarBrasil();
			super.updateTime(calendarHoraria.get(Calendar.HOUR_OF_DAY), calendarHoraria.get(Calendar.MINUTE));
		}
	}

}
