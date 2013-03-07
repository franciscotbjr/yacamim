/**
 * YBaseFragmentPagerAdapter.java
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
package br.org.yacamim;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 
 * Class YBaseFragmentPagerAdapter TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public abstract class YBaseFragmentPagerAdapter extends FragmentPagerAdapter {
	
	private Context context;

	/**
	 * 
	 */
	public YBaseFragmentPagerAdapter(final FragmentManager fragmentManager, final Context context) {
		super(fragmentManager);
		if(context == null) {
			throw new IllegalArgumentException("context : null");
		}
		this.context = context;
	}

	/**
	 * 
	 * @return
	 */
	protected Context getContext() {
		return this.context;
	}
	
	/**
	 * 
	 * @param resourceId
	 * @return
	 */
	public String getString(final int resourceId) {
		return this.getContext().getString(resourceId);
	}

}
