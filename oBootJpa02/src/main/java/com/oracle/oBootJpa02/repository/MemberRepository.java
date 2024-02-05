package com.oracle.oBootJpa02.repository;

import java.util.List;

import com.oracle.oBootJpa02.domain.Member;

public interface MemberRepository {
	Member save(Member member);
	List<Member> findAll();
	List<Member> findByNames(String searchName);
	
}
