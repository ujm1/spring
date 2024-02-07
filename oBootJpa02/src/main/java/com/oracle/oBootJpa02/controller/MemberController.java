package com.oracle.oBootJpa02.controller;

import com.oracle.oBootJpa02.domain.Member;
import com.oracle.oBootJpa02.service.MemberService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping({"/members/new"})
    public String createForm() {
        System.out.println("MemberController /members/new start.. ");
        return "members/createMemberForm";
    }

    @PostMapping({"/members/save"})
    public String create(Member member) {
        System.out.println("MemberController create start.. ");
        System.out.println("member.getTeamname()->" + member.getTeamname());
        System.out.println("member.getName()->" + member.getName());
        this.memberService.join(member);
        return "redirect:/";
    }

    @GetMapping({"/members"})
    public String listMember(Model model) {
        List<Member> memberList = this.memberService.getListAllMember();
        System.out.println("memberList.get(0).getName()->" + ((Member)memberList.get(0)).getName());
        System.out.println("memberList.get(0).getTeam().getName()->" + ((Member)memberList.get(0)).getTeam().getName());
        model.addAttribute("members", memberList);
        return "members/memberList";
    }

    @PostMapping({"/members/search"})
    public String search(Member member, Model model) {
        System.out.println("/members/search member.getName()->" + member.getName());
        List<Member> memberList = this.memberService.getListSearchMember(member.getName());
        System.out.println("/members/search memberList.size()->" + memberList.size());
        model.addAttribute("members", memberList);
        return "members/memberList";
    }

    @GetMapping({"/memberModifyForm"})
    public String memberModify(Member member3, Model model) {
        System.out.println("MemberController memberModify id->" + String.valueOf(member3.getId()));
        Optional<Member> member = this.memberService.findByMember(member3.getId());
        Member member2 = new Member();
        member2.setId(((Member)member.get()).getId());
        member2.setName(((Member)member.get()).getName());
        member2.setTeam(((Member)member.get()).getTeam());
        System.out.println("memberModifyForm member->" + String.valueOf(member));
        model.addAttribute("member", member2);
        return "members/memberModify";
    }

    @GetMapping({"/members/memberUpdate"})
    public String memberUpdate(Member member, Model model) {
        System.out.println("MemberController memberUpdate member->" + String.valueOf(member));
        this.memberService.memberUpdate(member);
        return "redirect:/members";
    }
}
