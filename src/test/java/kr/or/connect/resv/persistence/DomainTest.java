package kr.or.connect.resv.persistence;

import java.util.Optional;
import kr.or.connect.resv.domain.Member;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Log
@SpringBootTest
@Transactional
public class DomainTest {

    @Autowired
    private MemberRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

/*    @Test
    public void testInsert() {

        for (int i = 0; i <= 100; i++) {

            Member member = new Member();
            member.setUid("user" + i);
            member.setUpw("pw" + i);
            member.setUname("사용자" + i);

            MemberRole role = new MemberRole();
            if (i <= 80) {
                role.setRoleName("BASIC");
            } else if (i <= 90) {
                role.setRoleName("MANAGER");
            } else {
                role.setRoleName("ADMIN");
            }
            member.setRoles(Arrays.asList(role));

            repo.save(member);
        }

    }*/

    @Test
    public void testUpdateOldMember() {

        repo.findAll().forEach(member -> {

            member.setUpw(passwordEncoder.encode(member.getUpw()));

            repo.save(member);
        });

    }

    @Test
    public void testRead() {

        Optional<Member> result = repo.findById("user85");

        result.ifPresent(member -> log.info("member" + member));

    }

}
