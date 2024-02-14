package com.oracle.oBootMybatis01.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.oracle.oBootMybatis01.domain.Member;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepositoryImpl implements MemberJpaRepository {

	private final EntityManager em;

	@Override
	public Member save(Member member) {
		System.out.println("MemberJpaRepositoryImpl save start...");
		em.persist(member);
		return member;
	}

	@Override
	public List<Member> findAll() {
		System.out.println("MemberJpaRepositoryImpl findAll start...");
		List<Member> memberList = em.createQuery("select m mfrom Member m", Member.class)
				.getResultList();
		return memberList;
	}

	@Override
	public Optional<Member> findById(Long memberId) {
		Member member=em.find(Member.class, memberId); //null값도 될 수 있는데
		return Optional.ofNullable(member); //null값도 넘어감. 그걸 위해 optional을 사용
	}
	
	public void updateByMember(Member member) {
		System.out.println("MemberJpaRepositoryImpl findAll member->"+member);
		//em.merge(member); //현재 세팅된 것만 수정하고, 나머지는 null값으로 한다
		Member member3=em.find(Member.class,member.getId()); //그래서 null쓰지말고
		member3.setId(member.getId()); //find로 가져와서 세팅하는게 나음
		member3.setName(member.getName());
		return;
	}

}
