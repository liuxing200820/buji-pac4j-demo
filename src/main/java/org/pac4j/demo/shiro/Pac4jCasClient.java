package org.pac4j.demo.shiro;

import org.pac4j.cas.client.CasClient;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.redirect.RedirectAction;
import org.pac4j.core.util.CommonHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * Created by mason on 17-3-22.
 *
 */
public class Pac4jCasClient extends CasClient implements InitializingBean {

    public static final String REQUESTED_URL_CACHE_NAME = "Pac4jRequestedUrlCache";

    private CacheManager cacheManager;
    private Cache urlCache;

    @Override
    public RedirectAction getRedirectAction(WebContext context) throws HttpAction {
        init(context);
        // it's an AJAX request -> unauthorized (instead of a redirection)
        if (getAjaxRequestResolver().isAjax(context)) {
            logger.info("AJAX request detected -> returning 401");
            cleanRequestedUrl(context);
            throw HttpAction.unauthorized("AJAX request -> 401", context, null);
        }
        // authentication has already been tried -> unauthorized
        final String attemptedAuth = (String) context.getSessionAttribute(getName() + ATTEMPTED_AUTHENTICATION_SUFFIX);
        if (CommonHelper.isNotBlank(attemptedAuth)) {
            cleanAttemptedAuthentication(context);
            cleanRequestedUrl(context);
            throw HttpAction.unauthorized("authentication already tried -> forbidden", context, null);
        }

        return getRedirectActionBuilder().redirect(context);
    }

    private void cleanRequestedUrl(final WebContext context) {
        //context.setSessionAttribute(Pac4jConstants.REQUESTED_URL, "");
        if (urlCache != null) {
            if (context instanceof J2EContext) {
                urlCache.evict(((J2EContext) context).getRequest().getRequestedSessionId());
            }
        }
    }

    private void cleanAttemptedAuthentication(final WebContext context) {
        context.setSessionAttribute(getName() + ATTEMPTED_AUTHENTICATION_SUFFIX, "");
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
