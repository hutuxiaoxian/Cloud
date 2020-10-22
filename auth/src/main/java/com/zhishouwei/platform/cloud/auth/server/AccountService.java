package com.zhishouwei.platform.cloud.auth.server;

import com.zhishouwei.platform.cloud.auth.entity.OauthClientDetails;
import com.zhishouwei.platform.cloud.auth.mapper.AccountMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountService {

    @Resource
    private AccountMapper mapper;

    public boolean checkClientId(String clientId) {
        boolean checked = false;
        OauthClientDetails item = mapper.findByClientId(clientId);
        if (item == null) {
            checked = true;
        }
        return checked;
    }

    public boolean insert(OauthClientDetails details) {
        details.setAuthorizedGrantTypes(details.getAuthorizedGrantTypes().replace(" ", ""));
        return mapper.insert(details) > 0 ? true : false;
    }
}
