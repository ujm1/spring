package com.oracle.oBootMybatis01.dao;

import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;

import java.util.List;

public interface EmpDao {
    int totalEmp();

    List<Emp> listEmp(Emp var1);

    Emp detailEmp(int var1);

    int updateEmp(Emp var1);
    
    int insertEmp(Emp emp);

	int deleteEmp(int empno);

	List<Emp> empSearchList3(Emp emp);

	List<EmpDept> listEmpDept();
}
