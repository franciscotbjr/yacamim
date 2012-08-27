/**
 * UtilCryptographic.java
 *
 * Criado em 6 de Novembro de 2006, 11:01
 *
 * Copyright 2006 Yacamim.org (Francisco Tarcizo Bomfim Júnior)
 */
package br.org.yacamim.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

import android.util.Log;

/**
 * Class UtilCryptographic TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public strictfp abstract class UtilCryptographic {
    
	/**
	 * 
	 */
    private UtilCryptographic() {
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
        	Log.e("UtilCryptographic.encodeBase64", _e.getMessage());
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
        	Log.e("UtilCryptographic.decodeBase64", _e.getMessage());
            return null;
        }
    }    
    
    /**
     * 
     * @param _string
     * @return
     */
    public static String md5(final String _string) {
    	return UtilCryptographic.stringHexa(UtilCryptographic.makeHash(_string, "MD5"));
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
    	return UtilCryptographic.md5(bufferSenha.toString());
    }

    /**
     * 
     * @return
     */
    public static String newPassword() {
    	String retorno = UtilCryptographic.encryptPassword(System.nanoTime()+"");
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
			Log.e("UtilCryptographic.makeHash", _e.getMessage());
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
