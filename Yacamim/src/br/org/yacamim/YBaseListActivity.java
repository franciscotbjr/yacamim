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
	
	private static final String TAG = YBaseListActivity.class.getSimpleName();

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
			Log.e(TAG + ".keepScreenOn", _e.getMessage());
		}
	}

	/**
	 * @param listView
	 * @param position
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Object getObjectFromListView(final ListView listView,
			final int position) {
		return ((HashMap<String, Object>)listView.getAdapter().getItem(position)).get(YConstants.OBJECT);
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
	 * @param view
	 * @param objectValue
	 */
	public void onListViewClick(final View view, final Object objectValue) {
		
	}
	
	/**
	 * 
	 * @param mapList
	 * @param sourceList
	 */
	@SuppressWarnings("rawtypes")
	protected void fillMapList(final List<HashMap<String, Object>> mapList, final List sourceList) {
		try {
			for(final Object item : sourceList) {
				final HashMap<String, Object> map = new HashMap<String, Object>();
				map.put(YConstants.OBJECT, item);
				mapList.add(map);
			}
		} catch (Exception _e) {
			Log.e(TAG + ".fillMapList", _e.getMessage());
		}
	}

	/**
	 * 
	 * @param objectValue
	 * @param mapList
	 * @param sourceList
	 */
	@SuppressWarnings("rawtypes")
	protected void removeListViewItem(final Object objectValue, final List<HashMap<String, Object>> mapList,  final List sourceList) {
		try {
			HashMap<String, Object> mapToRemove = null;
			for(HashMap<String, Object> map  : mapList) {
				if(map.containsValue(objectValue)) {
					mapToRemove = map;
					break;
				}
			}
			if(mapToRemove != null) {
				sourceList.remove(objectValue);
				mapList.remove(mapToRemove);
				((SimpleAdapter)getListAdapter()).notifyDataSetChanged();
			}
		} catch (Exception _e) {
			Log.e(TAG + ".removeListViewItem", _e.getMessage());
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
	protected Dialog onCreateDialog(final int idDialog) {
		switch(idDialog) {
			case YConstants.ERROR_CONSTRAINT_DEPENDENCY:
				AlertDialog.Builder builderRegistroNaoPodeSerExcluidoPorDependencia = new DefaultAlertDialogBuilder(this, this.getText(YacamimResources.getInstance().getIdResourceMsgConstraintDependency()).toString(), false);
				builderRegistroNaoPodeSerExcluidoPorDependencia.setPositiveButton(YacamimResources.getInstance().getIdResourceMsgOK(), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface _dialog, int id) {
						removeDialog(idDialog);
					}
				});
				return builderRegistroNaoPodeSerExcluidoPorDependencia.show();
			default:
				return super.onCreateDialog(idDialog);
		}
	}
}