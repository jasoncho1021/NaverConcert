package kr.or.connect.resv.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import kr.or.connect.resv.domain.Member;
import kr.or.connect.resv.domain.MemberRole;

public class ResvSecurityUser extends User {

	private static final String ROLE_PREFIX = "ROLE_";

	private Member member;

	public ResvSecurityUser(Member member) {
		super(member.getUid(), member.getUpw(), makeGrantedAuthority(member.getRoles()));
		this.member = member;
	}

	public ResvSecurityUser(String userName, String password, Collection<? extends GrantedAuthority> authorities) {
		super(userName, password, authorities);
	}

	private static List<GrantedAuthority> makeGrantedAuthority(List<MemberRole> roles) {
		List<GrantedAuthority> list = new ArrayList<>();
		roles.forEach(role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName())));
		return list;
	}
}
