/**
 * YDatePikerFragment.java
 *
 * Copyright 2013 yacamim.org.br
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

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.DatePicker;
import br.org.yacamim.util.YUtilDate;

/**
 * Classe YDatePikerFragment TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public class YDatePikerFragment  extends DialogFragment implements DatePickerDialog.OnDateSetListener, DialogInterface.OnKeyListener {
	
	private int mDialogId;
	private Date mDate;
	private boolean mBackbuttonPressed;
	
	public YDatePikerFragment() {
		super();
	}
	
	
	/**
	 * 
	 * @see android.app.DialogFragment#onCreate(android.os.Bundle)
	 */
    @Override    
    public void onCreate(final Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	this.setCancelable(true);
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        setStyle(style,theme);
    }
    
    /**
     * 
     * @see android.app.DialogFragment#onCreateDialog(android.os.Bundle)
     */
    @Override    
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
    	int year, month, day;
    	
    	if(this.getDate() == null) {
    		this.setDate(Calendar.getInstance().getTime());
    	}
    	
    	final Calendar calendar = Calendar.getInstance();
    	calendar.setTime(mDate);
    	
    	year = calendar.get(Calendar.YEAR);
    	month = calendar.get(Calendar.MONTH);
    	day = calendar.get(Calendar.DAY_OF_MONTH);
    	
    	final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
    	datePickerDialog.setOnKeyListener(this);
    	
    	return datePickerDialog;
    }

	/**
	 * 
	 * @see android.app.DatePickerDialog.OnDateSetListener#onDateSet(android.widget.DatePicker, int, int, int)
	 */
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		if(!mBackbuttonPressed) {
			final Calendar calendar = YUtilDate.getCleanCalendar();
			calendar.set(year, monthOfYear, dayOfMonth);
			((OnDialogDoneListener) getActivity())
				.onDialogClick(
						new YDialogEventImpl.Builder()
						.withIsDate(true)
						.withDate(calendar.getTime())
						.withDialogId(getDialogId())
						.build()
						);
		}
		
	}
	

	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			mBackbuttonPressed = true;
			this.dismiss();
		}
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	private int getDialogId() {
		return mDialogId;
	}

	/**
	 * 
	 * @param dialogId
	 */
	private void setDialogId(int dialogId) {
		this.mDialogId = dialogId;
	}

	/**
	 * 
	 * @return
	 */
	private Date getDate() {
		return mDate;
	}

	/**
	 * 
	 * @param date
	 */
	private void setDate(Date date) {
		this.mDate = date;
	}

	/**
     * Builder
     * @author Francisco Tarciso Bomfim Júnior
     *
     */
	public static class Builder {
		
		private int mDialogId;
		private Date mDate;
		private boolean mCancelable;
		
		/**
		 * 
		 * @param dialogId
		 * @return
		 */
		public Builder withDialogId(final int dialogId) {
			mDialogId = dialogId;
			return this;
		}
		
		/**
		 * 
		 * @param date
		 * @return
		 */
		public Builder withDate(final Date date) {
			mDate = date;
			return this;
		}
		
		/**
		 * 
		 * @param cancelable
		 * @return
		 */
		public Builder withCancelable(boolean cancelable) {
			mCancelable = cancelable;
			return this;
		}
		
		/**
		 * 
		 * @return
		 */
		public YDatePikerFragment build() {
			final YDatePikerFragment yDatePikerFragment = new YDatePikerFragment();
			yDatePikerFragment.setDate(this.mDate);
			yDatePikerFragment.setDialogId(this.mDialogId);
			yDatePikerFragment.setCancelable(mCancelable);
			
			final Bundle bundle = new Bundle();
			yDatePikerFragment.setArguments(bundle);
			
			return yDatePikerFragment;
		}
		
	}

}