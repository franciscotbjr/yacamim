/**
 * ComplexListSimpleAdapter.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import br.org.yacamim.YBaseListActivity;
import br.org.yacamim.util.YConstants;

/**
 * Class ComplexListSimpleAdapter TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class ComplexListSimpleAdapter extends TextListSimpleAdapter {

	/**
	 *
	 */
	public static final String EVENT_HINT = "EVENT_HINT";

	/**
	 *
	 */
	private YBaseListActivity yBaseListActivity;

	/**
	 *
	 */
	private boolean noListModelSelection = false;

	/**
	 * <br/>
	 * The <tt>_listModelSelection</tt> parameter is meant to be used when each row of a <tt>ListView</tt> has a <tt>CheckBox</tt>. <br/>
	 * So each <tt>CheckBox</tt> selection action will result in a new item inside the <tt>_listModelSelection</tt>. For each deselection <br/>
	 * action, an item will be removed from <tt>_listModelSelection</tt>.<br/><br/>
	 * <br/>
	 *
	 *
	 * @param _baseListActivity The ListActivity instance responsible for handling <tt>ListView</tt> interactions.
	 * @param _data A list of <tt>Map</tt>s from which will be rendered each row of the <tt>ListView</tt>.
	 * @param _adapterConfig
	 */
	public ComplexListSimpleAdapter(final YBaseListActivity _baseListActivity,
			List<? extends Map<String, Object>> _data,
			AdapterConfig _adapterConfig) {
		super(_baseListActivity, _baseListActivity.getApplicationContext(), _data, _adapterConfig);
		this.yBaseListActivity = _baseListActivity;
		this.configNoListModelSelection();
	}

	/**
	 * <br/>
	 * The <tt>_listModelSelection</tt> parameter is meant to be used when each row of a <tt>ListView</tt> has a <tt>CheckBox</tt>. <br/>
	 * So each <tt>CheckBox</tt> selection action will result in a new item inside the <tt>_listModelSelection</tt>. For each deselection <br/>
	 * action, an item will be removed from <tt>_listModelSelection</tt>.<br/><br/>
	 * <br/>
	 *
	 *
	 * @param _baseListActivity The ListActivity instance responsible for handling <tt>ListView</tt> interactions.
	 * @param _context An instance of <tt>android.content.Context</tt>.
	 * @param _data A list of <tt>Map</tt>s from which will be rendered each row of the <tt>ListView</tt>.
	 * @param _adapterConfig
	 */
	public ComplexListSimpleAdapter(final YBaseListActivity _baseListActivity,
			Context _context,
			List<? extends Map<String, Object>> _data,
			AdapterConfig _adapterConfig) {
		super(_baseListActivity, _context, _data, _adapterConfig);
		this.yBaseListActivity = _baseListActivity;
		this.configNoListModelSelection();
	}


	/**
	 *
	 */
	protected void configNoListModelSelection() {
		try {
			this.noListModelSelection = super.getAdapterConfig().getListModelSelection() == null;
		} catch (Exception _e) {
			Log.e("ComplexListSimpleAdapter.configNoListModelSelection", _e.getMessage());
		}
	}


	/**
	 *
	 * @see android.widget.SimpleAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public View getView(final int _position, View _convertView, final ViewGroup _parent) {
		try {
			/* calls getView from superclasse TextListSimpleAdapter */
			_convertView = super.getView(_position, _convertView, _parent);

			final HashMap<String, Object> data = (HashMap<String, Object>) getItem(_position);
			final Object object = (Object) data.get(YConstants.OBJECT);
			final RowConfig rowConfig = super.selectRowConfig(_position, object);

			if(_convertView != null && rowConfig != null) {
				_convertView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(final View _view) {
						if(rowConfig.getResourcesHint() != null && rowConfig.getResourcesHint().length > 0) {
							Toast.makeText(ComplexListSimpleAdapter.this.yBaseListActivity, ComplexListSimpleAdapter.this.getTextHint(rowConfig.getResourcesHint()), Toast.LENGTH_SHORT).show();
						}
					}
				});
			}


			if(object != null && rowConfig.getRowConfigItems() != null) {
				for(RowConfigItem rowConfigItem : rowConfig.getRowConfigItems()) {
					if(rowConfigItem.getInteractionConfig() != null && rowConfigItem.getInteractionConfig().getResourceTypeForInteraction() != null) {
						if(rowConfigItem.getInteractionConfig().getResourceTypeForInteraction().equals(CheckBox.class) && !this.noListModelSelection) {
							this.treatCheckBox(_convertView, object, rowConfigItem);
						} else if (rowConfigItem.getInteractionConfig().getResourceTypeForInteraction().equals(Button.class)) {
							this.treatButton(_convertView, object, rowConfigItem);
						} else if(rowConfigItem.getInteractionConfig().getResourceTypeForInteraction().equals(ImageView.class)) {
							this.treatImageView(_convertView, object, rowConfigItem);
						}
					}
				}
			}
		} catch (Exception _e) {
			Log.e("ComplexListSimpleAdapter.getView", _e.getMessage());
		}
		return _convertView;
	}

	/**
	 *
	 * @param resourcesHint
	 * @return
	 */
	protected String getTextHint(int[] resourcesHint) {
		final StringBuffer buffer = new StringBuffer();
		if(resourcesHint != null) {
			for(int i = 0; i < resourcesHint.length; i++) {
				buffer.append("- " + this.yBaseListActivity.getText(resourcesHint[i]));
				if((i + 1) < resourcesHint.length) {
					buffer.append("\n");
				}
			}
		}
		return buffer.toString();
	}

	/**
	 *
	 * @param _convertView
	 * @param _object
	 * @param _rowConfigItem
	 */
	protected void treatButton(final View _convertView, final Object _object, final RowConfigItem _rowConfigItem) {
		try {
			final Button button = (Button)_convertView.findViewById(_rowConfigItem.getInteractionConfig().getResourceIDForInteraction());
			if(_rowConfigItem.getCondition() != null) {
				if(_rowConfigItem.getCondition().checkToVisibility(_object)) {
					this.trataBotaoVisible(_object, button);
				} else {
					button.setVisibility(View.GONE);
				}
				_rowConfigItem.getCondition().handle(_object, button);
			} else {
				this.trataBotaoVisible(_object, button);
			}
		} catch (Exception _e) {
			Log.e("ComplexListSimpleAdapter.treatButton", _e.getMessage());
		}
	}

	/**
	 *
	 * @param _convertView
	 * @param _object
	 * @param _rowConfigItem
	 */
	protected void treatCheckBox(final View _convertView, final Object _object, final RowConfigItem _rowConfigItem) {
		try {
			final CheckBox checkBox = (CheckBox)_convertView.findViewById(_rowConfigItem.getInteractionConfig().getResourceIDForInteraction());
			if(_rowConfigItem.getCondition() != null) {
				if(_rowConfigItem.getCondition().checkToVisibility(_object)) {
					this.trataCheckBoxVisible(_object, checkBox);
				} else {
					checkBox.setVisibility(View.GONE);
				}
				_rowConfigItem.getCondition().handle(_object, checkBox);
			} else {
				this.trataCheckBoxVisible(_object, checkBox);
			}
		} catch (Exception _e) {
			Log.e("ComplexListSimpleAdapter.treatCheckBox", _e.getMessage());
		}
	}

	/**
	 *
	 * @param _convertView
	 * @param _object
	 * @param _rowConfigItem
	 */
	protected void treatImageView(final View _convertView, final Object _object, final RowConfigItem _rowConfigItem) {
		try {
			final ImageView imageView = (ImageView)_convertView.findViewById(_rowConfigItem.getInteractionConfig().getResourceIDForInteraction());
			if(_rowConfigItem.getCondition() != null) {
				if(_rowConfigItem.getCondition().checkToVisibility(_object)) {
					imageView.setVisibility(View.VISIBLE);
				} else {
					imageView.setVisibility(View.GONE);
				}
				_rowConfigItem.getCondition().handle(_object, imageView);
			} else {
				imageView.setVisibility(View.VISIBLE);
			}
		} catch (Exception _e) {
			Log.e("ComplexListSimpleAdapter.treatImageView", _e.getMessage());
		}
	}

	/**
	 *
	 * @param _object
	 * @param _checkBox
	 */
	protected void trataCheckBoxVisible(final Object _object, final CheckBox _checkBox) {
		try {
			_checkBox.setVisibility(View.VISIBLE);
			_checkBox.setTag(_object);
			_checkBox.setChecked(ComplexListSimpleAdapter.this.getAdapterConfig().getListModelSelection().contains(_object));
			_checkBox.requestLayout();
			_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
					if(isChecked) {
						ComplexListSimpleAdapter.this.getAdapterConfig().getListModelSelection().add(buttonView.getTag());
					} else {
						ComplexListSimpleAdapter.this.getAdapterConfig().getListModelSelection().remove(buttonView.getTag());
					}
				}
			});
		} catch (Exception _e) {
			Log.e("ComplexListSimpleAdapter.trataCheckBoxVisible", _e.getMessage());
		}
	}

	/**
	 * @param _object
	 * @param _button
	 */
	protected void trataBotaoVisible(final Object _object, final Button _button) {
		try {
			_button.setVisibility(View.VISIBLE);
			_button.setTag(_object);
			_button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(final View _view) {
					ComplexListSimpleAdapter.this.yBaseListActivity.onListViewClick(_view, _view.getTag());
				}
			});
		} catch (Exception _e) {
			Log.e("ComplexListSimpleAdapter.trataBotaoVisible", _e.getMessage());
		}
	}
}