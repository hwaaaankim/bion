package com.dev.BionLifeScienceWar.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dev.BionLifeScienceWar.model.Member;
import com.dev.BionLifeScienceWar.model.MemberAccount;
import com.dev.BionLifeScienceWar.service.MemberService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MemberService userDetailsService;

    
//    @Autowired
//    private PasswordEncoding passwordEncoding;
    @Bean(name="AuthEncoder")
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        System.out.println("Authenticate");
        System.out.println("PW : " + password);
        System.out.println("ID : " + username);
        MemberAccount memberAccount = (MemberAccount) userDetailsService.loadUserByUsername(username);
        System.out.println(memberAccount.toString());
        System.out.println(memberAccount);
        if (memberAccount.getMember()==null) {
        	System.out.println("Account is None");
            throw new BadCredentialsException("NONE");
        }
        Member member = memberAccount.getMember();
        System.out.println(member.toString());
        if (!passwordEncoder().matches(password, member.getPassword())) {
        	System.out.println("Password not match");
            throw new BadCredentialsException("PWER");
        }
        

        if (!member.getEnabled()) {
        	System.out.println("not CertificationTT");
            throw new DisabledException("not CertificationTT"+member.getUsername());
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        		member,
                null,
                memberAccount.getAuthorities());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
