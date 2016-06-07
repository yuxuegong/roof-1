package org.roof.file.upload.integration.api;

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
	 * 文件名
	 * @return
	 */
	String getFileName();
	
	/**
	 * 
	 * @return 文件 支持二进制 String File 
	 */
	T getFile();
	
	/**
	 * 操作
	 * @return
	 */
	UploadFileOperate getOperate();
	 
}
