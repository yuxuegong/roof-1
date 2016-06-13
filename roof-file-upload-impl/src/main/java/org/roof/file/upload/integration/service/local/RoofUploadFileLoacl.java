package org.roof.file.upload.integration.service.local;

import java.io.File;
import java.nio.file.FileVisitOption;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.roof.file.upload.integration.api.UploadFile;
import org.roof.file.upload.integration.api.UploadFileOperate;
import org.roof.file.upload.integration.api.UploadTarget;
import org.roof.file.upload.integration.api.UploadType;
import org.roof.file.upload.integration.impl.UploadFileImpl;
import org.roof.file.upload.integration.impl.UploadTargetImpl;
import org.roof.spring.CurrentSpringContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.config.ExpressionFactoryBean;
import org.springframework.integration.file.DefaultFileNameGenerator;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.remote.RemoteFileOperations;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import com.alibaba.fastjson.JSON;

public class RoofUploadFileLoacl implements MessageHandler,InitializingBean {
	private static final Logger LOG = Logger.getLogger(RoofUploadFileLoacl.class);

	
	
	private FileWritingMessageHandler fileWritingMessageHandler;


	public void handleMessage(Message<?> message) throws MessagingException {
		LOG.debug(message);
	
		//UploadTarget test = new UploadTargetImpl(UploadType.SFTP, "192.168.159.149", "zz3310969", "3310969", 22,"E:/spring-integration-samples/output","");
		//System.out.println(JSON.toJSON(test));;
		//UploadFile<?> testfiel = new UploadFileImpl<Object>(test, "user/index", message.getPayload(), "test", UploadFileOperate.REPLACE);
		UploadFile<?> uploadFile = (UploadFile<?>) message.getHeaders().get("uploadfile");
		UploadTarget uploadTarget = uploadFile.getTarget();
		String[] hosts = uploadTarget.getHosts().split(",");
		for(String host :hosts){
			createHandler(uploadTarget, host).handleMessage(message);
			//ftpRemoteFileTemplate.send(message, uploadFile.getFileDirectory(), FileExistsMode.getForString(uploadFile.getOperate().name()));
		}
		
		
	}

	public void afterPropertiesSet() throws Exception {
		
	}
	
	protected FileWritingMessageHandler createHandler(UploadTarget uploadTarget,String host) {
		final FileWritingMessageHandler handler;
		
		handler = new FileWritingMessageHandler(new File(uploadTarget.getRemoteDirectory()));
	    handler.setExpectReply(false);
	    handler.setAutoCreateDirectory(true);
	    
	    
	    DefaultFileNameGenerator Generator = new DefaultFileNameGenerator();
	    Generator.setBeanFactory(CurrentSpringContext.getCurrentContext());
	    		
	    
	    
	    handler.setFileNameGenerator(Generator);
	    
		/*BeanDefinitionBuilder templateBuilder = BeanDefinitionBuilder.genericBeanDefinition(FileWritingMessageHandler.class);
		
		
		Expression directory = new LiteralExpression(uploadTarget.getRemoteDirectory());
		handler = new FileWritingMessageHandler(directory);
		handler.setAutoCreateDirectory(true);
		
		templateBuilder.addConstructorArgValue(directory);
		templateBuilder.addPropertyValue("autoCreateDirectory", true);
		templateBuilder.addPropertyValue("expectReply", false);
		
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) CurrentSpringContext.getCurrentContext();
        BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) configurableApplicationContext
                .getBeanFactory();
        beanDefinitonRegistry.registerBeanDefinition("fileWritingMessageHandler", templateBuilder.getBeanDefinition());
        //beanDefinitonRegistry.removeBeanDefinition(beanName);
        handler = CurrentSpringContext.getBean("fileWritingMessageHandler", FileWritingMessageHandler.class);*/
		return handler;
	}
	
	

}
