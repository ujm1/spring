package com.oracle.oBootBoard.dao;

import java.sql.Connection;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import com.oracle.oBootBoard.dto.BDto;

public class JdbcDao implements BDao {
	private final DataSource dataSource;
	
	public JdbcDao(DataSource dataSource) {this.dataSource=dataSource;}
	
	private Connection getConnection() {
		return DataSourceUtils.getConnection(dataSource);
		
	}
	
	public ArrayList<BDto> boardList() {
		return null;
	}
}
