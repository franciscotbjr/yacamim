/**
 * UtilIO.java
 *
 * Copyright 2011 yacamim.org.br
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
package br.org.yacamim.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Class UtilIO TODO
 * 
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class UtilIO {

	/**
	 * 
	 */
	private UtilIO() {
		super();
	}
	
	/**
	 * 
	 * @param _inputStream
	 * @return
	 */
	public static StringBuilder convertToStringBuilder(final InputStream _inputStream) {
		final StringBuilder stringBuilder = new StringBuilder();
		try {
			final BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(_inputStream, Charset.forName("UTF-8"))
				);
			String line = null;
			while((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (Exception _e) {
			Log.e("UtilIO.convertToStringBuilder", _e.getMessage());
		}
		return stringBuilder;
	}
	
	/**
	 * 
	 * @param _context
	 * @param _imageData
	 * @param _quality
	 * @param _directory
	 * @param _fileName
	 * @return
	 */
	public static String storeByteImage(final Context _context, final byte[] _imageData, final int _quality, final String _directory, final String _fileName) {
		String nomeImagem = null;
		FileOutputStream fileOutputStream = null;
		try {
			final File sdImageMainDirectory = new File(_directory);
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 5;
			
			final Bitmap myImage = BitmapFactory.decodeByteArray(_imageData, 0, _imageData.length,options);

			final String nomeImagemTemp = sdImageMainDirectory.toString() +"/" + _fileName + "_" + System.currentTimeMillis() + ".jpg";
			fileOutputStream = new FileOutputStream(nomeImagemTemp);
  
			final BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

			myImage.compress(CompressFormat.JPEG, _quality, bos);

			bos.flush();
			bos.close();

			nomeImagem = nomeImagemTemp;
		} catch (Exception _e) {
			Log.e("UtilIO.storeByteImage", _e.getMessage());
		}
		return nomeImagem;
	}
	
	/**
	 * 
	 * @param _fileImage
	 * @param _newImageName
	 * @return
	 */
	public static File renemaFile(File _fileImage, String _newImageName) {
		try {
			final File newFile = new File(_fileImage.getPath().substring(0, _fileImage.getPath().lastIndexOf(File.separatorChar + "") + 1) + _newImageName);
			_fileImage.renameTo(newFile);
			_fileImage.delete();
			return newFile;
		} catch (Exception _e) {
			Log.e("UtilIO.renemaFile", _e.getMessage());
		}
		return null;
	}

}
