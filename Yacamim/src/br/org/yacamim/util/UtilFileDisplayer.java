/**
 * UtilFileDisplayer.java
 *
 * Copyright 2011 yacamim.org.br
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
public final class UtilFileDisplayer {
	
	/**
	 * 
	 * @param _activity
	 * @param _path
	 * @param _fileExtension
	 * @param _resourceMsgFileNotFound
	 * @param _resourceMsgNoApplicationAvailableToDisplay
	 * @param _resourceMsgFileTypeNotFound
	 */
	public static void displayFile(final Activity _activity, final String _path, final String _fileExtension, 
			final int _resourceMsgFileNotFound,
			final int _resourceMsgNoApplicationAvailableToDisplay,
			final int _resourceMsgFileTypeNotFound) {
		try {
			File file = new File(_path);

            if (file.exists()) {
                final Uri path = Uri.fromFile(file);
                final Intent intent = new Intent(Intent.ACTION_VIEW);
                if(configFileType(_activity, _fileExtension, path, intent, _resourceMsgFileTypeNotFound)) {
                	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                	
                	displayFile(_activity, _fileExtension, intent, _resourceMsgNoApplicationAvailableToDisplay);
                }
            } else {
            	Toast.makeText(_activity, 
            			_activity.getText(_resourceMsgFileNotFound), 
        		        Toast.LENGTH_SHORT).show();
            }
		} catch (final Exception _e) {
			Log.e("UtilFileDisplayer.displayFile", _e.getMessage());
		}
	}

	/**
	 * 
	 * @param _activity
	 * @param _fileExtension
	 * @param _intent
	 * @param _resourceMsgNoApplicationAvailableToDisplay
	 */
	protected static void displayFile(final Activity _activity, final String _fileExtension, final Intent _intent,
			final int _resourceMsgNoApplicationAvailableToDisplay) {
		try {
			_activity.startActivity(_intent);
		} catch (final ActivityNotFoundException _e) {
		    Toast.makeText(_activity, 
		    		_activity.getText(_resourceMsgNoApplicationAvailableToDisplay) + _fileExtension.toUpperCase(), 
		        Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 
	 * @param _activity
	 * @param _fileExtension
	 * @param _path
	 * @param _intent
	 * @param _resourceMsgFileTypeNotFound
	 * @return
	 */
	protected static boolean configFileType(
			final Activity _activity, final String _fileExtension,
			final Uri _path, final Intent _intent,
			final int _resourceMsgFileTypeNotFound) {
		try {
			_intent.setDataAndType(_path, EnumMimeType.getMimeType(_fileExtension).getMimeType());
			return true;
		} catch (NullPointerException e) {
			 Toast.makeText(_activity, 
					 _activity.getText(_resourceMsgFileTypeNotFound) + _fileExtension.toUpperCase(), 
		        Toast.LENGTH_SHORT).show();
			 return false;
		}
	}
	
}