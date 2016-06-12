package org.roof.file.upload.integration.service.sftp;

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
import org.springframework.integration.file.remote.RemoteFileOperations;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpRemoteFileTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.util.HashMap;
import java.util.Map;

public class RoofUploadFileSFtp implements MessageHandler,InitializingBean {
	private static final Logger LOG = Logger.getLogger(RoofUploadFileSFtp.class);

	
	Map<String, RemoteFileOperations<?>> remoteFileOperationmaps = new HashMap<String,RemoteFileOperations<?>>();
	


	public void handleMessage(Message<?> message) throws MessagingException {
		LOG.debug(message);
	
		//UploadTarget test = new UploadTargetImpl(UploadType.Sftp, "192.168.159.149", "zz3310969", "3310969", 22,"","");
		//UploadFile<?> testfiel = new UploadFileImpl<Object>(test, "1", message.getPayload(), "test", UploadFileOperate.REPLACE);
		UploadFile<?> uploadFile = (UploadFile<?>) message.getHeaders().get("uploadfile");
		UploadTarget uploadTarget = uploadFile.getTarget();
		String[] hosts = uploadTarget.getHosts().split(",");
		for(String host :hosts){
			if(!remoteFileOperationmaps.containsKey(createBeanIdByHost(host))){
				putRemoteFileOperations(uploadTarget, host);
			}
			RemoteFileOperations<?> sftpRemoteFileTemplate = remoteFileOperationmaps.get(createBeanIdByHost(host));
			//if(UploadFileOperate.REMOVE.equals(uploadFile.getOperate()))

            UploadFileOperate op = uploadFile.getOperate();
            switch (op){
                case REMOVE :sftpRemoteFileTemplate.remove(uploadFile.getFileDirectory()); break;
                default:sftpRemoteFileTemplate.send(message, uploadFile.getFileDirectory(), FileExistsMode.getForString(uploadFile.getOperate().name()));
            }

		}
		
		
	}

	public void afterPropertiesSet() throws Exception {
		
	}
	
	private void putRemoteFileOperations(UploadTarget uploadTarget,String host) {
		DefaultSftpSessionFactory defaultSftpSessionFactory= new DefaultSftpSessionFactory();
		
		defaultSftpSessionFactory.setHost(host);
		defaultSftpSessionFactory.setUser(uploadTarget.getUser());
		defaultSftpSessionFactory.setPassword(uploadTarget.getPassword());
		defaultSftpSessionFactory.setPort(uploadTarget.getPort());
		
		BeanDefinitionBuilder templateBuilder = BeanDefinitionBuilder.genericBeanDefinition(SftpRemoteFileTemplate.class);

		templateBuilder.addConstructorArgValue(defaultSftpSessionFactory);
		
		Expression directory = new LiteralExpression(uploadTarget.getRemoteDirectory());
		templateBuilder.addPropertyValue("remoteDirectoryExpression", directory);
		templateBuilder.addPropertyValue("remoteFileSeparator", uploadTarget.getRemoteFileSeparator());
		templateBuilder.addPropertyValue("autoCreateDirectory",true);
		
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) CurrentSpringContext.getCurrentContext();
        BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) configurableApplicationContext
                .getBeanFactory();
        beanDefinitonRegistry.registerBeanDefinition(createBeanIdByHost(host), templateBuilder.getBeanDefinition());
        
        remoteFileOperationmaps.put(createBeanIdByHost(host), CurrentSpringContext.getBean(createBeanIdByHost(host), RemoteFileOperations.class));
	}
	
	private String createBeanIdByHost(String host) {
		host = "sftp"+host.replace(".", "");
		return host;
	}

}
