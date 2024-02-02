package com.oracle.oBootJpa01.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//lombok의 기능들
@Entity //쓰려면 gradle에 
//implementation 'org.springframework.boot:spring-boot-starter-data-jpa' 추가해줘야
@Table(name="member1")	//이걸 써주면 member대신 member1이라는 테이블 생성하며 거기에 대해.
@Getter // lombok 
@Setter// 이것도 lombok : 아래의 게터 세터 대체 : 알아서 추가해줌. 
//그래서 이제부턴 getter setter 안써도됨*/
@ToString // toString을 [id: this.id, name: this.name] 으로 자동으로 바꿔준다
public class Member {
	@Id
	private Long id;
	private String name;
	//이렇게 Entity 해주면, 자동으로 db에 속성 만들어준다. 
	//id는 number로 name은 varchar2로 자동으로.. 
	//pk로 쓸건 id로 지정
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String returnStr="";
		returnStr="[id:"+this.id+", name:"+this.name+"]";
		return returnStr;
	}
	
	
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
}
