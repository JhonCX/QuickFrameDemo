package com.cc.ata.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cc.ata.constant.Constant;
import com.cc.ata.entity.QUser;
import com.cc.ata.service.IQUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author :cc
 * @date : 2019/6/10
 */
public class CustomRealm extends AuthorizingRealm {
    private String ClassName = this.getClass().getName();

    @Autowired
    @Lazy   //必须懒加载，否则Ehcache缓存注解及事务管理注解无效
    private IQUserService userService;

    {
        super.setName(ClassName);
    }

    /**
     * 权限处理
     *
     * @param principals 用户名
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Long id = (Long) principals.getPrimaryPrincipal();
        QUser user = userService.getById(id);
        // 从数据库或者缓存中获得角色数据 此处简化
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        if (Objects.equals(Constant.ROLE_ADMIN, user.getType())) {
            //如果是管理员
            roles.add(Constant.ROLE_ADMIN);
            roles.add(Constant.ROLE_USER);
        } else if (Objects.equals(Constant.ROLE_USER, user.getType())) {
            //为0 是普通用户
            roles.add(Constant.ROLE_USER);
        }
        //上面的service层方法需要自己写
        //permissions简化 只分用户类型
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证处理
     *
     * @param token 凭证
     * @throws AuthenticationException 异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 1.从主体传过来的认证信息中，获得用户名
        String email = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials()).toLowerCase();
        QUser user;
        // 2.通过用户名到数据库中获取凭证
        user = userService.getOne(new QueryWrapper<>(new QUser().setEmail(email)));
        if (null == user) {
            throw new UnknownAccountException();
        }

        if (!Objects.equals(password, user.getPwd())) {
            throw new CredentialsException();
        }
        /*禁止登录时*/
        if (Objects.equals(Constant.LOGIN_FORBID, user.getFlag())) {
            throw new LockedAccountException();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setLoginTime(sdf.format(new Date()));

        boolean b = userService.updateById(user);
        if (!b) {
            throw new RuntimeException();
        }
        return new SimpleAuthenticationInfo(user.getId(), password, ClassName);
    }
}