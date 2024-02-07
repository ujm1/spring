package com.oracle.oBootJpa02.domain;

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

@Entity
@SequenceGenerator(name = "member_seq_gen", sequenceName = "member_seq_generate", initialValue = 1, allocationSize = 1) // 시퀀스
																														// 생성
@Table(name = "member2")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_gen")
	@Column(name = "member_id", precision = 10)
	private Long id;
	@Column(name = "user_name", length = 50)
	private String name;
	private Long sal;

	// 관계설정
	@ManyToOne // 다대일
	@JoinColumn(name = "team_id") // 이러면 team 테이블의 team_id가 외래키가 되어 이를 참조하게 된다.
	private Team team;

	@Transient // 매핑 무시. 이거 안쓰면 teamname도 테이블 컬럼화됨.
	private String teamname;
	@Transient
	private Long teamid;

	public Member() {
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Long getSal() {
		return this.sal;
	}

	public Team getTeam() {
		return this.team;
	}

	public String getTeamname() {
		return this.teamname;
	}

	public Long getTeamid() {
		return this.teamid;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSal(Long sal) {
		this.sal = sal;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public void setTeamname(String teamname) {
		this.teamname = teamname;
	}

	public void setTeamid(Long teamid) {
		this.teamid = teamid;
	}

	public String toString() {
		String var10000 = String.valueOf(this.getId());
		return "Member(id=" + var10000 + ", name=" + this.getName() + ", sal=" + String.valueOf(this.getSal())
				+ ", team=" + String.valueOf(this.getTeam()) + ", teamname=" + this.getTeamname() + ", teamid="
				+ String.valueOf(this.getTeamid()) + ")";
	}
}
