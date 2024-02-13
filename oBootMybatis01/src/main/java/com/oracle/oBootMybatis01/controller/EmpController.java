package com.oracle.oBootMybatis01.controller;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;
import com.oracle.oBootMybatis01.service.EmpService;
import com.oracle.oBootMybatis01.service.Paging;

import jakarta.validation.Valid;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmpController {
	private static final Logger log = LoggerFactory.getLogger(EmpController.class);
	private final EmpService es;

	public EmpController(EmpService es) {
		this.es = es;
	}

	@RequestMapping({ "listEmp" })
	public String empList(Emp emp, Model model) {
		System.out.println("EmpController Start listEmp...");
		if (emp.getCurrentPage() == null)
			emp.setCurrentPage("1");

		// emp 전체 count 15
		int totalEmp = es.totalEmp();
		System.out.println("EmpController Start totalEmp->" + totalEmp);

		// Paging 작업
		Paging page = new Paging(totalEmp, emp.getCurrentPage());
		emp.setStart(page.getStart());
		emp.setEnd(page.getEnd());
		List<Emp> listEmp = this.es.listEmp(emp);
		System.out.println("EmpController list listEmp.size()=>" + listEmp.size());
		model.addAttribute("totalEmp", totalEmp);
		model.addAttribute("listEmp", listEmp);
		model.addAttribute("page", page);
		return "list";
	}

	@GetMapping({ "detailEmp" })
	public String detailEmp(Emp emp1, Model model) {
		System.out.println("EmpController Start detailEmp...");
		Emp emp = this.es.detailEmp(emp1.getEmpno());
		model.addAttribute("emp", emp);
		return "detailEmp";
	}

	@GetMapping({ "updateFormEmp" })
	public String updateFormEmp(Emp emp1, Model model) {
		System.out.println("EmpController Start updateForm...");
		Emp emp = this.es.detailEmp(emp1.getEmpno());
		System.out.println("emp.getEname()->" + emp.getEname());
		System.out.println("emp.getHiredate()->" + emp.getHiredate());
		String hiredate = "";
		if (emp.getHiredate() != null) {
			hiredate = emp.getHiredate().substring(0, 10);
			emp.setHiredate(hiredate);
		}

		System.out.println("hiredate->" + hiredate);
		model.addAttribute("emp", emp);
		return "updateFormEmp";
	}

	@PostMapping({ "updateEmp" })
	public String updateEmp(Emp emp, Model model) {
		log.info("updateEmp Start...");
		int updateCount = this.es.updateEmp(emp);
		System.out.println("empController es.updateEmp updateCount-->" + updateCount);
		model.addAttribute("uptCnt", updateCount); // test code
		model.addAttribute("kk3", "Message Test"); // test code
		// return "forward:listEmp";
		// model.addAttribute를 데리고 감
		return "redirect:listEmp";
		// 단순히 페이지간의 이동
	}

	@RequestMapping(value = "writeFormEmp")
	public String wirteFormEmp(Model model) {
		System.out.println("empController writeFormEmp Start...");
		// 관리자 사번만 get
		List<Emp> empList = es.listManager();
		System.out.println("EmpController writeFormEmp empList.size()->" + empList.size());
		model.addAttribute("EmpMngList", empList);

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
		// Emp 전체 Count 25
		int totalEmp = es.condTotalEmp(emp); // 이상한 값 전달됨
		System.out.println("EmpController listSearch3 totalEmp->" + totalEmp);
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

}
