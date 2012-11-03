/**
 * YEnumMimeType.java
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


/**
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public enum YEnumMimeType {

	MIME_TYPE_PDF("pdf", "application/pdf"),
	MIME_TYPE_MPEG("mpg", "audio/mpeg"),
	MIME_TYPE_JPEG("jpg", "image/jpeg"),
	MIME_TYPE_PNG("png", "image/png"),
	MIME_TYPE_HTML("html", "text/html"),
	MIME_TYPE_GIF("gif", "image/gif"),
	MIME_TYPE_TXT("txt", "text/plain"),
	MIME_TYPE_RTF("rtf", "text/richtext"),
	MIME_TYPE_BMP("bmp", "image/bmp"),
	MIME_TYPE_DOC("doc", "application/msword"),
	MIME_TYPE_XLS("xls", "application/vnd.ms-excel"),
	MIME_TYPE_PPT("ppt", "application/vnd.ms-powerpoint"),
	MIME_TYPE_DOCX("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
	MIME_TYPE_XLSX("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
	MIME_TYPE_PPTX("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation")
	;

	private String mimeType;
	private String extention;


	/**
	 *
	 * @param _extention
	 * @param _mimeType
	 */
	private YEnumMimeType(final String _extention, final String _mimeType) {
		this.extention = _extention;
		this.mimeType = _mimeType;
	}

	/**
	 *
	 * @return
	 */
	public String getMimeType() {
		return this.mimeType;
	}

	/**
	 *
	 * @return
	 */
	public String getExtention() {
		return this.extention;
	}

	/**
	 *
	 * @param _extention
	 * @return
	 */
	public static YEnumMimeType getMimeType(final String _extention) {
		YEnumMimeType yEnumMimeType = null;
		if(_extention.equalsIgnoreCase(MIME_TYPE_PDF.getExtention())) {
			yEnumMimeType = MIME_TYPE_PDF;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_MPEG.getExtention())) {
			yEnumMimeType = MIME_TYPE_MPEG;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_JPEG.getExtention())) {
			yEnumMimeType = MIME_TYPE_JPEG;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_PNG.getExtention())) {
			yEnumMimeType = MIME_TYPE_PNG;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_HTML.getExtention())) {
			yEnumMimeType = MIME_TYPE_HTML;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_GIF.getExtention())) {
			yEnumMimeType = MIME_TYPE_GIF;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_TXT.getExtention())) {
			yEnumMimeType = MIME_TYPE_TXT;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_RTF.getExtention())) {
			yEnumMimeType = MIME_TYPE_RTF;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_BMP.getExtention())) {
			yEnumMimeType = MIME_TYPE_BMP;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_DOC.getExtention())) {
			yEnumMimeType = MIME_TYPE_DOC;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_XLS.getExtention())) {
			yEnumMimeType = MIME_TYPE_XLS;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_PPT.getExtention())) {
			yEnumMimeType = MIME_TYPE_PPT;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_DOCX.getExtention())) {
			yEnumMimeType = MIME_TYPE_DOCX;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_XLSX.getExtention())) {
			yEnumMimeType = MIME_TYPE_XLSX;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_PPTX.getExtention())) {
			yEnumMimeType = MIME_TYPE_PPTX;
		}
		return yEnumMimeType;
	}



}