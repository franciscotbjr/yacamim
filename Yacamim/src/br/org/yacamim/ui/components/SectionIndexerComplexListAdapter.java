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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.widget.SectionIndexer;
import br.org.yacamim.YBaseListActivity;
import br.org.yacamim.util.YConstants;
import br.org.yacamim.util.YUtilReflection;

/**
 * Class ComplexListSimpleAdapter TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class SectionIndexerComplexListAdapter<T> 
				extends ComplexListSimpleAdapter 
				implements	SectionIndexer {
	
	private HashMap<String, Integer> mAlphaIndexer;  
	private Object[] mSections;  
//	private Comparator<T> mComparator;
	final String mGraphToCompare;

	/**
	 * 
	 * @param baseListActivity
	 * @param context
	 * @param data
	 * @param adapterConfig
	 * @param comparator
	 * @param graphToCompare
	 */
	public SectionIndexerComplexListAdapter(
			final YBaseListActivity baseListActivity,
			final Context context, 
			final List<? extends Map<String, Object>> data,
			final AdapterConfig adapterConfig,
//			final Comparator<T> comparator,
			final String graphToCompare) {
		super(baseListActivity, context, data, adapterConfig);
//		mComparator = comparator;
		mGraphToCompare = graphToCompare;
		buildIndex(data);
	}

	/**
	 * 
	 * @param baseListActivity
	 * @param data
	 * @param adapterConfig
	 * @param comparator
	 * @param graphToCompare
	 */
	public SectionIndexerComplexListAdapter(
			final YBaseListActivity baseListActivity,
			final List<? extends Map<String, Object>> data,
			final AdapterConfig adapterConfig,
//			final Comparator<T> comparator,
			final String graphToCompare) {
		super(baseListActivity, data, adapterConfig);
//		mComparator = comparator;
		mGraphToCompare = graphToCompare;
		buildIndex(data);
	}
	
	private void buildIndex(
			final List<? extends Map<String, Object>> data) {
		
		final List items = new ArrayList(); 
		for(Map map : data) {
			items.add(map.get(YConstants.OBJECT));
		}
		
		mAlphaIndexer = new HashMap<String, Integer>();  
		int size = items.size();  
		for (int x = 0; x < size; x++) {  
			String s = (String)YUtilReflection.getPropertyValue(mGraphToCompare, items.get(x));  
			
			// get the first letter of the store  
			String ch = s.substring(0, 1);  
			// convert to uppercase otherwise lowercase a -z will be sorted  
			// after upper A-Z  
			ch = ch.toUpperCase();  
			
			//put only if the key does not exist  
			if (!mAlphaIndexer.containsKey(ch)) {
				mAlphaIndexer.put(ch, x);
			}
		}
		
		final Set<String> sectionLetters = mAlphaIndexer.keySet();
	    // create a list from the set to sort  
	    final List<String> sectionList = new ArrayList<String>(sectionLetters); 
		
		Collections.sort(sectionList); 
		
		mSections = new Object[sectionList.size()]; 
		
		sectionList.toArray(mSections); 
		
	}

	public int getPositionForSection(int section) {  
	    return mAlphaIndexer.get(mSections[section]);  
	}  
	
	//@Override
	//public int getPositionForSection(int section) {
	//    if (section > sections.length - 1) {
	//        return mItems.size() - 1;
	//    } else {
	//        return alphaIndexer.get(sections[section]);
	//    }
	//}

	public int getSectionForPosition(int position) {  
	    return 0;  
	}  
	
	public Object[] getSections() {  
	    return mSections;  
	} 

	
}