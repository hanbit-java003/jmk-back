package com.hanbit.there.api.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanbit.there.api.JmkConstants;
import com.hanbit.there.api.annotation.SignInRequired;
import com.hanbit.there.api.service.ManagerSerivce;
import com.hanbit.there.api.vo.ManagerVO;

@RestController
@RequestMapping("/api/manager")
public class ManagerController {
	
	@Autowired
	private ManagerSerivce managerSerivce;
	
	private static final ObjectMapper objectMapper = new ObjectMapper();
	
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
			HttpSession session,
			HttpServletResponse response) {
		
		//System.out.println(remember);
		
		ManagerVO managerVO = managerSerivce.signIn(email, password);
		
		if(remember) {
			Cookie cookie = new Cookie("rid", "1234");
			cookie.setMaxAge(600);
			response.addCookie(cookie);
		}
		
		// session 범위 ..
		session.setAttribute(JmkConstants.SIGNIN_KEY, true);
		session.setAttribute("uid", managerVO.getUid());
		session.setAttribute("email", managerVO.getEmail());
		
		if (managerVO.getDetail() != null) {
			session.setAttribute("avatar", managerVO.getDetail().getAvatar());
		}

		
		Map result = new HashMap();
		result.put("email", managerVO.getEmail());
		
		return result;
	}
	
	@RequestMapping("/get")
	public Map getManager(HttpSession session) {
		Map manager = new HashMap();
		
		if(session.getAttribute(JmkConstants.SIGNIN_KEY) == null) {
			manager.put(JmkConstants.SIGNIN_KEY, false);
		}
		else {
			manager.put(JmkConstants.SIGNIN_KEY, true);
			manager.put("email", session.getAttribute("email"));
			manager.put("avatar", session.getAttribute("avatar"));
		}
		
		
		return manager;
	}
	
	@RequestMapping("/signout")
	public Map signOut(HttpSession session,
			HttpServletRequest request,
			HttpServletResponse response){
		
		session.invalidate();
		
		Cookie cookie = WebUtils.getCookie(request, "rid");
		
		if (cookie != null) {
			cookie.setValue(null);
			response.addCookie(cookie);
		}
		
		Map result = new HashMap();
			result.put("status", "ok");
		
		return result;
	}
	
	@SignInRequired
	@RequestMapping("/detail")
	public ManagerVO getManagerDetail(HttpSession session) {
		String uid = (String) session.getAttribute("uid");
		
		return managerSerivce.getManagerDetail(uid);
	}
	
	@SignInRequired
	@PostMapping("/save")
	public Map saveManagerDetail(@RequestParam("manager")String json,
			@RequestParam(value = "avatar", required= false) MultipartFile image,
			HttpSession session) throws Exception {
		ManagerVO managerVO = objectMapper.readValue(json, ManagerVO.class);
		managerVO.setUid((String) session.getAttribute("uid"));
		
		managerSerivce.saveManagerDetail(managerVO, image);
		
		Map result = new HashMap();
		result.put("status", "ok");
		return result;
	}
	
}
