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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
	 * @param baseListActivity The ListActivity instance responsible for handling <tt>ListView</tt> interactions.
	 * @param data A list of <tt>Map</tt>s from which will be rendered each row of the <tt>ListView</tt>.
	 * @param adapterConfig
	 */
	public ComplexListSimpleAdapter(final YBaseListActivity baseListActivity,
			List<? extends Map<String, Object>> data,
			AdapterConfig adapterConfig) {
		super(baseListActivity, baseListActivity.getApplicationContext(), data, adapterConfig);
		this.yBaseListActivity = baseListActivity;
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
	 * @param baseListActivity The ListActivity instance responsible for handling <tt>ListView</tt> interactions.
	 * @param context An instance of <tt>android.content.Context</tt>.
	 * @param data A list of <tt>Map</tt>s from which will be rendered each row of the <tt>ListView</tt>.
	 * @param adapterConfig
	 */
	public ComplexListSimpleAdapter(final YBaseListActivity baseListActivity,
			Context context,
			List<? extends Map<String, Object>> data,
			AdapterConfig adapterConfig) {
		super(baseListActivity, context, data, adapterConfig);
		this.yBaseListActivity = baseListActivity;
		this.configNoListModelSelection();
	}


	/**
	 *
	 */
	protected void configNoListModelSelection() {
		try {
			this.noListModelSelection = super.getAdapterConfig().getListModelSelection() == null;
		} catch (Exception e) {
			Log.e("ComplexListSimpleAdapter.configNoListModelSelection", e.getMessage());
		}
	}


	/**
	 *
	 * @see android.widget.SimpleAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		try {
			/* calls getView from superclasse TextListSimpleAdapter */
			convertView = super.getView(position, convertView, parent);

			final HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);
			final Object object = (Object) data.get(YConstants.OBJECT);
			final RowConfig rowConfig = super.selectRowConfig(position, object);

			if(convertView != null && rowConfig != null) {
				// handleView
				if(getAdapterConfig().getRowCondition() != null) {
					getAdapterConfig().getRowCondition().handleView(convertView, data, position, getAdapterConfig());
				}
				// OnClickListener
				final View convertViewRef = convertView;
				convertView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(final View view) {
						if(rowConfig.getResourcesHint() != null && rowConfig.getResourcesHint().length > 0) {
							Toast.makeText(ComplexListSimpleAdapter.this.yBaseListActivity, ComplexListSimpleAdapter.this.getTextHint(rowConfig.getResourcesHint()), Toast.LENGTH_SHORT).show();
						}
						if(getAdapterConfig().isClickable()) {
							ComplexListSimpleAdapter.this.yBaseListActivity.onListViewClick(convertViewRef, view.getTag());
						}
					}
				});
			}

			if(object != null && rowConfig.getRowConfigItems() != null) {
				for(RowConfigItem rowConfigItem : rowConfig.getRowConfigItems()) {
					if(rowConfigItem.getInteractionConfig() != null && rowConfigItem.getInteractionConfig().getResourceTypeForInteraction() != null) {
						if(rowConfigItem.getInteractionConfig().getResourceTypeForInteraction().equals(CheckBox.class) && !this.noListModelSelection) {
							this.treatCheckBox(convertView, object, rowConfigItem);
						} else if (rowConfigItem.getInteractionConfig().getResourceTypeForInteraction().equals(Button.class)) {
							this.treatButton(convertView, object, rowConfigItem);
						} else if(rowConfigItem.getInteractionConfig().getResourceTypeForInteraction().equals(ImageView.class)) {
							this.treatImageView(convertView, object, rowConfigItem);
						} else if(rowConfigItem.getInteractionConfig().getResourceTypeForInteraction().equals(TextView.class)) {
							this.treatTextView(convertView, object, rowConfigItem);
						} else if(rowConfigItem.getInteractionConfig().getResourceTypeForInteraction().equals(EditText.class)) {
							this.treatEditText(convertView, object, rowConfigItem);
						}
					}
				}
			}
		} catch (Exception e) {
			Log.e("ComplexListSimpleAdapter.getView", e.getMessage());
		}
		return convertView;
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
	 * @param convertView
	 * @param object
	 * @param rowConfigItem
	 */
	protected void treatButton(final View convertView, final Object object, final RowConfigItem rowConfigItem) {
		try {
			final Button button = (Button)convertView.findViewById(rowConfigItem.getInteractionConfig().getResourceIDForInteraction());
			if(rowConfigItem.getCondition() != null) {
				if(rowConfigItem.getCondition().checkToVisibility(object)) {
					this.treatVisibleButtom(object, button);
				} else {
					button.setVisibility(View.GONE);
				}
				rowConfigItem.getCondition().handle(object, button);
			} else {
				this.treatVisibleButtom(object, button);
			}
		} catch (Exception e) {
			Log.e("ComplexListSimpleAdapter.treatButton", e.getMessage());
		}
	}

	/**
	 *
	 * @param convertView
	 * @param object
	 * @param rowConfigItem
	 */
	protected void treatCheckBox(final View convertView, final Object object, final RowConfigItem rowConfigItem) {
		try {
			final CheckBox checkBox = (CheckBox)convertView.findViewById(rowConfigItem.getInteractionConfig().getResourceIDForInteraction());
			if(rowConfigItem.getCondition() != null) {
				if(rowConfigItem.getCondition().checkToVisibility(object)) {
					this.treatVisibleCheckBox(object, checkBox, rowConfigItem);
				} else {
					checkBox.setVisibility(View.GONE);
				}
				rowConfigItem.getCondition().handle(object, checkBox);
			} else {
				this.treatVisibleCheckBox(object, checkBox, rowConfigItem);
			}
		} catch (Exception e) {
			Log.e("ComplexListSimpleAdapter.treatCheckBox", e.getMessage());
		}
	}

	/**
	 *
	 * @param convertView
	 * @param object
	 * @param rowConfigItem
	 */
	protected void treatImageView(final View convertView, final Object object, final RowConfigItem rowConfigItem) {
		try {
			final ImageView imageView = (ImageView)convertView.findViewById(rowConfigItem.getInteractionConfig().getResourceIDForInteraction());
			if(rowConfigItem.getCondition() != null) {
				if(rowConfigItem.getCondition().checkToVisibility(object)) {
					imageView.setVisibility(View.VISIBLE);
				} else {
					imageView.setVisibility(View.GONE);
				}
				rowConfigItem.getCondition().handle(object, imageView);
			} else {
				imageView.setVisibility(View.VISIBLE);
			}
		} catch (Exception _e) {
			Log.e("ComplexListSimpleAdapter.treatImageView", _e.getMessage());
		}
	}

	/**
	 * 
	 * @param convertView
	 * @param object
	 * @param rowConfigItem
	 */
	protected void treatTextView(final View convertView, final Object object, final RowConfigItem rowConfigItem) {
		try {
			final TextView textView = (TextView)convertView.findViewById(rowConfigItem.getInteractionConfig().getResourceIDForInteraction());
			if(rowConfigItem.getCondition() != null) {
				if(rowConfigItem.getCondition().checkToVisibility(object)) {
					textView.setVisibility(View.VISIBLE);
				} else {
					textView.setVisibility(View.GONE);
				}
				rowConfigItem.getCondition().handle(object, textView);
			} else {
				textView.setVisibility(View.VISIBLE);
			}
		} catch (Exception _e) {
			Log.e("ComplexListSimpleAdapter.treatTextView", _e.getMessage());
		}
	}

	/**
	 * 
	 * @param convertView
	 * @param object
	 * @param rowConfigItem
	 */
	protected void treatEditText(final View convertView, final Object object, final RowConfigItem rowConfigItem) {
		try {
			final EditText editText = (EditText)convertView.findViewById(rowConfigItem.getInteractionConfig().getResourceIDForInteraction());
			if(rowConfigItem.getCondition() != null) {
				if(rowConfigItem.getCondition().checkToVisibility(object)) {
					editText.setVisibility(View.VISIBLE);
				} else {
					editText.setVisibility(View.GONE);
				}
				rowConfigItem.getCondition().handle(object, editText);
			} else {
				editText.setVisibility(View.VISIBLE);
			}
		} catch (Exception _e) {
			Log.e("ComplexListSimpleAdapter.treatEditText", _e.getMessage());
		}
	}

	/**
	 *
	 * @param object
	 * @param checkBox
	 */
	protected void treatVisibleCheckBox(
			final Object object, 
			final CheckBox checkBox,
			final RowConfigItem rowConfigItem) {
		try {
			checkBox.setVisibility(View.VISIBLE);
			checkBox.setTag(object);
			checkBox.setChecked(ComplexListSimpleAdapter.this.getAdapterConfig().getListModelSelection().contains(object));
			checkBox.requestLayout();
			checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
					if(isChecked) {
						ComplexListSimpleAdapter.this.getAdapterConfig().getListModelSelection().add(buttonView.getTag());
					} else {
						ComplexListSimpleAdapter.this.getAdapterConfig().getListModelSelection().remove(buttonView.getTag());
					}
					if(rowConfigItem.getInteractionConfig() != null
							&& rowConfigItem.getInteractionConfig().getCondition() != null) {
						rowConfigItem.getInteractionConfig().getCondition().checkToVisibility(object);
						rowConfigItem.getInteractionConfig().getCondition().handle(object, checkBox);
					}
				}
			});
		} catch (Exception e) {
			Log.e("ComplexListSimpleAdapter.treatVisibleCheckBox", e.getMessage());
		}
	}

	/**
	 * @param object
	 * @param button
	 */
	protected void treatVisibleButtom(final Object object, final Button button) {
		try {
			button.setVisibility(View.VISIBLE);
			button.setTag(object);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(final View _view) {
					ComplexListSimpleAdapter.this.yBaseListActivity.onListViewClick(_view, _view.getTag());
				}
			});
		} catch (Exception e) {
			Log.e("ComplexListSimpleAdapter.treatVisible", e.getMessage());
		}
	}
}