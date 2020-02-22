package com.bestaone.hiauth.config.smscode;

import com.bestaone.hiauth.service.ResourceService;
import com.bestaone.hiauth.service.RoleService;
import com.bestaone.hiauth.service.UserService;
import com.bestaone.hiauth.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    public UserService userService;

    @Autowired
    public RoleService roleService;

    @Autowired
    public ResourceService resourceService;

    @Autowired
    private RedisUtil cacheUtil;

    @Bean
    public SmsUserDetailsService smsUserDetailsService(){
        SmsUserDetailsService smsUserDetailsService = new SmsUserDetailsService();
        smsUserDetailsService.setUserService(userService);
        smsUserDetailsService.setRoleService(roleService);
        smsUserDetailsService.setResourceService(resourceService);
        return smsUserDetailsService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //自定义SmsCodeAuthenticationFilter过滤器
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter("/signin/form/tel", "/signin?error=true");
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        //设置自定义SmsCodeAuthenticationProvider的认证器userDetailsService
        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setUserDetailsService(smsUserDetailsService());
        smsCodeAuthenticationProvider.setCacheUtil(cacheUtil);

        //在UsernamePasswordAuthenticationFilter过滤前执行
        http.authenticationProvider(smsCodeAuthenticationProvider)
                .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
