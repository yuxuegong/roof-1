package com.roof.demo.activity.dao.api;

import org.roof.roof.dataaccess.api.IDaoSupport;
import org.roof.roof.dataaccess.api.Page;

import com.roof.demo.activity.entity.Activity;

public interface IActivityDao extends IDaoSupport {
	Page page(Page page, Activity activity);
}