package com.hanbit.there.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hanbit.there.api.service.ManagerSerivce;
import com.hanbit.there.api.vo.ManagerVO;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {
	
	@Autowired
	private ManagerSerivce managerSerivce;
	
	@PostMapping("/signup")
	public Map signUp(@RequestParam("email") String email,
			@RequestParam("password") String password) {
		
		ManagerVO managerVO = new ManagerVO();
		managerVO.setEmail(email);
		managerVO.setPassword(password);
		
		managerSerivce.signUp(managerVO);
		
		Map result = new HashMap();
		result.put("status", "ok");
		
		return result;
	}
	
	
	@PostMapping("/signin")
	public Map signIn(@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("remember") boolean remember,
			HttpSession session) {
		ManagerVO managerVO = managerSerivce.signIn(email, password);
		
		// session 범위 ..
		session.setAttribute("signedIn", true);
		session.setAttribute("uid", managerVO.getUid());
		session.setAttribute("email", managerVO.getEmail());

		
		Map result = new HashMap();
		result.put("email", managerVO.getEmail());
		
		return result;
	}
	
}
