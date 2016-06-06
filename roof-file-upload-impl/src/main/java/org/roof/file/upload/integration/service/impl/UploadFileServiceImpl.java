package org.roof.file.upload.integration.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.roof.file.upload.integration.api.UploadFile;
import org.roof.file.upload.integration.api.UploadFileServiceI;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

public class UploadFileServiceImpl implements UploadFileServiceI{

	private static final Logger LOG = Logger.getLogger(UploadFileServiceImpl.class);

	
	private MessageChannel messageChannel;
	
	public void Upload(UploadFile<?> file) {
		LOG.debug(file);
		
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("uploadfile", file);
		Object filedata = file.getFile();
		String uploadtype = file.getTarget().getUploadType().name().toLowerCase();
		headers.put("uploadtype", uploadtype);
		headers.put("file_name", file.getFileName());
		
		Message<?> filemessage = new GenericMessage<Object>(filedata,headers);
		messageChannel.send(filemessage);
	}

	public void setMessageChannel(MessageChannel messageChannel) {
		this.messageChannel = messageChannel;
	}

}
