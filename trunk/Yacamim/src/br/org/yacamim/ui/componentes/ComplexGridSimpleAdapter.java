/**
 * ComplexGridSimpleAdapter.java
 *
 * Copyright 2011 yacamim.org.br
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
	private int[] resourcesToTags;
	
	/**
	 * 
	 */
	private Class<?>[] resourcesType;
	
	/**
	 * 
	 */
	private Condition[] resourcesConditions;
	
	/**
	 * 
	 */
	private List<Object> listModelSelection;
	
	/**
	 * 
	 */
	private boolean noListModelSelection;

	/**
	 * 
	 */
	private int[] resourcesHint;

	/**
	 *  
	 * <br/>
	 * The <tt>_listModelSelection</tt> parameter is meant to be used when each row of a <tt>ListView</tt> has a <tt>CheckBox</tt>. 
	 * So each <tt>CheckBox</tt> selection action will result in a new item inside the <tt>_listModelSelection</tt>. For each deselection 
	 * action, an item will be removed from <tt>_listModelSelection</tt>.<br/><br/>
	 * <br/>
	 * 
	 * 
	 * 
	 * @param _baseListActivity The ListActivity instance responsible for handling <tt>ListView</tt> interactions.
	 * @param _formatingTypes A list containing formating type identifiers supposed to format each information displayed. When there is no formatting, then <tt>-1</tt> is the value to be provided.
	 * @param _context An instance of <tt>android.content.Context</tt>. 
	 * @param _data A list of <tt>Map</tt>s from which will be rendered each row of the <tt>ListView</tt>.
	 * @param _listViewLayoutResource The identifier of a XML file layout that defines the appearance of its rows. 
	 * @param _graphFrom The list of graphs that will be scanned for the values to be displayed
	 * @param _resourcesIdTo The list of resource identifiers to which will be displayed the values obtained throw the scanned graphs
	 * @param _resourcesIDsForInteraction If there are any of CheckBox, Button or ImageView inside the rows and interaction is required, then there identifiers must be provided.
	 * @param _resourcesTypesForInteraction If there are any of CheckBox, Button or ImageView inside the rows and interaction is required, then there types must be provided.
	 * @param _listModelSelection A simple list that stores the data of each selected row. Should be <tt>null</tt> if there is no <tt>CheckBox</tt>.
	 */
	public ComplexGridSimpleAdapter(final BaseListActivity _baseListActivity, 
			int[] _formatingTypes,
			Context _context, 
			List<? extends Map<String, Object>> _data,
			int _listViewLayoutResource, 
			String[] _graphFrom, 
			int[] _resourcesIdTo,
			int[] _resourcesIDsForInteraction, 
			Class<?>[] _resourcesTypesForInteraction,
			List<Object> _listModelSelection) {
		super(_baseListActivity, _formatingTypes, _context, _data, _listViewLayoutResource, _graphFrom, _resourcesIdTo);
		this.baseListActivity = _baseListActivity;
		this.resourcesToTags = _resourcesIDsForInteraction;
		this.resourcesType = _resourcesTypesForInteraction;
		this.listModelSelection = _listModelSelection;
		this.resourcesConditions = null;
		setTipoFormatacoes(_formatingTypes);
	}

	/**
	 * 
	 * @param _baseListActivity The ListActivity instance responsible for handling <tt>ListView</tt> interactions.
	 * @param _formatingTypes
	 * @param _context
	 * @param _data
	 * @param _listViewLayoutResource
	 * @param _graphFrom
	 * @param _resourcesIdTo
	 * @param _resourcesIDsForInteraction
	 * @param _resourcesTypesForInteraction
	 * @param _listModelSelection
	 * @param _resourcesHint An array of resource identifiers that are going to be used to compose a hint to be displayed on row click.
	 */
	public ComplexGridSimpleAdapter(final BaseListActivity _baseListActivity, 
			int[] _formatingTypes,
			Context _context, 
			List<? extends Map<String, Object>> _data,
			int _listViewLayoutResource, 
			String[] _graphFrom, 
			int[] _resourcesIdTo,
			int[] _resourcesIDsForInteraction, 
			Class<?>[] _resourcesTypesForInteraction,
			List<Object> _listModelSelection,
			int[] _resourcesHint) {
		super(_baseListActivity, _formatingTypes, _context, _data, _listViewLayoutResource, _graphFrom,
				_resourcesIdTo);
		this.baseListActivity = _baseListActivity;
		this.resourcesToTags = _resourcesIDsForInteraction;
		this.resourcesType = _resourcesTypesForInteraction;
		this.listModelSelection = _listModelSelection;
		this.resourcesConditions = null;
		this.resourcesHint = _resourcesHint;
		setTipoFormatacoes(_formatingTypes);
	}

	/**
	 * 
	 * @param _baseListActivity
	 * @param _formatingTypes
	 * @param _context
	 * @param _data
	 * @param _listViewLayoutResource
	 * @param _graphFrom
	 * @param _resourcesIdTo
	 * @param _resourcesIDsForInteraction
	 * @param _resourcesTypesForInteraction
	 * @param _resourcesConditions
	 * @param _listModelSelection
	 */
	public ComplexGridSimpleAdapter(final BaseListActivity _baseListActivity, 
			int[] _formatingTypes,
			Context _context, 
			List<? extends Map<String, Object>> _data,
			int _listViewLayoutResource, 
			String[] _graphFrom, 
			int[] _resourcesIdTo,
			int[] _resourcesIDsForInteraction, 
			Class<?>[] _resourcesTypesForInteraction,
			Condition[] _resourcesConditions,
			List<Object> _listModelSelection) {
		super(_baseListActivity, _formatingTypes, _context, _data, _listViewLayoutResource, _graphFrom,
				_resourcesIdTo);
		this.baseListActivity = _baseListActivity;
		this.resourcesToTags = _resourcesIDsForInteraction;
		this.resourcesType = _resourcesTypesForInteraction;
		this.listModelSelection = _listModelSelection;
		this.resourcesConditions = _resourcesConditions;
	}
	
	/**
	 * 
	 * @param _baseListActivity
	 * @param _formatingTypes
	 * @param _context
	 * @param _data
	 * @param _listViewLayoutResource
	 * @param _graphFrom
	 * @param _resourcesIdTo
	 * @param _resourcesIDsForInteraction
	 * @param _resourcesConditions
	 * @param _resourcesTypesForInteraction
	 * @param _resourcesHint
	 */
	public ComplexGridSimpleAdapter(final BaseListActivity _baseListActivity, 
			int[] _formatingTypes,
			Context _context, 
			List<? extends Map<String, Object>> _data,
			int _listViewLayoutResource, 
			String[] _graphFrom, 
			int[] _resourcesIdTo,
			int[] _resourcesIDsForInteraction, 
			Condition[] _resourcesConditions, 
			Class<?>[] _resourcesTypesForInteraction,
			int[] _resourcesHint) {
		super(_baseListActivity, _formatingTypes, _context, _data, _listViewLayoutResource, _graphFrom,
				_resourcesIdTo);
		this.baseListActivity = _baseListActivity;
		this.resourcesToTags = _resourcesIDsForInteraction;
		this.resourcesType = _resourcesTypesForInteraction;
		this.noListModelSelection = true;
		this.resourcesConditions = _resourcesConditions;
		this.resourcesHint = _resourcesHint;
	}
	
	/**
	 *
	 * @see android.widget.SimpleAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public View getView(final int _position, View _convertView, final ViewGroup _parent) {
		try {
			/* chama o getView da superclasse TextGridSimpleAdapter */
			_convertView = super.getView(_position, _convertView, _parent);
			if(_convertView != null) {
				_convertView.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(final View _view) {
						if(ComplexGridSimpleAdapter.this.resourcesHint != null) {
							Toast.makeText(ComplexGridSimpleAdapter.this.baseListActivity, ComplexGridSimpleAdapter.this.getTextHint(), Toast.LENGTH_SHORT).show();
						}
					}
				});
			}
			
			final HashMap<String, Object> data = (HashMap<String, Object>) getItem(_position);
			 
			final Object object = (Object) data.get(Constants.OBJECT);
			 
			if(object != null && resourcesToTags != null && this.resourcesType != null) {
				for(int i = 0; i < resourcesToTags.length; i++) {
					final Condition condition = getCondition(i);
					if(this.resourcesType[i].equals(CheckBox.class) && !this.noListModelSelection) {
						/* A variável listModelSelection não pode ser NULL no caso de CheckBox, pois os
						 * elementos selecionados são atribuídos à lista. */
						this.trataCheckBox(_convertView, object, i, condition);
					} else if (this.resourcesType[i].equals(Button.class)) {
						this.trataButton(_convertView, object, i, condition);
					} else if(this.resourcesType[i].equals(ImageView.class)) {
						this.trataImageView(_convertView, object, i, condition);
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
		if(this.resourcesHint != null) {
			for(int i = 0; i < this.resourcesHint.length; i++) {
				buffer.append("- " + this.baseListActivity.getText(this.resourcesHint[i]));
				if((i + 1) < this.resourcesHint.length) {
					buffer.append("\n");
				}
			}
		}
		return buffer.toString();
	}

	/**
	 * @param _convertView
	 * @param _object
	 * @param _count
	 * @param _condition
	 */
	protected void trataButton(final View _convertView, final Object _object, final int _count, final Condition _condition) {
		try {
			/* No caso de Button, a variável listModelSelection não é necessário */
			final Button button = (Button)_convertView.findViewById(resourcesToTags[_count]);
			if(_condition != null) {
				if(_condition.checkToVisibility(_object)) {
					this.trataBotaoVisible(_object, button);
				} else {
					button.setVisibility(View.GONE);
				}
				_condition.handle(_object, button);
			} else {
				this.trataBotaoVisible(_object, button);
			}
		} catch (Exception _e) {
			Log.e("ComplexGridSimpleAdapter.trataButton", _e.getMessage());
		}
	}

	/**
	 * 
	 * @param _convertView
	 * @param _object
	 * @param _count
	 * @param _condition
	 */
	protected void trataCheckBox(final View _convertView, final Object _object, final int _count, final Condition _condition) {
		try {
			final CheckBox checkBox = (CheckBox)_convertView.findViewById(resourcesToTags[_count]);
			if(_condition != null) {
				if(_condition.checkToVisibility(_object)) {
					this.trataCheckBoxVisible(_object, checkBox);
				} else {
					checkBox.setVisibility(View.GONE);
				}
				_condition.handle(_object, checkBox);
			} else {
				this.trataCheckBoxVisible(_object, checkBox);
			}
		} catch (Exception _e) {
			Log.e("ComplexGridSimpleAdapter.trataCheckBox", _e.getMessage());
		}
	}

	/**
	 * @param _convertView
	 * @param _object
	 * @param _count
	 * @param _condition
	 */
	protected void trataImageView(final View _convertView, final Object _object, final int _count, final Condition _condition) {
		try {
			final ImageView imageView = (ImageView)_convertView.findViewById(resourcesToTags[_count]);
			if(_condition != null) {
				if(_condition.checkToVisibility(_object)) {
					imageView.setVisibility(View.VISIBLE);
				} else {
					imageView.setVisibility(View.GONE);
				}
				_condition.handle(_object, imageView);
			} else {
				imageView.setVisibility(View.VISIBLE);
			}
		} catch (Exception _e) {
			Log.e("ComplexGridSimpleAdapter.trataImageView", _e.getMessage());
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
			_checkBox.setChecked(ComplexGridSimpleAdapter.this.listModelSelection.contains(_object));
			_checkBox.requestLayout();
			_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
					if(isChecked) {
						ComplexGridSimpleAdapter.this.listModelSelection.add(buttonView.getTag());
					} else {
						ComplexGridSimpleAdapter.this.listModelSelection.remove(buttonView.getTag());
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

	/**
	 * @param _index
	 */
	protected Condition getCondition(int _index) {
		Condition condition = null;
		if(this.resourcesConditions != null && this.resourcesConditions.length >= _index) {
			condition = this.resourcesConditions[_index];
		}
		return condition;
	}

}
