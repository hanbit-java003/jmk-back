package com.hanbit.there.api.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanbit.there.api.vo.ManagerVO;

@Repository
public class ManagerDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insertManager(ManagerVO managerVO) {
		
		return sqlSession.insert("manager.insertManager", managerVO);
	}
	
	public int countManager(String email){
		
		return sqlSession.selectOne("manager.countManager",email);
	}
	
	public ManagerVO selectManager(String email) {
		
		return sqlSession.selectOne("manager.selectManager",email);
	}
	
	
}
