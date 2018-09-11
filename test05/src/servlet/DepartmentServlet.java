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

import dao.DepartmentDao;
import entity.Department;
import net.sf.json.JSONArray;
import util.Pagination;

//user�Ŀ������࣬��Ҫ�̳������
//mvcģʽ��model view controller 
//�û�ͨ����������ȷ��ʿ�������������ȥmodel��
//model����ָ���е�java�� 
//��Ȼ�󷵻���Ҫ�����ݵ���������
//Ȼ��Я������ת�����ض���һ��view��Ȼ����Ӧ���������
//��������һ�������ͽ�servlet��view����jsp
public class DepartmentServlet extends HttpServlet {
	// ���������Ҫ����������,���ǰ̨��get��ʽ�������������get����post����post��������ʽ����ֻ��form��ajax����ѡ���Ƿ�ʹ��post
private static final String path="WEB-INF/department/";
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		// �ڵ�һ��ȡ����ǰ����post������������������
		try {
		
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
			}
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
			DepartmentDao depDao = new DepartmentDao();
			Department dep = depDao.search(id);
			// �Ѳ������dep����updateҳ��
			request.setAttribute("dep", dep);
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
			Department dep = new Department();
			String name = request.getParameter("name");
		
		
			dep.setName(name);
		
			DepartmentDao depDao = new DepartmentDao();
			boolean flag = depDao.add(dep);
			// show(request, response);
			// ��������֮����ֱ��show����Ҫ�ض��򣬸����������һ����Ϣ��������ڷ���һ������ȥlist

			response.sendRedirect("department");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update(HttpServletRequest request, HttpServletResponse response) {
		try {
			Department dep = new Department();
			String name = request.getParameter("name");
	
			// �޸ı����Ӷ���һ��id����Ϊ�����ݿ����޸ĵ�ʱ���Ǹ���id���޸ĵģ���������Ҫ�õ�ѡ�е�id
			int id = Integer.parseInt(request.getParameter("id"));
		
			dep.setId(id);
			dep.setName(name);
	
			DepartmentDao depDao = new DepartmentDao();
			boolean flag = depDao.update(dep);
			// show(request, response);
			// ��������֮����ֱ��show����Ҫ�ض��򣬸����������һ����Ϣ��������ڷ���һ������ȥlist

			response.sendRedirect("department");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		try {

			// �޸ı����Ӷ���һ��id����Ϊ�����ݿ����޸ĵ�ʱ���Ǹ���id���޸ĵģ���������Ҫ�õ�ѡ�е�id
			int id = Integer.parseInt(request.getParameter("id"));

			DepartmentDao depDao = new DepartmentDao();
			boolean flag = depDao.delete(id);
			// show(request, response);
			// ��������֮����ֱ��show����Ҫ�ض��򣬸����������һ����Ϣ��������ڷ���һ������ȥlist

			response.sendRedirect("department");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		public void search(HttpServletRequest request, HttpServletResponse response) {
		try {
			Department condition = new Department();
			String name = request.getParameter("name");
			int empCount=-1;
			if(request.getParameter("empCount")!=null&&""!=request.getParameter("empCount")) {
				empCount = Integer.parseInt(request.getParameter("empCount"));
			}
			condition.setName(name);
			condition.setEmpCount(empCount);
			DepartmentDao depDao = new DepartmentDao();
			int ye = 1;
			if (request.getParameter("ye") != null) {
				ye = Integer.parseInt(request.getParameter("ye"));
			}
			//�����ѯcount�ǲ�ѯ����������Ա����Ŀ����show�����ﲻһ��
			int count = depDao.searchCount(condition);
			Pagination p=new Pagination(ye,count,util.Constant.EMP_NUM_IN_PAGE,util.Constant.EMP_NUM_OF_PAGE);
			request.setAttribute("p", p);
			//�������������Ϣ����ȥ�������������������ס��Щ��Ϣ������������һҳ��ʱ�򲻳�����
			request.setAttribute("c", condition);
			List<Department>list= depDao.search(condition,p.getBegin(),util.Constant.EMP_NUM_IN_PAGE );
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
