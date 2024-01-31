package com.oracle.oBootDBConnect;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oracle.oBootDBConnect.repository.JdbcMemberRepository;
import com.oracle.oBootDBConnect.repository.MemberRepository;
import com.oracle.oBootDBConnect.repository.MemoryMemberRepository;

@Configuration //이렇게 환경파일 만들어주면, 여기서 설정 해줬으므로, 다른 곳에서 @Repository 넣지 않아줘도, 여기서 설정해줘서 정상 작동.
public class SpringConfig {
	private final DataSource dataSource;

	public SpringConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Bean
	public MemberRepository memberRepository() {
		return new JdbcMemberRepository(dataSource); //이걸로 하면 db로 
//		return new MemoryMemberRepository(); //이걸로 하면 메모리로
	}
	
	

}
