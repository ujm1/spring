package com.oracle.test.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.test.domain.Emp;

@Controller
public class HelloController {
	private static final Logger logger=LoggerFactory.getLogger(HelloController.class);
	
	@RequestMapping("hello")
	public String hellouha(Model model) {
		System.out.println("hello start...");
		logger.info("start");
		model.addAttribute("parameter","boot start...");
		return "hello";
		
	}
	
	@ResponseBody
	@GetMapping("ajaxString")
	public String ajaxString(@RequestParam("ajaxName") String aName) {
		System.out.println("HelloController ajaxString aName->"+aName);
		return aName;
		
	}
	
	@ResponseBody
	@GetMapping("ajaxEmp") 
	//http://localhost:8381/ajaxEmp?empno=kkk&ename=aaa
	public Emp ajaxEmp(@RequestParam("empno") String empno, 
			@RequestParam("ename") String ename) {
		System.out.println("HelloController ajaxEmp empno->"+empno);
		logger.info("ename:{}",ename);
		Emp emp=new Emp();
		emp.setEmpno(empno);
		emp.setEname(ename);
		
		return emp;
		//이러면 콘솔에 HelloController ajaxEmp empno->kkk 출력되고
		//브라우저엔 json인 {"empno":"kkk","ename":"aaa"}
	
	}
			
}
