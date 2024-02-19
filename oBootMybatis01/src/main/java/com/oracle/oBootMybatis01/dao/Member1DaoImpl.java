package com.oracle.oBootMybatis01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.oracle.oBootMybatis01.model.Member1;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class Member1DaoImpl implements Member1Dao {

	// myBatis DB 연동
	private final SqlSession session;
	
	//트랜잭션
	private final PlatformTransactionManager transactionManager;

	public int memCount(String id) {
		int result = 0;
		System.out.println("Member1DaoImpl id" + id);
		try {
			result = session.selectOne("memCount", id);
		} catch (Exception e) {
			System.out.println("Member1DaoImpl memCount Exception->" + e.getMessage());
		}
		return result;
	}

	public List<Member1> listMem(Member1 member1) {
		System.out.println("Member1DaoImpl listMem start...");
		List<Member1> listMember1 = null;
		try {
			listMember1 = session.selectList("listMember1", member1);
		} catch (Exception e) {
			System.out.println("Member1DaoImpl listMem Exception->"+e.getMessage());
		}
		return listMember1;	
	}
	
	@Override
	public int transactionInsertUpdate() {
		int result = 0;
		System.out.println("Member1DaoImpl transactionInsertUpdate Start..." );
		Member1 member1 = new Member1();
		Member1 member2 = new Member1();

		
	    try {
			// 두개의 transaction test 성공과 실패
			// 결론 -> SqlSession은 하나 실행할 때마다 자동 커밋..
			member1.setId("1005");
			member1.setPassword("2345");
			member1.setName("강유6");
			result = session.insert("insertMember1", member1);
			System.out.println("Member1DaoImpl transacitonINsertUpdate member1 result->" + result);
			// 같은 pk로 실패 유도
			member2.setId("1005");
			member2.setPassword("3457");
			member2.setName("이순신7");
			result = session.insert("insertMember1", member2);
			System.out.println("Member1Dao transaciotionInsertUpdat  member2 result->" + result);

		} catch (Exception e) {
			System.out.println("Member1DaoImpll transaciontNINsertUpdate Exception->" + e.getMessage());
			result = -1;
		}

		return result;
		// ORA-00001: unique constraint (SCOTT.PK_MEMBER1_ID) violated. 두 개가 충돌..
	}

	@Override
	public int transactionInsertUpdate3() {
		int result = 0;
		System.out.println("Member1DaoImpl transactionInsertUpdate3 Start...");
		Member1 member1 = new Member1();
		Member1 member2 = new Member1();

		//transaction 관리
		//springFramework에서 가져옴
		TransactionStatus txStatus=
				transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			// 두개의 transaction test 성공과 실패
			// 결론 -> SqlSession은 하나 실행할 때마다 자동 커밋..
			
			member1.setId("1007");
			member1.setPassword("2345");
			member1.setName("강유7");
			result = session.insert("insertMember1", member1);
			System.out.println("Member1DaoImpl transacitonINsertUpdate3 member1 result->" + result);
			
			member2.setId("1007");
			member2.setPassword("3457");
			member2.setName("이순신7");
			result = session.insert("insertMember1", member2);
			System.out.println("Member1Dao transaciotionInsertUpdate3  member2 result->" + result);
			
			transactionManager.commit(txStatus);
			//동시에 커밋되거나(트랜잭션 단위로 커밋), 
			//동시에 롤백되거나. 이렇게 쓰려고 트랜잭션을 하는 것.
			
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			//오류나면 동시 롤백 (트랜잭션 단위로 롤백)
			System.out.println("Member1DaoImpll transaciontNINsertUpdate3 Exception->" + e.getMessage());
			result = -1;
		}

		return result;
		
	}

}
