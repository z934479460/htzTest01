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

//user的控制器类，需要继承这个类
//mvc模式：model view controller 
//用户通过浏览器首先访问控制器，控制器去model，
//model就是指所有的java类 
//，然后返回想要的数据到控制器，
//然后携带数据转发或重定向到一个view，然后响应回浏览器，
//控制器有一个技术就叫servlet，view就是jsp
public class Dep2ProServlet extends HttpServlet {
	// 这个类里需要有两个方法,如果前台用get方式发过来请求就用get，用post就用post，五种形式里面只有form和ajax可以选择是否使用post
	private static final String path = "WEB-INF/dep2pro/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		
		
		// 在第一次取参数前设置post方法不出现中文乱码
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

			// 修改比增加多了一个id，因为在数据库中修改的时候是根据id来修改的，所以这里要拿到选中的id
			int depId = Integer.parseInt(request.getParameter("depId"));
			int proId = Integer.parseInt(request.getParameter("proId"));
			Dep2ProDao dpDao = new Dep2ProDao();
			dpDao.delete(depId, proId);
			// show(request, response);
			// 增加完了之后不能直接show，需要重定向，给浏览器发送一个信息让浏览器在发送一个请求去list

			response.sendRedirect("dep2pro?depId=" + depId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete2(HttpServletRequest request, HttpServletResponse response) {
		try {

			// 修改比增加多了一个id，因为在数据库中修改的时候是根据id来修改的，所以这里要拿到选中的id
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

			// 修改比增加多了一个id，因为在数据库中修改的时候是根据id来修改的，所以这里要拿到选中的id
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
