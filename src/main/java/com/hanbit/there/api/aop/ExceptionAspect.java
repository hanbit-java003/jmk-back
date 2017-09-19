package com.hanbit.there.api.aop;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanbit.there.api.exception.ExceptionVO;
import com.hanbit.there.api.exception.HanbitException;

@Aspect
@Component
@Order(10)
public class ExceptionAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);
	
	private static final ObjectMapper jsonMapper = new ObjectMapper();
	
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void requestMapping(){
		
	}
	@Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	public void postMapping(){
		
	}
	@Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	public void getMapping(){
		
	}
	
	
	// 클래스 내부에서는 throws를 넣어서 에러를 잡고,
	// 여기서 따로 에러를 만들어줄 것이다.
	// 결과를 바꾸기 위해서 Around를 활용 할 것이다.
	@Around("requestMapping() || postMapping() || getMapping()")
	public Object handleException(ProceedingJoinPoint pjp) 
			throws UnsupportedEncodingException, IOException{
		
		try{
		   return pjp.proceed();
		}
		catch (Throwable t) {
			
			ServletRequestAttributes reuqestAttributes
				// RequestContextHolder.getRequestAttributes 요구한 속성을 가져와서 보관해준다?
				=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletResponse response = reuqestAttributes.getResponse();
			
			// 기본메세지
			String message = "사용자가 많아 서비스가 지연되고 있습니다.";
			ExceptionVO exceptionVO = null;
			int statusCode = 500;
			
			if (t instanceof HanbitException) {
				HanbitException e = (HanbitException) t;
				exceptionVO = new ExceptionVO(e.getErrorCode(), e.getMessage());
				statusCode = e.getErrorCode();
			}
			else {
				// 에러 정보를 알기 위해서 사용 하였다.
				logger.error(t.getMessage(), t);
				
				exceptionVO = new ExceptionVO(message);
			}
			
			
			byte[] bytes = jsonMapper.writeValueAsBytes(exceptionVO); // getBytes해서 다시 바꿔준다.
			
			response.setStatus(statusCode);
			//Type을 UTF8 형식으로 바꿔준다?
			response.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
			response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
			// json 길이를 셋팅 해준다.
			response.setContentLength(bytes.length);
			// 응답한 request로 받은 속성을 가져와서 bytes형태로 써준다?
			response.getOutputStream().write(bytes);
			// 이거 추가해줬다.
			response.flushBuffer();
		}
		return null;
	}
	
}
