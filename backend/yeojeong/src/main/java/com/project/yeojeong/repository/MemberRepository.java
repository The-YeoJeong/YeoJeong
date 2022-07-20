package com.project.yeojeong.repository;
import com.project.yeojeong.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

// User Entity에 매핑되는 UserRepository Interface
//JpaRepository를 상속해 findall, save와 같은 메소드 사용할 수 있음
public interface MemberRepository extends JpaRepository<Member, Integer> {
    // 아래의 메소드는 username을 기준으로 user정보를 가져올때 권한정보도 같이 가져옴
    //    @EntityGraph(attributePaths = "authorities") // EntityGraph annotation은 쿼리가 수행될때 Lazy조회가 아니고 Eager조회로 authorities 정보를 같이 가져옴
    //    Optional<User> findOneWithAuthoritiesByUserid(String userid);
    //    Optional<User> findOneByUserid(String userid);
    @EntityGraph(attributePaths = "authorities") // EntityGraph annotation은 쿼리가 수행될때 Lazy조회가 아니고 Eager조회로 authorities 정보를 같이 가져옴
    Optional<Member> findOneWithAuthoritiesByMemberId(String memberId);

    Member getByMemberId(String memberId);

//    void deleteByMemberId(String memberId);
//
////    DELETE FROM [테이블] WHERE [조건]
//    @Query(value = "DELETE m from member m where m.member_id = :memberId", nativeQuery = true)
//    void deleteByMemberIdTest(String memberId);
}
