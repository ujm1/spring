package com.oracle.oBootJpa02.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data						//객체 시퀀스                  //db시퀀스
@SequenceGenerator(name = "team_seq_gen", sequenceName = "team_seq_generator", initialValue = 1, allocationSize = 1)
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="team_seq_gen")
	private Long team_id;
	@Column(name = "teamname")
	private String name;
	private Long sal;

}
