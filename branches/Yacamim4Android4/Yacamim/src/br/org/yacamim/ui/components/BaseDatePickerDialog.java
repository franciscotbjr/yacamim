/**
 * BaseDatePickerDialog.java
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
import android.content.Context;
import br.org.yacamim.util.YUtilDate;

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
	 * @param context
	 * @param callBack
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @param idResourceTituloJanela
	 */
	public BaseDatePickerDialog(final Context context, final BaseOnDateSetListener callBack,
			final int year, final int monthOfYear, final int dayOfMonth,
			final int idResourceTituloJanela, final boolean updateDate) {
		super(context, callBack, year, monthOfYear, dayOfMonth);
		callBack.setBaseDatePickerDialog(this);
		this.setTitle(context.getText(idResourceTituloJanela));
		this.updateDate = updateDate;
	}

	/**
	 *
	 * @param context
	 * @param theme
	 * @param callBack
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @param idResourceTituloJanela
	 */
	public BaseDatePickerDialog(final Context context, final int theme,
			final BaseOnDateSetListener callBack, final int year, final int monthOfYear,
			final int dayOfMonth, final int idResourceTituloJanela, final boolean updateDate) {
		super(context, theme, callBack, year, monthOfYear, dayOfMonth);
		callBack.setBaseDatePickerDialog(this);
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
		if(this.updateDate) {
			final Calendar timeCalendar = YUtilDate.getCalendarBrasil();
			super.updateDate(timeCalendar.get(Calendar.YEAR), timeCalendar.get(Calendar.MONTH), timeCalendar.get(Calendar.DAY_OF_MONTH));
		}
	}

}