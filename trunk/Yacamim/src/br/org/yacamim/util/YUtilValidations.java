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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;
import br.org.yacamim.entity.BaseEntity;

/**
 *
 * Class YUtilValidations TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public strictfp abstract class YUtilValidations {

	/**
	 *
	 */
	private YUtilValidations() {
		super();
	}

	/**
	 * <strong>Brazilian users only</strong>.<br/>
	 *
	 * @param _strCpfParam
	 * @return
	 */
	public static boolean isCpfValido(String _strCpfParam) {
		if ((_strCpfParam == null) || _strCpfParam.equals("") || (_strCpfParam.length() < 11)) {
			return false;
		}
		if (!_strCpfParam.matches(YUtilString.CPF_REGEX)) {
			return false;
		}

		String cpfDesformatado = YUtilFormatting.unformatNumericValue(_strCpfParam);
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
		} catch (Exception _e) {
			Log.e("YUtilValidations.isCpfValido", _e.getMessage());
			return false;
		}
	}

	/**
	 *
	 * @param _email
	 * @return
	 */
	public static boolean isValidEmail(String _email) {
		if (YUtilString.isEmptyString(_email)) {
			return false;
		}
		Pattern p = Pattern.compile(YUtilString.EMAIL_REGEX);
		Matcher m = p.matcher(_email);
		if (m.find()) {
			return true;
		}
		return false;
	}


	/**
	 *
	 * @param _number
	 * @param _digit
	 * @return
	 * @throws Exception
	 */
	public static boolean isMod10(String _number, char _digit) throws Exception {
		String strDigit = "" + _digit;
		if(YUtilString.isEmptyString(strDigit)) {
			throw new Exception("Dígito vazio ou null!");
		}
		if(!YUtilString.isInteger(strDigit)) {
			throw new Exception("Dígito contém caracteres inválidos!");
		}


		int mod10 = mod10(_number);
		int digito = Integer.valueOf(strDigit);

		return mod10 == digito;
	}

	/**
	 *
	 * @param _number
	 * @return
	 * @throws Exception
	 */
	public static int mod10(String _number) throws Exception {

		if(YUtilString.isEmptyString(_number)) {
			throw new Exception("String vazia ou null!");
		}
		if(!YUtilString.isInteger(_number)) {
			throw new Exception("String contém caracteres inválidos!");
		}

	    int multi = 2;
	    int posicao1 = _number.length()-1;
	    int posicao2;
	    int acumula = 0;
	    int resultado;
	    int dac = 0;

	    while (posicao1 >= 0) {
	        resultado = Integer.parseInt(_number.substring(posicao1,posicao1+1)) * multi;
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
	 * @param _entity
	 * @return
	 */
	public static boolean isIntegralEntity(final BaseEntity _entity) {
		if(_entity == null || _entity.getId() <= 0) {
			return false;
		}
		return true;
	}

}
