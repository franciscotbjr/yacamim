/**
 * HomeFragment.java
 * 
 * Copyright 2013 br.com.joaoboscobezerrabonfim
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
package br.com.joaoboscobezerrabonfim;

import java.util.Locale;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import br.org.yacamim.YBaseFragmentPagerAdapter;

/**
 * 
 * Class HomeFragment TODO
 * 
 * @author br.com.joaoboscobezerrabonfim (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class HomeFragmentPagerAdapter extends YBaseFragmentPagerAdapter {

	/**
	 * 
	 * @param fragmentManager
	 * @param context
	 */
	public HomeFragmentPagerAdapter(final FragmentManager fragmentManager, final Context context) {
		super(fragmentManager, context);
	}
	
	/**
	 * 
	 * @see br.org.yacamim.YBaseFragmentPagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem(int position) {
		Fragment fragment = new HomeFragment();
		Bundle args = new Bundle();
		args.putInt(HomeFragment.ARG_SECTION_NUMBER, position + 1);
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * 
	 * @see br.org.yacamim.YBaseFragmentPagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		return 4;
	}

	/**
	 * 
	 * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
	 */
	@Override
	public CharSequence getPageTitle(int position) {
		Locale l = Locale.getDefault();
		switch (position) {
		case 0:
			return getString(R.string.title_section_poemas).toUpperCase(l);
		case 1:
			return getString(R.string.title_section_favoritos).toUpperCase(l);
		case 2:
			return getString(R.string.title_section_livros).toUpperCase(l);
		case 3:
			return getString(R.string.title_section_autor).toUpperCase(l);
		}
		return null;
	}

}
