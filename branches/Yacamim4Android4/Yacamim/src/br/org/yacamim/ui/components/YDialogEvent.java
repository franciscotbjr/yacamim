/**
 * YDialogEvent.java
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
 * Class YDialogEvent TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public interface YDialogEvent {
	
	/**
	 * 
	 * @return
	 */
	public boolean isConfirmed();

	/**
	 * 
	 * @return
	 */
	public boolean isCanceled();

	/**
	 * 
	 * @return
	 */
	public boolean isNeutral();
	
	/**
	 * 
	 * @return
	 */
	public DialogInterface getDialogInterface();
	
	/**
	 * 
	 * @return
	 */
	public int getDialogId();
	
	/**
	 * 
	 * @return
	 */
	public boolean isDate();
	
	/**
	 * 
	 * @return
	 */
	public Date getDate();

}
