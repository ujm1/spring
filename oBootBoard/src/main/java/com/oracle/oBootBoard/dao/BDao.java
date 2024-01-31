package com.oracle.oBootBoard.dao;

import java.util.ArrayList;

import com.oracle.oBootBoard.dto.BDto;

public interface BDao {
	public ArrayList<BDto> boardList();
	public void write(String bName, String bTitle, String bContent);	
	public BDto contentView(String bId);
}
