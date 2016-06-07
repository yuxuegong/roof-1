package org.roof.file.upload.integration.api;

public enum UploadFileOperate {
	

	/**
	 * If the file already exists, do nothing.
	 */
	IGNORE,

	/**
	 * If the file already exists, replace it.
	 */
	REPLACE,
	/**
	 * remove
	 */
	REMOVE;
}
