package org.roof.node.integration;

import java.util.HashMap;
import java.util.Map;

import org.roof.node.api.JobStarter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class JobStarterImpl implements JobStarter {

	private MessageChannel messageChannel;

	@Override
	public void start(String parameters) {
		// json转对象
		Map<String, Object> map = JSON.parseObject(parameters, Map.class);
		Object[] dataSourceContext = ((JSONArray) map.get("dataSourceContext")).toArray();

		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("templateId", map.get("templateId"));
		headers.put("templateContent", map.get("templateContent"));
		headers.put("dataSourceContext", map.get("dataSourceContext"));
		headers.put("templateData", map.get("templateData"));
		headers.put("fileUpload", map.get("fileUpload"));

		Message<?> message = new GenericMessage<Object>(new Object(), headers);
		messageChannel.send(message);
	}

	public void setMessageChannel(MessageChannel messageChannel) {
		this.messageChannel = messageChannel;
	}
}
