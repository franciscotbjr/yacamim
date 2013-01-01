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
	private String yError;
	private String yMessage;

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
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the error
	 */
	public String getYError() {
		return yError;
	}

	/**
	 * @param yError the error to set
	 */
	public void setYError(String yError) {
		this.yError = yError;
	}

	/**
	 * @return the message
	 */
	public String getYMessage() {
		return yMessage;
	}

	/**
	 * @param yMessage the message to set
	 */
	public void setYMessage(String yMessage) {
		this.yMessage = yMessage;
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
		result = prime * result + ((yError == null) ? 0 : yError.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((yMessage == null) ? 0 : yMessage.hashCode());
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
		if (yError == null) {
			if (other.yError != null) {
				return false;
			}
		} else if (!yError.equals(other.yError)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (yMessage == null) {
			if (other.yMessage != null) {
				return false;
			}
		} else if (!yMessage.equals(other.yMessage)) {
			return false;
		}
		return true;
	}

}