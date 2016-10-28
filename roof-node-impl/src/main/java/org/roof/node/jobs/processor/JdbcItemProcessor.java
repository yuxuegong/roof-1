package org.roof.node.jobs.processor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.roof.commons.RoofDateUtils;
import org.roof.roof.dataaccess.api.Page;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;



public class JdbcItemProcessor implements ItemProcessor<Page, Map> {
	private StepExecution stepExecution;
	private Object templateVo;

	@Override
	public Map<String, Object> process(Page page) throws Exception {
		Collection<?> collection = page.getDataList();
		JobParameters jobParameters = stepExecution.getJobParameters();// 获得job启动参数
		String html = "<p>Hello, my name is Alan.Today is . I am from Somewhere, TX. I have  kids:</p><ul><li>1:Jimmy is 12</li><li>2:Sally is 4</li></ul>"
				+ "<h1>reuse templete</h1>" + "<p>Home page</p>" + "<span>Powered by Handlebars.java</span>";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("file", html);
		map.put("hosts", "10.80.19.204");
		map.put("user", "root");
		map.put("password", "zjhcsoft");
		map.put("port", "22");
		map.put("remoteDirectory", "soft");
		map.put("remoteFileSeparator", "/");
		return map;
	}

	@BeforeStep
	// spring自动注入job执行上下文对象用于获取job的参数
	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

}
