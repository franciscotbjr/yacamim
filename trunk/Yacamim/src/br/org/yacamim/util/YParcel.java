/**
 * YParcel.java
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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 
 * Class BaseEntity TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YParcel implements Parcelable {
	
	private Object value;

	/**
	 * 
	 * @param value
	 */
	public YParcel(Object value) {
		super();
		this.value = value;
	}
	
	/**
	 * 
	 * @param parcel
	 */
	protected YParcel(Parcel parcel) {
		super();
		UtilParcel.fillValueFromParcel(this, parcel);
	}
	
	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	void setValue(Object value) {
		this.value = value;
	}

	/**
	 * 
	 *
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		return this.hashCode();
	}

	/**
	 * 
	 *
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.value.getClass().getName());
		UtilParcel.writeToParcel(this.value, dest);
	}
	
	/**
	 * 
	 */
	public static Parcelable.Creator<YParcel> CREATOR = new Parcelable.Creator<YParcel>() {
		public YParcel createFromParcel(Parcel in) {
			return new YParcel(in); 
		}

		public YParcel[] newArray(int size) {
			return new YParcel[size];
		}
	};

	/**
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/**
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof YParcel)) {
			return false;
		}
		YParcel other = (YParcel) obj;
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

}