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
	
	/**
	 * 
	 * @param confirmed
	 * @param canceled
	 * @param neutral
	 * @param dialogInterface
	 * @param dialogType
	 */
	YDialogEventImpl(final boolean confirmed, final boolean canceled, final boolean neutral, final DialogInterface dialogInterface, final int dialogId) {
		super();
		this.confirmed = confirmed;
		this.canceled = canceled;
		this.neutral = neutral;
		this.dialogInterface = dialogInterface;
		this.dialogId = dialogId;
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

}
