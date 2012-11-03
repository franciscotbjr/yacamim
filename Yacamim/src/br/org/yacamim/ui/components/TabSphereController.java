/**
 * TabSphereController.java
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
package br.org.yacamim.ui.components;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Class TabSphereController TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class TabSphereController {

	/**
	 *
	 */
	private Activity activity;

	/**
	 *
	 */
	private int idLinearLayoutTabSphereControl;

	/**
	 *
	 */
	private int idSelectedTabSphere;

	/**
	 *
	 */
	private int idNonSelectedTabSphere;

	/**
	 *
	 */
	private int tabAmount;

	/**
	 *
	 */
	private int selectedTabPosition;

	/**
	 *
	 * @param _activity
	 * @param _idLinearLayoutTabSphereControl
	 * @param _idSelectedTabSphere
	 * @param _idNonSelectedTabSphere
	 */
	public TabSphereController(
			final int _idLinearLayoutTabSphereControl,
			final int _idSelectedTabSphere,
			final int _idNonSelectedTabSphere) {
		super();
		this.idLinearLayoutTabSphereControl = _idLinearLayoutTabSphereControl;
		this.idSelectedTabSphere = _idSelectedTabSphere;
		this.idNonSelectedTabSphere = _idNonSelectedTabSphere;
	}

	/**
	 *
	 * @param _activity
	 * @param _selectedTabPosition
	 * @param _tabAmount
	 */
	public void display(final Activity _activity, final int _selectedTabPosition, final int _tabAmount) {
		this.activity = _activity;
		this.selectedTabPosition = _selectedTabPosition;
		this.tabAmount = _tabAmount;
		try {
			final LinearLayout linearLayout = (LinearLayout)this.activity.findViewById(this.idLinearLayoutTabSphereControl);
			linearLayout.setVisibility(View.VISIBLE);
			linearLayout.removeAllViews();
			for(int i = 1; i <= this.tabAmount; i++) {
				final ImageView imageView = this.buildNewImageView();
				if(selectedTabPosition == i) {
					imageView.setBackgroundResource(this.idSelectedTabSphere);
				} else {
					imageView.setBackgroundResource(this.idNonSelectedTabSphere);
				}
				linearLayout.addView(imageView);
			}
		} catch (Exception _e) {
			Log.e("TabSphereController.display", _e.getMessage());
		}
	}

	/**
	 *
	 * @return
	 */
	protected ImageView buildNewImageView() {
		ImageView imageView = new ImageView(this.activity);
		LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		imageView.setLayoutParams(layoutParams);
		return imageView;
	}

}
