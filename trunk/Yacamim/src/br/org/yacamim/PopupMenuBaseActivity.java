/**
 * PopupMenuBaseActivity.java
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
package br.org.yacamim;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import br.org.yacamim.ui.componentes.DefaultAlertDialogBuilder;

/**
 * Class PopupMenuBaseActivity.<br/>
 * 
 * 
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public abstract class PopupMenuBaseActivity extends MenuBaseActivity {
	
	public static final int POPUP_MENU = 2011074537;
	
	private PopupMenuItem[] menuItems;

	/**
	 * 
	 * @param _menuItems
	 */
	public PopupMenuBaseActivity(final PopupMenuItem[] _menuItems) {
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
	protected boolean onPopupMenuItemSelected(final PopupMenuItem _menuItem) {
		return true;
	}

    /**
     *
     * @see br.org.yacamim.BaseActivity#onCreateDialog(int)
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
							PopupMenuBaseActivity.this.removeDialog(_idDialog);
							PopupMenuBaseActivity.this.onPopupMenuItemSelected(PopupMenuBaseActivity.this.getMenuItems()[_item]);
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
	protected PopupMenuItem[] getMenuItems() {
		return this.menuItems;
	}

	/**
	 * 
	 * @param _menuItems
	 */
	protected void setMenuItems(final PopupMenuItem[] _menuItems) {
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
			Log.e("PopupMenuBaseActivity.getMenuItemsText", _e.getMessage());
		}
		return null;
	}
}