/**
 * BaseListActivity.java
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

import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import br.org.yacamim.ui.components.DefaultAlertDialogBuilder;
import br.org.yacamim.util.YConstants;

/**
 * Class BaseListActivity TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class BaseListActivity extends ListActivity {

	/**
	 * 
	 */
	public BaseListActivity() {
		super();
	}
	
	/**
	 *
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.keepScreenOn();
	}

    /**
     * 
     */
	private void keepScreenOn() {
		try {
			this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		} catch (Exception _e) {
			Log.e("BaseListActivity.keepScreenOn", _e.getMessage());
		}
	}

	/**
	 * @param _listView
	 * @param _position
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Object getObjectFromListView(final ListView _listView,
			final int _position) {
		return ((HashMap<String, Object>)_listView.getAdapter().getItem(_position)).get(YConstants.OBJECT);
	}
	
	/**
	 * 
	 */
	protected void finishForNoInformationFound() {
		super.setResult(YConstants.ERROR_NO_INFORMATION_FOUND);
		super.finish();
	}
	
	/**
	 * 
	 */
	protected void finishForNoRecordFound() {
		super.setResult(YConstants.ERROR_NO_RECORD_FOUND_FOR_PARAMETERS);
		super.finish();
	}

	/**
	 * 
	 * @param _view
	 * @param _objectValue
	 */
	public void onListViewClick(final View _view, final Object _objectValue) {
		
	}
	
	/**
	 * 
	 * @param _mapList
	 * @param _sourceList
	 */
	@SuppressWarnings("rawtypes")
	protected void fillMapList(final List<HashMap<String, Object>> _mapList, final List _sourceList) {
		try {
			for(final Object item : _sourceList) {
				final HashMap<String, Object> map = new HashMap<String, Object>();
				map.put(YConstants.OBJECT, item);
				_mapList.add(map);
			}
		} catch (Exception _e) {
			Log.e("BaseListActivity.fillMapList", _e.getMessage());
		}
	}

	/**
	 * 
	 * @param _objectValue
	 * @param _mapList
	 * @param _sourceList
	 */
	@SuppressWarnings("rawtypes")
	protected void removeListViewItem(final Object _objectValue, final List<HashMap<String, Object>> _mapList,  final List _sourceList) {
		try {
			HashMap<String, Object> mapToRemove = null;
			for(HashMap<String, Object> map  : _mapList) {
				if(map.containsValue(_objectValue)) {
					mapToRemove = map;
					break;
				}
			}
			if(mapToRemove != null) {
				_sourceList.remove(_objectValue);
				_mapList.remove(mapToRemove);
				((SimpleAdapter)getListAdapter()).notifyDataSetChanged();
			}
		} catch (Exception _e) {
			Log.e("BaseListActivity.removeListViewItem", _e.getMessage());
		}
	}
	
	
	/**
	 * 
	 */
	protected void showDialogConstraintDependency() {
		showDialog(YConstants.ERROR_CONSTRAINT_DEPENDENCY);
	}
	
	/**
	 * 
	 *
	 * @see android.app.Activity#onCreateDialog(int)
	 */
	@Override
	protected Dialog onCreateDialog(final int _idDialog) {
		switch(_idDialog) {
			case YConstants.ERROR_CONSTRAINT_DEPENDENCY:
				AlertDialog.Builder builderRegistroNaoPodeSerExcluidoPorDependencia = new DefaultAlertDialogBuilder(this, this.getText(YacamimState.getInstance().getYacamimResources().getIdResourceMsgConstraintDependency()).toString(), false);
				builderRegistroNaoPodeSerExcluidoPorDependencia.setPositiveButton(YacamimState.getInstance().getYacamimResources().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface _dialog, int id) {
						removeDialog(_idDialog);
					}
				});
				return builderRegistroNaoPodeSerExcluidoPorDependencia.show();
			default:
				return super.onCreateDialog(_idDialog);
		}
	}
}