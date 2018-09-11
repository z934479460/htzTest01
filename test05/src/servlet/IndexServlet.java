package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;

//user的控制器类，需要继承这个类
//mvc模式：model view controller 
//用户通过浏览器首先访问控制器，控制器去model，
//model就是指所有的java类 
//，然后返回想要的数据到控制器，
//然后携带数据转发或重定向到一个view，然后响应回浏览器，
//控制器有一个技术就叫servlet，view就是jsp
public class IndexServlet extends HttpServlet {
	// 这个类里需要有两个方法,如果前台用get方式发过来请求就用get，用post就用post，五种形式里面只有form和ajax可以选择是否使用post

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		//只有登录才能访问index
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			try {
				response.sendRedirect("user");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// 如果是第一次登录界面，type就是null，那么就showlogin，如果点击登录，就会dologin
			String type = request.getParameter("type");
			if (type == null) {
				index(request, response);
			}
		}
	}

	private void index(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("WEB-INF/index/index.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 对后台来说两种方法是一样的，所以可以直接调doget
	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		doGet(request, response);

	}
}
