package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.sun.corba.se.impl.orbutil.closure.Constant;

import dao.DepartmentDao;
import dao.EmployeeDao;
import entity.Department;
import entity.Employee;
import net.sf.json.JSONArray;
import util.Pagination;

//user�Ŀ������࣬��Ҫ�̳������
//mvcģʽ��model view controller 
//�û�ͨ����������ȷ��ʿ�������������ȥmodel��
//model����ָ���е�java�� 
//��Ȼ�󷵻���Ҫ�����ݵ���������
//Ȼ��Я������ת�����ض���һ��view��Ȼ����Ӧ���������
//��������һ�������ͽ�servlet��view����jsp
public class EmployeeServlet extends HttpServlet {
	// ���������Ҫ����������,���ǰ̨��get��ʽ�������������get����post����post��������ʽ����ֻ��form��ajax����ѡ���Ƿ�ʹ��post
	private static final String path = "WEB-INF/employee/";

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
			} else if ("add2".equals(type)) {
				add2(request, response);
			} else if ("showUpdate".equals(type)) {
				showUpdate(request, response);
			} else if ("update".equals(type)) {
				update(request, response);
			} else if ("delete".equals(type)) {
				delete(request, response);
			} else if ("deleteBatch".equals(type)) {
				deleteBatch(request, response);
			} else if ("showUpdateBatch1".equals(type)) {
				showUpdateBatch1(request, response);
			} else if ("updateBatch1".equals(type)) {
				updateBatch1(request, response);
			} else if ("showUpdateBatch2".equals(type)) {
				showUpdateBatch2(request, response);
			} else if ("updateBatch2".equals(type)) {
				updateBatch2(request, response);
			} else if ("updateBatch3".equals(type)) {
				updateBatch3(request, response);
			} else if ("showAdd2".equals(type)) {
				showAdd2(request, response);
			} else if ("upload".equals(type)) {
				upload(request, response);
			}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ת����loginҳ��
	// public void show(HttpServletRequest request, HttpServletResponse response) {
	// try {
	//
	// EmployeeDao empDao = new EmployeeDao();
	// // һ�㿪listҳ��,Ҫ��ʾ��һҳ������,��������ye�͵���null
	// int ye = 1;
	// if (request.getParameter("ye") != null) {
	// ye = Integer.parseInt(request.getParameter("ye"));
	// }
	// int count = empDao.searchCount();
	// //int size = 2;
	// //int numOfPage = 5;
	// //ע�⵼���𵼴��ˣ�jdkҲ��һ�������
	// //Ҫ����ȥ���ݼ���������Ե�ֵ����ǰ��ye��,������Ϣ����Ŀ,ÿҳ��ʾ����Ϣ������ҳ����ʾ����ҳ
	// Pagination p=new
	// Pagination(ye,count,util.Constant.EMP_NUM_IN_PAGE,util.Constant.EMP_NUM_OF_PAGE);
	// //request.setAttribute("maxYe", p.getMaxYe());
	// // �ڸմ򿪽����ʱ��ye��1��Ҫ��������list����ȥ������һҳ��һҳ���������õ���
	// //request.setAttribute("ye", p.getYe());
	// //request.setAttribute("beginYe", p.getBeginYe());
	// //request.setAttribute("endYe", p.getEndYe());
	// //�������ĸ�����ֱ�Ӵ�p
	// request.setAttribute("p", p);
	// List<Employee> list =
	// empDao.search(p.getBegin(),util.Constant.EMP_NUM_IN_PAGE);
	// // ���õ���list�ŵ�request��ȥ
	// request.setAttribute("list", list);
	// request.getRequestDispatcher(path+"list.jsp").forward(request, response);
	// } catch (ServletException | IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	public void showAdd(HttpServletRequest request, HttpServletResponse response) {
		try {
			DepartmentDao depDao = new DepartmentDao();
			List<Department> depList = depDao.search();
			request.setAttribute("depList", depList);
			request.getRequestDispatcher(path + "add.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showAdd2(HttpServletRequest request, HttpServletResponse response) {
		try {
			DepartmentDao depDao = new DepartmentDao();
			List<Department> depList = depDao.search();
			request.setAttribute("depList", depList);
			request.getRequestDispatcher(path + "add2.jsp").forward(request, response);
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
			EmployeeDao empDao = new EmployeeDao();
			Employee emp = empDao.search(id);
			DepartmentDao depDao = new DepartmentDao();
			List<Department> depList = depDao.search();
			request.setAttribute("depList", depList);
			// �Ѳ������emp����updateҳ��
			request.setAttribute("emp", emp);
			// response.sendRedirect(path+"update.jsp");
			request.getRequestDispatcher(path + "update.jsp").forward(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void add2(HttpServletRequest request, HttpServletResponse response) {
		try {
			Employee emp = new Employee();
			Department dep = new Department();
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			int age = Integer.parseInt(request.getParameter("age"));
			// �п��ܳ��ֲ�ѡ���ŵ��������ôdepId���ǿ��ַ���������������
			// �����depId���-1�������ݿ��в����ʣ�������Integer����������null
			Integer depId = null;
			if (!"".equals(request.getParameter("depId"))) {
				depId = Integer.parseInt(request.getParameter("depId"));
			}
			String photo = request.getParameter("picture");
			dep.setId(depId);
			emp.setName(name);
			emp.setSex(sex);
			emp.setAge(age);
			emp.setDep(dep);
			emp.setPhoto(photo);
			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.add(emp);
			// show(request, response);
			// ��������֮����ֱ��show����Ҫ�ض��򣬸����������һ����Ϣ��������ڷ���һ������ȥlist

			response.sendRedirect("employee");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void add(HttpServletRequest request, HttpServletResponse response) {
		try {
			String name = "";
			String sex = "";
			int age = 0;
			String depId = "";
			// �ϴ������·����,pic�ļ���
			// String path = request.getServletContext().getRealPath("/") + "/pic";
			// �ϴ�������·����������һ������·��ȥ����
			String path = "F:/test05/pic/";
			FileItemFactory factory = new DiskFileItemFactory();
			// Ϊ�����󴴽�һ��DiskFileItemFactory����ͨ��������������
			// ִ�н��������еı���Ŀ��������һ��List�С�
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			String photoName = "";
			for (int i = 0; i < items.size(); i++) {
				FileItem item = items.get(i);
				// getFieldName()�������ж��ϴ����ļ���ʲô���͵�
				if (item.getFieldName().equals("photo")) {
					// �û��ϴ����ļ���Ϊ�˷�ֹ�ظ�����ʹ��uuid���ļ���׺���ķ�ʽ
					UUID uuid = UUID.randomUUID();
					// ͨ���ļ��������һ��������ȡ��׺��
					String houzhui = item.getName().substring(item.getName().lastIndexOf("."));
					// toStringת�����ַ���
					photoName = uuid.toString() + houzhui;
					File savedFile = new File(path, photoName);
					item.write(savedFile);

					// �ϴ��ı�
				} else if (item.getFieldName().equals("name")) {
					// Ϊ�˷�ֹ�����������룬�����������ַ����Ƚ�������ת����utf-8������string��һ������
					name = new String(item.getString().getBytes("ISO-8859-1"), "utf-8");

				} else if (item.getFieldName().equals("sex")) {
					sex = new String(item.getString().getBytes("ISO-8859-1"), "utf-8");
				} else if (item.getFieldName().equals("age")) {
					age = Integer.parseInt(item.getString());
				} else if (item.getFieldName().equals("depId")) {
					depId = item.getString();
				}
			}
			Employee emp = new Employee();
			Department dep = new Department();

			if (!"".equals(depId)) {
				dep.setId(Integer.parseInt(depId));
			}
			emp.setName(name);
			emp.setSex(sex);
			emp.setAge(age);
			emp.setDep(dep);
			emp.setPhoto(photoName);
			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.add(emp);
			// show(request, response);
			// ��������֮����ֱ��show����Ҫ�ض��򣬸����������һ����Ϣ��������ڷ���һ������ȥlist

			response.sendRedirect("employee");
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void upload(HttpServletRequest request, HttpServletResponse response) {
		try {
			// �ϴ�������·����������һ������·��ȥ����
			String path = "F:/test05/pic/";
			FileItemFactory factory = new DiskFileItemFactory();
			// Ϊ�����󴴽�һ��DiskFileItemFactory����ͨ��������������
			// ִ�н��������еı���Ŀ��������һ��List�С�
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			String photoName = "";
		
			for (int i = 0; i < items.size(); i++) {
				FileItem item = items.get(i);
				// getFieldName()�������ж��ϴ����ļ���ʲô���͵�
				if (item.getFieldName().equals("photo")) {
					// �û��ϴ����ļ���Ϊ�˷�ֹ�ظ�����ʹ��uuid���ļ���׺���ķ�ʽ
					UUID uuid = UUID.randomUUID();
					// ͨ���ļ��������һ��������ȡ��׺��
					String houzhui = item.getName().substring(item.getName().lastIndexOf("."));
					// toStringת�����ַ���
					photoName = uuid.toString() + houzhui;
					File savedFile = new File(path, photoName);
					item.write(savedFile);
				
				}
			}

			PrintWriter out = response.getWriter();
			out.print(photoName);

		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void update(HttpServletRequest request, HttpServletResponse response) {
		try {
			Employee emp = new Employee();
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			// �޸ı����Ӷ���һ��id����Ϊ�����ݿ����޸ĵ�ʱ���Ǹ���id���޸ĵģ���������Ҫ�õ�ѡ�е�id
			int id = Integer.parseInt(request.getParameter("id"));
			int age = Integer.parseInt(request.getParameter("age"));
			// �п��ܳ��ֲ�ѡ���ŵ��������ôdepId���ǿ��ַ���������������
			// �����depId���-1�������ݿ��в����ʣ�������Integer����������null
			Integer depId = null;
			if (!"".equals(request.getParameter("depId"))) {
				depId = Integer.parseInt(request.getParameter("depId"));
			}
			emp.setId(id);
			emp.setName(name);
			emp.setSex(sex);
			emp.setAge(age);
			Department dep = new Department();
			dep.setId(depId);
			emp.setDep(dep);
			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.update(emp);
			// show(request, response);
			// ��������֮����ֱ��show����Ҫ�ض��򣬸����������һ����Ϣ��������ڷ���һ������ȥlist

			response.sendRedirect("employee");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		try {

			// �޸ı����Ӷ���һ��id����Ϊ�����ݿ����޸ĵ�ʱ���Ǹ���id���޸ĵģ���������Ҫ�õ�ѡ�е�id
			int id = Integer.parseInt(request.getParameter("id"));

			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.delete(id);
			// show(request, response);
			// ��������֮����ֱ��show����Ҫ�ض��򣬸����������һ����Ϣ��������ڷ���һ������ȥlist

			response.sendRedirect("employee");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteBatch(HttpServletRequest request, HttpServletResponse response) {
		try {

			// ��������ids����һ���ַ���
			String ids = request.getParameter("ids");

			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.deleteBatch(ids);
			// show(request, response);
			// ��������֮����ֱ��show����Ҫ�ض��򣬸����������һ����Ϣ��������ڷ���һ������ȥlist
			response.sendRedirect("employee");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ͳһ�޸�
	public void showUpdateBatch1(HttpServletRequest request, HttpServletResponse response) {
		try {
			// ��Ҫ����id�������ѡ�е�������updateҳ������ʾ��������Ҫ������
			// Ȼ�������id��ȥ��һ�飬����ֱ�Ӵ�list�в飬��Ϊlist��ʱ���ܻᱻ�޸�
			String ids = request.getParameter("ids");
			EmployeeDao empDao = new EmployeeDao();
			List<Employee> list = empDao.search(ids);
			DepartmentDao depDao = new DepartmentDao();
			List<Department> depList = depDao.search();
			request.setAttribute("depList", depList);
			// ��ʾ��һ��emp�����ԣ����ѡһ���������ʱ��Ҫ������emp����������Ҫ��idsҲ���أ��������������request�����з���ids
			request.setAttribute("emp", list.get(0));
			request.setAttribute("ids", ids);
			// response.sendRedirect(path+"update.jsp");
			request.getRequestDispatcher(path + "updateBatch1.jsp").forward(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateBatch1(HttpServletRequest request, HttpServletResponse response) {
		try {
			Employee emp = new Employee();
			// �õ���������ids
			String ids = request.getParameter("ids");
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			int age = Integer.parseInt(request.getParameter("age"));
			// �п��ܳ��ֲ�ѡ���ŵ��������ôdepId���ǿ��ַ���������������
			// �����depId���-1�������ݿ��в����ʣ�������Integer����������null
			Integer depId = null;
			if (!"".equals(request.getParameter("depId"))) {
				depId = Integer.parseInt(request.getParameter("depId"));
			}
			emp.setName(name);
			emp.setSex(sex);
			emp.setAge(age);
			Department dep = new Department();
			dep.setId(depId);
			emp.setDep(dep);
			EmployeeDao empDao = new EmployeeDao();
			// ��Ҫ����һ��ͳһ�޸ĵķ���
			boolean flag = empDao.updateBatch1(emp, ids);
			// show(request, response);
			// ��������֮����ֱ��show����Ҫ�ض��򣬸����������һ����Ϣ��������ڷ���һ������ȥlist

			response.sendRedirect("employee");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateBatch2(HttpServletRequest request, HttpServletResponse response) {
		try {
			// �õ�emps���ڷָ��ÿ��emp
			String emps = request.getParameter("emps");
			String[] array = emps.split(";");
			List<Employee> list = new ArrayList<>();
			// ����array�������ŵ�list��ȥ
			for (int i = 0; i < array.length; i++) {
				// ��array��ÿ��Ԫ���ڽ��зָ���ݶ��ŷָ�
				String[] temp = array[i].split(",");
				Employee emp = new Employee();
				emp.setId(Integer.parseInt(temp[0]));
				emp.setName(temp[1]);
				emp.setSex(temp[2]);
				emp.setAge(Integer.parseInt(temp[3]));
				Department dep = new Department();
				String a = temp[4];
				if (!a.equals("-1")) {
					dep.setId(Integer.valueOf(temp[4]));
				} else {
					dep.setId(null);
				}
				emp.setDep(dep);
				list.add(emp);
			}
			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.updateBatch2(list);
			response.sendRedirect("employee");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void showUpdateBatch2(HttpServletRequest request, HttpServletResponse response) {
		try {
			// ��Ҫ����id�������ѡ�е�������updateҳ������ʾ��������Ҫ������
			// Ȼ�������id��ȥ��һ�飬����ֱ�Ӵ�list�в飬��Ϊlist��ʱ���ܻᱻ�޸�
			String ids = request.getParameter("ids");
			EmployeeDao empDao = new EmployeeDao();
			List<Employee> list = empDao.search(ids);
			DepartmentDao depDao = new DepartmentDao();
			List<Department> depList = depDao.search();
			request.setAttribute("depList", depList);
			// ��ʾ��һ��emp�����ԣ����ѡһ���������ʱ��Ҫ������emp����������Ҫ��idsҲ���أ��������������request�����з���ids
			request.setAttribute("list", list);

			// response.sendRedirect(path+"update.jsp");
			request.getRequestDispatcher(path + "updateBatch2.jsp").forward(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateBatch3(HttpServletRequest request, HttpServletResponse response) {
		try {
			// �õ�emps���ڷָ��ÿ��emp
			String emps = request.getParameter("emps");
			// ��jsonת����list
			JSONArray jsonArray = JSONArray.fromObject(emps);
			// Employee.class �������ɵ�list�е�Ԫ�ص�����
			List<Employee> list = (List<Employee>) jsonArray.toCollection(jsonArray, Employee.class);

			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.updateBatch2(list);
			response.sendRedirect("employee");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void search(HttpServletRequest request, HttpServletResponse response) {
		try {
			EmployeeDao empDao = new EmployeeDao();
			DepartmentDao depDao = new DepartmentDao();
			Employee condition = new Employee();
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			int age = -1;
			if (request.getParameter("age") != null && "" != request.getParameter("age")) {
				age = Integer.parseInt(request.getParameter("age"));
			}
			int depId = -1;
			if (request.getParameter("depId") != null && "" != request.getParameter("depId")) {
				depId = Integer.parseInt(request.getParameter("depId"));
			}

			condition.setName(name);
			condition.setSex(sex);
			condition.setAge(age);
			Department dep = new Department();
			dep.setId(depId);
			condition.setDep(dep);
			int ye = 1;
			if (request.getParameter("ye") != null) {
				ye = Integer.parseInt(request.getParameter("ye"));
			}
			// �����ѯcount�ǲ�ѯ����������Ա����Ŀ����show�����ﲻһ��
			int count = empDao.searchCount(condition);
			Pagination p = new Pagination(ye, count, util.Constant.EMP_NUM_IN_PAGE, util.Constant.EMP_NUM_OF_PAGE);
			request.setAttribute("p", p);
			// �������������Ϣ����ȥ�������������������ס��Щ��Ϣ������������һҳ��ʱ�򲻳�����
			request.setAttribute("c", condition);
			List<Employee> list = empDao.search(condition, p.getBegin(), util.Constant.EMP_NUM_IN_PAGE);
			List<Department> depList = depDao.search();
			request.setAttribute("list", list);
			request.setAttribute("depList", depList);
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
