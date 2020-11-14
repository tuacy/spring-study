package com.tuacy.securityoauth.config;

import com.tuacy.securityoauth.extend.CustomTokenEnhancer;
import com.tuacy.securityoauth.extend.SMSCodeTokenGranter;
import com.tuacy.securityoauth.service.SMSRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.util.DigestUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.sql.DataSource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 配置授权服务
 *
 * @name: AuthorizationServerConfiguration
 * @author: tuacy.
 * @date: 2019/11/28.
 * @version: 1.0
 * @Description: OAuth2认证授权服务配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     * 注入权限验证控制器 来支持 password grant type
     */
    private AuthenticationManager authenticationManager;
    /**
     * 数据源
     */
    private DataSource dataSource;
    /**
     * 令牌的存储策略。管理令牌
     * 支持多种存储策略（内存，数据库，redis, jwt）
     */
    private TokenStore tokenStore;
    private AccessTokenConverter accessTokenConverter;
    /**
     * 注入userDetailsService，开启refresh_token需要用到
     */
    private UserDetailsService userDetailsService;
    private SMSRecordService smsRecordService;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Autowired
    public void setAccessTokenConverter(AccessTokenConverter accessTokenConverter) {
        this.accessTokenConverter = accessTokenConverter;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setSmsRecordService(SMSRecordService smsRecordService) {
        this.smsRecordService = smsRecordService;
    }

    /**
     * 声明 ClientDetails实现 -- JdbcClientDetailsService实现
     * 客户端信息配置，我们这里配置在数据库里面，注意这里密码配置
     */
    @Bean
    public ClientDetailsService clientDetails() {
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        if (accessTokenConverter instanceof TokenEnhancer) {
            tokenEnhancerChain.setTokenEnhancers(Arrays.asList(new CustomTokenEnhancer(), (TokenEnhancer) accessTokenConverter));
        } else {
            tokenEnhancerChain.setTokenEnhancers(Collections.singletonList(new CustomTokenEnhancer()));
        }
        return tokenEnhancerChain;
    }

    /**
     * 令牌token服务
     */
    @Primary
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        // 客户端服务信息
        tokenServices.setClientDetailsService(clientDetails());
        // 令牌token存储策略
        tokenServices.setTokenStore(tokenStore);
        // 支持使用refresh_token，是否产生刷新令牌token
        tokenServices.setSupportRefreshToken(true);
        // 不重复使用refresh_token,每交刷新完后，更新这个值
        tokenServices.setReuseRefreshToken(false);
        // 如果没有设置它,JWT就失效了.
        tokenServices.setTokenEnhancer(tokenEnhancer());
        // 令牌token有效期, 30分钟
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.MINUTES.toSeconds(30));
        // 刷新令牌token默认有效期，60分钟
        tokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.MINUTES.toSeconds(60));
        return tokenServices;
    }

    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束
     * 对应于配置AuthorizationServer安全认证的相关信息，创建ClientCredentialsTokenEndpointFilter核心过滤器
     *
     * /oauth/authorize: 授权端点
     * /oauth/token: 令牌端点
     * /oauth/confirm_access: 用户确认授权提供端点
     * /oauth/error: 授权服务错误信息端点
     * /oauth/check_token: 用于资源服务访问的令牌解端点
     * /oauth/token_key: 提供公有秘钥的端点，如果你使用了JWT令牌的话
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        super.configure(security);

        /*
         * 配置oauth2服务跨域
         */
        CorsConfigurationSource source = request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.addAllowedHeader("*");
            corsConfiguration.addAllowedOrigin(request.getHeader(HttpHeaders.ORIGIN));
            corsConfiguration.addAllowedMethod("*");
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.setMaxAge(3600L);
            return corsConfiguration;
        };

        // 对应 oath/token_key 公开
        security.tokenKeyAccess("permitAll()")
                // 对应 oath/check_token 公开
                .checkTokenAccess("permitAll()")
                // 允许表单认证
                .allowFormAuthenticationForClients()
                .addTokenEndpointAuthenticationFilter(new CorsFilter(source));
    }

    /**
     * 用来配置客户端详情服务（ClientDetailsService），当前服务支持哪些客户端
     * 客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
     *
     * 客户端要来申请令牌
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        super.configure(clients);
        clients.withClientDetails(clientDetails());
    }

    /**
     * 用来配置令牌(token)的访问端点和令牌服务(token service)
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     * 配置身份认证器，配置认证方式，TokenStore，TokenGranter，OAuth2RequestFactory
     * <p>
     * authenticationManager: 认证管理器，当你选择了资源所有者密码(password)授权类型的时候，请设置这个属性注入一个AuthenticationManager对象
     * userDetailService: 如果你设了这个属性的话，那说明你有一个自己的UserDetailService接口的实现，或者你可以把这个东西设置到全局与上面去(例如
     * GlobalAuthenticationManagerConfigurer这个配置对象)当年你这设置了对象之后那么“refresh_token”即刷新令牌授权类型模式的流程中就会包含一个检查，
     * 用来确保这个账号是否依然有效，假如说你禁用了这个账号的话。
     * authorizationCodeService: 这个属性是用来设置授权码服务器的(即AuthorizationCodeService的实例对象)，主要用于“authorization_code”授权码类型模式
     * implicitGrantService：这个属性用于设置隐式授权模式，用来关联隐式授权模式的状态。
     * tokenGranter:  当你设置了这个东西(即TokenGranter接口的实现)，那么授权将会交由你来完全掌控，并且会忽略掉上面的几个属性，这个属性一般用于拓展用途的，
     * 即标准的四种授权模式已经满足不了你的需求的时候，才会考虑使用这个
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        super.configure(endpoints);
        endpoints
                // 要使用refresh_token的话，需要额外配置userDetailsService
                .userDetailsService(userDetailsService)
                // token存到redis
                .tokenStore(tokenStore)
                .tokenGranter(tokenGranter())
                // 授权码服务
                .authorizationCodeServices(authorizationCodeServices())
                // 密码授权模式需要
                .authenticationManager(authenticationManager)
                // 告诉spring security token的生成方式
                .accessTokenConverter(accessTokenConverter)
                // 接收GET和POST
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                // 令牌token管理服务
                .tokenServices(tokenServices());
    }

    private OAuth2RequestFactory requestFactory() {
        return new DefaultOAuth2RequestFactory(clientDetails());
    }

    /**
     * 设置授权码验证模式授权码的存放地方 -- 我们这里存放在数据库中
     */
    @Primary
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }


    /**
     * 增加此方法是为了多增加一种授权模式（oath2之前是有四种验证模式:授权码模式,简化模式,密码模式,客户端模式），比如我们添加一个短信验证码的认证模式
     */
    private TokenGranter tokenGranter() {
        return new TokenGranter() {
            private CompositeTokenGranter delegate;

            @Override
            public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
                if (delegate == null) {
                    delegate = new CompositeTokenGranter(getDefaultTokenGranters());
                }
                return delegate.grant(grantType, tokenRequest);
            }
        };
    }

    /**
     * 这是从spring 的代码中 copy出来的,默认的几个 TokenGranter, 我们自定义的就加到这里就行了,目前我还没有加
     */
    private List<TokenGranter> getDefaultTokenGranters() {
        ClientDetailsService clientDetails = clientDetails();
        AuthorizationServerTokenServices tokenServices = tokenServices();
        OAuth2RequestFactory requestFactory = requestFactory();

        List<TokenGranter> tokenGranters = new ArrayList<>();
        tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices(), clientDetails, requestFactory));
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetails, requestFactory));
        tokenGranters.add(new ImplicitTokenGranter(tokenServices, clientDetails, requestFactory));
        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetails, requestFactory));
        if (authenticationManager != null) {
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, clientDetails, requestFactory));
        }
        // 增加一种验证码的认证模式
        tokenGranters.add(new SMSCodeTokenGranter(tokenServices, clientDetails, requestFactory, userDetailsService, smsRecordService));
        return tokenGranters;
    }
}
