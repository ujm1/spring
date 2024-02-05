package com.oracle.oBootJpa02.service;

import java.util.List;
import java.util.Optional;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Transactional //jpa->서비스 계층에 트랜잭션을 추가한다. 스프링은 여기 메서드 실행할 때 트랜잭션 시작하고,
//정상 종료되면 트랜잭션 커밋, 아니면 롤백하기 때문. jpa를 통한 모든 데이터변경은 트랜잭션 안에서 이루어짐
public class MemberService {
	private final MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository=memberRepository;
		
	}
	
	public Member join(Member member) {
		System.out.println("MemberService join member.getName()->"+member.getName());
		memberRepository.save(member);
		return member;
	}
	
	public List<Member> getListAllMember() {
		List<Member> listMember=memberRepository.findAll();
		System.out.println("MemberService getListAllMember listMember.size()->"+listMember.size());
		return listMember;
	}

	public List<Member> getListSearchMember(String searchName) {
		System.out.println("MemberService getListSearchMember Start...");
		System.out.println("MemberService getListSearchMember searchName-<"+searchName);
		List<Member> listMember=memberRepository.findByNames(searchName);
		System.out.println("MemberService getListSearchMember listMember.size()->"+listMember.size());
		return listMember;
	}
	
	public Optional<Member> findByMember(Long id) {
		Optional<Member> member=memberRepository.findByMember(id);
		System.out.println("MemberService findByMember member->"+member);
		return member;
	}
	
	public void memberUpdate(Member member) {
		System.out.println("MemberService memberUpdate member->"+member);
		memberRepository.updateByMember(member);
		return;
		
	}
}
