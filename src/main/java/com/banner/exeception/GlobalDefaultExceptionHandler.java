package com.banner.exeception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 1、创建一个类
 * 2、在类上添加@ControllerAdvice
 * 3、添加一个方法
 * 4、添加@ExceptionHandler拦截异常信息
 * 5、如果返回View--方法的返回值是ModelAndView
 * 6、若果返回的是String|Json 需要在方法上添加@ResponseBody
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String defaultExceptionHandler(HttpServletRequest req,Exception e){
	/*	//ModelAndView
		ModelAndView mv = new ModelAndView ();
		mv.setViewName(viewName);*/
		//返回String
		return "对不起，服务器繁忙，请稍后再试!";
		
	}
}
