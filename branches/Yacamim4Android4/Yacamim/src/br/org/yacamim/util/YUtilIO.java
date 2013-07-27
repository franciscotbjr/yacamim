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
	
	private static final String TAG = YUtilIO.class.getSimpleName();

	/**
	 *
	 */
	private YUtilIO() {
		super();
	}

	/**
	 *
	 * @param inputStream
	 * @return
	 */
	public static StringBuilder convertToStringBuilder(final InputStream inputStream) {
		final StringBuilder stringBuilder = new StringBuilder();
		try {
			final BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream, Charset.forName("UTF-8"))
				);
			String line = null;
			while((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (Exception e) {
			Log.e(TAG + ".convertToStringBuilder", e.getMessage());
		}
		return stringBuilder;
	}

	/**
	 *
	 * @param context
	 * @param imageData
	 * @param quality
	 * @param directory
	 * @param fileName
	 * @return
	 */
	public static String storeByteImage(final Context context, final byte[] imageData, final int quality, final String directory, final String fileName) {
		String nomeImagem = null;
		FileOutputStream fileOutputStream = null;
		try {
			final File sdImageMainDirectory = new File(directory);
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 5;

			final Bitmap myImage = BitmapFactory.decodeByteArray(imageData, 0, imageData.length,options);

			final String nomeImagemTemp = sdImageMainDirectory.toString() +"/" + fileName + "_" + System.currentTimeMillis() + ".jpg";
			fileOutputStream = new FileOutputStream(nomeImagemTemp);

			final BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

			myImage.compress(CompressFormat.JPEG, quality, bos);

			bos.flush();
			bos.close();

			nomeImagem = nomeImagemTemp;
		} catch (Exception e) {
			Log.e(TAG + ".storeByteImage", e.getMessage());
		}
		return nomeImagem;
	}

	/**
	 *
	 * @param fileImage
	 * @param newImageName
	 * @return
	 */
	public static File renemaFile(final File fileImage, final String newImageName) {
		try {
			final File newFile = new File(fileImage.getPath().substring(0, fileImage.getPath().lastIndexOf(File.separatorChar + "") + 1) + newImageName);
			fileImage.renameTo(newFile);
			fileImage.delete();
			return newFile;
		} catch (Exception e) {
			Log.e(TAG + ".renemaFile", e.getMessage());
		}
		return null;
	}

}
