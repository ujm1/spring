package com.oracle.oBootJpaApi01.controller;

import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootJpaApi01.domain.Member;
import com.oracle.oBootJpaApi01.service.MemberService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController // 일반 controller와 다른 점 : controller와 responseBody를 합친 개념
//ajax + restApi에 사용할 떄 사용
@RequiredArgsConstructor
@Slf4j // lombok
public class JpaRestApiController {
	private final MemberService memberService;

	// v1
	// postman ---> Body --> raw---> JSON
	// 예시 { "name" : "kkk222" }
	@PostMapping("/restApi/v1/memberSave") // 정석대로 post. 이렇게 정석대로 해야 api에 적합
	// RequestBody : Json(member)로 온 것들 -> Member member Setting
	// Valid : notNull로 온것 중... 어쩌고. 대충 제대로 안오면 에러 발생시키는 느낌
	// http://localhost:8386/restApi/v1/memberSave
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
		System.out.println("JpaRestApiController /api/v1/memberSave member.getId()->" + member.getId());

		log.info("member.getName()->{}.", member.getName());
		log.info("member.getSal()->{}.", member.getSal());
		Long id = memberService.saveMember(member);
		return new CreateMemberResponse(id);
		// 객체 형태로 돌려주기 위해 내장 클래스를 만듬
	}

	@Data
	@RequiredArgsConstructor
	static class CreateMemberResponse {
		private final Long id;
		// public CreateMemberResponse(Long id) {this.id=id;} 이렇게 생성자 작업 해줘야하나
		// Required 쓰면 되므로..

	}

	// v2
	// 목적 : Entity Member member --> 직접 화면이나 API위한 Setting 금지
	// 예시 : @NotEmpty --> @Column(name = "userName")
	// http://localhost:8386/restApi/v2/memberSave
	@PostMapping("/restApi/v2/memberSave")
	public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest cMember) {
		System.out.println("JpaRestApiController /api/v2/memberSavbe member.getId()->" + cMember.getName());
		log.info("cMember.getName()->{}." + cMember.getName());
		log.info("cMember.getSal()->{}." + cMember.getSal());
		Member member = new Member();
		member.setName(cMember.getName());
		member.setSal(cMember.getSal());
		// 객체로 만들어서 겟셋으로. 보안을 위함.
		Long id = memberService.saveMember(member);
		return new CreateMemberResponse(id);
	}

	@Data // get set 등...알아서
	static class CreateMemberRequest {
		@NotEmpty
		private String name;
		private Long sal;
	}

	// bad api
	@GetMapping("/restApi/v1/members")
	public List<Member> membersVer1() {
		System.out.println("paRestApiController /restApi/v1/members start..");
		List<Member> listMember = memberService.getListAllMember();
		return listMember;
	}

	// good api+easy version
	// 이름&급여만 전송
	@GetMapping("/restApi/v21/members")
	public Result membersVer21() {
		// 만약 public List<MemberRtnDto> membersVer21() 로 하면, 결과가 {"data" :
		// [{"name":~,"sal":~,}, {~~}, {~~}] } 로 나옴
		List<Member> findMembers = memberService.getListAllMember();
		System.out.println("JpaRestApiController rstApi/v21/members findMembers.size()" + findMembers.size());
		List<MemberRtnDto> resultList = new ArrayList<MemberRtnDto>();
		for (Member member : findMembers) {
			MemberRtnDto memberRtnDto = new MemberRtnDto(member.getName(), member.getSal());
			System.out.println("restApi/v21/members getName->" + memberRtnDto.getName());
			System.out.println("restApi/v21/members getSal->" + memberRtnDto.getSal());
			resultList.add(memberRtnDto);
		}
		System.out.println("restApi/v21/members resultList.size()->" + resultList.size());
		return new Result(resultList.size(), resultList); // totCount조회할때
		// return new Result(resultList); //name과 sal 조회
	}

	// Good API 람다 Version
	// 목표 : 이름 & 급여 만 전송
	@GetMapping("restApi/v22/members")
	public Result membersVer22() {
		List<Member> findMembers = memberService.getListAllMember();
		System.out.println("JpaRestApiController restApi/v22/members findMembers.size()->" + findMembers.size());
		List<MemberRtnDto> memberCollect = findMembers.stream() // stream : 람다 활용하는 메서드
				.map(m -> new MemberRtnDto(m.getName(), m.getSal())) // 누적
				.collect(Collectors.toList()); // stream 안에 누적되어있는 컬렉터를 리스트로 만듬
		System.out.println("restApi/v22/members memberCollect.size()->" + memberCollect.size());
		return new Result(memberCollect.size(), memberCollect);

	}

	@Data
	@AllArgsConstructor
	class Result<T> { // 제네릭스 : 인스턴스를 생성할 떄 구체적인 타입으로 변경해주기 떄문에 유연성이 높아 사용
		private final int totCount; // 총 인원수 추가 ->
		/*
		 * { "totCount": 3, "data": [ { "name": "황희", "sal": 7900 }, ~~~~~~....... }
		 */
		private final T data;

	}

	@Data
	@AllArgsConstructor // 이렇게 name과 sal만 가지고 dto 만듬-> 위의 membersVer2에 넣어서
	// 이 두개만 리스트에 넣어 결과로 뿌리기 위한 목적.
	class MemberRtnDto {

		private String name;
		private Long sal;

	}

	/*
	 * 수정 api : put 사용-전체 업데이트를 할 때. 해당 데이터가 있으면 업데이트. 여러번 put 해도 데이터가 같은 상태라 멱등
	 */
	@PutMapping("/restApi/v21/members/{id}") //이렇게 감싼 데이터가 해당 데이터.
	public UpdateMemberResponse updateMemberV21(@PathVariable("id") Long id,
			@RequestBody @Valid UpdateMemberRequest uMember) {
		memberService.updateMember(id, uMember.getName(), uMember.getSal());
		Member findMember = memberService.findByMember(id);
		return new UpdateMemberResponse(findMember.getId(), findMember.getName(), findMember.getSal());
	}
	@Data
	static class UpdateMemberRequest {
		private String name;
		private Long sal;
	}
	@Data
	@AllArgsConstructor
	static class UpdateMemberResponse {
		private Long id;
		private String name;
		private Long sal;
	}
	


}
