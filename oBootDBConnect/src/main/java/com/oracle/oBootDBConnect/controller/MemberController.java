package com.oracle.oBootDBConnect.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.oBootDBConnect.domain.Member1;
import com.oracle.oBootDBConnect.service.MemberService;


@Controller
public class MemberController {
	private final MemberService memberService;
	
	@Autowired
	public MemberController(MemberService memberService) {
		this.memberService=memberService;
	}
/*  생성자에 @Autowired 가 있으면 스프링이 연관된 객체를 
	스프링 컨테이너(메모리에 적제된)에서 꺼내서 작업에 씀
객체 의존관계를 외부에서 넣어주는 것을 DI (Dependency Injection), 의존성 주입이라 함
이전 에서는 개발자가 직접 주입했고, 여기서는 @Autowired에 의해 스프링이 주입*/ 
	
	@GetMapping(value="/members/memberForm")
	public String createMemberForm() {
		System.out.println("MemberController /members/memberForm start...");
		return "members/createMemberForm";
		
	}
	
	@PostMapping(value="/members/newSave")
	public String memberSave(Member1 member) {
		System.out.println("MemberController memberSave start...");
		memberService.memberSave(member);
		return "redirect:/";
	}
	
	@GetMapping(value="/members/memberList")
	public String memberLists(Model model) {
		List<Member1> memberList=memberService.findMembers();
		model.addAttribute("members",memberList);
	
		return "members/memberList";
	}
	
}
