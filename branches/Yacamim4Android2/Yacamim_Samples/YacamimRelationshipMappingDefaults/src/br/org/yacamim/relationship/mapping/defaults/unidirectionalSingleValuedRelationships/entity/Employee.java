/**
 * Employee.java
 *
 * Copyright 2013 yacamim.org.br
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
package br.org.yacamim.relationship.mapping.defaults.unidirectionalSingleValuedRelationships.entity;

import android.os.Parcel;
import android.os.Parcelable;
import br.org.yacamim.entity.YBaseEntity;
import br.org.yacamim.persistence.Column;
import br.org.yacamim.persistence.Entity;
import br.org.yacamim.persistence.Id;
import br.org.yacamim.persistence.ManyToOne;
import br.org.yacamim.persistence.OneToOne;
import br.org.yacamim.persistence.Table;

/**
 * Classe Employee TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="Employee_usvr")
public class Employee extends YBaseEntity {
	
	private long id;
	private String name;
	private TravelProfile profile;
	private Address address;

	/**
	 * 
	 */
	public Employee() {
		super();
	}

	/**
	 * @param parcel
	 */
	public Employee(final Parcel parcel) {
		super(parcel);
	}

	/**
	 * 
	 */
	@Id
	public long getId() {
		return id;
	}

	/**
	 * 
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	@Column
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 */
	@Column
	@OneToOne
	public TravelProfile getProfile() {
		return profile;
	}

	/**
	 * 
	 * @param profile
	 */
	public void setProfile(TravelProfile profile) {
		this.profile = profile;
	}

	/**
	 * 
	 * @return
	 */
	@Column
	@ManyToOne
	public Address getAddress() {
		return address;
	}

	/**
	 * 
	 * @param address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * 
	 */
	public static final Parcelable.Creator<Employee> CREATOR = new Parcelable.Creator<Employee>() {
        public Employee createFromParcel(Parcel pc) {
            return new Employee(pc);
        }
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
	};

	/**
	 * 
	 * @see br.org.yacamim.entity.YBaseEntity#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		return result;
	}

	/**
	 * 
	 * @see br.org.yacamim.entity.YBaseEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (profile == null) {
			if (other.profile != null)
				return false;
		} else if (!profile.equals(other.profile))
			return false;
		return true;
	}
	

}
