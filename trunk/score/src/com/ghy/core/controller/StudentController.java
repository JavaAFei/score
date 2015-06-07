package com.ghy.core.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ghy.common.excel.PoiReadExcelBean;
import com.ghy.common.excel.PoiReadExcelBeanImpl;
import com.ghy.common.util.Consts;
import com.ghy.common.util.GlobalConfig;
import com.ghy.common.util.JsonUtils;
import com.ghy.common.util.StringUtil;
import com.ghy.core.entity.Admin;
import com.ghy.core.entity.Score;
import com.ghy.core.entity.Student;
import com.ghy.core.service.IScoreService;
import com.ghy.core.service.IStudentService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping({"/stu"})
public class StudentController {
	@Resource(name="studentService")
	private IStudentService studentService;
	@Resource(name="scoreService")
	private IScoreService scoreService;
	private static String subPath = GlobalConfig.getProperty("path", "uploadPath");
	/**
	 * ��¼ǰ�ķ���
	 * @param admin
	 * @param request
	 * @return
	 */
	@RequestMapping({"beforeLogin"})
	public String beforeStudentLogin(HttpServletRequest request){
		try {
			Date lastUpdateTime = studentService.getLastUpdateTime();
			request.setAttribute("lastUpdateTime", lastUpdateTime);
		} catch (Exception e) {
			e.printStackTrace();
			return "/fail";
		}
		return "/studentLogin";
	}
	
	
	/**
	 * ѧ����¼����
	 * @param admin
	 * @param request
	 * @return
	 */
	@RequestMapping({"login"})
	public void studentLogin(@ModelAttribute("student") Student student,HttpServletRequest request,HttpServletResponse response){
		try {
			PrintWriter writer = response.getWriter();
			List<Student> studentList = studentService.getStudent(student.getName(),student.getExamNo());
			if(studentList == null || studentList.size() == 0){
				writer.write("��¼��Ϣ��������ϵͳ���ݻ�δ����    ���������뼰ʱ��ϵ0531-87196580!");
			}else{
				student = studentList.get(0);
				request.getSession().setAttribute(Consts.SESSION_STUDENT, student);
				writer.write("success");
			}
			writer.flush();
			writer.close();
			writer = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ѧ���˳�����
	 * @param request
	 * @return
	 */
	@RequestMapping({"loginout"})
	public String studentLoginout(HttpServletRequest request){
		request.getSession().removeAttribute(Consts.SESSION_STUDENT);
		return "/studentLogin";
	}
	
	/**
	 * ѧ����ѯ����
	 * @param request 
	 * @param stundentId
	 * @return
	 */
	@RequestMapping({"query"})
	public String queryScore( HttpServletRequest request){
		Student student = (Student) request.getSession().getAttribute(Consts.SESSION_STUDENT);
		if(student ==null ){
			return "/studentLogin";
		}
		List<Score> scoreList = scoreService.getScoreByStudentId(student.getId());
		request.setAttribute("scoreList", scoreList);
		return "/queryScore";
	}

	/**
	 * ��ѯȫ����ѧ���ͳɼ���Ϣ
	 * @param request
	 * @param response
	 */
	@RequestMapping({"queryAll"})
	public void queryStudentAndScores( HttpServletRequest request,HttpServletResponse response) {
		try {
			String page = request.getParameter("page");
			String rows = request.getParameter("rows");
			String examNo = request.getParameter("examNoS");
			String name = request.getParameter("nameS");
			Map<String,String> parmMap = new HashMap<String, String>();
			parmMap.put("page", page);
			parmMap.put("rows", rows);
			parmMap.put("examNo", examNo);
			parmMap.put("name", name);
			Map<String, Object> resultMap = scoreService.getStudentAndScores(parmMap);
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
	 * ɾ������ѧ���Ͷ�Ӧ�ɼ�
	 * 
	 */
	@RequestMapping({"del"})
	public void delete( HttpServletRequest request,HttpServletResponse response,String studentId) {
		try {
			studentService.deleteById(studentId);
			PrintWriter writer = response.getWriter();
			writer.write("0");
			writer.flush();
			writer.close();
			writer = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ɾ��ȫ��ѧ���Ͷ�Ӧ�ɼ�
	 * 
	 */
	@RequestMapping({"delAll"})
	public void deleteAll( HttpServletRequest request,HttpServletResponse response) {
		try {
			studentService.deleteAll();
			PrintWriter writer = response.getWriter();
			writer.write("0");
			writer.flush();
			writer.close();
			writer = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * �ϴ�ѧ�����ɼ�
	 * @param request 
	 * @param stundentId
	 * @return
	 */
	@RequestMapping("/upload")  
    public String upload( HttpServletRequest request, HttpServletResponse response) {
		Admin admin = (Admin) request.getSession().getAttribute(Consts.SESSION_ADMIN);
		if(admin==null){
			return "/fail";
		}
		
		//1�������ļ�
		String filePath = this.saveExcel(request);
		Map readMap = null;
		
		try {
			PoiReadExcelBean poiReadBean = new PoiReadExcelBeanImpl();// poi����������
			List list = poiReadBean.readExcel(filePath, 0);
			StringBuilder msg = new StringBuilder();// ��֤������Ϣ
			String result = "Y";
			if (list == null || list.size() <= 1) {
				msg.append("����ģ�岻��Ϊ�գ�");
				result = "N";
			}
			if (!"N".equals(result)) {
				//��ȡ��һ����Ϣ����Ϊscore��name
				Map<Integer, String> columnMap =(HashMap) list.get(0);
				String[] columns=new String[columnMap.size()];
				int c=0;
				for (Map.Entry<Integer, String> entry : columnMap.entrySet()) {
					columns[c] =entry.getValue();
					c++;
				}
				list.remove(0);
				//�Ƴ���һ��
				// ������ȡ
				int everyCount = 500;
				int size = list.size();
				System.out.println ("�����ϴ�������:" + size);
				int bathCount = size / everyCount + 1;
				System.out.println("��Ҫ���ִ�еĴ���:" + bathCount);
				List bathList = new ArrayList();
				Long successNum = 0L;
				
				for (int j = 0; j < bathCount; j++) {
					//�����ν�ȡlist
					if ((j + 1) * everyCount > size) {
						bathList = list.subList(j * everyCount, size);
					} else {
						bathList = list.subList(j * everyCount, (j + 1) * everyCount);
					}
					
					List<Student> stuList =  new ArrayList<Student>();
					for (int i = 0; bathList != null && i < bathList.size(); i++) {
						readMap = (Map) bathList.get(i);
						if (readMap.get(0) == null || StringUtil.isBlank(readMap.get(0).toString())) {
							continue;
						}
						Student stu = new Student();
						stu.setExamNo(readMap.get(0).toString().trim());
						stu.setName(readMap.get(1).toString().trim());
						stu.setCreator(admin.getId());
						stu.setCteateTime(new Date());
						stu.setUpdater(admin.getId());
						stu.setUpdateTime(new Date());
						
						Set<Score> sets= new HashSet<Score>();
						for(int k=2;k<columns.length;k++){
							Score sc = new Score ();
							sc.setStudent(stu);
							sc.setName(columns[k]);
							sc.setValue(readMap.get(k).toString().trim());
							sc.setIsShow("0");
							sc.setSort(k+"");
							sets.add(sc);
						}
						stu.setSets(sets);
						stuList.add(stu);
						successNum++;
					}
					studentService.saveAll(stuList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return "/main";
    }

	/**
	 * ����excel 
	 * @param request
	 * @return
	 */
	private   String  saveExcel(HttpServletRequest request) {
		try {
			CommonsMultipartResolver multipartResolver  = new CommonsMultipartResolver(request.getSession().getServletContext());
			if(multipartResolver.isMultipart(request)){
				MultipartHttpServletRequest  multiRequest = (MultipartHttpServletRequest)request;
				Iterator<String>  iter = multiRequest.getFileNames();
				while(iter.hasNext()){
						MultipartFile file = multiRequest.getFile((String)iter.next());
					if(file != null){
						String fileName = "demoUpload" + file.getOriginalFilename();
						String path = subPath + fileName;
						File localFile = new File(path);
						file.transferTo(localFile);
						return path;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
