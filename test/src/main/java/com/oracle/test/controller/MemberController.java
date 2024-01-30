package com.oracle.test.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.oracle.test.domain.Member1;
import com.oracle.test.service.MemberService;

@Controller
public class MemberController {
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
//	MemberService memberService=new MemberService(); 전통적 방식

	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping(value = "members/memberForm")
	public String memberForm() {
		System.out.println("MemberController /members/memberForm start...");
		return "members/memberForm"; // MemberController가 members/memberForm으로 이동시킨다. 이런 느낌
	} // ds->vr->templates/+members/memberForm+.html
	// http://localhost:8381/members/memberForm/ members는 폴더(view단에서.. java단에선 패키지라
	// 부르고)

	@PostMapping(value = "/members/save") // 저장할땐 PostMapping이 일반적
	public String save(Member1 member1) { // 이 또한 컨트롤러가 memberSave.html로 이동시킴. 매핑해서
		System.out.println("MemberController /members/save Start...");
		System.out.println("MemberController /members/save member1.getName()->" + member1.getName());
		Long id = memberService.memberSave(member1);
		System.out.println("MemberController /members/save id->" + id);
		return "redirect:/"; // 이름 등록..
	}

	@GetMapping(value = "/members/memberList") // 조회할땐, 가져올땐, GetMapping이 일반적
	public String memberList(Model model) {
		logger.info("memberList start...");
		List<Member1> memberLists = memberService.allMembers();
		model.addAttribute("memberLists", memberLists);
		logger.info("memberLists.size()->{}", memberLists.size());
		return "members/memberList";
	}
}
