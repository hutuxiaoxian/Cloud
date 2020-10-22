package com.zhishouwei.platform.cloud.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhishouwei.platform.cloud.auth.server.AccountService;
import com.zhishouwei.platform.cloud.auth.entity.OauthClientDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("uaa")
public class AccessUserController {

    @Value("${auth.clientId}")
    private String user;
    @Value("${auth.password}")
    private String passwd;

    @Resource
    private AccountService service;

    @RequestMapping(value = "checkClientId", method = {RequestMethod.GET,RequestMethod.POST})
    public JSONObject checkClientId(@RequestParam("clientId") String clientId) {
        JSONObject resp = new JSONObject();
        if (clientId == null || clientId.length() > 20 || clientId.length() < 5) {
            resp.put("code", 400);
            resp.put("message", "clientId不合法，请上传5-20位的clientId");
        }
        boolean chencked = service.checkClientId(clientId);
        if (chencked) {
            resp.put("code", 200);
            resp.put("message", "clientId可以使用");
        } else {
            resp.put("code", 300);
            resp.put("message", "clientId已存在，请尝试使用新的clientId");
        }
        return resp;
    }

    @RequestMapping(value = "applyDeveloper", method = {RequestMethod.POST})
    public JSONObject applyDeveloper(@RequestBody OauthClientDetails details) {
        JSONObject resp = new JSONObject();
        if (details != null) {
            if (details.getClientId() == null
                    || details.getClientId().length() > 20
                    || details.getClientId().length() < 5) {
                resp.put("code", 401);
                resp.put("message", "clientId不合法，请上传5-20位的clientId");
            } else if (details.getClientSecret() == null
                    || details.getClientSecret().length() == 0){
                resp.put("code", 402);
                resp.put("message", "clientSecret不合法，请上传clientSecret");
            } else if (details.getWebServerRedirectUri() == null
                    || details.getWebServerRedirectUri().length() == 0
                    || !details.getWebServerRedirectUri().startsWith("http")) {
                resp.put("code", 403);
                resp.put("message", "RedirectUri不合法，RedirectUri 必需是一个合法的http或https请求。以http://或https://开头");
            } else {
                boolean isOK = service.insert(details);
                if (isOK) {
                    resp.put("code", 200);
                    resp.put("message", "数据申请成功");
                } else {
                    resp.put("code", 401);
                    resp.put("message", "数据申请失败");
                }
            }
        } else {
            resp.put("code", 400);
            resp.put("message", "无效的数据请求");
        }
        return resp;
    }

}
