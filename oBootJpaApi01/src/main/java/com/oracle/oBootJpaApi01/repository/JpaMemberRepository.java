package com.oracle.oBootJpaApi01.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.oracle.oBootJpaApi01.domain.Member;

import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor //이거 넣으니 아래 em 오류가 사라짐
//지정된 속성에 대해서만 생성자 만들어줌. final에 사용
public class JpaMemberRepository implements MemberRepository {

	private final EntityManager em; //final이므로 반드시 생성자 만들어줘야하나
	//requiredArsConstructor로 대체
	

	@Override
	public Long save(@Valid Member member) {
		System.out.println("JpaMmeberRepository save before ...");
		em.persist(member); //em이 member를 관리하게됨, 그래서 db에 
		//postman에서 보낸 이름과 sal이 저장됨? db 연결은 yml에서 해줬고..
		return member.getId();
	}
	
	@Override
	public List<Member> findAll() {
		List<Member> memberList=em.createQuery("select m from Member m", Member.class).getResultList();
		System.out.println("JpaMemberRepository findAll memberList.size()"+memberList.size());
		return memberList;
		
	}

	@Override
	public void updateByMember(Member member) {
		int result=0;
		Member member3=em.find(Member.class,member.getId());
		if(member3!=null) {
			member3.setName(member.getName());
			member3.setSal(member.getSal());
			result=1;
			System.out.println("JpaMemberRepository updateByMember Update...");
		} else {
			result=0;
			System.out.println("JpaMemberRepository updateByMember No Exist..");
		}
		return;
	}
	
	public Member findByMember(Long memberId) {
		Member member=em.find(Member.class,memberId);
		return member;
	}
	
	


}
