package com.zhishouwei.platform.cloud.auth.server;

import com.alibaba.fastjson.JSONObject;
import com.zhishouwei.common.model.AuthUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "${authserver.load-service:auth-service}",
        fallback = UserServiceFallbackImpl.class
)
public interface UserService {
    @PostMapping({"/account/findUserByUsername"})
    AuthUser findUserByUsername(@RequestParam("username") String name);

    @PostMapping({"/account/findUserByMobile"})
    AuthUser findUserByMobile(@RequestParam("mobile") String phone);

    @PostMapping({"/account/findUserByOpenId"})
    AuthUser findUserByOpenId(@RequestParam("openId") String openId);

    @PostMapping({"/account/findUserByMobileAndCode"})
    AuthUser findUserByMobileAndCode(@RequestParam("mobile") String mobile, @RequestParam("code") String code);
}

