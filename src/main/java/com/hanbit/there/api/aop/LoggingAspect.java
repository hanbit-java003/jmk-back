package com.hanbit.there.api.aop;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect // aspectj위버 에 있는 녀석이다.
@Component  // 이녀석이 붙여줘야 spring Bean으로 등록된다.
public class LoggingAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	//@Pointcut("execution(* com.hanbit.there.api..controller.*.*(..))")
	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void allControllerMethod() {
	
	}
	
	@Before(value="allControllerMethod()", argNames="joinPoint")
	public void logRequest(JoinPoint joinpotint) {
		MethodSignature signature=(MethodSignature)joinpotint.getSignature();
		
		String methodName = signature.toShortString();
		
		
		ServletRequestAttributes reuqestAttributes
			// RequestContextHolder.getRequestAttributes 요구한 속성을 가져와서 보관해준다?
			=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = reuqestAttributes.getRequest();
		// Request를 가져왔기때문에 얘가 누를 호출했는지? IP 같은거 알 수 있다.
		//getRemoteAddr 호출한 IP이다.
		String remoteAddr = request.getRemoteAddr();
		// uri를 가져오기 위해서.
		String uri = request.getRequestURI();
		
		
		logger.debug(methodName+ "has requested by " + remoteAddr+ "("+uri+")");
		
		/*methodName += "(";
		// 자바 8부터 request를 하면 파라미터를 가져올 수 있다.
		//getParameterTypes 리턴 타입은 클래스 배열이다.
		Class[] paramTypes= signature.getParameterTypes();
		for (Class paramType: paramTypes) {
			methodName += paramType.getSimpleName() + ",";
		}
		methodName += ")";*/
	}
		
	
/*	//@Pointcut("execution(* com.hanbit.there.api..service.*.*(..))")
    //Aspect에 문법 excution(PointCut)=>(리턴타입 아무거나/패키지명.모든클래스.모든메소드(모든파라미터))
	@Pointcut("within(com.hanbit.there.api..service.*)") // within 클래스만 잡을 수 있다. 
	public void allServiceMethod() {
		
	}
	                          // PointCut     // service부분으로 바꿈.
	@Before(value = "allControllerMethod() || allServiceMethod()",  // Advice안에 있는 녀석.
			argNames = "joinPoint")  // 어디서 join할 것인지?
	//Aspect에 문법 excution(PointCut)=>(리턴타입 아무거나/패키지명.모든클래스.모든메소드(모든파라미터))
	public void logController(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();
		logger.debug(methodName + "has called (before)");
		//컨트롤러 debug 시켜준다.	
	}
	
	
	// 다른 녀석들을 추가하고 싶을땐 value를 사용
	@AfterReturning(value="allServiceMethod()", // 정상실행됬을때
					returning="retVal") 
	public void logServiceReturn(Object retVal) {  //type 최상위 Object
		logger.debug("method returned" + retVal);
	}
	
	@AfterThrowing( value = "allServiceMethod()", // 에러가 났을때
					throwing="thrown") 
	public void logServiceThrow(Throwable thrown) { // Exception의 최상위 
		
		logger.debug("method throw " + thrown);
	}
	
	
	@After(value="allServiceMethod()",
		   argNames="joinPoint")
	public void logService(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();
		
		logger.debug(methodName + " has called (after)");
	}
	
	@Around("allServiceMethod()") // 이녀석은 리턴이 있다.
	public Object logServiceAround(ProceedingJoinPoint pjp)  //실행중인? 무조건 파라미터가 있다. 
			throws Throwable{ 
		// pjp의 시점은 Before이다.
		logger.debug("before (around)");
		
		Object retVal = null;
		try{
		retVal = pjp.proceed();// Joinpoint는 메소드의 대한 정보
		//AfterReturning	 // pjp는 실행중인 메소드.proceed를 통해서 실행이 된다.
		logger.debug("AfterReturning (around)");
		
		retVal = "[]";
		}                    // 중간에 에러가 날 수 있으므로, Throwable을 추가해 준다.
		catch (Throwable t) {
			// AfterThrowing
			logger.debug("AfterThrowing (around)");
			throw t;
		}
		// After 시점은 잡을 수 없다.	
		 return retVal;
	}*/	
}







