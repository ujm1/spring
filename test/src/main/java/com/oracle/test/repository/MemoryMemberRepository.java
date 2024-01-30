package com.oracle.test.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oracle.test.domain.Member1;

@Repository
public class MemoryMemberRepository implements MemberRepository {
	//Memory를 사용해 MemberRespository 구현
	//Memory : Map
	
	private static Map<Long, Member1> store=new HashMap<Long,Member1>();
	private static Long sequence=0L;
	
	//repository에서
	//멤버 저장
	public Member1 save(Member1 member1) {
		member1.setId(++sequence);
		//아이디 지정
		store.put(member1.getId(),member1);
		//맵에 넣기
		System.out.println("MemoryMemberRepository sequence->"+sequence);
		System.out.println("MemoryMemberRepository member1.getName()->"+member1.getName());
		return member1;
		//저장한 멤버를 리턴
	}
	
	public List<Member1> findAll() {
		System.out.println("MemoryMemberRepository findAll start...");
		List<Member1> listMember=new ArrayList<>(store.values());
		//repository에 map으로 저장한 멤버들을 리스트로 뽑아냄
		System.out.println("MemoryMemberRepository findAll listMember.size()->"+listMember.size());
		
		return listMember;
		//멤버수를 리턴
	}
}
