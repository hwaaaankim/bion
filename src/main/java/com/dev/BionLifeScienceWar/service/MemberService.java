package com.dev.BionLifeScienceWar.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dev.BionLifeScienceWar.model.Member;
import com.dev.BionLifeScienceWar.model.MemberAccount;
import com.dev.BionLifeScienceWar.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberService implements UserDetailsService {

	@Bean(name = "saveBean")
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername");
		Optional<Member> member = memberRepository.findByUsername(username);
		if (!member.isPresent()) {
			throw new UsernameNotFoundException("NONE");
		}

		MemberAccount mem = new MemberAccount(member.get());

		return mem;
	}

	public Member insertAdmin(Member member) {
		String encodedPassword = passwordEncoder().encode(member.getPassword());
		member.setPassword(encodedPassword);
		member.setEnabled(true);
		member.setRole("ROLE_ADMIN");
		return memberRepository.save(member);

	}
}
