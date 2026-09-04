package com.haijinhan.gateway.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Principal;

@Configuration
@EnableWebFluxSecurity
public class GatewayExtendedConfig {

    /**
     * 1. 安全配置：OAuth2 Resource Server + 自定义 401 异常
     */
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/actuator/**", "/auth/**", "/login/**", "/oauth2/**").permitAll()
                .anyExchange().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor()))
            )
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint((exchange, ex) -> {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
                    String body = "{\"code\":401,\"message\":\"Token is invalid or expired\"}";
                    DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
                    return response.writeWith(Mono.just(buffer));
                })
            );
        return http.build();
    }

    /**
     * 2. JWT 权限提取器
     */
    @Bean
    public ReactiveJwtAuthenticationConverterAdapter grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

    /**
     * 3. 全局过滤器：JWT 解析与用户信息透传
     */
    @Bean
    public GlobalFilter authGlobalFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            return exchange.getPrincipal()
                    .cast(Principal.class)
                    .defaultIfEmpty(new org.springframework.security.authentication.UsernamePasswordAuthenticationToken("anonymous", null))
                    .flatMap(principal -> {
                        ServerHttpRequest mutatedRequest = request;

                        if (principal instanceof JwtAuthenticationToken jwtAuth) {
                            String userId = jwtAuth.getToken().getSubject();
                            String username = jwtAuth.getToken().getClaimAsString("preferred_username");
                            if (username == null) username = userId;

                            mutatedRequest = request.mutate()
                                    .header("X-User-Id", userId)
                                    .header("X-User-Name", username)
                                    .header("X-Auth-Token", jwtAuth.getToken().getTokenValue())
                                    .build();
                        }
                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                    });
        };
    }

    /**
     * 4. 限流 KeyResolver
     */
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> {
            String userId = exchange.getRequest().getHeaders().getFirst("X-User-Id");
            if (userId != null && !"anonymous".equals(userId)) {
                return Mono.just(userId);
            }
            String ip = exchange.getRequest().getRemoteAddress() != null ?
                    exchange.getRequest().getRemoteAddress().getAddress().getHostAddress() : "unknown";
            return Mono.just(ip);
        };
    }

    /**
     * 5. 全局 CORS 配置
     */
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        // 使用 Nimbus 实现，传入 JWK Set URI
        return NimbusReactiveJwtDecoder.withJwkSetUri("http://localhost:22004/oauth2/jwks").build();
        // 也可以使用 issuer-uri 方式
        // return NimbusReactiveJwtDecoder.withIssuerLocation("http://your-auth-server").build();
    }
}