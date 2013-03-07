/**
 * HomeFragment.java
 * 
 * Copyright 2013 br.com.joaoboscobezerrabonfim
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
package br.com.joaoboscobezerrabonfim;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import br.org.yacamim.YBaseFragment;

/**
 * 
 * Class HomeFragment TODO
 * 
 * @author br.com.joaoboscobezerrabonfim (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class HomeFragment extends YBaseFragment {
	
	public static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * 
	 */
	public HomeFragment() {
		super();
	}
	
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		View rootView = null;
		switch(getArguments().getInt(ARG_SECTION_NUMBER)) {
			case 1:
				rootView = inflater.inflate(R.layout.fragment_poemas, container, false);
				break;
			case 2:
				rootView = inflater.inflate(R.layout.fragment_favoritos, container, false);
				break;
			case 3:
				rootView = inflater.inflate(R.layout.fragment_publicacoes, container, false);
				break;
			case 4:
				rootView = inflater.inflate(R.layout.fragment_autor, container, false);
				break;
		}
		TextView dummyTextView = (TextView) rootView
				.findViewById(R.id.section_label);
		dummyTextView.setText(Integer.toString(getArguments().getInt(
				ARG_SECTION_NUMBER)));
		return rootView;
	}

}
