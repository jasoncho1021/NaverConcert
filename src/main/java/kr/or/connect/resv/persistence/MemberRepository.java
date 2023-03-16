package kr.or.connect.resv.persistence;

import kr.or.connect.resv.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, String> {


}
