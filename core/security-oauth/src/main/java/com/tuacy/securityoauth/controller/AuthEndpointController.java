package com.tuacy.securityoauth.controller;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @name: CheckTokenController
 * @author: tuacy.
 * @date: 2019/12/7.
 * @version: 1.0
 * @Description: 获取当前认证用户的信息
 */
@RestController
public class AuthEndpointController {

    /**
     * 用于给第三方应用校验权限使用(spring cloud security)
     * security:
     *   oauth2:
     *     resource:
     *       user-info-uri: http://127.0.0.1:8101/security-service/auth/user
     */
    @RequestMapping(value = {"/auth/user"}, produces = "application/json")
    public Map<String, Object> user(OAuth2Authentication user) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user", user.getUserAuthentication().getPrincipal());
        userInfo.put("authorities", AuthorityUtils.authorityListToSet(user.getUserAuthentication().getAuthorities()));
        return userInfo;
    }

}
