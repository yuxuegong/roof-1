package org.roof.node.integration;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.roof.node.jobs.datasource.DataSourceContext;
import org.springframework.integration.handler.AbstractMessageHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 读数据
 * 
 * @author liht
 *
 */
public class JdbcReader extends AbstractMessageHandler {
	private MessageChannel messageChannel;
	private DataSourceContext dataSourceContext;

	@Override
	protected void handleMessageInternal(Message<?> message) throws Exception {
		Map<String, Object> header = message.getHeaders();
		Object[] templateData = ((JSONArray) header.get("templateData")).toArray();
		Map<String, Object> resultMap = new HashMap<>();

		for (Object object : templateData) {
			String key = ((JSONObject) object).getString("key");
			String dataSourceName = ((JSONObject) object).getString("dataSourceName");
			String sql = URLDecoder.decode(((JSONObject) object).getString("sql"), "utf-8");
			Map<String, Object> args = (Map<String, Object>) ((JSONObject) object).get("args");
			String dbType = ((JSONObject) object).getString("dbType");
			Assert.notNull(dataSourceName, "数据源名称不能为空");
			if (StringUtils.isEmpty(dbType)) { // 默认数据库类型为 mysql
				dbType = "mysql";
			}
			Assert.notNull(sql, "查询语句不能为空");
			DataSource dataSource = getDataSource(dataSourceName); // 从数据源context中获取当前数据源
			Assert.notNull(dataSource, "未找到name=[" + dataSourceName + "]的jdbc数据源");
			NamedParameterJdbcTemplate namedParameterjdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
			List<Map<String, Object>> result = namedParameterjdbcTemplate.queryForList(sql, args);
			resultMap.put(key, result);
		}

		Message<?> resoutMessage = new GenericMessage<Object>(resultMap, message.getHeaders());
		messageChannel.send(resoutMessage);
	}

	private DataSource getDataSource(String dataSourceName) {
		Object dataSource = dataSourceContext.get(dataSourceName);
		if (dataSource instanceof DataSource) {
			DataSource result = (DataSource) dataSource;
			return result;
		}
		return null;
	}

	public void setDataSourceContext(DataSourceContext dataSourceContext) {
		this.dataSourceContext = dataSourceContext;
	}

	public void setMessageChannel(MessageChannel messageChannel) {
		this.messageChannel = messageChannel;
	}
}
