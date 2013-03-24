/**
 * Project.java
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
package br.org.yacamim.relationship.mapping.defaults.bidirectionalManyToManyRelationships.entity;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import br.org.yacamim.entity.YBaseEntity;
import br.org.yacamim.persistence.Column;
import br.org.yacamim.persistence.Entity;
import br.org.yacamim.persistence.Id;
import br.org.yacamim.persistence.ManyToMany;
import br.org.yacamim.persistence.Table;

/**
 * Classe Project TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="Project_mm")
public class Project extends YBaseEntity {
	
	private long id;
	private String name;
	private List<Employee> employees;

	/**
	 * 
	 */
	public Project() {
		super();
	}

	/**
	 * @param parcel
	 */
	public Project(final Parcel parcel) {
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
	@ManyToMany
	@Column
	public List<Employee> getEmployees() {
		return employees;
	}
	
	/**
	 * 
	 * @param employees
	 */
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	/**
	 * 
	 */
	public static final Parcelable.Creator<Project> CREATOR = new Parcelable.Creator<Project>() {
        public Project createFromParcel(Parcel pc) {
            return new Project(pc);
        }
        public Project[] newArray(int size) {
            return new Project[size];
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
		result = prime * result
				+ ((employees == null) ? 0 : employees.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Project other = (Project) obj;
		if (employees == null) {
			if (other.employees != null)
				return false;
		} else if (!employees.equals(other.employees))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
