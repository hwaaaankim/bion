package com.dev.BionLifeScienceWar.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import com.dev.BionLifeScienceWar.handler.AjaxAuthenticationEntryPoint;
import com.dev.BionLifeScienceWar.handler.CustomAuthFailureHandler;
import com.dev.BionLifeScienceWar.handler.CustomAuthenticationProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private CustomAuthFailureHandler customFailureHandler;
	
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	
	@Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }
    
	@Override
    public void configure(WebSecurity webSecurity) throws Exception{
        webSecurity.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
				.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
				.antMatchers("/**", "/api/v1/**").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/memberLoginForm")
			.loginProcessingUrl("/memberLogin")
			.failureHandler(customFailureHandler)
			.defaultSuccessUrl("/admin/index")
			.and()
		.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/")
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true)
			.permitAll();
		http.exceptionHandling()
			.authenticationEntryPoint(new AjaxAuthenticationEntryPoint("/memberLoginForm"));
	}	
}