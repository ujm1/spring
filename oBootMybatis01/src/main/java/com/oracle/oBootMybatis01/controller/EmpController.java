package com.oracle.oBootMybatis01.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.DeptVO;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;
import com.oracle.oBootMybatis01.model.Member1;
import com.oracle.oBootMybatis01.service.EmpService;
import com.oracle.oBootMybatis01.service.Paging;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EmpController {

	private final EmpService es;
	private final JavaMailSender mailSender; // 이것또한 생성자 생성됨

	@RequestMapping(value = "listEmp")
	public String empList(Emp emp, Model model) {
		System.out.println("EmpController Start listEmp...");
		if (emp.getCurrentPage() == null)
			emp.setCurrentPage("1");

		// emp 전체 count 15
		int totalEmp = es.totalEmp();
		System.out.println("EmpController Start totalEmp->" + totalEmp);

		// Paging 작업
		Paging page = new Paging(totalEmp, emp.getCurrentPage());
		// Parameter emp --> Page만 추가 Setting
		emp.setStart(page.getStart()); // 시작시 1
		emp.setEnd(page.getEnd()); // 시작시 10

		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("EmpController list listEmp.size()=>" + listEmp.size());

		model.addAttribute("totalEmp", totalEmp);
		model.addAttribute("listEmp", listEmp);
		model.addAttribute("page", page);

		return "list";

	}

	@GetMapping(value = "detailEmp")
	public String detailEmp(Emp emp1, Model model) {
		System.out.println("EmpController Start detailEmp...");
//		1. EmpService안에 detailEmp method 선언
//		   1) parameter : empno
//		   2) Return      Emp
//
//		2. EmpDao   detailEmp method 선언 
////		                    mapper ID   ,    Parameter
//		emp = session.selectOne("tkEmpSelOne",    empno);
//		System.out.println("emp->"+emp1);
		Emp emp = es.detailEmp(emp1.getEmpno());
		model.addAttribute("emp", emp);

		return "detailEmp";
	}

	@GetMapping(value = "updateFormEmp")
	public String updateFormEmp(Emp emp1, Model model) {
		System.out.println("EmpController Start updateForm...");

		Emp emp = es.detailEmp(emp1.getEmpno());
		System.out.println("emp.getEname()->" + emp.getEname());
		System.out.println("emp.getHiredate()->" + emp.getHiredate());
		// System.out.println("hiredate->"+hiredate);
		// 문제
		// 1. DTO String hiredate
		// 2.View : 단순조회 OK ,JSP에서 input type="date" 문제 발생
		// 3.해결책 : 년월일만 짤라 넣어 주어야 함
		String hiredate = "";
		if (emp.getHiredate() != null) {
			hiredate = emp.getHiredate().substring(0, 10);
			emp.setHiredate(hiredate);
		}
		System.out.println("hiredate->" + hiredate);

		model.addAttribute("emp", emp);
		return "updateFormEmp";

	}

	@PostMapping(value = "updateEmp")
	public String updateEmp(Emp emp, Model model) {
		log.info("updateEmp Start...");
//      1. EmpService안에 updateEmp method 선언
//      1) parameter : Emp
//      2) Return      updateCount (int)
//
//   2. EmpDao updateEmp method 선언
////                              mapper ID   ,    Parameter
		int updateCount = es.updateEmp(emp);
		System.out.println("empController es.updateEmp updateCount-->" + updateCount);
		model.addAttribute("uptCnt", updateCount); // test code
		model.addAttribute("kk3", "Message Test"); // test code
		// return "forward:listEmp": model.addAttribute를 데리고 감
		return "redirect:listEmp";
		// 단순히 페이지간의 이동
	}

	@RequestMapping(value = "writeFormEmp")
	public String wirteFormEmp(Model model) {
		System.out.println("empController writeFormEmp Start...");
		// 관리자 사번만 get
		List<Emp> empList = es.listManager();
		System.out.println("EmpController writeFormEmp empList.size()->" + empList.size());
		model.addAttribute("EmpMngList", empList);// emp Manager List
		// 1. Service , DAO --> listManager
		// 2. Mapper -> tkSelectManager
		// 1) Emp Table --> MGR 등록된 정보 Get
		// 부서(코드,부서명)
		List<Dept> deptList = es.deptSelect();
		model.addAttribute("deptList", deptList);
		System.out.println("EmpController writeFormEmp deptList.size()->" + deptList.size());
		return "writeFormEmp";
	}

	@PostMapping(value = "writeEmp")
	public String writeEmp(Emp emp, Model model) {
		System.out.println("EmpController Start writeEmp...");
		int insertResult = es.insertEmp(emp);
		if (insertResult > 0) {
			return "redirect:listEmp";
		} else {
			model.addAttribute("msg", "입력 실패 확인해보세요");
			return "forward:writeFormEmp";
		}
	}

	@RequestMapping(value = "writeFormEmp3")
	public String wirteFormEmp3(Model model) {
		System.out.println("empController writeFormEmp3 Start...");
		// 관리자 사번만 get
		List<Emp> empList = es.listManager();
		System.out.println("EmpController writeFormEmp3 empList.size()->" + empList.size());
		model.addAttribute("EmpMngList", empList);

		List<Dept> deptList = es.deptSelect();
		model.addAttribute("deptList", deptList);
		System.out.println("EmpController writeFormEmp3 deptList.size()->" + deptList.size());
		return "writeFormEmp3";
	}

	// validation 참조시->wirteEmp3 사용
	@PostMapping(value = "writeEmp3")
	public String writeEmp3(@ModelAttribute("emp") @Valid Emp emp, BindingResult result, Model model) {
		System.out.println("EmpController Start writeEmp3...");

		// validation 오류시 result
		if (result.hasErrors()) {
			System.out.println("EmpController writeEmp3 hasErrors..");
			model.addAttribute("msg", "BindingResult 입력 실패. 확인해보세요");
			return "forward:writeFormEmp3";
		}
		// service, dao, mapper명(insertEmp)까지->insert
		int insertResult = es.insertEmp(emp);
		if (insertResult > 0) {
			return "redirect:listEmp";
		} else {
			model.addAttribute("msg", "입력 실패. 확인해보세요");
			return "forward:writeFormEmp3";
		}

	}

	@GetMapping(value = "confirm")
	public String confirm(Emp emp1, Model model) {
		Emp emp = es.detailEmp(emp1.getEmpno());
		model.addAttribute("empno", emp1.getEmpno());
		if (emp != null) {
			System.out.println("empCOntroller confirm 중복된 사번..");
			model.addAttribute("msg", "중복된 사번입니다");
			return "forward:writeFormEmp";
		} else {
			System.out.println("empController confirm 사용 가능한 사번");
			model.addAttribute("msg", "사용 가능한 사번입니다");
			return "forward:writeFormEmp";
		}

	}

	@RequestMapping(value = "deleteEmp")
	public String deleteEmp(Emp emp, Model model) {
		System.out.println("EmpController Start delete...");
		int result = es.deleteEmp(emp.getEmpno());
		return "redirect:listEmp";
	}

	@RequestMapping(value = "listSearch3")
	public String listSearch3(Emp emp, Model model) {

		int totalEmp = es.condTotalEmp(emp); // Emp면 14명
		System.out.println("EmpController listSearch3 totalEmp->" + totalEmp);
		// paging 작업
		Paging page = new Paging(totalEmp, emp.getCurrentPage());
		// 파라미터 emp-> page만 추가 setting
		emp.setStart(page.getStart());
		emp.setEnd(page.getEnd());

		List<Emp> listSearchEmp = es.listSearchEmp(emp);
		System.out.println("EmpController listSearch3 listSearchEmp.size()->" + listSearchEmp.size());
		model.addAttribute("totalEmp", totalEmp);
		model.addAttribute("listEmp", listSearchEmp);
		model.addAttribute("page", page);
		return "list";
	}

	@GetMapping(value = "listEmpDept")
	public String listEmpDept(Model model) {
		System.out.println("EmpController listEmpDept Start...");
		// service, dao ->listEmpDEpt
		// Mapper만->tkListEmpDept
		List<EmpDept> listEmpDept = es.listEmpDept();
		model.addAttribute("listEmpDept", listEmpDept);
		return "listEmpDept";

	}

	@RequestMapping(value = "mailTransport")
	public String mailTransport(HttpServletRequest request, Model model) {
		System.out.println("mailSending..");
		String tomail = "ujm1jaman@gmail.com"; // 받는사람 이메일
		System.out.println(tomail);
		String setfrom = "woakswoaks@gmail.com"; // 보내는사람 이메일
		String title = "mailTransport입니다"; // 제목
		try {
			// Mime : 전자우편 인터넷 표준 format
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setFrom(setfrom); // 보내는 사람 생략하면 정삭자동하지않음
			messageHelper.setTo(tomail); // 받는사람 이메일
			messageHelper.setSubject(title); // 메일제목은 생략 가능함
			String tempPassword = (int) (Math.random() * 999999) + 1 + "";
			messageHelper.setText("임시비밀번호입니다" + tempPassword); // 메일 내용
			System.out.println("임시 비밀번호입니다:" + tempPassword);
			mailSender.send(message);
			model.addAttribute("check", 1); // 정상 전달
			// 이 아래에는 db logic 구성이 들어가야한다.
		} catch (Exception e) {
			System.out.println("mailTransport e.getMessage()" + e.getMessage());
			model.addAttribute("check", 2);
		}
		return "mailResult";
	}

	// procedure test 입려화면
	@RequestMapping(value = "writeDeptIn")
	public String writeDeptIn(Model model) {
		System.out.println("writeDeptIn Start..");
		return "writeDept3"; // writeDept3.jsp로 이동

	}

	// 프로시저 입력
	@PostMapping(value = "writeDept")
	public String writeDept(DeptVO deptVO, Model model) {
		es.insertDept(deptVO);
		if (deptVO == null) {
			System.out.println("deptVo null");
		} else {
			System.out.println("deptVO.getOdeptno()->" + deptVO.getOdeptno());
			System.out.println("deptVO.getOdname()->" + deptVO.getOdname());
			System.out.println("deptVO.getOloc()->" + deptVO.getOloc());
			model.addAttribute("msg", "정상 입력되었습니다");
			model.addAttribute("dept", deptVO);

		}
		return "writeDept3";
	}

	// 커서-맵적용
	@GetMapping(value = "writeDeptCursor")
	public String writeDeptCursor(Model model) {
		System.out.println("EmpController writeDeptCursor start..");
		// 부서범위 조회
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("sDeptno", 30);
		map.put("eDeptno", 54);
		// 이렇게 Map으로 넘기는 방법은, 내맘대로 쓸수 있고
		// dto로 넘기는 방법은, 팀원끼리 합의해서 세팅해놔야 사용
		es.selListDept(map);

		List<Dept> deptLists = (List<Dept>) map.get("dept");
		// 맵 안에 dept라는 버퍼가 들어가고, 그걸 리스트로
		/*
		 * 다시 말해, // 30 SALES30 CHICAGO30 // 40 OPERATIONS BOSTON // 52 구매2 홍대2 // 53
		 * 인사팀 이대 이렇게 4개의 행이 여기에 들어가는 것
		 */

		for (Dept dept : deptLists) {
			System.out.println("dept.getDname->" + dept.getDname());
			System.out.println("dept.getLoc->" + dept.getLoc());
		}
		System.out.println("deptList Size->" + deptLists.size());
		model.addAttribute("deptList", deptLists);
		return "writeDeptCursor";

	}

	// interCeptor 시작화면
	@RequestMapping(value = "interCeptorForm")
	public String interCeptorForm(Model model) {
		System.out.println("interCeptorForm Start");
		return "interCeptorForm";
	}

	@RequestMapping(value = "interCeptor")
	// public String interCeptor(Member1 member, Model model) {
	public void interCeptor(Member1 member1, Model model) {
		System.out.println("EmpController interCeptor Test Start");
		System.out.println("EmpController interCeptor id->" + member1.getId());
		// 존재:1 비존재:0
		int memCnt = es.memCount(member1.getId());

		System.out.println("EmpController interCeptor memCnt->" + memCnt);
		model.addAttribute("id", member1.getId());
		model.addAttribute("memCnt", memCnt);
		System.out.println("interCeptor Test End");
		// return "interCeptor"; //user 존재하면 user 이용 조회 page
		return;
	}

	// sampleInterceptor 내용 받아 처리
	@RequestMapping(value = "doMemberWrite", method = RequestMethod.GET)
	public String doMemberWrite(Model model, HttpServletRequest request) {
		String ID = (String) request.getSession().getAttribute("ID");
		System.out.println("doMemberWrite부터 하세요");
		model.addAttribute("id", ID);
		return "doMemberWrite";
	}

	// interCeptor 진행 Test
	@RequestMapping(value = "doMemberList")
	public String doMemberList(Model model, HttpServletRequest request) {
		String ID = (String) request.getSession().getAttribute("ID");
		System.out.println("doMemberList Test Start Id->" + ID);
		Member1 member1 = null;
		// Member List Get Service
		List<Member1> listMem = es.listMem(member1);
		model.addAttribute("ID", ID);
		model.addAttribute("listMem", listMem);
		return "doMemberList";
	}

	// ajaxForm Test 입력화면
	@RequestMapping(value = "ajaxForm")
	public String ajaxForm(Model model) {
		System.out.println("ajaxForm Star..");
		return "ajaxForm";
	}

	@ResponseBody
	@RequestMapping(value = "getDeptName")
	public String getDeptName(Dept dept, Model model) {
		System.out.println("deptno" + dept.getDeptno());
		String deptName = es.deptName(dept.getDeptno());
		System.out.println("deptName->" + deptName);
		return deptName;
	}

	// ajax List Test
	@RequestMapping(value = "listEmpAjaxForm")
	public String listEmpAjaxForm(Model model) {
		Emp emp = new Emp();
		System.out.println("Ajax List Test STart..");
		// parameter emp-> page만 추가 Setting
		emp.setStart(1); // 시작시 1
		emp.setEnd(10); // 끝 10

		List<Emp> listEmp = es.listEmp(emp);
		System.out.println("EmpController listEmpAjax listEmp.size()->" + listEmp);
		model.addAttribute("result", "kkk");
		model.addAttribute("listEmp", listEmp);
		return "listEmpAjaxForm";
	}

	@ResponseBody // ajax니까 이거 넣어야됨. 안그러면 404 에러
	@RequestMapping(value = "empSerializeWrite")
	public Map<String, Object> empSerializeWrite(@RequestBody @Valid Emp emp) {
		// 맵에 하나하나 Emp 담아다가 객체로, 한꺼번에, 전달하기 위한 목적
		System.out.println("EmpController Start..");
		System.out.println("EmpController emp->" + emp);
		int writeResult = 1;

		// int writeResult=kkk.writeEMp(emp);
		// String followingProStr=Integer.toString(followingPro);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		System.out.println("EmpController empSerializeWrite writeResult->" + writeResult);

		resultMap.put("writeResult", writeResult); // 객체로 넣음
		return resultMap; // 객체를 넘겨줌
	}

	// listEmpAjaxForm2에서 리스트로 띄워주기 위함
	@RequestMapping(value = "listEmpAjaxForm2")
	public String listEmpAjaxForm2(Model model) {
		System.out.println("listEmpAjaxForm2 Start..");
		Emp emp = new Emp();
		System.out.println("Ajax  List Test Start");
		// Parameter emp --> Page만 추가 Setting
		emp.setStart(1); // 시작시 1
		emp.setEnd(15); // 시작시 15
		List<Emp> listEmp = es.listEmp(emp);
		model.addAttribute("listEmp", listEmp); // 여기서 만든 listEmp를 "listEmp"로 해서 뒤에서 쓸 예정
		return "listEmpAjaxForm2";
	}

	@RequestMapping(value = "listEmpAjaxForm3")
	public String listEmpAjaxForm3(Model model) {
		System.out.println("listEmpAjaxForm3 Start..");
		Emp emp = new Emp();
		// Parameter emp --> Page만 추가 Setting
		emp.setStart(1); // 시작시 1
		emp.setEnd(15); // 시작시 15
		List<Emp> listEmp = es.listEmp(emp);
		model.addAttribute("listEmp", listEmp);
		return "listEmpAjaxForm3";
	}

	@ResponseBody
	@RequestMapping(value = "empListUpdate")
	public Map<String, Object> empListUpdate(@RequestBody @Valid List<Emp> listEmp) {
		System.out.println("EmpController empListUpdate Start...");
		int updateResult = 1;

		for (Emp emp : listEmp) {
			System.out.println("EmpController empListUpdate emp->" + emp);
		}
		// int writeResult = kkk.lisrUpdateEmp(emp);
		// String followingProStr = Integer.toString(followingPro);

		System.out.println("EmpController empListUpdate writeResult -> " + updateResult);
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("updateResult", updateResult);
		return resultMap;
	}

	@ResponseBody
	@RequestMapping(value = "transactionInsertUpdate")
	public String transactionInsertUpdate(Emp emp, Model model) {
		System.out.println("EmpController transactionInsertUpdate Start...");
		// member Insert 성공 과 실패
		int returnMember = es.transactionInsertUpdate();
		System.out.println("EmpController transactionInsertUpdate returnMember=>" + returnMember);
		String returnMemberString = String.valueOf(returnMember);

		return returnMemberString;

	}

}
