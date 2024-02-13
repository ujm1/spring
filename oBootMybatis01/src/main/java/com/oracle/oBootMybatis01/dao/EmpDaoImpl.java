package com.oracle.oBootMybatis01.dao;

import com.oracle.oBootMybatis01.model.Emp;
import com.oracle.oBootMybatis01.model.EmpDept;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class EmpDaoImpl implements EmpDao {
	// DataSource, Entity와 같은, Mybatis에서 사용하는 DB 연동 객체
	private final SqlSession session;

	public EmpDaoImpl(SqlSession session) {
		this.session = session;
	} // 이거 있어서, 생성자작업 @로 대체 안해도..

	public int totalEmp() {
		int totEmpCount = 0;
		System.out.println("EmpDaoImpl Start total...");

		try {
			totEmpCount = (Integer) this.session.selectOne("com.oracle.oBootMybatis01.EmpMapper.empTotal");
			System.out.println("EmpDaoImpl totalEmp totEmpCount->" + totEmpCount);
		} catch (Exception var3) {
			System.out.println("EmpDaoImpl totalEmp Exception->" + var3.getMessage());
		}

		return totEmpCount;
	}

	public List<Emp> listEmp(Emp emp) {
		List<Emp> empList = null;
		System.out.println("EmpDaoImpl listEmp Start ...");

		try {
			empList = this.session.selectList("tkEmpListAll", emp);
			System.out.println("EmpDaoImpl listEmp empList.size()->" + empList.size());
		} catch (Exception var4) {
			System.out.println("EmpDaoImpl listEmp e.getMessage()->" + var4.getMessage());
		}

		return empList;
	}

	public Emp detailEmp(int empno) {
		System.out.println("EmpDaoImpl detail start..");
		Emp emp = new Emp();

		try {
			emp = (Emp) this.session.selectOne("tkEmpSelOne", empno);
			System.out.println("EmpDaoImpl detail getEname->" + emp.getEname());
		} catch (Exception var4) {
			System.out.println("EmpDaoImpl detail Exception->" + var4.getMessage());
		}

		return emp;
	}

	public int updateEmp(Emp emp) {
		System.out.println("EmpDaoImpl update start..");
		int updateCount = 0;

		try {
			updateCount = this.session.update("tkEmpUpdate", emp);
		} catch (Exception var4) {
			System.out.println("EmpDaoImpl updateEmp Exception->" + var4.getMessage());
		}

		return updateCount;
	}

	public int insertEmp(Emp emp) {
		int result = 0;
		System.out.println("EmpDaoImpl insert Start...");
		try {
			result = session.insert("insertEmp", emp);
			// mybatis에선 자동으로, insert는 여러개인경우 1 자동 리턴해줌
			// update는 업데이트된 행의 개수, delete는 삭제된 행의 개수 리턴
		} catch (Exception e) {
			System.out.println("EmpDaoImpl insert Exceiption->" + e.getMessage());
		}
		return result;
	}

	@Override
	public int deleteEmp(int empno) {
		int result = 0;
		System.out.println("EmpDaoImpl delete Start...");
		try {
			result = session.delete("deleteEmp", empno);
		} catch (Exception e) {
			System.out.println("EmpDaoImpl delete Exception" + e.getMessage());
		}
		return result;
	}
	
	public List<Emp> empSearchList3(Emp emp) {
		List<Emp> empSearchList3=null;
		System.out.println("EmpDaoImpl empSearchList3 Start...");
		System.out.println("EmpDaoImpl empSearch3 emp->"+emp);
		try {									//Map id		//parameter
			empSearchList3=session.selectList("tkEmpSearchList3",emp);
			
		} catch (Exception e) {
			System.out.println("EmpDaoImpl listEmp Exception->"+e.getMessage());
		}
		return empSearchList3;
	}
	
	public List<EmpDept> listEmpDept() {
		System.out.println("EmpServiceImpl listEmpDept Start...");
		List<EmpDept> empDept =null;
		try {
			empDept=session.selectList("tkListEmpDept");
			System.out.println("EMpDaoImpl listEmpDept empDept.size()->"+empDept.size());
		} catch (Exception e) {
			System.out.println("EmpDaoImpl delete Exce4ption->"+e.getMessage());
		} return empDept;
		
	}

}
