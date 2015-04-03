/**
 * YacamimResources.java
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
package br.org.yacamim;

import java.io.Serializable;

/**
 * 
 * Class YacamimResources TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class YacamimResources implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9163134809769378354L;
	
	private static final YacamimResources singleton = new YacamimResources();
	
	private int idResourceMsgOK;
	private int idResourceMsgSuccesfullyInserted;
	private int idResourceMsgSuccesfullyUpdated;
	private int idResourceMsgInvalidData;
	private int idResourceMsgSelectAnItem;
	private int idResourceMsgWait;
	private int idResourceMsgNoInformationFound;
	private int idResourceMsgNoRecordsFoundForParameters;
	private int idResourceMsgNoRecordSelected;
	private int idResourceMsgNoConnectivityAvailable;
	private int idResourceMsgNoWifiConnectivityAvailable;
	private int idResourceMsgConstraintDependency;

	/**
	 * 
	 */
	private YacamimResources() {
		super();
	}
	
	/**
	 * 
	 * @return
	 */
	public static YacamimResources getInstance() {
		return YacamimResources.singleton;
	}

	/**
	 * @return the idResourceMsgOK
	 */
	public int getIdResourceMsgOK() {
		return idResourceMsgOK;
	}

	/**
	 * @param idResourceMsgOK the idResourceMsgOK to set
	 */
	public YacamimResources setIdResourceMsgOK(int idResourceMsgOK) {
		this.idResourceMsgOK = idResourceMsgOK;
		return this;
	}

	/**
	 * @return the idResourceMsgSuccesfullyInserted
	 */
	public int getIdResourceMsgSuccesfullyInserted() {
		return idResourceMsgSuccesfullyInserted;
	}

	/**
	 * @param idResourceMsgSuccesfullyInserted the idResourceMsgSuccesfullyInserted to set
	 */
	public YacamimResources setIdResourceMsgSuccesfullyInserted(
			int idResourceMsgSuccesfullyInserted) {
		this.idResourceMsgSuccesfullyInserted = idResourceMsgSuccesfullyInserted;
		return this;
	}

	/**
	 * @return the idResourceMsgSuccesfullyUpdated
	 */
	public int getIdResourceMsgSuccesfullyUpdated() {
		return idResourceMsgSuccesfullyUpdated;
	}

	/**
	 * @param idResourceMsgSuccesfullyUpdated the idResourceMsgSuccesfullyUpdated to set
	 */
	public YacamimResources setIdResourceMsgSuccesfullyUpdated(
			int idResourceMsgSuccesfullyUpdated) {
		this.idResourceMsgSuccesfullyUpdated = idResourceMsgSuccesfullyUpdated;
		return this;
	}

	/**
	 * @return the idResourceMsgInvalidData
	 */
	public int getIdResourceMsgInvalidData() {
		return idResourceMsgInvalidData;
	}

	/**
	 * @param idResourceMsgInvalidData the idResourceMsgInvalidData to set
	 */
	public YacamimResources setIdResourceMsgInvalidData(int idResourceMsgInvalidData) {
		this.idResourceMsgInvalidData = idResourceMsgInvalidData;
		return this;
	}

	/**
	 * @return the idResourceMsgSelectAnItem
	 */
	public int getIdResourceMsgSelectAnItem() {
		return idResourceMsgSelectAnItem;
	}

	/**
	 * @param idResourceMsgSelectAnItem the idResourceMsgSelectAnItem to set
	 */
	public YacamimResources setIdResourceMsgSelectAnItem(int idResourceMsgSelectAnItem) {
		this.idResourceMsgSelectAnItem = idResourceMsgSelectAnItem;
		return this;
	}

	/**
	 * @return the idResourceMsgWait
	 */
	public int getIdResourceMsgWait() {
		return idResourceMsgWait;
	}

	/**
	 * @param idResourceMsgWait the idResourceMsgWait to set
	 */
	public YacamimResources setIdResourceMsgWait(int idResourceMsgWait) {
		this.idResourceMsgWait = idResourceMsgWait;
		return this;
	}

	/**
	 * @return the idResourceMsgNoInformationFound
	 */
	public int getIdResourceMsgNoInformationFound() {
		return idResourceMsgNoInformationFound;
	}

	/**
	 * @param idResourceMsgNoInformationFound the idResourceMsgNoInformationFound to set
	 */
	public YacamimResources setIdResourceMsgNoInformationFound(
			int idResourceMsgNoInformationFound) {
		this.idResourceMsgNoInformationFound = idResourceMsgNoInformationFound;
		return this;
	}

	/**
	 * @return the idResourceMsgNoRecordsFoundForParameters
	 */
	public int getIdResourceMsgNoRecordsFoundForParameters() {
		return idResourceMsgNoRecordsFoundForParameters;
	}

	/**
	 * @param idResourceMsgNoRecordsFoundForParameters the idResourceMsgNoRecordsFoundForParameters to set
	 */
	public YacamimResources setIdResourceMsgNoRecordsFoundForParameters(
			int idResourceMsgNoRecordsFoundForParameters) {
		this.idResourceMsgNoRecordsFoundForParameters = idResourceMsgNoRecordsFoundForParameters;
		return this;
	}

	/**
	 * @return the idResourceMsgNoRecordSelected
	 */
	public int getIdResourceMsgNoRecordSelected() {
		return idResourceMsgNoRecordSelected;
	}

	/**
	 * @param idResourceMsgNoRecordSelected the idResourceMsgNoRecordSelected to set
	 */
	public YacamimResources setIdResourceMsgNoRecordSelected(int idResourceMsgNoRecordSelected) {
		this.idResourceMsgNoRecordSelected = idResourceMsgNoRecordSelected;
		return this;
	}

	/**
	 * @return the idResourceMsgNoConnectivityAvailable
	 */
	public int getIdResourceMsgNoConnectivityAvailable() {
		return idResourceMsgNoConnectivityAvailable;
	}

	/**
	 * @param idResourceMsgNoConnectivityAvailable the idResourceMsgNoConnectivityAvailable to set
	 */
	public YacamimResources setIdResourceMsgNoConnectivityAvailable(
			int idResourceMsgNoConnectivityAvailable) {
		this.idResourceMsgNoConnectivityAvailable = idResourceMsgNoConnectivityAvailable;
		return this;
	}

	/**
	 * @return the idResourceMsgNoWifiConnectivityAvailable
	 */
	public int getIdResourceMsgNoWifiConnectivityAvailable() {
		return idResourceMsgNoWifiConnectivityAvailable;
	}

	/**
	 * @param idResourceMsgNoWifiConnectivityAvailable the idResourceMsgNoWifiConnectivityAvailable to set
	 */
	public YacamimResources setIdResourceMsgNoWifiConnectivityAvailable(int idResourceMsgNoWifiConnectivityAvailable) {
		this.idResourceMsgNoWifiConnectivityAvailable = idResourceMsgNoWifiConnectivityAvailable;
		return this;
	}

	/**
	 * @return the idResourceMsgConstraintDependency
	 */
	public int getIdResourceMsgConstraintDependency() {
		return idResourceMsgConstraintDependency;
	}

	/**
	 * @param idResourceMsgConstraintDependency the idResourceMsgConstraintDependency to set
	 */
	public YacamimResources setIdResourceMsgConstraintDependency(int idResourceMsgConstraintDependency) {
		this.idResourceMsgConstraintDependency = idResourceMsgConstraintDependency;
		return this;
	}
	
}