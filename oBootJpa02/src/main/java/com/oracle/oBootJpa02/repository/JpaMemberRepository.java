package com.oracle.oBootJpa02.repository;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.domain.Team;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {
	private final EntityManager em;

	public JpaMemberRepository(EntityManager em) {
		this.em = em;
	}

	public Member save(Member member) {
		Team team = new Team();
		team.setName(member.getTeamname());
		this.em.persist(team);
		member.setTeam(team); // 단방향 연고나관계 설정, 참조 저장
		this.em.persist(member);
		Member member3 = (Member) this.em.find(Member.class, member.getId());
		System.out.println("JpaMemberRepository save member->" + String.valueOf(member));
		return member3;
	}

	public List<Member> findAll() {
		List<Member> memberList = this.em.createQuery("select m from Member m", Member.class).getResultList();
		return memberList;
	}

	public List<Member> findByNames(String searchName) {
		String pname = searchName + "%";
		System.out.println("JpaMemberRepository findByNames searchName->" + searchName);
		List<Member> memberList = this.em.createQuery("select m from Member m where name Like :name", Member.class)
				.setParameter("name", pname).getResultList();
		System.out.println("JpaMemberRepository memberList.size()->" + memberList.size());
		return memberList;
	}

	public Optional<Member> findByMember(Long id) {
		Member member = (Member) this.em.find(Member.class, id);
		return Optional.ofNullable(member);
	}

	public void updateByMember(Member member) {
		int result = false;
		Member member3 = (Member) this.em.find(Member.class, member.getId());
		if (member3 != null) {
			Team team = (Team) this.em.find(Team.class, member.getTeamid());
			if (team != null) {
				team.setName(member.getTeamname());
				this.em.persist(team);
			}

			member3.setTeam(team);
			member3.setName(member.getName());
			this.em.persist(member3);
			System.out.println("JpaMemberRepository updateByMember member->" + String.valueOf(member));
			result = true;
		} else {
			result = false;
			System.out.println("JpaMemberRepository updateByMember No Exist..");
		}

	}
}
