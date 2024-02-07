package com.oracle.oBootMybatis01.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmpDaoImpl implements EmpDao {

	private final SqlSession session;
	
	public int totalEmp() {
		return 0;
		
	}
}
