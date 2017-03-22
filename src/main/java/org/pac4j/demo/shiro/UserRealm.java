package org.pac4j.demo.shiro;

import io.buji.pac4j.realm.Pac4jRealm;
import io.buji.pac4j.subject.Pac4jPrincipal;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created by mason on 17-3-16.
 *
 */
public class UserRealm extends Pac4jRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        AuthenticationInfo info = super.doGetAuthenticationInfo(authenticationToken);
        /*if (info != null) {
            PrincipalCollection principals = info.getPrincipals();
            if (principals != null) {
                Pac4jPrincipal principal = principals.oneByType(Pac4jPrincipal.class);
                if (principal != null) {
                    return new SimpleAuthenticationInfo(principal.getProfile().getId(), principal.hashCode(), getName());
                }
            }
        }*/
        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals != null) {
            Pac4jPrincipal principal = principals.oneByType(Pac4jPrincipal.class);
            if (principal != null) {
                String id = principal.getProfile().getId();
            }
        }
        return super.doGetAuthorizationInfo(principals);
    }
}
