package org.roof.node.integration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import com.alibaba.fastjson.JSONArray;

public class Processor extends AbstractMessageHandler {

	private MessageChannel messageChannel;

	// TODO 需要增加调用渲染模板的逻辑
	@Override
	protected void handleMessageInternal(Message<?> message) throws Exception {
		Map<String, Object> header = message.getHeaders();
		// 模板id
		String templateId = (String) header.get("templateId");
		// 模板内容
		String templateContent = (String) header.get("templateContent");
		// 模板数据，key为定义的标签的名字，后面是数据，map形式
		Map<String, Object> dataMap = (Map<String, Object>) message.getPayload();
		// 渲染
		String html = "<p>Hello, my name is Alan.Today is . I am from Somewhere, TX. I have  kids:</p><ul><li>1:Jimmy is 12</li><li>2:Sally is 4</li></ul>"
				+ "<h1>reuse templete</h1>" + "<p>Home page</p>" + "<span>Powered by Handlebars.java</span>";
		// 把渲染好的html string 发送给file节点
		Message<?> resultMessage = new GenericMessage<Object>(html, header);
		messageChannel.send(resultMessage);
	}

	public void setMessageChannel(MessageChannel messageChannel) {
		this.messageChannel = messageChannel;
	}
}
