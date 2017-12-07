package org.roof.web.cache.action;

import com.google.common.collect.Lists;
import org.roof.spring.Result;
import org.roof.web.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuxin
 */
@Controller
public class CacheController {

    private CacheManager cacheManager;

    @RequestMapping(value = "cache", method = {RequestMethod.GET})
    public @ResponseBody
    Result list() {
        Collection<String> cacheNames = cacheManager.getCacheNames();
        List<Map<String,String>> list = Lists.newArrayList();
        cacheNames.stream().forEach(c -> {
            Map<String,String> map = new HashMap<String,String>(1);
            map.put("cacheName",c);
            list.add(map);
        });
        return new Result(Result.SUCCESS, list);
    }

    @RequestMapping(value = "cache/{cacheName}", method = {RequestMethod.DELETE})
    public @ResponseBody
    Result delete(@PathVariable String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        cache.clear();
        return new Result("清除成功！");
    }

    @Autowired
    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

}
