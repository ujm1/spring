package com.oracle.oBootJpa01.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oracle.oBootJpa01.domain.Member;

import jakarta.persistence.EntityManager;

@Repository // 써먹을 repository는 그냥 이거 해준다. 자체 기능
public class JpaMemberRepository implements MemberRepository {

	private final EntityManager em;

	// jpa에서 dml로 쓸 때, 써먹을 객체를 EntityManager로 선언하는게 필수.
	// 이 entity는 : jpa에선 entity, myBatis에선 dto라고 부르는 것이다.
	// 개체 모델링 논리 모델링에선 entity, 물리 모델링에선 table이라고
	// ansi(미국표준)에서 지정하였으므로 이름을 이렇게 한다.
	public JpaMemberRepository(EntityManager em) {
		this.em = em;
	} //생성자-인스턴스 작업

	@Override
	public Member memberSave(Member member) {
		// TODO Auto-generated method stub
		em.persist(member); //과거에 했던 그 긴 작업을 이 하나로 끝냄
		System.out.println("jpaMemberRepository memberSave member After...");
		return member;
	}

	@Override
	public List<Member> findAllMember() {						//여기서 Member는 테이블 이름이 아닌, 어노테이션으로 부여된 개체. 
																//즉 여기선 @Entity로 지정해준 Member class..
		List<Member> memberList=em.createQuery("select m from Member m", Member.class).getResultList();
																	      		//결과값을 list 형태로..
		System.out.println("jpaMemberRepository findAllMember memberList.size()-> "+memberList.size());
		return memberList;
	}
	
	public List<Member> findByNames(String searchName) {
		String pname=searchName+"%";
		System.out.println("jpaMemberRepository findByNames pname->"+pname);
		List<Member> memberList=em.createQuery("select m from Member m where name Like :name", Member.class)
				.setParameter("name", pname).getResultList();
		//jpa 안쓸 떄 :? 하고 따로 값 넣어주던 것과 동일 
		//Hibernate: Hibernate: select m1_0.id,m1_0.name from member1 m1_0 where m1_0.name like ? 라고 해석한다
		System.out.println("jpaMemberRepository memberList.size():"+memberList.size());
		return memberList;
	}

}
