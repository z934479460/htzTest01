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

//user�Ŀ������࣬��Ҫ�̳������
//mvcģʽ��model view controller 
//�û�ͨ����������ȷ��ʿ�������������ȥmodel��
//model����ָ���е�java�� 
//��Ȼ�󷵻���Ҫ�����ݵ���������
//Ȼ��Я������ת�����ض���һ��view��Ȼ����Ӧ���������
//��������һ�������ͽ�servlet��view����jsp 
public class UserServlet extends HttpServlet {
	//servlet��һ���������̵߳�ģʽ�����ж�����������ʣ����Ծ�����ʹ�ó�Ա�����������������
	
	
	// ���������Ҫ����������,���ǰ̨��get��ʽ�������������get����post����post��������ʽ����ֻ��form��ajax����ѡ���Ƿ�ʹ��post
	private static final String path = "WEB-INF/user/";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		// ����ǵ�һ�ε�¼���棬type����null����ô��showlogin����������¼���ͻ�dologin
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
				//�������������Ѷȷ�ֹ���ܣ�һ�������ʱ��
				user.setPassword(CreatMD5.getMD5(password)+"���");
				UserDao ud = new UserDao();
				boolean flag = ud.register(user);
				if (flag) {
					request.setAttribute("mes", "ע��ɹ�");
					request.getRequestDispatcher(path + "login.jsp").forward(request, response);
				}
			}else {
				request.setAttribute("mes", "ע��ʧ��");
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

	// ת����loginҳ��
	public void showLogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			// ��������û�������һ��cookie��Ȼ�󴫵�ҳ���ϣ�����ÿ�δ�ҳ�涼�ܼ�ס����û���
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
			//�������������Ѷȷ�ֹ����
			user.setPassword(CreatMD5.getMD5(password)+"���");
			UserDao ud = new UserDao();
			// ��������˺����봫��ȥ���õ�һ��Boolean
			boolean flag = ud.search(user);
			if (flag) {
				// session������������һ�����ϣ��û���¼��ʱ�����һ��id�������
				// �ڸշ�����վ��ʱ��������Ͱ�sessionid�������ˣ�������ķ����õ���
				// ȥ������ƥ�䣬Ȼ��������map�д洢��Ϣ
				HttpSession session = request.getSession();
				session.setAttribute("user", username);
				// cookie��ֵֻ�����ַ�����session��ֵ�����Ƕ�����Ϊ���Ǵ��ڷ�������
				// cookie�Ǵ���������ϣ����Ƕ��Ƿ������Լ����ɵ�
				Cookie cookie = new Cookie("username", username);
				// ��������ʱ�䣬��λ����
				cookie.setMaxAge(30);
				response.addCookie(cookie);
				// �ض����Ƿ���һ��url���󣬶�ת����ȷ�еĽ���
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

	// �Ժ�̨��˵���ַ�����һ���ģ����Կ���ֱ�ӵ�doget
	public void doPost(HttpServletRequest request, HttpServletResponse response) {

		doGet(request, response);

	}
}
