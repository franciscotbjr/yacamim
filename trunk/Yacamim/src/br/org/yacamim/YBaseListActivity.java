/**
 * YBaseListActivity.java
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
 * Class YBaseListActivity TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YBaseListActivity extends ListActivity {

	/**
	 * 
	 */
	public YBaseListActivity() {
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
			Log.e("YBaseListActivity.keepScreenOn", _e.getMessage());
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
			Log.e("YBaseListActivity.fillMapList", _e.getMessage());
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
			Log.e("YBaseListActivity.removeListViewItem", _e.getMessage());
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
				AlertDialog.Builder builderRegistroNaoPodeSerExcluidoPorDependencia = new DefaultAlertDialogBuilder(this, this.getText(YacamimResources.getInstance().getIdResourceMsgConstraintDependency()).toString(), false);
				builderRegistroNaoPodeSerExcluidoPorDependencia.setPositiveButton(YacamimResources.getInstance().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
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