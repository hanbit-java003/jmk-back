package com.hanbit.there.api.service;

import java.util.Random;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hanbit.there.api.dao.ManagerDAO;
import com.hanbit.there.api.vo.ManagerVO;

@Service
public class ManagerSerivce {
	
	//중복확인
	private static final char[] CHARS ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
	
	@Autowired
	private ManagerDAO managerDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void signUp(ManagerVO managerVO){
		// 0보다 크면 있는거 중복확인
		if (managerDAO.countManager(managerVO.getEmail()) > 0 ) {
			throw new RuntimeException("이미 가입된 이메일 입니다");
		}
		// 중복확인을 위해서.
		managerVO.setUid(generateUid());
		
		String encodedPassword = passwordEncoder.encode(managerVO.getPassword());
		managerVO.setPassword(encodedPassword);
		
		
		managerDAO.insertManager(managerVO);
		
	}
	
	private String generateUid(){
		
		int length = 12;
		
		char [] uid = new char[length];
		
		Random random = new Random();
		
		for (int i=0; i<length; i++) {
			uid[i] = CHARS[random.nextInt(CHARS.length)];
		}
		
		return new String(uid);
	}
	
	public ManagerVO signIn(String email, String password){
		ManagerVO managerVO = managerDAO.selectManager(email);
		
		if (managerVO == null) {
			throw new RuntimeException("가입되지 않은 관리자 ID입니다.");
		}
		if (!passwordEncoder.matches(password, managerVO.getPassword())){
			throw new RuntimeException("잘못된 비밀번호 입니다.");
		}
		
		return managerVO;
	}
	
	
}
