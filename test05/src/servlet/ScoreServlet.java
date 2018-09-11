package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Dep2ProDao;
import dao.DepartmentDao;
import dao.ProjectDao;
import dao.ScoreDao;
import entity.Department;
import entity.Employee;
import entity.Project;
import entity.Score;
import jdk.nashorn.internal.ir.RuntimeNode.Request;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import sun.font.LayoutPathImpl.EmptyPath;
import util.Grade;
import util.Pagination;

//user的控制器类，需要继承这个类
//mvc模式：model view controller 
//用户通过浏览器首先访问控制器，控制器去model，
//model就是指所有的java类 
//，然后返回想要的数据到控制器，
//然后携带数据转发或重定向到一个view，然后响应回浏览器，
//控制器有一个技术就叫servlet，view就是jsp
public class ScoreServlet extends HttpServlet {
	// 这个类里需要有两个方法,如果前台用get方式发过来请求就用get，用post就用post，五种形式里面只有form和ajax可以选择是否使用post
	private static final String path = "WEB-INF/score/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
	response.setContentType("text/html;charset=utf-8");
		try {
			HttpSession session = request.getSession();
			if (session.getAttribute("user") == null) {
				response.sendRedirect("user");
			}else {
			request.setCharacterEncoding("utf-8");
			// 如果是第一次登录界面，type就是null，那么就showlogin，如果点击登录，就会dologin
			String type = request.getParameter("type");
			if (type == null) {
				search(request, response);
			} else if ("manage".equals(type)) {
				manage(request, response);
			} else if ("input".equals(type)) {
				input(request, response);
			}}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void input(HttpServletRequest request, HttpServletResponse response) {
		try {
			Integer value = Integer.parseInt(request.getParameter("value"));
			int id = Integer.parseInt(request.getParameter("id"));
			Score sc = new Score();
			sc.setValue(value);
			sc.setId(id);
			ScoreDao scDao = new ScoreDao();
			scDao.update(sc);
			Score sc2=scDao.search(id);
			PrintWriter out = response.getWriter();
			JSON json = JSONObject.fromObject(sc2);
			out.print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void manage(HttpServletRequest request, HttpServletResponse response) {
		try {
			ScoreDao scDao = new ScoreDao();
			List<Score> list = new ArrayList<Score>();
			DepartmentDao depDao = new DepartmentDao();
			ProjectDao proDao = new ProjectDao();
			Score condition = new Score();
			Employee emp = new Employee();
			Department dep = new Department();
			Project pro = new Project();
			int depId = -1;
			if (request.getParameter("depId") != null && "" != request.getParameter("depId")) {
				depId = Integer.parseInt(request.getParameter("depId"));
			}
			dep.setId(depId);
			emp.setName(request.getParameter("name"));
			emp.setDep(dep);
			int proId = -1;
			if (request.getParameter("proId") != null && "" != request.getParameter("proId")) {
				proId = Integer.parseInt(request.getParameter("proId"));
			}

			pro.setId(proId);
			// Integer value=(Integer.valueOf(request.getParameter("value")));

			condition.setProject(pro);
			condition.setEmployee(emp);
			// condition.setValue(value);
			request.setAttribute("c", condition);
			int ye = 1;
			if (request.getParameter("ye") != null) {
				ye = Integer.parseInt(request.getParameter("ye"));
			}
			// 这里查询count是查询符合条件的员工数目，跟show方法里不一样
			int count = scDao.searchCount();
			Pagination p = new Pagination(ye, count, util.Constant.EMP_NUM_IN_PAGE, util.Constant.EMP_NUM_OF_PAGE);
			list = scDao.search(condition, p.getBegin(), util.Constant.EMP_NUM_IN_PAGE);
			List<Department> depList = depDao.search();
			List<Project> proList = proDao.search();
			Grade[] grades = Grade.values();

			request.setAttribute("depList", depList);
			request.setAttribute("proList", proList);
			request.setAttribute("p", p);
			request.setAttribute("grades", grades);
			// 把输入的搜索信息发过去，这样搜索框里就能留住这些信息，搜索完点击下一页的时候不出问题
			// request.setAttribute("c", condition);
			// List<Employee> list = empDao.search(condition, p.getBegin(),
			// util.Constant.EMP_NUM_IN_PAGE);
			request.setAttribute("list", list);
			request.getRequestDispatcher(path + "manage.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void search(HttpServletRequest request, HttpServletResponse response) {
		try {
			ScoreDao scDao = new ScoreDao();
			List<Score> list = new ArrayList<Score>();
			DepartmentDao depDao = new DepartmentDao();
			ProjectDao proDao = new ProjectDao();
			Score condition = new Score();
			Employee emp = new Employee();
			Department dep = new Department();
			Project pro = new Project();
			int depId = -1;
			if (request.getParameter("depId") != null && "" != request.getParameter("depId")) {
				depId = Integer.parseInt(request.getParameter("depId"));
			}
			dep.setId(depId);
			emp.setName(request.getParameter("name"));
			emp.setDep(dep);
			int proId = -1;
			if (request.getParameter("proId") != null && "" != request.getParameter("proId")) {
				proId = Integer.parseInt(request.getParameter("proId"));
			}

			pro.setId(proId);
			// Integer value=(Integer.valueOf(request.getParameter("value")));

			condition.setProject(pro);
			condition.setEmployee(emp);
			// condition.setValue(value);
			request.setAttribute("c", condition);
			int ye = 1;
			if (request.getParameter("ye") != null) {
				ye = Integer.parseInt(request.getParameter("ye"));
			}
			// 这里查询count是查询符合条件的员工数目，跟show方法里不一样
			int count = scDao.searchCount();
			Pagination p = new Pagination(ye, count, util.Constant.EMP_NUM_IN_PAGE, util.Constant.EMP_NUM_OF_PAGE);
			list = scDao.search(condition, p.getBegin(), util.Constant.EMP_NUM_IN_PAGE);
			List<Department> depList = depDao.search();
			List<Project> proList = proDao.search();
			Grade[] grades = Grade.values();

			request.setAttribute("depList", depList);
			request.setAttribute("proList", proList);
			request.setAttribute("p", p);
			request.setAttribute("grades", grades);
			// 把输入的搜索信息发过去，这样搜索框里就能留住这些信息，搜索完点击下一页的时候不出问题
			// request.setAttribute("c", condition);
			// List<Employee> list = empDao.search(condition, p.getBegin(),
			// util.Constant.EMP_NUM_IN_PAGE);
			request.setAttribute("list", list);
			request.getRequestDispatcher(path + "list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

}
