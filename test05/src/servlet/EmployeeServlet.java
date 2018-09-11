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

//user的控制器类，需要继承这个类
//mvc模式：model view controller 
//用户通过浏览器首先访问控制器，控制器去model，
//model就是指所有的java类 
//，然后返回想要的数据到控制器，
//然后携带数据转发或重定向到一个view，然后响应回浏览器，
//控制器有一个技术就叫servlet，view就是jsp
public class EmployeeServlet extends HttpServlet {
	// 这个类里需要有两个方法,如果前台用get方式发过来请求就用get，用post就用post，五种形式里面只有form和ajax可以选择是否使用post
	private static final String path = "WEB-INF/employee/";

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

	// 转发到login页面
	// public void show(HttpServletRequest request, HttpServletResponse response) {
	// try {
	//
	// EmployeeDao empDao = new EmployeeDao();
	// // 一点开list页面,要显示第一页的数据,传过来的ye就等于null
	// int ye = 1;
	// if (request.getParameter("ye") != null) {
	// ye = Integer.parseInt(request.getParameter("ye"));
	// }
	// int count = empDao.searchCount();
	// //int size = 2;
	// //int numOfPage = 5;
	// //注意导包别导错了，jdk也有一个这个包
	// //要传过去数据计算各个属性的值，当前的ye数,所有信息的数目,每页显示的信息数量，页码显示多少页
	// Pagination p=new
	// Pagination(ye,count,util.Constant.EMP_NUM_IN_PAGE,util.Constant.EMP_NUM_OF_PAGE);
	// //request.setAttribute("maxYe", p.getMaxYe());
	// // 在刚打开界面的时候，ye是1，要把它传到list里面去，让上一页下一页操作可以拿到它
	// //request.setAttribute("ye", p.getYe());
	// //request.setAttribute("beginYe", p.getBeginYe());
	// //request.setAttribute("endYe", p.getEndYe());
	// //传上面四个就是直接传p
	// request.setAttribute("p", p);
	// List<Employee> list =
	// empDao.search(p.getBegin(),util.Constant.EMP_NUM_IN_PAGE);
	// // 把拿到的list放到request里去
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
			// 需要根据id查出这条选中的数据在update页面上显示出来，需要传过来
			// 然后用这个id在去查一遍，不能直接从list中查，因为list随时可能会被修改
			int id = Integer.parseInt(request.getParameter("id"));
			EmployeeDao empDao = new EmployeeDao();
			Employee emp = empDao.search(id);
			DepartmentDao depDao = new DepartmentDao();
			List<Department> depList = depDao.search();
			request.setAttribute("depList", depList);
			// 把查出来的emp传到update页面
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
			// 有可能出现不选部门的情况，那么depId就是空字符串，规避这种情况
			// 如果把depId设成-1，在数据库中不合适，所以用Integer，它可以有null
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
			// 增加完了之后不能直接show，需要重定向，给浏览器发送一个信息让浏览器在发送一个请求去list

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
			// 上传到相对路径里,pic文件里
			// String path = request.getServletContext().getRealPath("/") + "/pic";
			// 上传到绝对路径，在配置一个虚拟路径去访问
			String path = "F:/test05/pic/";
			FileItemFactory factory = new DiskFileItemFactory();
			// 为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。
			// 执行解析后，所有的表单项目都保存在一个List中。
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			String photoName = "";
			for (int i = 0; i < items.size(); i++) {
				FileItem item = items.get(i);
				// getFieldName()表单名，判断上传的文件是什么类型的
				if (item.getFieldName().equals("photo")) {
					// 用户上传的文件名为了防止重复可以使用uuid加文件后缀名的方式
					UUID uuid = UUID.randomUUID();
					// 通过文件名的最后一个点来截取后缀名
					String houzhui = item.getName().substring(item.getName().lastIndexOf("."));
					// toString转换成字符串
					photoName = uuid.toString() + houzhui;
					File savedFile = new File(path, photoName);
					item.write(savedFile);

					// 上传文本
				} else if (item.getFieldName().equals("name")) {
					// 为了防止出现中文乱码，讲传过来的字符串先解析，在转换成utf-8，这是string的一个方法
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
			// 增加完了之后不能直接show，需要重定向，给浏览器发送一个信息让浏览器在发送一个请求去list

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
			// 上传到绝对路径，在配置一个虚拟路径去访问
			String path = "F:/test05/pic/";
			FileItemFactory factory = new DiskFileItemFactory();
			// 为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。
			// 执行解析后，所有的表单项目都保存在一个List中。
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(request);
			String photoName = "";
		
			for (int i = 0; i < items.size(); i++) {
				FileItem item = items.get(i);
				// getFieldName()表单名，判断上传的文件是什么类型的
				if (item.getFieldName().equals("photo")) {
					// 用户上传的文件名为了防止重复可以使用uuid加文件后缀名的方式
					UUID uuid = UUID.randomUUID();
					// 通过文件名的最后一个点来截取后缀名
					String houzhui = item.getName().substring(item.getName().lastIndexOf("."));
					// toString转换成字符串
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
			// 修改比增加多了一个id，因为在数据库中修改的时候是根据id来修改的，所以这里要拿到选中的id
			int id = Integer.parseInt(request.getParameter("id"));
			int age = Integer.parseInt(request.getParameter("age"));
			// 有可能出现不选部门的情况，那么depId就是空字符串，规避这种情况
			// 如果把depId设成-1，在数据库中不合适，所以用Integer，它可以有null
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
			// 增加完了之后不能直接show，需要重定向，给浏览器发送一个信息让浏览器在发送一个请求去list

			response.sendRedirect("employee");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) {
		try {

			// 修改比增加多了一个id，因为在数据库中修改的时候是根据id来修改的，所以这里要拿到选中的id
			int id = Integer.parseInt(request.getParameter("id"));

			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.delete(id);
			// show(request, response);
			// 增加完了之后不能直接show，需要重定向，给浏览器发送一个信息让浏览器在发送一个请求去list

			response.sendRedirect("employee");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteBatch(HttpServletRequest request, HttpServletResponse response) {
		try {

			// 传过来的ids就是一个字符串
			String ids = request.getParameter("ids");

			EmployeeDao empDao = new EmployeeDao();
			boolean flag = empDao.deleteBatch(ids);
			// show(request, response);
			// 增加完了之后不能直接show，需要重定向，给浏览器发送一个信息让浏览器在发送一个请求去list
			response.sendRedirect("employee");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 统一修改
	public void showUpdateBatch1(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 需要根据id查出这条选中的数据在update页面上显示出来，需要传过来
			// 然后用这个id在去查一遍，不能直接从list中查，因为list随时可能会被修改
			String ids = request.getParameter("ids");
			EmployeeDao empDao = new EmployeeDao();
			List<Employee> list = empDao.search(ids);
			DepartmentDao depDao = new DepartmentDao();
			List<Department> depList = depDao.search();
			request.setAttribute("depList", depList);
			// 显示哪一个emp都可以，随便选一个，保存的时候要对所有emp操作，所以要把ids也返回，这个方法就是在request对象中放入ids
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
			// 拿到传过来的ids
			String ids = request.getParameter("ids");
			String name = request.getParameter("name");
			String sex = request.getParameter("sex");
			int age = Integer.parseInt(request.getParameter("age"));
			// 有可能出现不选部门的情况，那么depId就是空字符串，规避这种情况
			// 如果把depId设成-1，在数据库中不合适，所以用Integer，它可以有null
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
			// 需要建立一个统一修改的方法
			boolean flag = empDao.updateBatch1(emp, ids);
			// show(request, response);
			// 增加完了之后不能直接show，需要重定向，给浏览器发送一个信息让浏览器在发送一个请求去list

			response.sendRedirect("employee");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateBatch2(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 拿到emps，在分割成每个emp
			String emps = request.getParameter("emps");
			String[] array = emps.split(";");
			List<Employee> list = new ArrayList<>();
			// 遍历array，把它放到list中去
			for (int i = 0; i < array.length; i++) {
				// 对array的每个元素在进行分割，根据逗号分割
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
			// 需要根据id查出这条选中的数据在update页面上显示出来，需要传过来
			// 然后用这个id在去查一遍，不能直接从list中查，因为list随时可能会被修改
			String ids = request.getParameter("ids");
			EmployeeDao empDao = new EmployeeDao();
			List<Employee> list = empDao.search(ids);
			DepartmentDao depDao = new DepartmentDao();
			List<Department> depList = depDao.search();
			request.setAttribute("depList", depList);
			// 显示哪一个emp都可以，随便选一个，保存的时候要对所有emp操作，所以要把ids也返回，这个方法就是在request对象中放入ids
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
			// 拿到emps，在分割成每个emp
			String emps = request.getParameter("emps");
			// 把json转换成list
			JSONArray jsonArray = JSONArray.fromObject(emps);
			// Employee.class 声明生成的list中的元素的类型
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
			// 这里查询count是查询符合条件的员工数目，跟show方法里不一样
			int count = empDao.searchCount(condition);
			Pagination p = new Pagination(ye, count, util.Constant.EMP_NUM_IN_PAGE, util.Constant.EMP_NUM_OF_PAGE);
			request.setAttribute("p", p);
			// 把输入的搜索信息发过去，这样搜索框里就能留住这些信息，搜索完点击下一页的时候不出问题
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
