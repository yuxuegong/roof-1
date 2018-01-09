package org.roof.web.user.service.api;

import org.roof.web.resource.entity.Resource;
import org.springframework.security.web.FilterInvocation;

/**
 * @author <a href="mailto:liuxin@zjhcsoft.com">liuxin</a>
 * @version 1.0 InvocationSecurityMetadataSourceService.java 2012-7-5
 */
public class InvocationSecurityMetadataSourceService extends AbstractSecurityMetadataSourceService {

    @Override
    protected String createResourcePattern(Resource resource) {
        if (resource == null) {
            return null;
        }
        return resource.getPattern();
    }

    @Override
    protected String getRequestURL(Object object) {
        if (object == null) {
            return null;
        }
        String url = null;
        if (object instanceof FilterInvocation) {
            FilterInvocation filterInvocation = (FilterInvocation) object;
            url = filterInvocation.getRequestUrl();
        }
        if (object instanceof String) {
            url = object.toString();
        }
        return url;
    }
}
