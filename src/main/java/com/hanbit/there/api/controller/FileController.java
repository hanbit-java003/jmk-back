package com.hanbit.there.api.controller;

import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanbit.there.api.service.FileService;
import com.hanbit.there.api.vo.FileVO;


@RestController
@RequestMapping("/api/file")
public class FileController {

	private static final String FILE_ROOT = "/hanbit/";
	
	@Autowired
	private FileService fileService;

	@RequestMapping("/{fileId}")
	public void getFile(@PathVariable("fileId") String fileId,
			HttpServletResponse response)throws Exception {
		
		FileVO fileVO = fileService.getFile(fileId);

		String filePath = fileVO.getFilePath();
		String contentType = fileVO.getContentType();
		long contentLength= fileVO.getContentLength();
		String fileName = fileVO.getFileName();
		//byte[] buffer = new byte[4096];
		
		// 파일마다 mime type이 있다..
		response.setContentType(contentType);
		response.setContentLengthLong(contentLength);
		// 부가정보를 헤더에 넣어준다.. 파일이름을 넣어줄 것이다.
		response.setHeader("Content-Disposition", "filename=" + fileName);
		
		// 파일사이즈의 얼만큼인지 알려준다.
		//response.setContentLength(len);
		

		
		//1. 이새기를 읽어서
		try(FileInputStream inputStream = new FileInputStream(filePath)){
			//2. 여기로 보낸다.  응답 아웃풋 스트림을 만들어준다.
			OutputStream outputStream = response.getOutputStream();
			
			IOUtils.copyLarge(inputStream, outputStream);

			/*// 아래방식은 아까 업로드 방식과 비슷하다.
			while (inputStream.available() > 0) {
				int readLength = inputStream.read(buffer,0, 
						Math.min(buffer.length,inputStream.available()));
				
				outputStream.write(buffer,0,readLength);
			}
*/		}

	}
}
