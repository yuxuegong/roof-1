package org.roof.node.integration;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.roof.node.api.JobStarter;
import org.roof.spring.CurrentSpringContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring-all.xml" })
public class JobStarterImplTest extends AbstractJUnit4SpringContextTests {
	private String config = "{	'templateId' : '',	'templateContent' : '',	'dataSourceContext' : [ {		'name' : 'letv',		'url' : 'jdbc:mysql://127.0.0.1:3306/node',		'username' : 'root',		'password' : '123'	}, {		'name' : 'letv1',		'url' : 'jdbc:mysql://127.0.0.1:3306/ccc',		'username' : 'root',		'password' : '123'	} ],	'templateData' : [ {		'key' : 'two',		'dataSourceName' : 'letv',		'sql' : 'select * from s_dictionary where type = :type',		'args' : {			'type' : 'DEV_STATUS'		}	}, {		'key' : 'one',		'dataSourceName' : 'letv',		'sql' : 'select * from s_dictionary where type = :type',		'args' : {			'type' : 'ALARM_LEVEL'		}	} ],	'fileUpload' : {		'port' : 22,		'uploadType' : 'SFTP',		'remoteDirectory' : 'E:/spring-integration-samples/output',		'hosts' : '192.168.159.149',		'charset' : 'UTF-8',		'remoteFileSeparator' : '',		'user' : 'zz3310969',		'password' : '3310969',		'fileDirectory' : 'user/index',		'fileName' : 'index.html',		'operate' : 'REPLACE'	}}";// 数据源配置
	private JobStarter jobStarter;

	@Before
	public void setUp() throws Exception {
		CurrentSpringContext.setCurrentContext(this.applicationContext);
	}

	@Test
	public void testStart() {
		jobStarter.start(config);
	}

	@Autowired
	public void setJobStarter(@Qualifier("jobStarter") JobStarter jobStarter) {
		this.jobStarter = jobStarter;
	}

}
