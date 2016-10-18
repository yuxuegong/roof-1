package org.roof.node.integration;

import java.util.Map;

import org.roof.node.jobs.datasource.DataSourceRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

import com.alibaba.fastjson.JSONArray;

/**
 * 注册数据源
 * 
 * @author liht
 *
 */
public class JsonJdbcDataSourceRegisterNode extends AbstractMessageHandler {
	private DataSourceRegister jsonJdbcDataSourceRegister;
	private MessageChannel messageChannel;

	@Override
	protected void handleMessageInternal(Message<?> message) throws Exception {
		Map<String, Object> header = message.getHeaders();
		Object[] dataSourceContext = ((JSONArray) header.get("dataSourceContext")).toArray();
		for (Object object : dataSourceContext) {
			jsonJdbcDataSourceRegister.regist(object.toString(), null);// 注册数据源
		}
		messageChannel.send(message);
	}

	public void setMessageChannel(MessageChannel messageChannel) {
		this.messageChannel = messageChannel;
	}

	public void setJsonJdbcDataSourceRegister(DataSourceRegister jsonJdbcDataSourceRegister) {
		this.jsonJdbcDataSourceRegister = jsonJdbcDataSourceRegister;
	}

}
