/**
 * YUtilIO.java
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
 * Class YUtilIO TODO
 *
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public final class YUtilIO {

	/**
	 *
	 */
	private YUtilIO() {
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
			Log.e("YUtilIO.convertToStringBuilder", _e.getMessage());
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
			Log.e("YUtilIO.storeByteImage", _e.getMessage());
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
			Log.e("YUtilIO.renemaFile", _e.getMessage());
		}
		return null;
	}

}
