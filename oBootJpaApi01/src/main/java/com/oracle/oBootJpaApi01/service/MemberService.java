package com.oracle.oBootJpaApi01.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.oBootJpaApi01.domain.Member;
import com.oracle.oBootJpaApi01.repository.MemberRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional //서비스에선 반드시. 앞에서 한 내용. 무결성.
public class MemberService {
	private final MemberRepository memberRepository;
	
	public Long saveMember(@Valid Member member) {
		System.out.println("MemberServcie saveMember member.getName()->"+member.getName());
		Long id=memberRepository.save(member);
		return id;
	}
	
	public List<Member> getListAllMember() {
		List<Member> listMember=memberRepository.findAll();
		System.out.println("MemberService getListAllMember listMember.size()"+listMember.size());
		return listMember;
	}
	
	public void updateMember(Long id, String name, Long sal) {
		Member member=new Member();
		member.setId(id);
		member.setName(name);
		member.setSal(sal);
		System.out.println("MemberService updateMember member.getName()->"+member.getName());
		System.out.println("MemberService updateMember member.getSal()->"+member.getSal());
		memberRepository.updateByMember(member);
		return;
	}
	
	public Member findByMember(Long memberId) {
		Member member=memberRepository.findByMember(memberId);
		System.out.println("MemberService findByMember member.get().getId()->"+member.getId());
		System.out.println("MemberService findByMember member.get().getName()->"+member.getName());
		return member;
	}

}
