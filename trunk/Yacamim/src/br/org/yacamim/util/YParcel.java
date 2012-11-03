/**
 * YParcel.java
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
		YUtilParcel.fillValueFromParcel(this, parcel);
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
		YUtilParcel.writeToParcel(this.value, dest);
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