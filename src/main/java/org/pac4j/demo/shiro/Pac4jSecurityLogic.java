package org.pac4j.demo.shiro;

import org.pac4j.core.client.Client;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.engine.DefaultSecurityLogic;
import org.pac4j.core.exception.HttpAction;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.List;

/**
 * Created by mason on 17-3-22.
 *
 */
public class Pac4jSecurityLogic extends DefaultSecurityLogic<Object, J2EContext> implements InitializingBean {

    private CacheManager cacheManager;
    private Cache urlCache;

    @Override
    protected void saveRequestedUrl(J2EContext context, List<Client> currentClients) throws HttpAction {
        final String requestedUrl = context.getFullRequestURL();
        logger.debug("requestedUrl: {}", requestedUrl);
        //context.setSessionAttribute(Pac4jConstants.REQUESTED_URL, requestedUrl);

        if (urlCache != null) {
            urlCache.put(context.getRequest().getRequestedSessionId(), requestedUrl);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (cacheManager != null) {
            urlCache = cacheManager.getCache(Pac4jCasClient.REQUESTED_URL_CACHE_NAME);
        }
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
