package org.roof.node.jobs.writer;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.roof.file.upload.integration.api.UploadFile;
import org.roof.file.upload.integration.api.UploadFileOperate;
import org.roof.file.upload.integration.api.UploadFileServiceI;
import org.roof.file.upload.integration.api.UploadTarget;
import org.roof.file.upload.integration.api.UploadType;
import org.roof.file.upload.integration.impl.UploadFileImpl;
import org.roof.file.upload.integration.impl.UploadTargetImpl;
import org.roof.spring.CurrentSpringContext;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;

/**
 * 将分页写入通道 <br>
 * job启动参数:<br>
 * outChannel 写出频道 <br>
 * 
 * @author liuxin
 *
 */
public class ChannelPageItemWriter implements ItemWriter<Map> {

	private StepExecution stepExecution;

	private UploadFileServiceI uploadFileService;

	@Override
	public void write(List<? extends Map> items) throws Exception {
		String html = "<p>Hello, my name is Alan.Today is . I am from Somewhere, TX. I have  kids:</p><ul><li>1:Jimmy is 12</li><li>2:Sally is 4</li></ul>"
				+ "<h1>reuse templete</h1>" + "<p>Home page</p>" + "<span>Powered by Handlebars.java</span>";

		UploadTarget target = new UploadTargetImpl(UploadType.SFTP, "10.80.19.204", "root", "zjhcroot", 22, "soft",
				"/");

		UploadFile<?> file = new UploadFileImpl(target, "", html, "xxx.html", UploadFileOperate.REPLACE);
		if(uploadFileService == null){
			uploadFileService = CurrentSpringContext.getBean("uploadFileService", UploadFileServiceI.class);
		}
		uploadFileService.Upload(file);
		// String customOutChannelName =
		// stepExecution.getJobParameters().getString("outChannel");
		// MessageChannel customOutChannel = null;
		// if (StringUtils.isNotEmpty(customOutChannelName)) {
		// customOutChannel = CurrentSpringContext.getBean(customOutChannelName,
		// MessageChannel.class);
		// }

		// for (Page page : items) {
		// Message<Page> message = new GenericMessage<Page>(page);
		// if (customOutChannel != null) {
		// customOutChannel.send(message);
		// } else {
		// outChannel.send(message);
		// }
		// }
	}

	@Resource(name = "uploadFileService")
	public void setUploadFileService(UploadFileServiceI uploadFileService) {
		this.uploadFileService = uploadFileService;
	}

	@BeforeStep
	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

}
