package org.roof.node.integration;

import java.util.Map;

import org.roof.file.upload.integration.api.UploadFile;
import org.roof.file.upload.integration.api.UploadFileOperate;
import org.roof.file.upload.integration.api.UploadFileServiceI;
import org.roof.file.upload.integration.api.UploadTarget;
import org.roof.file.upload.integration.api.UploadType;
import org.roof.file.upload.integration.impl.UploadFileImpl;
import org.roof.file.upload.integration.impl.UploadTargetImpl;
import org.roof.spring.CurrentSpringContext;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;

/**
 * 写文件
 * 
 * @author liht
 *
 */
public class Writer extends AbstractMessageHandler {
	private UploadFileServiceI uploadFileService;

	@Override
	protected void handleMessageInternal(Message<?> message) throws Exception {
		Map<String, Object> header = message.getHeaders();
		String html = (String) message.getPayload();
		// 模板id
		Map<String, Object> map = (Map<String, Object>) header.get("fileUpload");
		UploadTarget target = null;
		if (map.get("uploadType") == null || "SFTP".equals(map.get("uploadType"))) {
			target = new UploadTargetImpl(UploadType.SFTP, map.get("hosts").toString(), map.get("user").toString(),
					map.get("password").toString(), Integer.parseInt(map.get("port").toString()),
					map.get("remoteDirectory").toString(), map.get("remoteFileSeparator").toString());
		}
		if (map.get("uploadType") == null || "FTP".equals(map.get("uploadType"))) {
			target = new UploadTargetImpl(UploadType.FTP, map.get("hosts").toString(), map.get("user").toString(),
					map.get("password").toString(), Integer.parseInt(map.get("port").toString()),
					map.get("remoteDirectory").toString(), map.get("remoteFileSeparator").toString());
		}

		UploadFile<?> file = null;
		if (map.get("operate") == null || "REPLACE".equals(map.get("operate"))) {
			file = new UploadFileImpl(target,
					map.get("fileDirectory") != null ? map.get("fileDirectory").toString() : "", html,
					map.get("fileName").toString(), UploadFileOperate.REPLACE);
		}
		if (uploadFileService == null) {
			uploadFileService = CurrentSpringContext.getBean("uploadFileService", UploadFileServiceI.class);
		}
		uploadFileService.Upload(file);
	}

	public void setUploadFileService(UploadFileServiceI uploadFileService) {
		this.uploadFileService = uploadFileService;
	}
}
