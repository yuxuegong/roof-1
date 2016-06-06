package org.roof.file.upload.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	public static ApplicationContext ctx;
	public static void main(String... args) throws Exception {
		ctx = new ClassPathXmlApplicationContext("spring-upload-file.xml");
		
	}
}

