package com.banner.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.banner.bean.User;
import com.banner.service.UserService;
import com.banner.utils.execel.Const;
import com.banner.utils.execel.FileDownload;
import com.banner.utils.execel.FileUpload;
import com.banner.utils.execel.ObjectExcelRead;
import com.banner.utils.execel.ObjectExcelView;
import com.banner.utils.execel.PageData;
import com.banner.utils.execel.PathUtil;

@Controller
public class HelloController {
	
	
	
	@RequestMapping("index")
	public String index(Map<String, Object> map){
		map.put("name", "Allen");
		return "index";
	}
	@Autowired
	public UserService userService;
	
	@RequestMapping("/list")
	public List<User> getAll(){
		return userService.getAll();
	}
	
	/*
	 * 导出用户信息到EXCEL
	 * @return
	 */
	@RequestMapping(value="/exportExcel.do")
	public ModelAndView exportExcel(){
		ModelAndView mv = new ModelAndView();
		try{
				Map<String,Object> dataMap = new HashMap<String,Object>();
				List<String> titles = new ArrayList<String>();
				titles.add("用户名"); 		//1
				titles.add("邮箱");			//2
				titles.add("性别");  		//3
				dataMap.put("titles", titles);				
				List<User> userList = userService.getAll();
				List<PageData> varList = new ArrayList<PageData>();				
				for(int i=0;i<userList.size();i++){
					PageData vpd = new PageData();
					vpd.put("var0", userList.get(i).getUserName());		//0
					System.out.println("======>："+userList.get(i).getUserName());
					
					vpd.put("var1", userList.get(i).getEmail());		//1
					vpd.put("var2", userList.get(i).getSex().equals("0")?"男":"女");//2					
					varList.add(vpd);
				}
				dataMap.put("varList", varList);
				ObjectExcelView erv = new ObjectExcelView();					//执行excel操作
				 mv = new ModelAndView(erv,dataMap);			 
		} catch(Exception e){		
		}
		return mv;
	}
	 

	/**
	 * 打开上传EXCEL页面
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("uploadexcel");
		return mv;
	}
	
	/**
	 * 下载模版
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "userexcel.xls", "userexcel.xls");
		//FileDownload.fileDownload(response, "E:/workspace/springboot-jsp/uploadFiles/file/users.xls", "users.xls");
	}
	
	/**
	 * 从EXCEL导入到数据库
	 */
	@RequestMapping(value="/readExcel")
	public ModelAndView readExcel(
			@RequestParam(value="excel",required=false) MultipartFile file
			) throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "userexcel");							//执行上传			
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 1, 0, 0);	//执行读EXCEL操作,读出的数据导入List 1:从第2行开始；0:从第A列开始；0:第0个sheet	
			/*存入数据库操作======================================*/
			/**
			 * var1 :姓名
			 * var2 :性别
			 * var3 :邮箱
			 */
			 User  user = new User();
			for(int i=0;i<listPd.size();i++){
				System.out.println("====================>"+listPd.get(i).getString("var0"));
				System.out.println("====================>"+listPd.get(i).getString("var1"));
				System.out.println("====================>"+listPd.get(i).getString("var2"));
				user.setUserName(listPd.get(i).getString("var0"));//姓名
				user.setEmail(listPd.get(i).getString("var1"));//邮箱
				user.setSex(listPd.get(i).getString("var2").equals("男")?0:1);				
				userService.saveU(user);
			}
			/*存入数据库操作======================================*/
		}	
		mv.setViewName("save_result");
		return mv;
	}
	
	
}
