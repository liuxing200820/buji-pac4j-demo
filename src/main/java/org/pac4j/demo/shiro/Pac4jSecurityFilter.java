package org.pac4j.demo.shiro;

import io.buji.pac4j.filter.SecurityFilter;
import io.buji.pac4j.profile.ShiroProfileManager;

/**
 * Created by mason on 17-3-22.
 *
 */
public class Pac4jSecurityFilter extends SecurityFilter {

    public Pac4jSecurityFilter(Pac4jSecurityLogic securityLogic) {
        setSecurityLogic(securityLogic);
        securityLogic.setProfileManagerFactory(ShiroProfileManager::new);
    }
}
