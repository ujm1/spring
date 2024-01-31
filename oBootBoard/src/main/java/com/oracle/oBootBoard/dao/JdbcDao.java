package com.oracle.oBootBoard.dao;

import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import com.oracle.oBootBoard.dto.BDto;


public class JdbcDao implements BDao {
	private final DataSource dataSource;

	public JdbcDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	private Connection getConnection() {
		return DataSourceUtils.getConnection(dataSource);

	}

	public ArrayList<BDto> boardList() {
		ArrayList<BDto> bList = new ArrayList<BDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println("BDao boardList start...");

		try {// 생성자 이용하기
			conn = getConnection();

			String sql = "SELECT bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent "
					+ "FROM mvc_board order by bGroup desc, bStep asc";
			pstmt = conn.prepareStatement(sql);
			System.out.println("BDao query->"+sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");

				BDto bdto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				bList.add(bdto);

			}
		} catch (SQLException e) {
			System.out.println("list datasource->"+e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			
		}
		return bList;
	}

	public void write(String bName, String bTitle, String bContent) {
		
		// 1. Insert Into mvc_board
					// 2 prepareStatement
					// 3. mvc_board_seq
		// 4. bId , bGroup 같게
					// 5.  bStep, bIndent, bDate --> 0, 0 , sysdate
	}
	
	public BDto contentView(String strId) {BDto dto=null;
		return dto;
	}
	
	
}
