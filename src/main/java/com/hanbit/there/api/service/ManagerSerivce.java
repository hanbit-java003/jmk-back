package com.hanbit.there.api.service;

import java.io.IOException;
import java.util.Random;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hanbit.there.api.dao.ManagerDAO;
import com.hanbit.there.api.exception.JmkException;
import com.hanbit.there.api.vo.FileVO;
import com.hanbit.there.api.vo.ManagerVO;

@Service
public class ManagerSerivce {
	
	//중복확인
	private static final char[] CHARS ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
	
	@Autowired
	private ManagerDAO managerDAO;
	
	@Autowired
	private FileService fileService;
	
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
	
	public ManagerVO getManagerDetail(String uid) {
		
		return managerDAO.selectManagerDetail(uid);
	}
	
	@Transactional // 여러개의 DAO를 실해아해야 되기 때문에
	public void saveManagerDetail(ManagerVO managerVO, MultipartFile image) throws IOException {
		if(image != null) { // 파일이 있으면
			FileVO fileVO = new FileVO();
			// 파일 ID 셋팅
			String fileId = "avatar-" + managerVO.getUid();
			fileVO.setFileId(fileId);
			// 파일 확장자 셋팅
			String fileExt = FilenameUtils.getExtension(image.getOriginalFilename());
			String fileName = fileId + "." + fileExt;
			fileVO.setFileName(fileName);
			fileVO.setFilePath("/hanbit/webpack/jmk-front/src/img/avatars/" + fileName);
			// type 사이즈
			fileVO.setContentType(image.getContentType());
			fileVO.setContentLength(image.getSize());
			// 사진저장
			fileService.modifyFile(fileVO, image.getInputStream(), 200);
			managerVO.getDetail().setAvatar("/api/file/" + fileId);
			
		}
		
		managerDAO.insertManagerDetail(managerVO);
		
		if (StringUtils.isNotBlank(managerVO.getPassword())) {
			changePassword(managerVO.getUid(),managerVO.getCurrentPw(),managerVO.getPassword());
		}
		
	}
	
	private void changePassword(String uid, String currentPw, String newPw) {
		String encodedPw = managerDAO.selectPassword(uid); // 암호화된 패스워드
		
		if(!passwordEncoder.matches(currentPw, encodedPw)) {
			throw new JmkException("현재 패스워드가 일치하지 않습니다.");
		}
		
		//패스워드를 암호화 하기 위해서 만듦.
		String password = passwordEncoder.encode(newPw);
		
		ManagerVO managerVO = new ManagerVO();
		managerVO.setUid(uid);
		managerVO.setPassword(password); // 암호화 할것!
		
		managerDAO.updatePassword(managerVO);
		
	}
	
	
}
