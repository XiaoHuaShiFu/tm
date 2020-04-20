package com.xiaohuashifu.tm.aspect.annotation;

public @interface PointLog {
	
	String value() default "";
	
	int point() default 0;
	
}
