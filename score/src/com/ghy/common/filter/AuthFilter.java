/**
 *
 */
package com.ghy.common.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ghy.common.util.Consts;


/**
 * @author Administrator
 * 
 */
public class AuthFilter  implements Filter  {
	private static Log log = LogFactory.getLog(AuthFilter.class);
	
	private FilterConfig filterConfig;
	private String[] exclude;

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		
		log.info(req.getRemoteAddr() + "\tvisite\t" + uri);
		String project=req.getContextPath();
		/*if (SESSION_COMPANY == null && SESSION_BUYER == null && SESSION_BRANCH==null && !uri.endsWith(project+"/logout.do")) {
			cookieLogin((HttpServletRequest)request, (HttpServletResponse)response);
		}*/
		if((project+"/").equals(uri)||(project+"/index.jsp").equals(uri)){
			res.sendRedirect(req.getContextPath() + "/index.do");// 用户未登
		}
		
		if (isNeedCheck(uri,project)) {
			if (1==1) {
				//如果toLogin参数存在，则登录以后跳回到原页面
				String toLogin = req.getParameter("toLogin");
				String returnURL = "";
				if(null != toLogin)
					returnURL = req.getHeader("Referer");
				// 用户未登
				res.sendRedirect(req.getContextPath() + "/login.jsp?returnURL=" + returnURL);
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * 是否
	 * 
	 * @param uri
	 * @return
	 */
	private boolean isNeedCheck(String uri,String project) {

		for (int i = 0; i < exclude.length; i++) {
			if (uri.contains(exclude[i])||uri.equals(project+"/")||uri.endsWith(".html")) {
				return false;
			}
		}
		return true;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		if (StringUtils.isNotBlank(filterConfig.getInitParameter("exclude"))) {
			String ex[] = filterConfig.getInitParameter("exclude").trim().split(",");
			exclude = new String[ex.length];
			for (int i = 0; i < ex.length; i++) {
				exclude[i] = ex[i].trim();
			}
		}
		this.filterConfig=filterConfig;
	}
	/**
	 * 清除session防止多种用户同时登录
	 * 
	 * @param userType
	 */
	public void removeSession(HttpServletRequest request) {
		request.getSession().removeAttribute(Consts.SESSION_ADMIN);
		request.getSession().invalidate();
	}
}
