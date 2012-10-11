/**
 * YUtilCryptographic.java
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
    
	/**
	 * 
	 */
    private YUtilCryptographic() {
        super();
    }
    
    /**
     * 
     * @param _string
     * @return
     */
    public static String encodeBase64(final String _string) {
        try {
            Base64 base64 = new Base64();
            String resultado = new String(base64.encode(_string.getBytes()));
            return resultado;
        } catch (Exception _e) {
        	Log.e("YUtilCryptographic.encodeBase64", _e.getMessage());
            return null;
        }

    }
	
    /**
     * 
     * @param _string
     * @return
     */
    public static String decodeBase64(final String _string) {
        try {
            Base64 base64 = new Base64();
            byte[] b = base64.decode(_string.getBytes());
            String resultado = new String(b);
            return resultado;
        } catch (Exception _e) {
        	Log.e("YUtilCryptographic.decodeBase64", _e.getMessage());
            return null;
        }
    }    
    
    /**
     * 
     * @param _string
     * @return
     */
    public static String md5(final String _string) {
    	return YUtilCryptographic.stringHexa(YUtilCryptographic.makeHash(_string, "MD5"));
    }
    

    /**
     * 
     * @param _string
     * @return
     */
    public static String encryptPassword(final String _string) {
    	final int maxSize = 35;
    	int dif = maxSize - _string.length();
    	StringBuffer bufferSenha = new StringBuffer();
    	bufferSenha.append(_string);
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
     * @param _phrase
     * @param _algorithm
     * @return
     */
    private static byte[] makeHash(String _phrase, String _algorithm) {
		try {
			MessageDigest md = MessageDigest.getInstance(_algorithm);
			md.update(_phrase.getBytes());
			return md.digest();
		} catch (NoSuchAlgorithmException _e) {
			Log.e("YUtilCryptographic.makeHash", _e.getMessage());
			return null;
		}
    }
	
    /**
     * 
     * @param _bytes
     * @return
     */
	private static String stringHexa(byte[] _bytes) {
	   StringBuilder s = new StringBuilder();
	   for (int i = 0; i < _bytes.length; i++) {
	       int parteAlta = ((_bytes[i] >> 4) & 0xf) << 4;
	       int parteBaixa = _bytes[i] & 0xf;
	       if (parteAlta == 0) s.append('0');
	       s.append(Integer.toHexString(parteAlta | parteBaixa));
	   }
	   return s.toString();
	}    
}
