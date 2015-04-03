/**
 * AppBoscoActivity.java
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

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import br.org.yacamim.YBaseTabFragmentActivity;


/**
 * 
 * Class AppBoscoActivity TODO
 * 
 * @author br.com.joaoboscobezerrabonfim (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class AppBoscoActivity extends YBaseTabFragmentActivity {

	/**
	 * 
	 */
	HomeFragmentPagerAdapter mHomeFragmentPagerAdapter;

	/**
	 * 
	 */
	ViewPager mViewPager;

	/**
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_bosco);

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		mHomeFragmentPagerAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager(), this);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mHomeFragmentPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mHomeFragmentPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mHomeFragmentPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	/**
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 
	 * @see br.org.yacamim.YBaseTabFragmentActivity#onTabSelected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
	 */
	@Override
	public void onTabSelected(final ActionBar.Tab tab, final FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	/**
	 * 
	 * @see br.org.yacamim.YBaseTabFragmentActivity#onTabUnselected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
	 */
	@Override
	public void onTabUnselected(final ActionBar.Tab tab,
			final FragmentTransaction fragmentTransaction) {
	}

	/**
	 * 
	 * @see br.org.yacamim.YBaseTabFragmentActivity#onTabReselected(android.app.ActionBar.Tab, android.app.FragmentTransaction)
	 */
	@Override
	public void onTabReselected(final ActionBar.Tab tab, final FragmentTransaction fragmentTransaction) {
	}

}