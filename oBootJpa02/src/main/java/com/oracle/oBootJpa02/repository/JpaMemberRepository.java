package com.oracle.oBootJpa02.repository;

import java.util.List;
import java.util.Optional;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.domain.Team;

import jakarta.persistence.EntityManager;

public class JpaMemberRepository implements MemberRepository {
	
	private final EntityManager em;
	public JpaMemberRepository(EntityManager em) {this.em=em;}

	@Override
	public Member save(Member member) {
		Team team=new Team();
		team.setName(member.getTeamname());
		em.persist(team);
		member.setTeam(team); //단방향 연고나관계 설정, 참조 저장
		em.persist(member);
		System.out.println("jpaMemberRepository save member->"+member);
		return member;
	}

	@Override
	public List<Member> findAll() {
		List<Member> memberList=em.createQuery("selectm from Member m",Member.class).getResultList();
		return memberList;
	}
	
	public List<Member> findByNames(String searchName) {
		String pname=searchName+"%";
		System.out.println("jpaMemberRepository findByNames searchName->"+searchName);
		List<Member> memberList=em.createQuery("select m from Member m where name Like :name", Member.class)
				.setParameter("name",pname).getResultList();
		System.out.println("jpaMemberRepository memberList.size()->"+memberList.size());
		return memberList;
	}
	
	public Optional<Member> findByMember(Long id) {
		Member member=em.find(Member.class, id);
		return Optional.ofNullable(member);
	}
	
	public void updateByMember(Member member) {
		
	}

}
