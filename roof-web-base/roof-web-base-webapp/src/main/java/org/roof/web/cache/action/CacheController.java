package org.roof.web.cache.action;

import org.roof.spring.Result;
import org.roof.web.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author liuxin
 *
 */
@Controller
@RequestMapping("cache")
public class CacheController {

	private CacheManager cacheManager;

	@RequestMapping(method = {RequestMethod.GET})
	public @ResponseBody Result list() {
		return new Result(Result.SUCCESS,cacheManager.getCacheNames());
	}

	@RequestMapping(method = {RequestMethod.DELETE})
	public @ResponseBody Result delete(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		cache.clear();
		return new Result("清除成功！");
	}

	@Autowired
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

}
