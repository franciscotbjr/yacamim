/**
 * EnumMimeType.java
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


/**
 * @author yacamim.org.br (Francisco Tarcizo Bomfim Júnior)
 * @version 1.0
 * @since 1.0
 */
public enum EnumMimeType {
	
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
	private EnumMimeType(final String _extention, final String _mimeType) {
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
	public static EnumMimeType getMimeType(final String _extention) {
		EnumMimeType enumMimeType = null;
		if(_extention.equalsIgnoreCase(MIME_TYPE_PDF.getExtention())) {
			enumMimeType = MIME_TYPE_PDF;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_MPEG.getExtention())) {
			enumMimeType = MIME_TYPE_MPEG;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_JPEG.getExtention())) {
			enumMimeType = MIME_TYPE_JPEG;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_PNG.getExtention())) {
			enumMimeType = MIME_TYPE_PNG;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_HTML.getExtention())) {
			enumMimeType = MIME_TYPE_HTML;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_GIF.getExtention())) {
			enumMimeType = MIME_TYPE_GIF;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_TXT.getExtention())) {
			enumMimeType = MIME_TYPE_TXT;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_RTF.getExtention())) {
			enumMimeType = MIME_TYPE_RTF;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_BMP.getExtention())) {
			enumMimeType = MIME_TYPE_BMP;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_DOC.getExtention())) {
			enumMimeType = MIME_TYPE_DOC;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_XLS.getExtention())) {
			enumMimeType = MIME_TYPE_XLS;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_PPT.getExtention())) {
			enumMimeType = MIME_TYPE_PPT;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_DOCX.getExtention())) {
			enumMimeType = MIME_TYPE_DOCX;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_XLSX.getExtention())) {
			enumMimeType = MIME_TYPE_XLSX;
		} else if(_extention.equalsIgnoreCase(MIME_TYPE_PPTX.getExtention())) {
			enumMimeType = MIME_TYPE_PPTX;
		}
		return enumMimeType;
	}
	
	
	
}