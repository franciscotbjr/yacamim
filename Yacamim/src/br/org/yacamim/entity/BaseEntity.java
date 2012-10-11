/**
 * BaseEntity.java
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
package br.org.yacamim.entity;

import android.os.Parcel;
import android.os.Parcelable;
import br.org.yacamim.util.YUtilParcel;

/**
 * 
 * Class BaseEntity TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class BaseEntity implements Parcelable {
	
	protected long id;
	private String error;
	private String message;

	/**
	 * 
	 */
	public BaseEntity() {
		super();
	}
	
	/**
	 * 
	 */
	protected BaseEntity(Parcel parcel) {
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
	public static Parcelable.Creator<? extends BaseEntity> CREATOR = new Parcelable.Creator<BaseEntity>() {
		public BaseEntity createFromParcel(Parcel in) {
			return new BaseEntity(in); 
		}

		public BaseEntity[] newArray(int size) {
			return new BaseEntity[size];
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
		if (!(obj instanceof BaseEntity)) {
			return false;
		}
		BaseEntity other = (BaseEntity) obj;
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