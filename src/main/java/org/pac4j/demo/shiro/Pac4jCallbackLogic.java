package org.pac4j.demo.shiro;

import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.engine.DefaultCallbackLogic;
import org.pac4j.core.exception.HttpAction;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import static org.pac4j.core.util.CommonHelper.isNotBlank;

/**
 * Created by mason on 17-3-22.
 *
 */
public class Pac4jCallbackLogic extends DefaultCallbackLogic<Object, J2EContext> implements InitializingBean {

    private CacheManager cacheManager;
    private Cache urlCache;

    @Override
    protected HttpAction redirectToOriginallyRequestedUrl(J2EContext context, String defaultUrl) {
        //final String requestedUrl = (String) context.getSessionAttribute(Pac4jConstants.REQUESTED_URL);
        String requestedUrl = null;
        String sessionId = null;
        if (urlCache != null) {
            sessionId = context.getRequest().getRequestedSessionId();
            requestedUrl = urlCache.get(sessionId, String.class);
        }
        String redirectUrl = defaultUrl;
        if (isNotBlank(requestedUrl)) {
            //context.setSessionAttribute(Pac4jConstants.REQUESTED_URL, null);
            urlCache.evict(sessionId);
            redirectUrl = requestedUrl;
        }
        logger.debug("redirectUrl: {}", redirectUrl);
        return HttpAction.redirect("redirect", context, redirectUrl);
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
