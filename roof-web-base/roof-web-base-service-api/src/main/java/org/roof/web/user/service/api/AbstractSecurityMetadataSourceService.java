package org.roof.web.user.service.api;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.roof.web.resource.dao.api.IResourceDao;
import org.roof.web.resource.entity.Resource;
import org.roof.web.role.dao.api.IRoleDao;
import org.roof.web.role.entity.BaseRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.*;

public abstract class AbstractSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    protected static final Logger LOGGER = LoggerFactory.getLogger(InvocationSecurityMetadataSourceService.class);

    protected static final String DEFAULT_RESOURCE_MAP_KEY = "resource_map";
    protected static final String DEFAULT_CACHE_NAME = "MetadataSourceService#loadResourceDefine";
    protected static final String ALL_PATH_PATTERN = "/**/*";
    protected static final String ALL_PATH_ROLE = "ROLE_-1";
    protected IResourceDao resourceDao;
    protected IRoleDao roleDao;

    protected CacheManager cacheManager;
    protected PathMatcher pathMatcher = new AntPathMatcher();

    protected Map<String, Collection<ConfigAttribute>> resourceMap = null;
    protected String cacheName = DEFAULT_CACHE_NAME;
    protected String resourceMapKey = DEFAULT_RESOURCE_MAP_KEY;

    /**
     * 加载权限数据到内存
     */
    private void loadResourceDefine() {
        this.resourceMap = null;
        this.resourceMap = loadFromCache();
        if (resourceMap != null) {
            return;
        }
        synchronized (this) {
            if (resourceMap != null) {
                return;
            }
            LOGGER.debug("load resource map start");
            loadFromDatabase();
            addDefaultPermission();
            cacheManager.getCache(cacheName).put(resourceMapKey, resourceMap);
            LOGGER.debug("permission load success");
        }
    }


    @SuppressWarnings("unchecked")
    private Map<String, Collection<ConfigAttribute>> loadFromCache() {
        Cache cache = cacheManager.getCache(cacheName);
        Cache.ValueWrapper valueWrapper = cache.get(resourceMapKey);
        if (valueWrapper != null) {
            return (Map<String, Collection<ConfigAttribute>>) valueWrapper.get();
        }
        return null;
    }

    private void loadFromDatabase() {
        this.resourceMap = new HashMap<>();
        List<Resource> resources = resourceDao.loadAll();
        for (Resource resource : resources) {
            String resourcePattern = createResourcePattern(resource);
            if (StringUtils.isEmpty(resourcePattern)) {
                continue;
            }
            Collection<ConfigAttribute> configAttributes;
            if (resourceMap.containsKey(resourcePattern)) {
                configAttributes = resourceMap.get(resourcePattern);
            } else {
                configAttributes = new ArrayList<>();
            }
            for (BaseRole role : roleDao.selectByResource(resource.getId())) {
                configAttributes.add(new SecurityConfig(role.getAuthority()));
                LOGGER.debug("role：[" + role.getAuthority() + "] has [" + resource.getId()
                        + " " + createResourcePattern(resource) + "] permission");
            }
            resourceMap.put(resourcePattern, configAttributes);
        }
    }

    /**
     * 将resource转换为 请求匹配规则
     *
     * @param resource 资源对象
     * @return 匹配规则
     */
    protected abstract String createResourcePattern(Resource resource);

    private void addDefaultPermission() {
        Collection<ConfigAttribute> configAttributes = resourceMap.get(ALL_PATH_PATTERN);
        if (configAttributes == null) {
            configAttributes = new ArrayList<>();
        }
        configAttributes.add(new SecurityConfig(ALL_PATH_ROLE));
        resourceMap.put(ALL_PATH_PATTERN, configAttributes);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        loadResourceDefine();
        Collection<ConfigAttribute> result = new ArrayList();
        for (Collection<ConfigAttribute> configAttribute : resourceMap.values()) {
            result.addAll(configAttribute);
        }
        return result;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (object == null) {
            return null;
        }
        String url = getRequestURL(object);
        Collection<ConfigAttribute> result = null;
        Cache cache = cacheManager.getCache(cacheName);
        Cache.ValueWrapper valueWrapper = cache.get(url);
        if (valueWrapper != null) {
            result = (Collection<ConfigAttribute>) valueWrapper.get();
        }
        if (result != null) {
            return result;
        }
        loadResourceDefine();
        result = new ArrayList<>();
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String pattern = ite.next();
            if (pathMatcher.match(pattern, url)) {
                Collection<ConfigAttribute> configAttrs = resourceMap.get(pattern);
                if (CollectionUtils.isNotEmpty(configAttrs)) {
                    result.addAll(configAttrs);
                }
            }
        }
        cache.put(url, result);
        return result;
    }

    /**
     * 获得请求的url
     *
     * @param object the object being secured
     * @return request url
     */
    protected abstract String getRequestURL(Object object);

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public IResourceDao getResourceDao() {
        return resourceDao;
    }

    public void setResourceDao(IResourceDao resourceDao) {
        this.resourceDao = resourceDao;
    }

    public IRoleDao getRoleDao() {
        return roleDao;
    }

    public void setRoleDao(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public PathMatcher getPathMatcher() {
        return pathMatcher;
    }

    public void setPathMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }
}
