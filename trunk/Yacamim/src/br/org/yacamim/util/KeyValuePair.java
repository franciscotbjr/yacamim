/**
 * KeyValuePair.java
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

import java.io.Serializable;

/**
 * Componente utilitario que presenta pares do tipo key=value.<br>
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class KeyValuePair<C, V> implements Serializable, Cloneable {
	
	/**
	 * 
	 * @since 1.0
	 */
	private static final long serialVersionUID = -288711680984740741L;
    
    private static final String KEY_CAN_T_BE_NULL = "Key can't be null!";

	/**
     * 
     * @since 1.0
     */
    protected static final int PRIME = 31;
    
    /**
     * Pair key.<br>
     * 
     * @since 1.0
     */
    private C key;
    
    /**
     * Pair value.<br>
     * 
     * @since 1.0
     */
    private V value;
    
    /**
     * 
     * @since 1.0
     */
    private int keyHash;

    /**
     * 
     */
    public KeyValuePair() {
        super();
    }
    
    /**
     * 
     * @param _key
     * @param _value
     */
    public KeyValuePair(C _key, V _value) {
        super();
        if(_key == null) {
        	throw new IllegalArgumentException(KEY_CAN_T_BE_NULL);
        }
        this.key = _key;
        this.value =  _value;
        this.makeKeyHash();
    }    

    /**
     * 
     * @return
     */
    public C getKey() {
        return this.key;
    }

    /**
     * 
     * @return
     */
    public V getValue() {
        return this.value;
    }

    /**
     * 
     * @param _key
     */
    public void setKey(C _key) {
        if(_key == null) {
        	throw new IllegalArgumentException(KEY_CAN_T_BE_NULL);
        }
        this.key = _key;
        this.makeKeyHash();
    }

    /**
     * 
     * @param _value
     */
    public void setValue(V _value) {
        this.value = _value;
    }
    
    /**
     * 
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "[" + this.key + "=" + this.value + "]";
    }
    
    /**
     * 
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = -1;
        
        result = PRIME * result;
        result = PRIME * result + ((this.key==null)? 0 : this.key.hashCode());
        result = PRIME * result + ((this.value==null)? 0 : this.value.hashCode());
        
        return result;
    }
    
    /**
     * 
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object _o) {
        if(_o == null) {
            return false;
        }
    	if(this == _o) {
            return true;
        }
        if(!(_o instanceof br.org.yacamim.util.KeyValuePair<?, ?>)) {
            return false;
        }
        if(_o.hashCode() != this.hashCode()) {
            return false;
        }
        return true;
    }
    
    /**
     * 
     * @return
     */
    public int keyHash() {
    	return this.keyHash;
    }

    /**
     * 
     */
    private void makeKeyHash() {
        this.keyHash = KeyValuePair.keyHash(this.key);
    }
    
    /**
     * 
     * @param _key
     * @return
     */
    public static int keyHash(Object _key) {
    	int result = -1;
        result = PRIME * result;
        result = PRIME * result + ((_key==null)? 0 : _key.hashCode());        
        return result;
    }
    
    /**
     * 
     *
     * @see java.lang.Object#clone()
     */
    public Object clone() {
		return new KeyValuePair<C, V>(this.key, this.value);
    }
}
