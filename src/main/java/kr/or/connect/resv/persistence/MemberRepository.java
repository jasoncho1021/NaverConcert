package kr.or.connect.resv.persistence;

import org.springframework.data.repository.CrudRepository;

import kr.or.connect.resv.domain.Member;

public interface MemberRepository extends CrudRepository<Member, String> {

}
