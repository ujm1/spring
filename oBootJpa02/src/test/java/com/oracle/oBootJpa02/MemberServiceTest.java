package com.oracle.oBootJpa02;

import static org.junit.jupiter.api.Assertions.*;

package com.oracle.oBootJpa02;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.repository.MemberRepository;
import com.oracle.oBootJpa02.service.MemberService;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest 
class MemberServiceTest {

	//테스트용 클래스인데,
	    @Autowired
	    MemberService memberService;
	    @Autowired
	    MemberRepository memberRepository;

	    public MemberServiceTest() {
	    }

	    @BeforeEach
	    public void before1() {
	        System.out.println("Test @BeforeEach ...");
	    }

	    @Test
	    public void memberSave() {
	        Member member = new Member();
	        member.setTeamname("고구려");
	        member.setName("강이식");
	        Member member3 = this.memberService.join(member);
	        System.out.println("MemberServiceTest memberSave member.getId()->" + String.valueOf(member.getId()));
	        System.out.println("MemberServiceTest memberSave member3.getId()->" + String.valueOf(member3.getId()));
	    }
	    @Test
	    public void memberFind() {
	    	Long findId=1L;
	    	Optional<Member> member=memberService.
	    	System.out.println("MemberServiceTest memberList.size()"+memberList.size());
	    }
	    
	}
}
