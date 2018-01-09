package org.roof.web.user.service.api;

import org.apache.commons.lang3.StringUtils;
import org.roof.web.resource.entity.Resource;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.UrlUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 基于restful API 请求安全数据源服务
 */
public class RestfulSecurityMetadataSourceService extends AbstractSecurityMetadataSourceService {
    private static final String METHOD_PATTERN_SPLITER = " ";

    @Override
    protected String createResourcePattern(Resource resource) {
        if (resource == null || StringUtils.isBlank(resource.getPattern())) {
            return null;
        }
        String method = resource.getMethod();
        if (StringUtils.isBlank(method)) {
            method = ALL_METHOD;
        }
        return method + METHOD_PATTERN_SPLITER + resource.getPattern();
    }

    @Override
    protected String getRequestURL(Object object) {
        if (object == null) {
            return null;
        }
        String url = null;
        if (object instanceof FilterInvocation) {
            FilterInvocation filterInvocation = (FilterInvocation) object;
            HttpServletRequest request = filterInvocation.getHttpRequest();
            String method = getRequestMethod(request);
            url = method + METHOD_PATTERN_SPLITER + UrlUtils.buildRequestUrl(request);
        }
        if (object instanceof String) {
            url = DEFAULT_METHOD + METHOD_PATTERN_SPLITER + object;
        }
        return url;
    }
}
