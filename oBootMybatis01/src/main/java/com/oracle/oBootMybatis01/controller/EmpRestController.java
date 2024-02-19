package com.oracle.oBootMybatis01.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.oBootMybatis01.model.Dept;
import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.SampleVO;
import com.oracle.oBootMybatis01.service.EmpService;

import lombok.RequiredArgsConstructor;

@RestController // = @controller+@responseBody
@RequiredArgsConstructor
public class EmpRestController {
	private final EmpService es;
	
	@RequestMapping("/helloText")
	public String helloText() {
		System.out.println("EmpRestController Start...");
		String hello="안녕";
		//String Converter
		return hello;
	} /* html에서 <a href="/helloText">helloText </a>
	helloText 링크 클릭 -> 여기로 이동-> 리턴 안녕 이니까, 화면에 안녕 출력 */
	
	@RequestMapping("/sample/sendVO2")
	public SampleVO sendVO2(Dept dept) {
		System.out.println("@RestController dept.getDeptno()"+dept.getDeptno());
		SampleVO vo=new SampleVO();
		vo.setFirstName("길동");
		vo.setLastName("홍");
		vo.setMno(dept.getDeptno());
		return vo;
	}
	
	@RequestMapping("/sendVO3")
	public List<Dept> sendVO3() {
		System.out.println("@RestController sendVO3 start..");
		List<Dept> deptList=es.deptSelect();
		return deptList;
	}
	
	@RequestMapping("/empnoDelete")
	public String  empnoDelete(Emp emp) {
		System.out.println("@RestController empnoDelete  START");
		System.out.println("@RestController empnoDelete emp.getEname()->" + emp.getEname());
		int    delStatus = es.deleteEmp(emp.getEmpno());
		String delStatusStr = Integer.toString(delStatus);
		return delStatusStr;
	}
}
