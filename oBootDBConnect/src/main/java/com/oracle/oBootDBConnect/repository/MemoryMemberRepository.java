package com.oracle.oBootDBConnect.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.oracle.oBootDBConnect.domain.Member1;

//@Repository
public class MemoryMemberRepository implements MemberRepository {
	private static Map<Long, Member1> store = new HashMap<>();
	private static long sequence = 0L;

	@Override
	public Member1 save(Member1 member1) {
		System.out.println("MemoryMemberRepository save start...");
		member1.setId(++sequence);
		store.put(member1.getId(), member1);
		return member1;
	}

	@Override
	public List<Member1> findAll() {
		System.out.println("MemoryMemberRepository findAll start...");
		
		List<Member1> listMember=new ArrayList<>(store.values());
		System.out.println("MemoryMemberRepository findAll aListMember...");
		return listMember;
		
		//return new ArrayList<>(store.values());
		//저 3줄을 이 한 줄로 바꿀 수 있다.
	}

}
