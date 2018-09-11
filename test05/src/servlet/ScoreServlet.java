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

//user�Ŀ������࣬��Ҫ�̳������
//mvcģʽ��model view controller 
//�û�ͨ����������ȷ��ʿ�������������ȥmodel��
//model����ָ���е�java�� 
//��Ȼ�󷵻���Ҫ�����ݵ���������
//Ȼ��Я������ת�����ض���һ��view��Ȼ����Ӧ���������
//��������һ�������ͽ�servlet��view����jsp
public class ScoreServlet extends HttpServlet {
	// ���������Ҫ����������,���ǰ̨��get��ʽ�������������get����post����post��������ʽ����ֻ��form��ajax����ѡ���Ƿ�ʹ��post
	private static final String path = "WEB-INF/score/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
	response.setContentType("text/html;charset=utf-8");
		try {
			HttpSession session = request.getSession();
			if (session.getAttribute("user") == null) {
				response.sendRedirect("user");
			}else {
			request.setCharacterEncoding("utf-8");
			// ����ǵ�һ�ε�¼���棬type����null����ô��showlogin����������¼���ͻ�dologin
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
			// �����ѯcount�ǲ�ѯ����������Ա����Ŀ����show�����ﲻһ��
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
			// �������������Ϣ����ȥ�������������������ס��Щ��Ϣ������������һҳ��ʱ�򲻳�����
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
			// �����ѯcount�ǲ�ѯ����������Ա����Ŀ����show�����ﲻһ��
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
			// �������������Ϣ����ȥ�������������������ס��Щ��Ϣ������������һҳ��ʱ�򲻳�����
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
