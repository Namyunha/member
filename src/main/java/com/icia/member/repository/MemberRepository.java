package com.icia.member.repository;

import com.icia.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
//    select => findBy
//    select * from member_table where member_table where member_email=?

    Optional<MemberEntity> findByMemberEmail(String memberEmail);

//    select * from member_table where member_email=? and member_password=?
    Optional<MemberEntity> findByMemberEmailAndMemberPassword(String memberEmail, String memberPassword);

}
