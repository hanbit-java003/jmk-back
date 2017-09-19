package com.hanbit.there.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)  // 실행중에도
@Target(ElementType.METHOD) // 어디에 붙일 것인지 명시 @Target({ElementType.METHOD, ElementType.TYPE})으로도 가능.
public @interface SignInRequired {

	String[] value() default {};

}
