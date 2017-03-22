package org.pac4j.demo.shiro;

import io.buji.pac4j.filter.CallbackFilter;
import io.buji.pac4j.profile.ShiroProfileManager;

/**
 * Created by mason on 17-3-22.
 *
 */
public class Pac4jCallbackFilter extends CallbackFilter {

    public Pac4jCallbackFilter(Pac4jCallbackLogic callbackLogic) {
        setCallbackLogic(callbackLogic);
        callbackLogic.setProfileManagerFactory(ShiroProfileManager::new);
    }
}
