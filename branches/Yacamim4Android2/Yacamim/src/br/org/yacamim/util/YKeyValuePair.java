/**
 * YKeyValuePair.java
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

import java.io.Serializable;

/**
 * Componente utilitario que presenta pares do tipo key=value.<br>
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YKeyValuePair<C, V> implements Serializable, Cloneable {

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
    public YKeyValuePair() {
        super();
    }

    /**
     *
     * @param key
     * @param value
     */
    public YKeyValuePair(final C key, final V value) {
        super();
        if(key == null) {
        	throw new IllegalArgumentException(KEY_CAN_T_BE_NULL);
        }
        this.key = key;
        this.value =  value;
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
     * @param key
     */
    public void setKey(final C key) {
        if(key == null) {
        	throw new IllegalArgumentException(KEY_CAN_T_BE_NULL);
        }
        this.key = key;
        this.makeKeyHash();
    }

    /**
     *
     * @param value
     */
    public void setValue(final V value) {
        this.value = value;
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
    public boolean equals(final Object o) {
        if(o == null) {
            return false;
        }
    	if(this == o) {
            return true;
        }
        if(!(o instanceof br.org.yacamim.util.YKeyValuePair<?, ?>)) {
            return false;
        }
        if(o.hashCode() != this.hashCode()) {
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
        this.keyHash = YKeyValuePair.keyHash(this.key);
    }

    /**
     *
     * @param key
     * @return
     */
    public static int keyHash(final Object key) {
    	int result = -1;
        result = PRIME * result;
        result = PRIME * result + ((key==null)? 0 : key.hashCode());
        return result;
    }

    /**
     *
     *
     * @see java.lang.Object#clone()
     */
    public Object clone() {
		return new YKeyValuePair<C, V>(this.key, this.value);
    }
}
