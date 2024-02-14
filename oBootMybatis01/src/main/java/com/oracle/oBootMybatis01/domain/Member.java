package com.oracle.oBootMybatis01.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "member3")
@Getter //@data로 해도 되지만. 이렇게 해도 된다는걸 보여주기 위함. 
@Setter //@data는 이거 세개를 다 포함
@ToString
public class Member {
	@Id //이거 안하면(pk 안두면) 에러남
	private Long id;
	private String name;
	private String password;
	
	@Column(nullable=false, columnDefinition = "date default sysdate")
	private Date reg_date=new Date();
	
}
