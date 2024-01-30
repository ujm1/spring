package com.oracle.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.test.domain.Member1;
import com.oracle.test.repository.MemberRepository;
import com.oracle.test.repository.MemoryMemberRepository;

@Service
public class MemberService {
//	MemberRepository memberRepository=new MemoryMemberRepository(); 전통적

	private final MemberRepository memberRepository; //컴포넌트화
	
	@Autowired //생성자. 생성자-컴포넌트 이용 방식이 spring에서 보편적.
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public Long memberSave(Member1 member1) {
		System.out.println("MemberService memberSave start...");
		memberRepository.save(member1);
		return member1.getId();
	} // repository에 저장하고, 저장한 멤버의 id를 리턴

	public List<Member1> allMembers() {
		System.out.println("MemberService allMembers start...");
		List<Member1> memList = null;
		memList = memberRepository.findAll();
		// 저장한 멤버들을 뽑아내 리스트에 넣음
		System.out.println("memList.size()->" + memList.size());
		// 리스트 멤버수(저장한 멤버수) 출력
		return memList;
		// 리스트 리턴
	}
}
