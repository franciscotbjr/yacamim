/**
 * YProxyLoad.java
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
import java.util.List;

import android.util.Log;
import br.org.yacamim.entity.YBaseEntity;
import br.org.yacamim.util.YUtilReflection;

/**
 * Class responsible for loading proxy objects in entities
 * 
 * @author manny.
 * Criado em Mar 16, 2013 6:40:10 AM
 * @version 1.0
 * @since 1.0
 */
public class YProxyLoad {
	/**
	 * Load the entity of a proxy object.
	 * 
	 * @param proxy
	 * @param loadAll
	 * @return
	 */
	public YBaseEntity load(Object proxy, boolean loadAll) {
		YBaseEntity objReturn = null;
		try {
			objReturn = (YBaseEntity) proxy.getClass().getSuperclass().newInstance();
			List<Method> getMethods = YUtilReflection.getGetMethodList(proxy.getClass());
			for (Method method : getMethods) {
				String propertyName = YUtilReflection.getPropertyName(method);
				Object value = YUtilReflection.getPropertyValue(propertyName, proxy);
				if (value != null) {
					if (value.getClass().getName().indexOf("_Proxy") < 0) {
						YUtilReflection.setValueToProperty(propertyName, value, objReturn);
					} else if (loadAll) {
						Object objRelation = load(value, loadAll);
						YUtilReflection.setValueToProperty(propertyName, objRelation, objReturn);
					}
				}
			}
		} catch (InstantiationException e) {
			Log.e("YProxyLoad.load", e.getMessage());
		} catch (IllegalAccessException e) {
			Log.e("YProxyLoad.load", e.getMessage());
		} catch (Exception e) {
			Log.e("YProxyLoad.load", e.getMessage());
		}

		return objReturn;
	}
}