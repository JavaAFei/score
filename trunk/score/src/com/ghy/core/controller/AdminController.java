package com.ghy.core.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ghy.common.util.Consts;
import com.ghy.common.util.DESUtil;
import com.ghy.common.util.JsonUtils;
import com.ghy.core.entity.Admin;
import com.ghy.core.entity.ShowOption;
import com.ghy.core.service.IAdminService;
import com.ghy.core.service.IShowOptionService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping({"/admin"})
public class AdminController {
	@Resource(name="adminService")
	private IAdminService adminService;
	@Resource(name="showOptionService")
	private IShowOptionService showOptionService;
	private static Logger logger = Logger.getLogger(AdminController.class);  
	  
	
	
	 public static void main(String[] args) {
		 	//PropertyConfigurator.configure("log4j.properties");
	        // 记录debug级别的信息  
	        logger.debug("This is debug message.");  
	        // 记录info级别的信息  
	        logger.info("This is info message.");  
	        // 记录error级别的信息  
	        logger.error("This is error message.");  
	    }  
	/**
	 * 管理员登录方法
	 * @param admin
	 * @param request
	 * @return
	 */
	@RequestMapping({"login"})
	public String adminLogin(@ModelAttribute("admin") Admin admin,HttpServletRequest request){
		
		
		try {
		
		if(request.getSession().getAttribute(Consts.SESSION_ADMIN) != null && StringUtils.isBlank(admin.getLoginCode()) && StringUtils.isBlank(admin.getPassword())){
			return "/main";
		}
		
		if(StringUtils.isBlank(admin.getLoginCode()) || StringUtils.isBlank(admin.getPassword())){
			return "/index";
		}
		
		admin.setPassword(DESUtil.jiaM(admin.getPassword()));
		
		List<Admin> adminList = this.adminService.
				getAdminByLoginCodeAndPassword(admin.getLoginCode(),admin.getPassword());
		if(adminList == null || adminList.size() == 0){
			request.setAttribute("result", "用户名或密码错误，请重新输入!");
			return "/index";
		}
		admin = adminList.get(0);

		request.getSession().removeAttribute(Consts.SESSION_ADMIN);
		request.getSession().setAttribute(Consts.SESSION_ADMIN, admin);
		
		//new StudentController().queryStudentAndScores();
		} catch (Exception e) {
			e.printStackTrace();
			return "/fail";
		}
		return "/main";
	}
	
	/**
	 * 管理员退出方法
	 * @param admin
	 * @param request
	 * @return
	 */
	@RequestMapping({"loginout"})
	public String adminLoginout(HttpServletRequest request){
		request.getSession().removeAttribute(Consts.SESSION_ADMIN);
		return "/index";
	}
	
	/**
	 * 管理员修改密码
	 * @param admin
	 * @param request
	 * @return
	 */
	@RequestMapping({"editPassword"})
	public void editPassword( HttpServletRequest request,HttpServletResponse response){
		String oldPassword = request.getParameter("oldPassword");
		String newPassWord = request.getParameter("newPassWord");
		Admin admin = (Admin) request.getSession().getAttribute(Consts.SESSION_ADMIN);
		
		try {
			PrintWriter writer = response.getWriter();
			if(admin!=null && oldPassword !=null && admin.getPassword().equals(DESUtil.jiaM(oldPassword))
					&& !StringUtils.isEmpty(newPassWord)){
				admin.setPassword(DESUtil.jiaM(newPassWord));
				this.adminService.update(admin);
				writer.write("0");
			}else{
				writer.write("1");
			}
			writer.flush();
			writer.close();
			writer = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 管理员配置字段是否显示
	 * @param admin
	 * @param request
	 * @return
	 */
	@RequestMapping({"option"})
	public void optionColumn( HttpServletRequest request,HttpServletResponse response){
		try {
			// 检查t_show_option表，插入缺少的数据
			showOptionService.updateColumn();
			Map<String, Object> resultMap = showOptionService.queryOption();
			PrintWriter writer = response.getWriter();
			writer.write(resultMap==null?"1":JsonUtils.toJSON(resultMap));
			writer.flush();
			writer.close();
			writer = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 管理员更改配置字段是否显示
	 * @param admin
	 * @param request
	 * @return
	 */
	@RequestMapping({"updateOption"})
	public void updateOptionColumn( HttpServletRequest request,HttpServletResponse response){
		try {
			PrintWriter writer = response.getWriter();
			String id = request.getParameter("id");
			String option = request.getParameter("option");
			ShowOption showOption = showOptionService.getById(id);
			if(showOption!=null){
				showOption.setIsShow(option);
				// 检查t_show_option表，插入缺少的数据
				showOptionService.update(showOption);
				writer.write("0");
			}
			writer.write("1");
			writer.flush();
			writer.close();
			writer = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
