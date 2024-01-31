package com.oracle.oBootDBConnect.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.oracle.oBootDBConnect.domain.Member1;

//@Repository
public class JdbcMemberRepository implements MemberRepository {

	// jdbc사용
	private final DataSource dataSource;

	@Autowired
	public JdbcMemberRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private Connection getConnection() {
		return DataSourceUtils.getConnection(dataSource);
	}

	@Override	//저장, 즉 추가
	public Member1 save(Member1 member1) {
		String sql = "insert into member7(id,name) values(member7_seq.nextval,?)";
		System.out.println("JdbcMemberRepository sql->" + sql);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
	

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member1.getName()); 
			//물음표에. 즉 number seq name 이거 삽입 위한 목적
			pstmt.executeUpdate();
			System.out.println("JdbcMemberRepositor pstmt.executeUpdate After");
			return member1;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			close(conn, pstmt, rs);
		}
	}

	// close 모듈 분리
	private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (conn != null)
				close(conn); // 이것만 다르게 아래에
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
					//커넥션만 다르게.
	private void close(Connection conn) throws SQLException {
		DataSourceUtils.releaseConnection(conn, dataSource);
	}

	@Override			//db에서 찾아서 멤버들 다 리턴하기
	public List<Member1> findAll() {
		String sql = "select * from member7";
		Connection conn = null;
		PreparedStatement pstmt = null;
		System.out.println("jdbcMembersrepository findAll sql->"+sql);
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<Member1> members = new ArrayList<>();
			while (rs.next()) {
				Member1 member = new Member1();
				member.setId(rs.getLong("id"));
				member.setName(rs.getString("name"));
				members.add(member);
				//리스트에 추가
				}
			return members; //리스트 리턴
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			close(conn, pstmt, rs);
		}

	}

}
