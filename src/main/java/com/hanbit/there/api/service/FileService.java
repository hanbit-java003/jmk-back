package com.hanbit.there.api.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.management.RuntimeErrorException;

import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hanbit.there.api.dao.FileDAO;
import com.hanbit.there.api.utils.ImageUtils;
import com.hanbit.there.api.vo.FileVO;

@Service
public class FileService {

	@Autowired
	private FileDAO fileDAO;

	public FileVO getFile(String fileId){
		return fileDAO.selectFile(fileId);
	}
	
	@Transactional // 에러가 발생하면 취소하기 위해서..  따로따로 실행되는걸 막기위해서.
	public void addFile(FileVO fileVO, InputStream inputStream){// 오버로딩 중복정의
		addFile(fileVO, inputStream, 0);
	}

	@Transactional // 에러가 발생하면 취소하기 위해서..  따로따로 실행되는걸 막기위해서.
	public void addFile(FileVO fileVO, InputStream inputStream, int width){
		saveFile(fileVO.getFilePath(), inputStream, width);
		// save할때 런타임으로 바꿔서 한 이유는 트렌젝션은 런타임만 롤백해주기때문이다.
		
		if(width> 0) {
			fileVO.setContentLength(new File(fileVO.getFilePath()).length());
		}
		
		fileDAO.insertFile(fileVO);

	}
	
	@Transactional
	public void modifyFile(FileVO fileVO, InputStream inputStream){// 오버로딩 중복정의
		modifyFile(fileVO, inputStream, 0);
	}

	@Transactional
	public void modifyFile(FileVO fileVO, InputStream inputStream, int width){
		saveFile(fileVO.getFilePath(), inputStream, width);
		
		if(width> 0) {
			fileVO.setContentLength(new File(fileVO.getFilePath()).length());
		}
		
		fileDAO.replaceFile(fileVO);
	}

	private void saveFile(String filePath, InputStream inputStream, int width){
		if(width < 1) {
			try(FileOutputStream outputStream = new FileOutputStream(filePath)){
				IOUtils.copyLarge(inputStream, outputStream);
			}
			catch(Exception e){
				throw new RuntimeException(e);
			}
		}
		else {
			try{
			ImageUtils.rezize(inputStream, width, filePath);
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void removeFile(String fileId) {
		FileVO fileVO = fileDAO.selectFile(fileId);
		String filePath = fileVO.getFilePath();

		fileDAO.deleteFile(fileId);

		//deleteQuietly는 지우다가 못지우면 false가 난다.. 
		//forceDelete는 지우다가 못지우면 에러가 난다.
		FileUtils.deleteQuietly(new File(filePath));

	}

}
