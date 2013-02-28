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
package br.org.yacamim.relationship.mapping.defaults.unidirectionalMultiValuedRelationships.entity;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import br.org.yacamim.entity.YBaseEntity;
import br.org.yacamim.persistence.Column;
import br.org.yacamim.persistence.Entity;
import br.org.yacamim.persistence.Id;
import br.org.yacamim.persistence.OneToMany;
import br.org.yacamim.persistence.Table;

/**
 * Classe Employee TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="Employee_umvr")
public class Employee extends YBaseEntity {
	
	private long id;
	private String name;
	private List<AnnualReview> annualReviews;

	/**
	 * 
	 */
	public Employee() {
		super();
	}

	/**
	 * 
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
	@OneToMany
	@Column
	public List<AnnualReview> getAnnualReviews() {
		return annualReviews;
	}

	/**
	 * 
	 */
	public void setAnnualReviews(List<AnnualReview> annualReviews) {
		this.annualReviews = annualReviews;
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

}