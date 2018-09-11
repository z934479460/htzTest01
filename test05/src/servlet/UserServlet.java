package servlet;

import java.io.IOException;
import java.net.CookieStore;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDao;
import entity.User;
import util.CreatMD5;

//user的控制器类，需要继承这个类
//mvc模式：model view controller 
//用户通过浏览器首先访问控制器，控制器去model，
//model就是指所有的java类 
//，然后返回想要的数据到控制器，
//然后携带数据转发或重定向到一个view，然后响应回浏览器，
//控制器有一个技术就叫servlet，view就是jsp 
public class UserServlet extends HttpServlet {
	//servlet是一个单例多线程的模式，会有多个请求来访问，所以尽量不使用成员变量，否则很容易乱
	
	
	// 这个类里需要有两个方法,如果前台用get方式发过来请求就用get，用post就用post，五种形式里面只有form和ajax可以选择是否使用post
	private static final String path = "WEB-INF/user/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		// 如果是第一次登录界面，type就是null，那么就showlogin，如果点击登录，就会dologin
		String type = request.getParameter("type");
		if (type == null) {
			showLogin(request, response);
		} else if ("doLogin".equals(type)) {
			doLogin(request, response);
		} else if ("showRegister".equals(type)) {
			showRegister(request, response);
		} else if ("doRegister".equals(type)) {
			doRegister(request, response);
		}

	}

	private void doRegister(HttpServletRequest request, HttpServletResponse response) {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String password1 = request.getParameter("password1");
			if (!"".equals(username)&&!"".equals(password)&&!"".equals(password)&&password.equals(password1)) {
				User user = new User();
				user.setUsername(username);
				//加盐增加密码难度防止解密，一般可以用时间
				user.setPassword(CreatMD5.getMD5(password)+"你好");
				UserDao ud = new UserDao();
				boolean flag = ud.register(user);
				if (flag) {
					request.setAttribute("mes", "注册成功");
					request.getRequestDispatcher(path + "login.jsp").forward(request, response);
				}
			}else {
				request.setAttribute("mes", "注册失败");
				request.getRequestDispatcher(path + "register.jsp").forward(request, response);
			}
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	private void showRegister(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher(path + "register.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 转发到login页面
	public void showLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 把输入的用户名赋给一个cookie，然后传到页面上，这样每次打开页面都能记住这个用户名
			String name = "";
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if ("username".equals(cookies[i].getName())) {
						name = cookies[i].getValue();
					}
				}
			}
			request.setAttribute("name", name);
			request.getRequestDispatcher(path + "login.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			User user = new User();
			user.setUsername(username);
			//加盐增加密码难度防止解密
			user.setPassword(CreatMD5.getMD5(password)+"你好");
			UserDao ud = new UserDao();
			// 把输入的账号密码传过去，得到一个Boolean
			boolean flag = ud.search(user);
			if (flag) {
				// session：服务器创建一个集合，用户登录的时候给他一个id用来标记
				// 在刚访问网站的时候服务器就把sessionid创建好了，用下面的方法拿到，
				// 去集合中匹配，然后在它的map中存储信息
				HttpSession session = request.getSession();
				session.setAttribute("user", username);
				// cookie的值只能是字符串，session的值可以是对象，因为它是存在服务器上
				// cookie是存在浏览器上，他们都是服务器自己生成的
				Cookie cookie = new Cookie("username", username);
				// 可以设置时间，单位是秒
				cookie.setMaxAge(30);
				response.addCookie(cookie);
				// 重定向是发送一个url请求，而转发是确切的界面
				// response.sendRedirect("index");
				request.getRequestDispatcher("WEB-INF/index/index.jsp").forward(request, response);
			} else {
				response.sendRedirect("user");
				// request.getRequestDispatcher(path+"login.jsp").forward(request, response);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 对后台来说两种方法是一样的，所以可以直接调doget
	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		doGet(request, response);

	}
}
