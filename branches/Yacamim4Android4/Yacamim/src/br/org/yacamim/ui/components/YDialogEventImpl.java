/**
 * YDialogEventImpl.java
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

import java.util.Date;

import android.content.DialogInterface;

/**
 *
 * Class YDialogEventImpl TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
class YDialogEventImpl implements YDialogEvent {
	
	private boolean confirmed;
	private boolean canceled;
	private boolean neutral;
	private DialogInterface dialogInterface;
	private int dialogId;
	private boolean isDate;
	private Date date;
	
	private YDialogEventImpl() {
		super();
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public boolean isConfirmed() {
		return this.confirmed;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public boolean isCanceled() {
		return this.canceled;
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public boolean isNeutral() {
		return this.neutral;
	}
	
	/**
	 * 
	 * @return
	 */
	@Override
	public DialogInterface getDialogInterface() {
		return this.dialogInterface;
	}

	/**
	 * 
	 * @see br.org.yacamim.ui.components.YDialogEvent#getDialogId()
	 */
	@Override
	public int getDialogId() {
		return this.dialogId;
	}

	/**
	 * 
	 * @see br.org.yacamim.ui.components.YDialogEvent#isDate()
	 */
	@Override
	public boolean isDate() {
		return this.isDate;
	}

	/**
	 * 
	 * @see br.org.yacamim.ui.components.YDialogEvent#getDate()
	 */
	@Override
	public Date getDate() {
		return this.date;
	}
	
	/**
	 * 
	 * @param confirmed
	 */
	private void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	/**
	 * 
	 * @param canceled
	 */
	private void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	/**
	 * 
	 * @param neutral
	 */
	private void setNeutral(boolean neutral) {
		this.neutral = neutral;
	}

	/**
	 * 
	 * @param dialogInterface
	 */
	private void setDialogInterface(DialogInterface dialogInterface) {
		this.dialogInterface = dialogInterface;
	}

	/**
	 * 
	 * @param dialogId
	 */
	private void setDialogId(int dialogId) {
		this.dialogId = dialogId;
	}

	/**
	 * 
	 * @param isDate
	 */
	private void setIsDate(boolean isDate) {
		this.isDate = isDate;
	}

	/**
	 * 
	 * @param date
	 */
	private void setDate(Date date) {
		this.date = date;
	}

	/**
	 * 
	 * @author Francisco Tarciso Bomfim Júnior
	 *
	 */
	public static class Builder {
		
		private boolean confirmed;
		private boolean canceled;
		private boolean neutral;
		private DialogInterface dialogInterface;
		private int dialogId;
		private boolean isDate;
		private Date date;
		
		/**
		 * 
		 * @param confirmed
		 * @return
		 */
		public Builder withConfirmed(boolean confirmed) {
			this.confirmed = confirmed;
			return this;
		}

		/**
		 * 
		 * @param canceled
		 * @return
		 */
		public Builder withCanceled(boolean canceled) {
			this.canceled = canceled;
			return this;
		}

		/**
		 * 
		 * @param neutral
		 * @return
		 */
		public Builder withNeutral(boolean neutral) {
			this.neutral = neutral;
			return this;
		}

		/**
		 * 
		 * @param dialogInterface
		 * @return
		 */
		public Builder withDialogInterface(DialogInterface dialogInterface) {
			this.dialogInterface = dialogInterface;
			return this;
		}

		/**
		 * 
		 * @param dialogId
		 * @return
		 */
		public Builder withDialogId(int dialogId) {
			this.dialogId = dialogId;
			return this;
		}

		/**
		 * 
		 * @param isDate
		 * @return
		 */
		public Builder withIsDate(boolean isDate) {
			this.isDate = isDate;
			return this;
		}

		/**
		 * 
		 * @param date
		 * @return
		 */
		public Builder withDate(Date date) {
			this.date = date;
			return this;
		}

		/**
		 * 
		 * @return
		 */
		public YDialogEventImpl build() {
			final YDialogEventImpl yDialogEventImpl = new YDialogEventImpl();
			
			yDialogEventImpl.setCanceled(canceled);
			yDialogEventImpl.setConfirmed(confirmed);
			yDialogEventImpl.setNeutral(neutral);
			yDialogEventImpl.setDialogInterface(dialogInterface);
			yDialogEventImpl.setDialogId(dialogId);
			yDialogEventImpl.setIsDate(isDate);
			yDialogEventImpl.setDate(date);
			
			return yDialogEventImpl;
		}
	}

}
