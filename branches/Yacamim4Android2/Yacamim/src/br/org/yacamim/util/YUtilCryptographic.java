/**
 * YUtilCryptographic.java
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import android.util.Log;

/**
 * Class YUtilCryptographic TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public strictfp abstract class YUtilCryptographic {
	
	private static final String TAG = YUtilCryptographic.class.getSimpleName();

	/**
	 *
	 */
    private YUtilCryptographic() {
        super();
    }

    /**
     *
     * @param string
     * @return
     */
    public static String encodeBase64(final String string) {
        try {
            Base64 base64 = new Base64();
            String resultado = new String(base64.encode(string.getBytes()));
            return resultado;
        } catch (Exception e) {
        	Log.e(TAG + ".encodeBase64", e.getMessage());
            return null;
        }

    }

    /**
     *
     * @param string
     * @return
     */
    public static String decodeBase64(final String string) {
        try {
            Base64 base64 = new Base64();
            byte[] b = base64.decode(string.getBytes());
            String resultado = new String(b);
            return resultado;
        } catch (Exception e) {
        	Log.e(TAG + ".decodeBase64", e.getMessage());
            return null;
        }
    }

    /**
     *
     * @param string
     * @return
     */
    public static String md5(final String string) {
    	return YUtilCryptographic.stringHexa(YUtilCryptographic.makeHash(string, "MD5"));
    }


    /**
     *
     * @param string
     * @return
     */
    public static String encryptPassword(final String string) {
    	final int maxSize = 35;
    	int dif = maxSize - string.length();
    	StringBuffer bufferSenha = new StringBuffer();
    	bufferSenha.append(string);
    	while(dif >= 0) {
    		dif--;
    		bufferSenha.insert(0,"0");
    	}
    	return YUtilCryptographic.md5(bufferSenha.toString());
    }

    /**
     *
     * @return
     */
    public static String newPassword() {
    	String retorno = YUtilCryptographic.encryptPassword(System.nanoTime()+"");
    	retorno = retorno.substring(0, 8);
    	return retorno;
    }

    /**
     *
     * @param phrase
     * @param algorithm
     * @return
     */
    private static byte[] makeHash(final String phrase, final String algorithm) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(phrase.getBytes());
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			Log.e(TAG + ".makeHash", e.getMessage());
			return null;
		}
    }

    /**
     *
     * @param bytes
     * @return
     */
	private static String stringHexa(final byte[] bytes) {
	   StringBuilder s = new StringBuilder();
	   for (int i = 0; i < bytes.length; i++) {
	       int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
	       int parteBaixa = bytes[i] & 0xf;
	       if (parteAlta == 0) s.append('0');
	       s.append(Integer.toHexString(parteAlta | parteBaixa));
	   }
	   return s.toString();
	}
}
