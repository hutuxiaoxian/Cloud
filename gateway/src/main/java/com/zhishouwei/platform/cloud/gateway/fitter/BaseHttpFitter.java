package com.zhishouwei.platform.cloud.gateway.fitter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
public class BaseHttpFitter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        log.info("ServerWebExchange");
        String url = exchange.getRequest().getURI().toString();
        String headers = exchange.getRequest().getHeaders().toString();
        String method = exchange.getRequest().getMethodValue();
        MultiValueMap<String, String> parma = exchange.getRequest().getQueryParams();
        byte[] arr = exchange.getResponse().bufferFactory().allocateBuffer().asByteBuffer().array();
        String strResp = new String(arr);

        log.info("=========== HTTP Request {} ===========\nURL:{}\nHeader:{}\nParma:{}\n==================\nResponse:{}", method, url, headers, parma, strResp);
        return chain.filter(exchange);
    }

//    @Override
//    public ShortcutType shortcutType() {
//        return null;
//    }
//
//    @Override
//    public List<String> shortcutFieldOrder() {
//        return null;
//    }

//    @Override
//    public String shortcutFieldPrefix() {
//        return "BaseHttp";
//    }

}
