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

//user�Ŀ������࣬��Ҫ�̳������
//mvcģʽ��model view controller 
//�û�ͨ����������ȷ��ʿ�������������ȥmodel��
//model����ָ���е�java�� 
//��Ȼ�󷵻���Ҫ�����ݵ���������
//Ȼ��Я������ת�����ض���һ��view��Ȼ����Ӧ���������
//��������һ�������ͽ�servlet��view����jsp
public class ProjectServlet extends HttpServlet {
	// ���������Ҫ����������,���ǰ̨��get��ʽ�������������get����post����post��������ʽ����ֻ��form��ajax����ѡ���Ƿ�ʹ��post
private static final String path="WEB-INF/project/";
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
			// ��Ҫ����id�������ѡ�е�������updateҳ������ʾ��������Ҫ������
			// Ȼ�������id��ȥ��һ�飬����ֱ�Ӵ�list�в飬��Ϊlist��ʱ���ܻᱻ�޸�
			int id = Integer.parseInt(request.getParameter("id"));
			ProjectDao proDao = new ProjectDao();
			Project pro = proDao.search(id);
			// �Ѳ������pro����updateҳ��
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
			// ��������֮����ֱ��show����Ҫ�ض��򣬸����������һ����Ϣ��������ڷ���һ������ȥlist

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
	
			// �޸ı����Ӷ���һ��id����Ϊ�����ݿ����޸ĵ�ʱ���Ǹ���id���޸ĵģ���������Ҫ�õ�ѡ�е�id
			int id = Integer.parseInt(request.getParameter("id"));
		
			pro.setId(id);
			pro.setName(name);
	
			ProjectDao proDao = new ProjectDao();
			boolean flag = proDao.update(pro);
			// show(request, response);
			// ��������֮����ֱ��show����Ҫ�ض��򣬸����������һ����Ϣ��������ڷ���һ������ȥlist

			response.sendRedirect("project");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		try {

			// �޸ı����Ӷ���һ��id����Ϊ�����ݿ����޸ĵ�ʱ���Ǹ���id���޸ĵģ���������Ҫ�õ�ѡ�е�id
			int id = Integer.parseInt(request.getParameter("id"));

			ProjectDao proDao = new ProjectDao();
			boolean flag = proDao.delete(id);
			// show(request, response);
			// ��������֮����ֱ��show����Ҫ�ض��򣬸����������һ����Ϣ��������ڷ���һ������ȥlist

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
			//�����ѯcount�ǲ�ѯ����������Ա����Ŀ����show�����ﲻһ��
			int count = proDao.searchCount(condition);
			Pagination p=new Pagination(ye,count,util.Constant.EMP_NUM_IN_PAGE,util.Constant.EMP_NUM_OF_PAGE);
			request.setAttribute("p", p);
			//�������������Ϣ����ȥ�������������������ס��Щ��Ϣ������������һҳ��ʱ�򲻳�����
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
