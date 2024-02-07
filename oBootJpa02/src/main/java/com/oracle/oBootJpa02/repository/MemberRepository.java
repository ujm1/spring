package com.oracle.oBootJpa02.repository;

import java.util.List;
import java.util.Optional;

import com.oracle.oBootJpa02.domain.Member;

public interface MemberRepository {
    Member save(Member var1);

    List<Member> findAll();

    List<Member> findByNames(String var1);

    Optional<Member> findByMember(Long var1);

    void updateByMember(Member var1);
}