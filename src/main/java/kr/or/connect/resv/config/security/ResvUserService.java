package kr.or.connect.resv.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.or.connect.resv.persistence.MemberRepository;
import lombok.extern.java.Log;

@Service
@Log
public class ResvUserService implements UserDetailsService {

	@Autowired
	MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return memberRepository.findById(userName).filter(m -> m != null).map(m -> new ResvSecurityUser(m)).get();
	}

}
