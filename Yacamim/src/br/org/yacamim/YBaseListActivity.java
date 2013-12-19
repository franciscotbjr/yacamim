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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import br.org.yacamim.ui.components.YBaseListInteraction;
import br.org.yacamim.ui.components.OnDialogDoneListener;
import br.org.yacamim.ui.components.YAlertDialogFragment;
import br.org.yacamim.ui.components.YDialogEvent;
import br.org.yacamim.ui.components.YProgressDialogFragment;
import br.org.yacamim.util.YConstants;

/**
 * Class YBaseListActivity TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YBaseListActivity 
				extends ListActivity 
				implements OnDialogDoneListener, YBaseListInteraction {
	
	private static final String TAG = YBaseListActivity.class.getSimpleName();
	
	private static final List<YProgressDialogFragment> mYProgressDialogFragmentStack = new ArrayList<YProgressDialogFragment>();

	/**
	 * 
	 */
	public YBaseListActivity() {
		super();
		YacamimState.getInstance().setCurrentContext(this);
	}
	
	/**
	 *
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.dismissCurrentProgressDialog();
	}

    /**
     * 
     */
	protected void keepScreenOn() {
		try {
			this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		} catch (Exception e) {
			Log.e(TAG + ".keepScreenOn", e.getMessage());
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
	protected void fillMapList(
			final List<HashMap<String, Object>> mapList, 
			final List sourceList) {
		try {
			for(final Object item : sourceList) {
				final HashMap<String, Object> map = new HashMap<String, Object>();
				map.put(YConstants.OBJECT, item);
				mapList.add(map);
			}
		} catch (Exception e) {
			Log.e(TAG + ".fillMapList", e.getMessage());
		}
	}

	/**
	 * 
	 * @param objectValue
	 * @param mapList
	 * @param sourceList
	 */
	@SuppressWarnings("rawtypes")
	protected void removeListViewItem(
			final Object objectValue, 
			final List<HashMap<String, Object>> mapList,  
			final List sourceList) {
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
		} catch (Exception e) {
			Log.e(TAG + ".removeListViewItem", e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param titleResourceID
	 * @param messageResourceID
	 */
	public void displayProgressDialog(
			final int titleResourceID, 
			final int messageResourceID) {
		try {
			progressDialog(titleResourceID, messageResourceID);
		} catch (Exception e) {
			Log.e(TAG + ".displayProgressDialog(int titleResourceID, int messageResourceID)", e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param titleResourceID
	 */
	public void displayProgressDialog(final int titleResourceID) {
		try {
			progressDialog(titleResourceID, YacamimResources.getInstance().getIdResourceMsgWait());
		} catch (Exception e) {
			Log.e(TAG + ".displayProgressDialog(int messageResourceID)", e.getMessage());
		}
	}
	
	/**
	 * 
	 */
	public void displayProgressDialog() {
		try {
			progressDialog(0, YacamimResources.getInstance().getIdResourceMsgWait());
		} catch (Exception e) {
			Log.e(TAG + ".displayProgressDialog()", e.getMessage());
		}
	}
	
	/**
	 * 
	 * @param titleResourceID
	 * @param messageResourceID
	 */
	private void progressDialog(
			final int titleResourceID,
			final int messageResourceID) {
		final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
		
		YProgressDialogFragment yProgressDialogFragment = YProgressDialogFragment.newInstance(
				YConstants.DIALOG_WAIT, 
				titleResourceID, 
				messageResourceID);
		
		mYProgressDialogFragmentStack.add(yProgressDialogFragment);

		fragmentTransaction.add(yProgressDialogFragment, YConstants.Y_PROGRESS_DIALOG_FRAGMENT_PREFIX + YConstants.DIALOG_WAIT);
		
		fragmentTransaction.commit();
		
		fragmentTransaction.show(yProgressDialogFragment);
	}
	
	/**
	 * 
	 */
	public void dismissCurrentProgressDialog() {
		try {
			for(YProgressDialogFragment yProgressDialogFragment : mYProgressDialogFragmentStack) {
				yProgressDialogFragment.dismiss();
			}
			mYProgressDialogFragmentStack.clear();
		} catch (final Exception e) {
			Log.e(TAG + ".dimissCurrentProgressDialog()", e.getMessage());
		}
	}
	
	
	/**
	 * 
	 * @param dialogId
	 * @param titleResource
	 * @param mensageResource
	 * @param positiveButtonresource
	 */
	protected void buildSimplePositiveAlertDialog(
			final int dialogId, 
			final int titleResource, 
			final int mensageResource, 
			final int positiveButtonresource) {
		final YAlertDialogFragment yAlertDialogFragment = 
				YAlertDialogFragment.newInstance(dialogId, 
						titleResource, 
						mensageResource, 
						positiveButtonresource);
		yAlertDialogFragment.show(this.getFragmentManager(), TAG);
	}

	/**
	 * 
	 * @param dialogId
	 * @param title
	 * @param mensage
	 * @param positiveButtonresource
	 */
	protected void buildSimplePositiveAlertDialog(
			final int dialogId, 
			final String title, 
			final String mensage, 
			final int positiveButtonresource) {
		final YAlertDialogFragment yAlertDialogFragment = 
				YAlertDialogFragment.newInstance(dialogId, 
						title, 
						mensage, 
						positiveButtonresource);
		yAlertDialogFragment.show(this.getFragmentManager(), TAG);
	}

	
	@Override
	public void onDialogClick(
			final YDialogEvent dialogEvent) {
		// TODO Auto-generated method stub
		
	}
	
}