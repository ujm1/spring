package com.oracle.oBootJpa02.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="member2")
public class Member {
	private Long id;
	private String name;
	private Long sal;
}
