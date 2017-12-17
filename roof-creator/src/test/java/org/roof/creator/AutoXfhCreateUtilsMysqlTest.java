package org.roof.creator;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.roof.commons.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath:spring.xml" })
public class AutoXfhCreateUtilsMysqlTest extends
		AbstractJUnit4SpringContextTests {
	AutoXfhCreateUtilsMysql autoXfhCreateUtils;

	@Test
	public void testCreateCode() {
		// 包名
		String packagePath = "org.roof.web.user";

		// 添加需要生成的表名
		List<String> sourcelist = new ArrayList<String>();
		sourcelist.add("s_user");

		autoXfhCreateUtils.setProject_name("base");
		// 拆分键
		autoXfhCreateUtils.createCode(packagePath, sourcelist, "", false); // 不带拆分键，不带页签
		// autoLetvCreateUtils.createCode(packagePath, sourcelist, "", true); //
		// 不带拆分键，带页签
		// autoLetvCreateUtils.createCode(packagePath, sourcelist, "username");
		// // 带拆分键（不填默认为false）
		
	}

	@Test
	public void testSql() {

	}

	@Autowired(required = true)
	public void setAutoXfhCreateUtils(
			@Qualifier("autoXfhCreateUtilsMysql") AutoXfhCreateUtilsMysql autoXfhCreateUtils) {
		this.autoXfhCreateUtils = autoXfhCreateUtils;
	}

}