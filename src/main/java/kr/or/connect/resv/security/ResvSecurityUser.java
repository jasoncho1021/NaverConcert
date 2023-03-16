package kr.or.connect.resv.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kr.or.connect.resv.domain.Member;
import kr.or.connect.resv.domain.MemberRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;


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
        roles.forEach(System.out::println);
        roles.forEach(role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName())));
        return list;
    }
}

