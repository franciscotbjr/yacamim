/**
 * ManyToManyResult.java
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
package br.org.yacamim.persistence;

import java.lang.reflect.Method;

/**
 * Classe ManyToManyResult TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
class ManyToManyResult {
	
	private Class<?> targetClass;
	private Method targetMethod;
	private Object targetObject;
	private Method idMethod;
	
	/**
	 * 
	 */
	ManyToManyResult() {
		super();
	}

	/**
	 * 
	 * @return
	 */
	public Class<?> getTargetClass() {
		return targetClass;
	}

	/**
	 * 
	 * @param targetClass
	 */
	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	/**
	 * 
	 * @return
	 */
	public Method getTargetMethod() {
		return targetMethod;
	}

	/**
	 * 
	 * @param targetMethod
	 */
	public void setTargetMethod(Method targetMethod) {
		this.targetMethod = targetMethod;
	}

	/**
	 * 
	 * @return
	 */
	public Object getTargetObject() {
		return targetObject;
	}

	/**
	 * 
	 * @param targetObject
	 */
	public void setTargetObject(Object targetObject) {
		this.targetObject = targetObject;
	}

	/**
	 * 
	 * @return
	 */
	public Method getIdMethod() {
		return idMethod;
	}

	/**
	 * 
	 * @param idMethod
	 */
	public void setIdMethod(Method idMethod) {
		this.idMethod = idMethod;
	}

	/**
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idMethod == null) ? 0 : idMethod.hashCode());
		result = prime * result
				+ ((targetClass == null) ? 0 : targetClass.hashCode());
		result = prime * result
				+ ((targetMethod == null) ? 0 : targetMethod.hashCode());
		result = prime * result
				+ ((targetObject == null) ? 0 : targetObject.hashCode());
		return result;
	}

	/**
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ManyToManyResult other = (ManyToManyResult) obj;
		if (idMethod == null) {
			if (other.idMethod != null)
				return false;
		} else if (!idMethod.equals(other.idMethod))
			return false;
		if (targetClass == null) {
			if (other.targetClass != null)
				return false;
		} else if (!targetClass.equals(other.targetClass))
			return false;
		if (targetMethod == null) {
			if (other.targetMethod != null)
				return false;
		} else if (!targetMethod.equals(other.targetMethod))
			return false;
		if (targetObject == null) {
			if (other.targetObject != null)
				return false;
		} else if (!targetObject.equals(other.targetObject))
			return false;
		return true;
	}


}
