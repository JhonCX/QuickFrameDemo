package com.cc.ata.shiro;

import com.cc.ata.entity.QUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

/*更新shiro缓存 当传入的不是email 而是QUser对象时使用*/
public class ShiroUtils {
    public static Subject getSubjct(){
        return SecurityUtils.getSubject();
    }

    public static QUser getUser(){
        return (QUser) getSubjct().getPrincipal();
    }
    public static void setUser(QUser user) {
        Subject subject = SecurityUtils.getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection =
            new SimplePrincipalCollection(user, realmName);
        subject.runAs(newPrincipalCollection);
    }
}
