package org.roof.file.upload.integration.service.ftp;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.roof.file.upload.integration.api.UploadFile;
import org.roof.file.upload.integration.api.UploadTarget;
import org.roof.spring.CurrentSpringContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.file.remote.RemoteFileOperations;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

public class RoofUploadFileFtp implements MessageHandler,InitializingBean {
	private static final Logger LOG = Logger.getLogger(RoofUploadFileFtp.class);

	
	Map<String, RemoteFileOperations<?>> remoteFileOperationmaps = new HashMap<String,RemoteFileOperations<?>>();
	


	public void handleMessage(Message<?> message) throws MessagingException {
		LOG.debug(message);
	
		//UploadTarget test = new UploadTargetImpl(UploadType.Sftp, "192.168.159.149", "zz3310969", "3310969", 22);
		//UploadFile<?> testfiel = new UploadFileImpl<Object>(test, "", message.getPayload(), "test", FileExistsMode.REPLACE);
		UploadFile<?> uploadFile = (UploadFile<?>) message.getHeaders().get("uploadfile");
		UploadTarget uploadTarget = uploadFile.getTarget();
		String[] hosts = uploadTarget.getHosts().split(",");
		for(String host :hosts){
			if(!remoteFileOperationmaps.containsKey(createBeanIdByHost(host))){
				putRemoteFileOperations(uploadTarget, host);
			}
			RemoteFileOperations<?> ftpRemoteFileTemplate = remoteFileOperationmaps.get(createBeanIdByHost(host));
			
			ftpRemoteFileTemplate.send(message, uploadFile.getFileDirectory(), uploadFile.getMode());
			
		}
		
		
	}

	public void afterPropertiesSet() throws Exception {
		
	}
	
	private void putRemoteFileOperations(UploadTarget uploadTarget,String host) {
		DefaultFtpSessionFactory defaultftpSessionFactory= new DefaultFtpSessionFactory();
		
		defaultftpSessionFactory.setHost(host);
		defaultftpSessionFactory.setUsername(uploadTarget.getUser());
		defaultftpSessionFactory.setPassword(uploadTarget.getPassword());
		defaultftpSessionFactory.setPort(uploadTarget.getPort());
		
		BeanDefinitionBuilder templateBuilder = BeanDefinitionBuilder.genericBeanDefinition(FtpRemoteFileTemplate.class);

		templateBuilder.addConstructorArgValue(defaultftpSessionFactory);
		
		Expression directory = new LiteralExpression("/home/zz3310969");
		templateBuilder.addPropertyValue("remoteDirectoryExpression", directory);
		templateBuilder.addPropertyValue("remoteFileSeparator", "/");
		
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) CurrentSpringContext.getCurrentContext();
        BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) configurableApplicationContext
                .getBeanFactory();
        beanDefinitonRegistry.registerBeanDefinition(createBeanIdByHost(host), templateBuilder.getBeanDefinition());
        
        remoteFileOperationmaps.put(createBeanIdByHost(host), CurrentSpringContext.getBean(createBeanIdByHost(host), RemoteFileOperations.class));
	}
	
	private String createBeanIdByHost(String host) {
		host = "ftp"+host.replace(".", "");
		return host;
	}

}
