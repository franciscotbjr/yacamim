/**
 * YBaseEntity.java
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
package br.org.yacamim.entity;

import android.os.Parcel;
import android.os.Parcelable;
import br.org.yacamim.util.YUtilParcel;

/**
 *
 * Class YBaseEntity TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim JÃºnior)
 * @version 1.0
 * @since 1.0
 */
public class YBaseEntity implements Parcelable {

	protected long id;
	private String error;
	private String message;

	/**
	 *
	 */
	public YBaseEntity() {
		super();
	}

	/**
	 *
	 */
	protected YBaseEntity(Parcel parcel) {
		super();
		YUtilParcel.fillAttributesFromParcel(this, parcel);
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param _id the id to set
	 */
	public void setId(long _id) {
		this.id = _id;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param _error the error to set
	 */
	public void setError(String _error) {
		this.error = _error;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param _message the message to set
	 */
	public void setMessage(String _message) {
		this.message = _message;
	}

	// Parcelable implementations

	/**
	 *
	 */
	@Override
	public int describeContents() {
		return hashCode();
	}

	/**
	 *
	 *
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		YUtilParcel.writeToParcel(this, dest);
	}

	/**
	 *
	 */
	public static Parcelable.Creator<? extends YBaseEntity> CREATOR = new Parcelable.Creator<YBaseEntity>() {
		public YBaseEntity createFromParcel(Parcel in) {
			return new YBaseEntity(in);
		}

		public YBaseEntity[] newArray(int size) {
			return new YBaseEntity[size];
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
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((message == null) ? 0 : message.hashCode());
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
		if (!(obj instanceof YBaseEntity)) {
			return false;
		}
		YBaseEntity other = (YBaseEntity) obj;
		if (error == null) {
			if (other.error != null) {
				return false;
			}
		} else if (!error.equals(other.error)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (message == null) {
			if (other.message != null) {
				return false;
			}
		} else if (!message.equals(other.message)) {
			return false;
		}
		return true;
	}

}