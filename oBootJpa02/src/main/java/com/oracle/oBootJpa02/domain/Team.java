package com.oracle.oBootJpa02.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
                               // 객체 시퀀스 //db시퀀스
@SequenceGenerator(name = "team_seq_gen", sequenceName = "team_seq_generator", initialValue = 1, allocationSize = 1)
public class Team {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_seq_gen")
	private Long team_id;
	@Column(name = "teamname")
	private String name;

	public Long getTeam_id() {
		return this.team_id;
	}

	public String getName() {
		return this.name;
	}

	public void setTeam_id(Long team_id) {
		this.team_id = team_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof Team)) {
			return false;
		} else {
			Team other = (Team) o;
			if (!other.canEqual(this)) {
				return false;
			} else {
				Object this$team_id = this.getTeam_id();
				Object other$team_id = other.getTeam_id();
				if (this$team_id == null) {
					if (other$team_id != null) {
						return false;
					}
				} else if (!this$team_id.equals(other$team_id)) {
					return false;
				}

				Object this$name = this.getName();
				Object other$name = other.getName();
				if (this$name == null) {
					if (other$name != null) {
						return false;
					}
				} else if (!this$name.equals(other$name)) {
					return false;
				}

				return true;
			}
		}
	}

	protected boolean canEqual(Object other) {
		return other instanceof Team;
	}

	public int hashCode() {
		int PRIME = true;
		int result = 1;
		Object $team_id = this.getTeam_id();
		result = result * 59 + ($team_id == null ? 43 : $team_id.hashCode());
		Object $name = this.getName();
		result = result * 59 + ($name == null ? 43 : $name.hashCode());
		return result;
	}

	public String toString() {
		String var10000 = String.valueOf(this.getTeam_id());
		return "Team(team_id=" + var10000 + ", name=" + this.getName() + ")";
	}

	public Team() {
	}
}
