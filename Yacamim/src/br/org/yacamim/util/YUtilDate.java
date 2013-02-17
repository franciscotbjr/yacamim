/**
 * YUtilDate.java
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
package br.org.yacamim.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import br.org.yacamim.entity.GpsLocationInfo;

/**
 *
 * Class YUtilDate TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public strictfp abstract class YUtilDate {

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 */
	public static final Locale LOCALE_BRASIL;

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 */
	public static final TimeZone TIME_ZONE_PADRAO_BRASIL;

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 */
	public static final String ISO_COUNTRY_BRASIL = "BR";

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 */
	public static final String ISO_LANGUAGE_BRASIL = "pt";

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 */
	public static final String GMT_PADRAO_BRASIL = "GMT-3:00";

	static {
		LOCALE_BRASIL = new Locale(ISO_LANGUAGE_BRASIL, ISO_COUNTRY_BRASIL);

		TIME_ZONE_PADRAO_BRASIL = TimeZone.getTimeZone(GMT_PADRAO_BRASIL);
	}

	/**
	 *
	 */
	private YUtilDate() {
		super();
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 * @return
	 */
	public static Locale getLocaleBrasil() {
		return LOCALE_BRASIL;
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 * @return
	 */
	public static TimeZone getTimeZoneBrasil() {
		return TIME_ZONE_PADRAO_BRASIL;
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 * @return
	 */
	public static String getCurrentSQLLiteDateTime() {
		return getSimpleDateFormatDateTime().format(getCalendarBrasil().getTime());
	}


	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 * @return
	 */
	public static SimpleDateFormat getSimpleDateFormatDateTime() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", LOCALE_BRASIL);
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 * @return
	 */
	public static SimpleDateFormat getSimpleDateFormatHorario() {
		return new SimpleDateFormat("HH:mm:ss", LOCALE_BRASIL);
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 * @return
	 */
	public static SimpleDateFormat getSimpleDateFormatHorarioCurto() {
		return new SimpleDateFormat("HH:mm", LOCALE_BRASIL);
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 * @return
	 */
	public static SimpleDateFormat getSimpleDateFormatData() {
		return new SimpleDateFormat("dd/MM/yyyy", LOCALE_BRASIL);
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 * @param _value
	 * @return
	 */
	public static Date convertDate(String _value) {
		Date retorno = null;
		try {
			if (!YUtilString.isEmptyString(_value)) {
				retorno = getSimpleDateFormatData().parse(_value);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return retorno;
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 * @param _value
	 * @return
	 */
	public static Date convertHoraCurta(String _value) {
		Date retorno = null;
		try {
			if (!YUtilString.isEmptyString(_value)) {
				retorno = getSimpleDateFormatHorarioCurto().parse(_value);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return retorno;
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 * @param _value
	 * @return
	 */
	public static Date convertHora(String _value) {
		Date retorno = null;
		try {
			if (!YUtilString.isEmptyString(_value)) {
				retorno = getSimpleDateFormatHorario().parse(_value);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return retorno;
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 * @return
	 */
	public static Calendar getCleanCalendar() {
		final Calendar calendar = Calendar.getInstance(getLocaleBrasil());

		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.YEAR, 0);

		return calendar;
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 * @return
	 */
	public static Calendar getCalendarBrasil() {
		return Calendar.getInstance(getLocaleBrasil());
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 *
	 * @param _millisecond
	 * @param _second
	 * @param _minute
	 * @param _hourOfDay
	 * @param _dayOfMonth
	 * @param _month
	 * @param _year
	 * @return
	 */
	public static Calendar getFilledCleanedCalendar(
			final int _millisecond, final int _second, final int _minute,
			final int _hourOfDay, final int _dayOfMonth, final int _month, final int _year) {
		final Calendar calendar = getCleanCalendar();

		calendar.set(Calendar.MILLISECOND, _millisecond);
		calendar.set(Calendar.SECOND, _second);
		calendar.set(Calendar.MINUTE, _minute);
		calendar.set(Calendar.HOUR_OF_DAY, _hourOfDay);
		calendar.set(Calendar.DAY_OF_MONTH, _dayOfMonth);
		calendar.set(Calendar.MONTH, _month);
		calendar.set(Calendar.YEAR, _year);

		return calendar;
	}


	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 *
	 * @param gpsLocationInfo
	 * @return
	 */
	public static Date getDate(final GpsLocationInfo gpsLocationInfo) {
		Calendar calendar = getCalendar(gpsLocationInfo);
		final Date dataMomento = calendar.getTime();
		return dataMomento;
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 *
	 * @param gpsLocationInfo
	 * @return
	 */
	public static Calendar getCalendar(final GpsLocationInfo gpsLocationInfo) {
		Calendar calendar = YUtilDate.getCleanCalendar();
		if(gpsLocationInfo != null && gpsLocationInfo.getTime() > 0L) {
			calendar.setTime(new Date(gpsLocationInfo.getTime()));
		} else {
			calendar = YUtilDate.getCalendarBrasil();
		}
		return calendar;
	}

}