package org.roof.file.upload.integration.api;

/**
 * 服务器配置接口
 */
public interface UploadTarget {
	
	/**
	 * 上传类型
	 * @return
	 */
	UploadType getUploadType();
	/**
	 * ip
	 * @return
	 */
	String getHosts();
	/**
	 * 用户
	 * @return
	 */
	String getUser();
	/**
	 * 密码
	 * @return
	 */
	String getPassword();
	/**
	 * 端口
	 * @return
	 */
	int getPort();
	/**
	 * 固定目录
	 * @return
	 */
	String getRemoteDirectory();
	/**
	 * 分隔符
	 * @return
	 */
	String getRemoteFileSeparator();
	/**
	 * 字符集
	 * @return
	 */
	String getCharset();
	
}
