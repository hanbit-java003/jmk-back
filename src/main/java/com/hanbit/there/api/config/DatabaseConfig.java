package com.hanbit.there.api.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// 마이바티스에서 디비를 접속하기 위해 환경설정을 해준 것이다.

@Configuration
@EnableAutoConfiguration //자동 구성 사용
@EnableTransactionManagement // 이걸하려면 트렌스터매니지먼트를 하나더 만들어줘야한다.
public class DatabaseConfig {
	
	@Autowired  // 자동으로 넣기위해 사용한다.
	private ApplicationContext applicationContext;
	
	@Bean // @EnableTransactionManagement를 사용하기위래 만들었다.
	public PlatformTransactionManager transactionManager(DataSource dataSource){
		return new DataSourceTransactionManager(dataSource);
	}
	
	
	@Bean  // 데이타소스는 application만든거에서 가져옴
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception  {
		// SqlSessionFactory는 인터페이스이기 때문에 bean으로 만든다.
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		// mybatis와 연결을 도와준다.
		sqlSessionFactory.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
		//위에꺼와 메소드가 다르다..  여러개이기 때문에.
		// 이녀석으로 인해 IOException이 발생하므로 throws 처리를 해준다.
		sqlSessionFactory.setMapperLocations(applicationContext.getResources("classpath:mybatis/mappers/**/*.xml"));
		
		//이녀석으로 인해 Exception이 발생하므로 throws 처리를 해준다.
		return sqlSessionFactory.getObject();
	}
	
	@Bean   // 위에서 만든 팩토리에서 빈을 추가한걸 여기다가 넣어줌.
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {		
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
}
