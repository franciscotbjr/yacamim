/**
 * YUtilFileDisplayer.java
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
package br.org.yacamim.util;

import java.io.File;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;


/**
 * Class utilitária para realizar log de funcionalidades.
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class YUtilFileDisplayer {
	
	private static final String TAG = YUtilIO.class.getSimpleName();

	/**
	 *
	 * @param activity
	 * @param path
	 * @param fileExtension
	 * @param resourceMsgFileNotFound
	 * @param resourceMsgNoApplicationAvailableToDisplay
	 * @param resourceMsgFileTypeNotFound
	 */
	public static void displayFile(final Activity activity, final String path, final String fileExtension,
			final int resourceMsgFileNotFound,
			final int resourceMsgNoApplicationAvailableToDisplay,
			final int resourceMsgFileTypeNotFound) {
		try {
			File file = new File(path);

            if (file.exists()) {
                final Uri uriPath = Uri.fromFile(file);
                final Intent intent = new Intent(Intent.ACTION_VIEW);
                if(configFileType(activity, fileExtension, uriPath, intent, resourceMsgFileTypeNotFound)) {
                	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                	displayFile(activity, fileExtension, intent, resourceMsgNoApplicationAvailableToDisplay);
                }
            } else {
            	Toast.makeText(activity,
            			activity.getText(resourceMsgFileNotFound),
        		        Toast.LENGTH_SHORT).show();
            }
		} catch (final Exception e) {
			Log.e(TAG + ".displayFile", e.getMessage());
		}
	}

	/**
	 *
	 * @param activity
	 * @param fileExtension
	 * @param intent
	 * @param resourceMsgNoApplicationAvailableToDisplay
	 */
	protected static void displayFile(final Activity activity, final String fileExtension, final Intent intent,
			final int resourceMsgNoApplicationAvailableToDisplay) {
		try {
			activity.startActivity(intent);
		} catch (final ActivityNotFoundException e) {
		    Toast.makeText(activity,
		    		activity.getText(resourceMsgNoApplicationAvailableToDisplay) + fileExtension.toUpperCase(),
		        Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 *
	 * @param activity
	 * @param fileExtension
	 * @param path
	 * @param intent
	 * @param resourceMsgFileTypeNotFound
	 * @return
	 */
	protected static boolean configFileType(
			final Activity activity, final String fileExtension,
			final Uri path, final Intent intent,
			final int resourceMsgFileTypeNotFound) {
		try {
			intent.setDataAndType(path, YEnumMimeType.getMimeType(fileExtension).getMimeType());
			return true;
		} catch (NullPointerException e) {
			 Toast.makeText(activity,
					 activity.getText(resourceMsgFileTypeNotFound) + fileExtension.toUpperCase(),
		        Toast.LENGTH_SHORT).show();
			 return false;
		}
	}

}