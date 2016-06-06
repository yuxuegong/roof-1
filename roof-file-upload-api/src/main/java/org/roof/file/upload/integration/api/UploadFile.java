package org.roof.file.upload.integration.api;

import org.springframework.integration.file.support.FileExistsMode;

public interface UploadFile<T> {
	
	/**
	 * 
	 * @return 服务器配置
	 */
	UploadTarget getTarget();
	
	/**
	 * 
	 * @return 上传目录
	 */
	String getFileDirectory();
	
	/**
	 * 
	 * @return 文件 支持二进制 String File 
	 */
	T getFile();
	
	FileExistsMode getMode();
	 
}
