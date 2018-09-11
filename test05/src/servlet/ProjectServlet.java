package servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.corba.se.impl.orbutil.closure.Constant;

import dao.ProjectDao;
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
public class ProjectServlet extends HttpServlet {
	// 这个类里需要有两个方法,如果前台用get方式发过来请求就用get，用post就用post，五种形式里面只有form和ajax可以选择是否使用post
private static final String path="WEB-INF/project/";
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
			} else if ("showAdd".equals(type)) {
				showAdd(request, response);
			} else if ("add".equals(type)) {
				add(request, response);
			} else if ("showUpdate".equals(type)) {
				showUpdate(request, response);
			} else if ("update".equals(type)) {
				update(request, response);
			} else if ("delete".equals(type)) {
				delete(request, response);
			}}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public void showAdd(HttpServletRequest request, HttpServletResponse response) {
		try {

			request.getRequestDispatcher(path+"add.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showUpdate(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 需要根据id查出这条选中的数据在update页面上显示出来，需要传过来
			// 然后用这个id在去查一遍，不能直接从list中查，因为list随时可能会被修改
			int id = Integer.parseInt(request.getParameter("id"));
			ProjectDao proDao = new ProjectDao();
			Project pro = proDao.search(id);
			// 把查出来的pro传到update页面
			request.setAttribute("pro", pro);
			// response.sendRedirect(path+"update.jsp");
			request.getRequestDispatcher(path+"update.jsp").forward(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void add(HttpServletRequest request, HttpServletResponse response) {
		try {
			Project pro = new Project();
			String name = request.getParameter("name");
		
		
			pro.setName(name);
		
			ProjectDao proDao = new ProjectDao();
			boolean flag = proDao.add(pro);
			// show(request, response);
			// 增加完了之后不能直接show，需要重定向，给浏览器发送一个信息让浏览器在发送一个请求去list

			response.sendRedirect("project");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update(HttpServletRequest request, HttpServletResponse response) {
		try {
			Project pro = new Project();
			String name = request.getParameter("name");
	
			// 修改比增加多了一个id，因为在数据库中修改的时候是根据id来修改的，所以这里要拿到选中的id
			int id = Integer.parseInt(request.getParameter("id"));
		
			pro.setId(id);
			pro.setName(name);
	
			ProjectDao proDao = new ProjectDao();
			boolean flag = proDao.update(pro);
			// show(request, response);
			// 增加完了之后不能直接show，需要重定向，给浏览器发送一个信息让浏览器在发送一个请求去list

			response.sendRedirect("project");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		try {

			// 修改比增加多了一个id，因为在数据库中修改的时候是根据id来修改的，所以这里要拿到选中的id
			int id = Integer.parseInt(request.getParameter("id"));

			ProjectDao proDao = new ProjectDao();
			boolean flag = proDao.delete(id);
			// show(request, response);
			// 增加完了之后不能直接show，需要重定向，给浏览器发送一个信息让浏览器在发送一个请求去list

			response.sendRedirect("project");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		public void search(HttpServletRequest request, HttpServletResponse response) {
		try {
			Project condition = new Project();
			String name = request.getParameter("name");
			
			condition.setName(name);
			
			ProjectDao proDao = new ProjectDao();
			int ye = 1;
			if (request.getParameter("ye") != null) {
				ye = Integer.parseInt(request.getParameter("ye"));
			}
			//这里查询count是查询符合条件的员工数目，跟show方法里不一样
			int count = proDao.searchCount(condition);
			Pagination p=new Pagination(ye,count,util.Constant.EMP_NUM_IN_PAGE,util.Constant.EMP_NUM_OF_PAGE);
			request.setAttribute("p", p);
			//把输入的搜索信息发过去，这样搜索框里就能留住这些信息，搜索完点击下一页的时候不出问题
			request.setAttribute("c", condition);
			List<Project>list= proDao.search(condition,p.getBegin(),util.Constant.EMP_NUM_IN_PAGE );
			request.setAttribute("list", list);
			request.getRequestDispatcher(path+"list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		doGet(request, response);
	}

}
