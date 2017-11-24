package com.roof.demo.activity.service.impl;

import java.io.Serializable;
import java.util.List;
import org.roof.roof.dataaccess.api.Page;
import com.roof.demo.activity.dao.api.IActivityDao;
import com.roof.demo.activity.entity.Activity;
import com.roof.demo.activity.entity.ActivityVo;
import com.roof.demo.activity.service.api.IActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ActivityService implements IActivityService {
	private IActivityDao activityDao;

	public Serializable save(Activity activity){
		return activityDao.save(activity);
	}

	public void delete(Activity activity){
		activityDao.delete(activity);
	}
	
	public void deleteByExample(Activity activity){
		activityDao.deleteByExample(activity);
	}

	public void update(Activity activity){
		activityDao.update(activity);
	}
	
	public void updateIgnoreNull(Activity activity){
		activityDao.updateIgnoreNull(activity);
	}
		
	public void updateByExample(Activity activity){
		activityDao.update("updateByExampleActivity", activity);
	}

	public ActivityVo load(Activity activity){
		return (ActivityVo)activityDao.reload(activity);
	}
	
	public ActivityVo selectForObject(Activity activity){
		return (ActivityVo)activityDao.selectForObject("selectActivity",activity);
	}
	
	public List<ActivityVo> selectForList(Activity activity){
		return (List<ActivityVo>)activityDao.selectForList("selectActivity",activity);
	}
	
	public Page page(Page page, Activity activity) {
		return activityDao.page(page, activity);
	}

	@Autowired
	public void setIActivityDao(
			@Qualifier("activityDao") IActivityDao  activityDao) {
		this.activityDao = activityDao;
	}
	

}
