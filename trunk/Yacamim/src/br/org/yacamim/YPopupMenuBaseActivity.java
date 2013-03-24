/**
 * YPopupMenuBaseActivity.java
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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import br.org.yacamim.ui.components.DefaultAlertDialogBuilder;

/**
 * Class YPopupMenuBaseActivity.<br/>
 * 
 * 
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public abstract class YPopupMenuBaseActivity extends YMenuBaseActivity {
	
	private static final String TAG = YPopupMenuBaseActivity.class.getSimpleName();
	
	public static final int POPUP_MENU = 2011074537;
	
	private YPopupMenuItem[] menuItems;

	/**
	 * 
	 * @param _menuItems
	 */
	public YPopupMenuBaseActivity(final YPopupMenuItem[] _menuItems) {
		super();
		this.setMenuItems(_menuItems);
	}
	
    /**
     * 
     * @param _menuItems
     * @return
     */
    public void showPopupMenu(final View _view) {
    	if(this.getMenuItems() != null && this.getMenuItems().length > 0) {
    		this.showDialog(POPUP_MENU);
    	}
    }
	
	/**
	 * 
	 * @param _menuItem
	 * @return
	 */
	protected boolean onPopupMenuItemSelected(final YPopupMenuItem _menuItem) {
		return true;
	}

    /**
     *
     * @see br.org.yacamim.YBaseActivity#onCreateDialog(int)
     */
	@Override
	protected Dialog onCreateDialog(final int _idDialog) {
		switch (_idDialog) {
			case POPUP_MENU:
				final String[] companhiasDomesticas = this.getMenuItemsText();
				if(companhiasDomesticas != null && companhiasDomesticas.length > 0) {
					AlertDialog.Builder builderCompanhiasDomesticas = new DefaultAlertDialogBuilder(this, null, null, true);
					builderCompanhiasDomesticas.setItems(companhiasDomesticas, new DialogInterface.OnClickListener() {
						public void onClick(final DialogInterface _dialog, final int _item) {
							YPopupMenuBaseActivity.this.removeDialog(_idDialog);
							YPopupMenuBaseActivity.this.onPopupMenuItemSelected(YPopupMenuBaseActivity.this.getMenuItems()[_item]);
						}
					});
					return builderCompanhiasDomesticas.create();
				}
			default:
				return super.onCreateDialog(_idDialog);
		}
	}



	/**
	 * 
	 * @return
	 */
	protected YPopupMenuItem[] getMenuItems() {
		return this.menuItems;
	}

	/**
	 * 
	 * @param _menuItems
	 */
	protected void setMenuItems(final YPopupMenuItem[] _menuItems) {
		this.menuItems = _menuItems;
	}
	
	/**
	 * 
	 * @return
	 */
	private String[] getMenuItemsText() {
		try {
			if(this.getMenuItems() != null && this.getMenuItems().length > 0) {
				final String[] textosMenus = new String[this.getMenuItems().length];
				for(int i = 0; i < this.getMenuItems().length; i++) {
					textosMenus[i] = (String)this.getText(this.getMenuItems()[i].getDescriptionResourceId());
				}
				return textosMenus;
			}
		} catch (Exception _e) {
			Log.e(TAG + ".getMenuItemsText", _e.getMessage());
		}
		return null;
	}
}