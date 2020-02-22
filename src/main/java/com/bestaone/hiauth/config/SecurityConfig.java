package com.bestaone.hiauth.config;

import com.bestaone.hiauth.config.smscode.SmsCodeAuthenticationSecurityConfig;
import com.bestaone.hiauth.config.validatecode.ValidateCodeSecurityConfig;
import com.bestaone.hiauth.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig(){
        return new SmsCodeAuthenticationSecurityConfig();
    }

    @Bean
    public ValidateCodeSecurityConfig validateCodeSecurityConfig(){
        return new ValidateCodeSecurityConfig();
    }

    @Bean
    public UserDetailsService simpleUserDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(simpleUserDetailsService());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.userDetailsService(userDetailsService());
        http.csrf().disable();

        //开启短信登陆功能
        http.apply(smsCodeAuthenticationSecurityConfig());
        //开启验证码功能
        http.apply(validateCodeSecurityConfig());

        http.formLogin()
                .loginPage("/signin").loginProcessingUrl("/signin/form/account").defaultSuccessUrl("/index").and()
                //.successHandler(new MyAuthenticationSuccessHandler())//.defaultSuccessUrl("/index")
                .logout().logoutUrl("/signout").logoutSuccessUrl("/signin").and()
            .authorizeRequests()
                .antMatchers("/signin","/signin/form/tel","/code/image","/code/mobile","/static/**").permitAll()
                //这个地址由ApiAuthFilter过滤，不需要登录拦截
                .antMatchers("/api/**").permitAll()
                //这个地址由AuthorizationServer使用，不需要登录拦截
                .antMatchers("/oauth/**").permitAll()
                //这个地址开放地址
                .antMatchers("/public/**").permitAll()
                .antMatchers("/user/**").hasAnyRole("USER","ADMIN")
            .anyRequest().authenticated();//其他任意请求需要登录

    }

}