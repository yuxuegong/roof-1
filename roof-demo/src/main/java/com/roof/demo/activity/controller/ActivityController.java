package com.roof.demo.activity.controller;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageUtils;
import org.roof.spring.Result;
import com.roof.demo.activity.entity.Activity;
import com.roof.demo.activity.entity.ActivityVo;
import com.roof.demo.activity.service.api.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("vote")
public class ActivityController {
	private IActivityService activityService;




    @RequestMapping(value = "activity", method = {RequestMethod.GET})
    public @ResponseBody Result<Page> list(Activity activity, HttpServletRequest request, Model model) {
	    Page page = PageUtils.createPage(request);
	    page = activityService.page(page, activity);
	    return new Result(Result.SUCCESS, page);
	}
	


	@RequestMapping(value = "activity", method = {RequestMethod.POST})
	public @ResponseBody Result create(Activity activity) {
		if (activity != null) {
			activityService.save(activity);
			return new Result("保存成功!");
		} else {
			return new Result(Result.FAIL,"数据传输失败!");
		}
	}
	
	@RequestMapping(value = "activity", method = {RequestMethod.PUT})
	public @ResponseBody Result update(Activity activity) {
		if (activity != null) {
			activityService.updateIgnoreNull(activity);
			return new Result("保存成功!");
		} else {
			return new Result(Result.FAIL,"数据传输失败!");
		}
	}
	
	@RequestMapping(value = "activity", method = {RequestMethod.DELETE})
	public @ResponseBody Result delete(Activity activity) {
		// TODO 有些关键数据是不能物理删除的，需要改为逻辑删除
		activityService.delete(activity);
		return new Result("删除成功!");
	}

	@Autowired(required = true)
	public void setActivityService(
			@Qualifier("activityService") IActivityService activityService) {
		this.activityService = activityService;
	}


}
