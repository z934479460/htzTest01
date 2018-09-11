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

import com.sun.corba.se.impl.orbutil.closure.Constant;

import dao.Dep2ProDao;
import dao.DepartmentDao;
import dao.ProjectDao;
import entity.Department;
import entity.Project;
import net.sf.json.JSONArray;
import util.Pagination;

//user�Ŀ������࣬��Ҫ�̳������
//mvcģʽ��model view controller 
//�û�ͨ����������ȷ��ʿ�������������ȥmodel��
//model����ָ���е�java�� 
//��Ȼ�󷵻���Ҫ�����ݵ���������
//Ȼ��Я������ת�����ض���һ��view��Ȼ����Ӧ���������
//��������һ�������ͽ�servlet��view����jsp
public class Dep2ProServlet extends HttpServlet {
	// ���������Ҫ����������,���ǰ̨��get��ʽ�������������get����post����post��������ʽ����ֻ��form��ajax����ѡ���Ƿ�ʹ��post
	private static final String path = "WEB-INF/dep2pro/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		
		// �ڵ�һ��ȡ����ǰ����post������������������
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
			} else if ("add".equals(type)) {
				add(request, response);
			} else if ("delete".equals(type)) {
				delete(request, response);
			} else if ("m2".equals(type)) {
				m2(request, response);
			} else if ("add2".equals(type)) {
				add2(request, response);
			} else if ("delete2".equals(type)) {
				delete2(request, response);
			} else if ("m3".equals(type)) {
				m3(request, response);
			}else if ("m4".equals(type)) {
				m4(request, response);
			}else if ("addBatch".equals(type)) {
				addBatch(request, response);
			}else if ("deleteBatch".equals(type)) {
				deleteBatch(request, response);
			}else if ("m5".equals(type)) {
				m5(request, response);
			}}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void m2(HttpServletRequest request, HttpServletResponse response) {
		try {
			int depId = Integer.parseInt(request.getParameter("depId"));
			DepartmentDao depDao = new DepartmentDao();
			Department dep = new Department();
			dep = depDao.search(depId);
			Dep2ProDao dpDao = new Dep2ProDao();
			List<Project> list = dpDao.searchByDepartment(depId);
			List<Project> noList = dpDao.searchByNotDepartment(depId);
			request.setAttribute("dep", dep);
			request.setAttribute("list", list);
			request.setAttribute("noList", noList);
			request.getRequestDispatcher(path + "list2.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void m3(HttpServletRequest request, HttpServletResponse response) {
		try {
			int depId = Integer.parseInt(request.getParameter("depId"));
			DepartmentDao depDao = new DepartmentDao();
			Department dep = new Department();
			dep = depDao.search(depId);
			Dep2ProDao dpDao = new Dep2ProDao();
			List<Project> list = dpDao.searchByDepartment(depId);
			List<Project> noList = dpDao.searchByNotDepartment(depId);
			request.setAttribute("dep", dep);
			request.setAttribute("list", list);
			request.setAttribute("noList", noList);
			request.getRequestDispatcher(path + "list3.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void m4(HttpServletRequest request, HttpServletResponse response) {
		try {
			int depId = Integer.parseInt(request.getParameter("depId"));
			DepartmentDao depDao = new DepartmentDao();
			Department dep = new Department();
			dep = depDao.search(depId);
			Dep2ProDao dpDao = new Dep2ProDao();
			List<Project> list = dpDao.searchByDepartment(depId);
			List<Project> noList = dpDao.searchByNotDepartment(depId);
			request.setAttribute("dep", dep);
			request.setAttribute("list", list);
			request.setAttribute("noList", noList);
			request.getRequestDispatcher(path + "list4.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void m5(HttpServletRequest request, HttpServletResponse response) {
		try {
			int depId = Integer.parseInt(request.getParameter("depId"));
			DepartmentDao depDao = new DepartmentDao();
			Department dep = new Department();
			dep = depDao.search(depId);
			Dep2ProDao dpDao = new Dep2ProDao();
			List<Project> list = dpDao.searchByDepartment(depId);
			List<Project> noList = dpDao.searchByNotDepartment(depId);
			request.setAttribute("dep", dep);
			request.setAttribute("list", list);
			request.setAttribute("noList", noList);
			request.getRequestDispatcher(path + "list5.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	


	public void add(HttpServletRequest request, HttpServletResponse response) {
		try {
			int depId = Integer.parseInt(request.getParameter("depId"));
			int proId = Integer.parseInt(request.getParameter("proId"));
			Dep2ProDao dpDao = new Dep2ProDao();
			dpDao.add(depId, proId);
			response.sendRedirect("dep2pro?depId=" + depId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void add2(HttpServletRequest request, HttpServletResponse response) {
		try {
			int depId = Integer.parseInt(request.getParameter("depId"));
			int proId = Integer.parseInt(request.getParameter("proId"));
			Dep2ProDao dpDao = new Dep2ProDao();
			Boolean flag = dpDao.add(depId, proId);

			PrintWriter out = response.getWriter();

			out.print(flag);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addBatch(HttpServletRequest request, HttpServletResponse response) {
		try {
			int depId = Integer.parseInt(request.getParameter("depId"));
			String[] proIds = request.getParameter("proId").split(",");
			Dep2ProDao dpDao = new Dep2ProDao();
			Boolean flag = dpDao.addBatch(depId, proIds);
			PrintWriter out = response.getWriter();
			out.print(flag);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void delete(HttpServletRequest request, HttpServletResponse response) {
		try {

			// �޸ı����Ӷ���һ��id����Ϊ�����ݿ����޸ĵ�ʱ���Ǹ���id���޸ĵģ���������Ҫ�õ�ѡ�е�id
			int depId = Integer.parseInt(request.getParameter("depId"));
			int proId = Integer.parseInt(request.getParameter("proId"));
			Dep2ProDao dpDao = new Dep2ProDao();
			dpDao.delete(depId, proId);
			// show(request, response);
			// ��������֮����ֱ��show����Ҫ�ض��򣬸����������һ����Ϣ��������ڷ���һ������ȥlist

			response.sendRedirect("dep2pro?depId=" + depId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete2(HttpServletRequest request, HttpServletResponse response) {
		try {

			// �޸ı����Ӷ���һ��id����Ϊ�����ݿ����޸ĵ�ʱ���Ǹ���id���޸ĵģ���������Ҫ�õ�ѡ�е�id
			int depId = Integer.parseInt(request.getParameter("depId"));
			int proId = Integer.parseInt(request.getParameter("proId"));
			Dep2ProDao dpDao = new Dep2ProDao();
			Boolean flag = dpDao.delete(depId, proId);
			PrintWriter out = response.getWriter();
			out.print(flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void deleteBatch(HttpServletRequest request, HttpServletResponse response) {
		try {

			// �޸ı����Ӷ���һ��id����Ϊ�����ݿ����޸ĵ�ʱ���Ǹ���id���޸ĵģ���������Ҫ�õ�ѡ�е�id
			int depId = Integer.parseInt(request.getParameter("depId"));
			String[] proIds = request.getParameter("proId").split(",");
			Dep2ProDao dpDao = new Dep2ProDao();
			Boolean flag = dpDao.deleteBatch(depId, proIds);
			PrintWriter out = response.getWriter();
			out.print(flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void search(HttpServletRequest request, HttpServletResponse response) {
		try {
			int depId = Integer.parseInt(request.getParameter("depId"));
			DepartmentDao depDao = new DepartmentDao();
			Department dep = new Department();
			dep = depDao.search(depId);
			Dep2ProDao dpDao = new Dep2ProDao();
			List<Project> list = dpDao.searchByDepartment(depId);
			List<Project> noList = dpDao.searchByNotDepartment(depId);
			request.setAttribute("dep", dep);
			request.setAttribute("list", list);
			request.setAttribute("noList", noList);
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
