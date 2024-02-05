package com.oracle.oBootJpaApi01.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@SequenceGenerator(name = "member_seq_gen5", sequenceName = "member_seq_generate5", initialValue = 1, allocationSize = 1)
// 시퀀스 생성
@Table(name = "member5")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_gen5")
	@Column(name = "member_id")
	private Long id;
	@Column(name = "user_name", length = 50)
	private String name;
	private Long sal;

	// 관계설정
	@ManyToOne // 다대일
	@JoinColumn(name = "team_id") // 이러면 team 테이블의 team_id가 외래키가 되어 이를 참조하게 된다.
	private Team team;
	
	@Transient //매핑 무시. 이거 안쓰면 teamname도 테이블 컬럼화됨.
	private String teamname;
	
	@Transient
	private Long teamid;

}
