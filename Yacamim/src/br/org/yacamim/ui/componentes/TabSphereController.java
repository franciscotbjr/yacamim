/**
 * TabSphereController.java
 *
 * Copyright 2012 yacamim.org.br
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.org.yacamim.ui.componentes;

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
