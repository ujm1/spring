package com.oracle.oBootMybatis01.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
				//프로시저용. dto와 유사하면서 다른것. 면접 나올 수 있음
public class DeptVO {
	//입력
	private int deptno;
	private String dname;
	private String loc;
	//출력
	private int odeptno;
	private String odname;
	private String oloc;
}
