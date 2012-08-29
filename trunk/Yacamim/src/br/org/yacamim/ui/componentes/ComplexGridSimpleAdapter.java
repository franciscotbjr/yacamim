/**
 * ComplexGridSimpleAdapter.java
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
import br.org.yacamim.BaseListActivity;
import br.org.yacamim.util.Constants;

/**
 * Class ComplexGridSimpleAdapter TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class ComplexGridSimpleAdapter extends TextGridSimpleAdapter {
	
	/**
	 * 
	 */
	public static final String EVENT_HINT = "EVENT_HINT";
	
	/**
	 * 
	 */
	private BaseListActivity baseListActivity;
	
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
	 * @param _context An instance of <tt>android.content.Context</tt>. 
	 * @param _data A list of <tt>Map</tt>s from which will be rendered each row of the <tt>ListView</tt>.
	 * @param _adapterConfig
	 */
	public ComplexGridSimpleAdapter(final BaseListActivity _baseListActivity, 
			Context _context, 
			List<? extends Map<String, Object>> _data,
			AdapterConfig _adapterConfig) {
		super(_baseListActivity, _context, _data, _adapterConfig);
		this.baseListActivity = _baseListActivity;
		this.configNoListModelSelection();
	}


	/**
	 * 
	 */
	protected void configNoListModelSelection() {
		try {
			this.noListModelSelection = super.getAdapterConfig().getListModelSelection() == null;
		} catch (Exception _e) {
			Log.e("ComplexGridSimpleAdapter.configNoListModelSelection", _e.getMessage());
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
			/* calls getView from superclasse TextGridSimpleAdapter */
			_convertView = super.getView(_position, _convertView, _parent);
			
			final HashMap<String, Object> data = (HashMap<String, Object>) getItem(_position);
			final Object object = (Object) data.get(Constants.OBJECT);
			final RowConfig rowConfig = super.selectRowConfig(_position, object);
			
			if(_convertView != null) {
				_convertView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(final View _view) {
						if(ComplexGridSimpleAdapter.this.getAdapterConfig().getResourceHints() != null) {
							Toast.makeText(ComplexGridSimpleAdapter.this.baseListActivity, ComplexGridSimpleAdapter.this.getTextHint(), Toast.LENGTH_SHORT).show();
						}
					}
				});
			}
			
			 
			if(object != null && rowConfig.getRowConfigItems() != null) {
				for(RowConfigItem rowConfigItem : rowConfig.getRowConfigItems()) {
					if(rowConfigItem.getResourceTypeForInteraction().equals(CheckBox.class) && !this.noListModelSelection) {
						/* A variável listModelSelection não pode ser NULL no caso de CheckBox, pois os
						 * elementos selecionados são atribuídos à lista. */
						this.treatCheckBox(_convertView, object, rowConfigItem);
					} else if (rowConfigItem.getResourceTypeForInteraction().equals(Button.class)) {
						this.treatButton(_convertView, object, rowConfigItem);
					} else if(rowConfigItem.getResourceTypeForInteraction().equals(ImageView.class)) {
						this.treatImageView(_convertView, object, rowConfigItem);
					}
				}
			}
		} catch (Exception _e) {
			Log.e("ComplexGridSimpleAdapter.getView", _e.getMessage());
		}
		return _convertView;
	}
	
	/**
	 * 
	 * @return
	 */
	protected String getTextHint() {
		final StringBuffer buffer = new StringBuffer();
		if(this.getAdapterConfig().getResourceHints() != null) {
			for(int i = 0; i < this.getAdapterConfig().getResourceHints().length; i++) {
				buffer.append("- " + this.baseListActivity.getText(this.getAdapterConfig().getResourceHints()[i]));
				if((i + 1) < this.getAdapterConfig().getResourceHints().length) {
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
			final Button button = (Button)_convertView.findViewById(_rowConfigItem.getResourceIDForInteraction());
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
			Log.e("ComplexGridSimpleAdapter.treatButton", _e.getMessage());
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
			final CheckBox checkBox = (CheckBox)_convertView.findViewById(_rowConfigItem.getResourceIDForInteraction());
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
			Log.e("ComplexGridSimpleAdapter.treatCheckBox", _e.getMessage());
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
			final ImageView imageView = (ImageView)_convertView.findViewById(_rowConfigItem.getResourceIDForInteraction());
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
			Log.e("ComplexGridSimpleAdapter.treatImageView", _e.getMessage());
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
			_checkBox.setChecked(ComplexGridSimpleAdapter.this.getAdapterConfig().getListModelSelection().contains(_object));
			_checkBox.requestLayout();
			_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
					if(isChecked) {
						ComplexGridSimpleAdapter.this.getAdapterConfig().getListModelSelection().add(buttonView.getTag());
					} else {
						ComplexGridSimpleAdapter.this.getAdapterConfig().getListModelSelection().remove(buttonView.getTag());
					}
				}
			});
		} catch (Exception _e) {
			Log.e("ComplexGridSimpleAdapter.trataCheckBoxVisible", _e.getMessage());
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
					ComplexGridSimpleAdapter.this.baseListActivity.onListViewClick(_view, _view.getTag());
				}
			});
		} catch (Exception _e) {
			Log.e("ComplexGridSimpleAdapter.trataBotaoVisible", _e.getMessage());
		}
	}
}