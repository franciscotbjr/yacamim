/**
 * YUtilValidations.java
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

import java.util.regex.Pattern;

import android.util.Log;
import br.org.yacamim.entity.YBaseEntity;

/**
 *
 * Class YUtilValidations TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public strictfp abstract class YUtilValidations {
	
	private static final String TAG = YUtilValidations.class.getSimpleName();

	/**
	 *
	 */
	private YUtilValidations() {
		super();
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 *
	 * @param strCpfParam
	 * @return
	 */
	public static boolean isCpfValido(final String strCpfParam) {
		if ((strCpfParam == null) || strCpfParam.equals("") || (strCpfParam.length() < 11)) {
			return false;
		}
		if (!strCpfParam.matches(YUtilString.CPF_REGEX)) {
			return false;
		}

		String cpfDesformatado = YUtilFormatting.unformatNumericValue(strCpfParam);
		if (cpfDesformatado.length() != 11) {
			return false;
		}

		int soma = 0;

		try {

			if((cpfDesformatado.equals("11111111111"))||
				(cpfDesformatado.equals("22222222222"))||
				(cpfDesformatado.equals("33333333333"))||
				(cpfDesformatado.equals("44444444444"))||
				(cpfDesformatado.equals("55555555555"))||
				(cpfDesformatado.equals("66666666666"))||
				(cpfDesformatado.equals("77777777777"))||
				(cpfDesformatado.equals("88888888888"))||
				(cpfDesformatado.equals("99999999999"))){
				return false;
			}


			for (int i = 0; i < 9; i++) {
				soma += Integer.parseInt(cpfDesformatado.substring(i, i + 1)) * (10 - i);
			}
			if (soma == 0) {
				return false;
			}
			soma = 11 - (soma % 11);
			if (soma > 9) {
				soma = 0;
			}
			if (Integer.parseInt(cpfDesformatado.substring(9, 10)) != soma) {
				return false;
			}
			soma *= 2;
			for (int i = 0; i < 9; i++) {
				soma += Integer.parseInt(cpfDesformatado.substring(i, i + 1)) * (11 - i);
			}
			soma = 11 - (soma % 11);
			if (soma > 9) {
				soma = 0;
			}
			if (Integer.parseInt(cpfDesformatado.substring(10)) != soma) {
				return false;
			}
			return true;
		} catch (Exception e) {
			Log.e(TAG + ".isCpfValido", e.getMessage());
			return false;
		}
	}

	/**
	 *
	 * @param number
	 * @param digit
	 * @return
	 * @throws Exception
	 */
	public static boolean isMod10(final String number, final char digit) throws Exception {
		String strDigit = "" + digit;
		if(YUtilString.isEmptyString(strDigit)) {
			throw new Exception("Digit empty or null!");
		}
		if(!YUtilString.isInteger(strDigit)) {
			throw new Exception("Digit contains invalid characters!");
		}


		int mod10 = mod10(number);
		int digito = Integer.valueOf(strDigit);

		return mod10 == digito;
	}

	/**
	 *
	 * @param number
	 * @return
	 * @throws Exception
	 */
	public static int mod10(final String number) throws Exception {

		if(YUtilString.isEmptyString(number)) {
			throw new Exception("Empty String or null!");
		}
		if(!YUtilString.isInteger(number)) {
			throw new Exception("String contains invalid characters!");
		}

	    int multi = 2;
	    int posicao1 = number.length()-1;
	    int posicao2;
	    int acumula = 0;
	    int resultado;
	    int dac = 0;

	    while (posicao1 >= 0) {
	        resultado = Integer.parseInt(number.substring(posicao1,posicao1+1)) * multi;
	        posicao2  = Integer.toString(resultado).length()-1;

	        while (posicao2 >= 0) {
	            acumula += Integer.parseInt(Integer.toString(resultado).substring(posicao2,posicao2+1));
	            posicao2--;
	        }

	        if (multi == 2) {
	            multi = 1;
	        } else {
	            multi = 2;
	        }

	        posicao1--;
	    }

	    dac = acumula % 10;
	    dac = 10 - dac;

	    if (dac == 10) {
	        dac = 0;
	    }

	    return dac;
	}
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isValidEmail(final String email){
		if(YUtilString.isEmptyString(email)) {
			return false;
		}
		return Pattern
				.compile(YUtilString.EMAIL_REGEX)
					.matcher(email)
						.matches();
	}

	/**
	 *
	 * @param entity
	 * @return
	 */
	public static boolean isIntegralEntity(final YBaseEntity entity) {
		if(entity == null || entity.getId() <= 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param value
	 * @param minRange
	 * @param maxRange
	 * @return
	 */
	public static boolean isStringInRange(final String value, final int minRange, final int maxRange) {
		if (YUtilString.isEmptyString(value)) {
			return false;
		}
		return ((value.length() >= minRange) && (value.length() <= maxRange));
	}

}
