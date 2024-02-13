package com.oracle.oBootMybatis01.model;

import lombok.Data;

@Data
public class EmpDept {

	//EMp용
	private int empno;	private String ename;
	private String job;	private int mgr;
	private String hiredate;	private int sa;
	private int comm;	private int deptno;
	
	//Dept용 (많다는 가정)
	private String dname;
	private String loc;
}
