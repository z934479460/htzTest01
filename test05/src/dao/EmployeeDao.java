package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.Department;
import entity.Employee;

//对数据库增删改查的方法类
public class EmployeeDao extends BaseDao {

	// 上面的方法是存储方法，还需要一个读取的方法，再次运行程序的时候能看到上一次输入的数据，也就是输入流
	// 这里没有void 是有返回值的方法，List<Employee>这是定义这个方法返回来的值的类型
	public List<Employee> search() {
		List<Employee> list = new ArrayList<Employee>();
		// 在这里新建一个list
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {

			// 3.与数据库建立连接
			conn = getConnection();
			// 4.建立statement sql语句执行器
			stat = conn.createStatement();
			// 5.执行sql语句并得到结果
			// 下面这个是查询,查出来的结果类型是ResultSet类型的，跟游标一样
			// String sql = "select e.*,d.name as dName,emp_count from employee as e left
			// join department as"
			// + " d on e.d_id=d.id ";
			String sql = "select * from employee ";
			rs = stat.executeQuery(sql);
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("Name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				list.add(emp);
			}

			// 7.关闭
			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<Employee> search(int begin, int size) {
		List<Employee> list = new ArrayList<Employee>();
		// 在这里新建一个list
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {

			// 3.与数据库建立连接
			conn = getConnection();
			// 4.建立statement sql语句执行器
			stat = conn.createStatement();
			// 5.执行sql语句并得到结果
			// 下面这个是查询,查出来的结果类型是ResultSet类型的，跟游标一样
			// String sql = "select e.*,d.name as dName,emp_count from employee as e left
			// join department as"
			// + " d on e.d_id=d.id ";
			String sql = "select * from employee limit " + begin + "," + size;
			rs = stat.executeQuery(sql);
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("Name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				list.add(emp);
			}

			// 7.关闭
			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public int searchCount() {
		int count = 0;
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {

			// 3.与数据库建立连接
			conn = getConnection();
			// 4.建立statement sql语句执行器
			stat = conn.createStatement();
			// 查询employee表中的人数
			String sql = "select count(*) from employee ";
			rs = stat.executeQuery(sql);
			if (rs.next()) {
				// 查出来的结果集就1列，直接设置1
				count = rs.getInt(1);
			}

			// 7.关闭
			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	public int searchCount(Employee condition) {
		int count = 0;
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {

			// 3.与数据库建立连接
			conn = getConnection();
			// 4.建立statement sql语句执行器
			stat = conn.createStatement();
			String where = "where 1=1";
			if (condition.getName() != null && !condition.getName().equals("")) {
				where += " and e.name='" + condition.getName() + "'";
			}
			if (condition.getSex() != null && !condition.getSex().equals("")) {
				where += " and sex='" + condition.getSex() + "'";
			}

			if (condition.getAge() != -1) {
				where += " and age=" + condition.getAge();
			}
			// 如果搜索条件中有部门的话，用这个部门的id去employee表中找d_id
			if (condition.getDep() != null && condition.getDep().getId() != -1) {
				where += " and d_id=" + condition.getDep().getId();
			}

			// 查询employee表中的人数
			String sql = "select count(*) from employee as e left join department as d on e.d_id=d.id " + where;
			rs = stat.executeQuery(sql);
			if (rs.next()) {
				// 查出来的结果集就1列，直接设置1
				count = rs.getInt(1);
			}

			// 7.关闭
			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	public boolean add(Employee emp) {
		int rs = 0;
		Connection conn = null;
		PreparedStatement pstat = null;

		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {

			// 3.与数据库建立连接
			conn = getConnection();
			// 4.建立statement sql语句执行器

			// 5.执行sql语句并得到结果
			// 下面这个是查询,查出来的结果类型是ResultSet类型的，跟游标一样
			String sql = "insert into employee(name,sex,age,d_id,photo)values(?,?,?,?,?)";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, emp.getName());
			pstat.setString(2, emp.getSex());
			pstat.setInt(3, emp.getAge());
			//因为dep的id是integer类型，如果添加的时候不选部门，它就是个null，那么在这里会报空指针异常
			//它在传回去的时候会报，这是个特殊的例子，所以用object
			pstat.setObject(4, emp.getDep().getId());
			pstat.setString(5, emp.getPhoto());
			rs = pstat.executeUpdate();
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}

		return rs > 0;
	}

	public boolean update(Employee emp) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstat = null;
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {
			conn = getConnection();
			// 4.建立statement sql语句执行器
			// Statement stat = conn.createStatement();
			// 5.执行sql语句并得到结果
			// 下面这个是增加,出来的结果类型是int，影响的行数
			// 增删改全调这个方法
			// int rs = stat.executeUpdate("update employee set name=('hh')where
			// '"+emp.getName()+"'=name");

			String sql = "update employee set name=?,sex=?,age=?,d_id=? where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, emp.getName());
			pstat.setString(2, emp.getSex());
			pstat.setInt(3, emp.getAge());
			pstat.setObject(4, emp.getDep().getId());
			pstat.setInt(5, emp.getId());
			// employee表中没有具体的部门，只有d_id
			int rs = pstat.executeUpdate();

			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
			if (rs > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}
		return flag;
	}

	// 这个方法是点击修改的时候用选中的那一行的id去数据库中查找一遍，显示到update页面
	public Employee search(int id) {
		Employee emp = new Employee();
		Department dep=new Department();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			// 4.建立statement sql语句执行器
			stat = conn.createStatement();
			// 5.执行sql语句并得到结果
			// 下面这个是查询,查出来的结果类型是ResultSet类型的，跟游标一样
			String sql = "select e.*,d.name as dName from employee as e left join department as d on e.d_id=d.id where e.id =" + id;
			rs = stat.executeQuery(sql);
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
			while (rs.next()) {
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("dName"));
				emp.setDep(dep);

			}

			// 7.关闭
			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return emp;
	}

	public boolean delete(int id) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstat = null;
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {
			conn = getConnection();
			// 4.建立statement sql语句执行器
			// Statement stat = conn.createStatement();
			// 5.执行sql语句并得到结果
			// 下面这个是增加,出来的结果类型是int，影响的行数
			// 增删改全调这个方法
			// int rs = stat.executeUpdate("update employee set name=('hh')where
			// '"+emp.getName()+"'=name");

			String sql = "delete from employee where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			int rs = pstat.executeUpdate();

			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
			if (rs > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}
		return flag;
	}

	public boolean deleteBatch(String ids) {
		boolean flag = false;
		Connection conn = null;
		Statement stat = null;
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {
			conn = getConnection();
			// 4.建立statement sql语句执行器
			// Statement stat = conn.createStatement();
			// 5.执行sql语句并得到结果
			// 下面这个是增加,出来的结果类型是int，影响的行数
			// 增删改全调这个方法
			// int rs = stat.executeUpdate("update employee set name=('hh')where
			// '"+emp.getName()+"'=name");

			String sql = "delete from employee where id in (" + ids + ")";
			stat = conn.createStatement();
			int rs = stat.executeUpdate(sql);

			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
			if (rs > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, stat, null);
		}
		return flag;
	}

	public List<Employee> search(String ids) {
		List<Employee> list = new ArrayList<Employee>();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			// 4.建立statement sql语句执行器
			stat = conn.createStatement();
			// 5.执行sql语句并得到结果
			// 下面这个是查询,查出来的结果类型是ResultSet类型的，跟游标一样
			String sql = "select e.* ,d.name as dName from employee as e left join department as d on e.d_id=d.id where e.id in (" + ids + ")";
			rs = stat.executeQuery(sql);
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				Department dep=new Department();
				dep.setName(rs.getString("dName"));
				dep.setId(rs.getInt("d_id"));
				emp.setDep(dep);
				list.add(emp);
			}

			// 7.关闭
			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public boolean updateBatch1(Employee emp, String ids) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstat = null;
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {
			conn = getConnection();
			// 4.建立statement sql语句执行器
			// Statement stat = conn.createStatement();
			// 5.执行sql语句并得到结果
			// 下面这个是增加,出来的结果类型是int，影响的行数
			// 增删改全调这个方法
			// int rs = stat.executeUpdate("update employee set name=('hh')where
			// '"+emp.getName()+"'=name");

			String sql = "update employee set name=?,sex=?,age=?,d_id=? where id in (" + ids + ")";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, emp.getName());
			pstat.setString(2, emp.getSex());
			pstat.setInt(3, emp.getAge());
			pstat.setObject(4, emp.getDep().getId());
			// employee表中没有具体的部门，只有d_id
			int rs = pstat.executeUpdate();

			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
			if (rs > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}
		return flag;
	}

	public boolean updateBatch2(List<Employee> list) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement pstat = null;
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {
			conn = getConnection();
			// 循环list，挨个修改
			for (int i = 0; i < list.size(); i++) {
				Employee emp = list.get(i);
				String sql = "update employee set name=?,sex=?,age=?,d_id=? where id = ?";
				pstat = conn.prepareStatement(sql);
				pstat.setString(1, emp.getName());
				pstat.setString(2, emp.getSex());
				pstat.setInt(3, emp.getAge());
				pstat.setObject(4, emp.getDep().getId());
				pstat.setInt(5, emp.getId());
				// employee表中没有具体的部门，只有d_id
				int rs = pstat.executeUpdate();

				// 6.对结果集进行处理
				// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
				if (rs > 0) {
					flag = true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, pstat, null);
		}
		return flag;
	}

	public List<Employee> search(Employee condition, int begin, int size) {
		List<Employee> list = new ArrayList<Employee>();
		// 在这里新建一个list
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		// 2利用反射 ，加载数据库驱动,加载MySQL就是下面这么写
		try {

			// 3.与数据库建立连接
			conn = getConnection();
			// 4.建立statement sql语句执行器
			stat = conn.createStatement();
			// 5.执行sql语句并得到结果
			// 下面这个是查询,查出来的结果类型是ResultSet类型的，跟游标一样
			String where = "where 1=1";
			if (condition.getName() != null && !condition.getName().equals("")) {
				where += " and e.name='" + condition.getName() + "'";
			}
			if (condition.getSex() != null && !condition.getSex().equals("")) {
				where += " and sex='" + condition.getSex() + "'";
			}

			if (condition.getAge() != -1) {
				where += " and age=" + condition.getAge();
			}
			// 如果搜索条件中有部门的话，用这个部门的id去employee表中找d_id
			if (condition.getDep() != null && condition.getDep().getId() != -1) {
				where += " and d_id=" + condition.getDep().getId();
			}
			//
			// if (condition.getDep().getId() != -1) {
			// // 如果输入部门来查询，那么dep的id就不是-1
			// where += " and d_id= " + condition.getDep().getId();
			// // employee与department表的联系就是d_id
			// // 搜索输入的是一个员工的属性之一，也就是dep，它是department类的，它有id，而这个id就是employee表中的d_id
			// }
			// String sql = "select e.*,d.name as dName,emp_count from employee as e left
			// join department as d on e.d_id=d.id "
			// + where;
			// 注意分号周围加空格
			String sql = " select e.*,d.name as dName from employee as e left join department as d on e.d_id=d.id " + where + " limit " + begin + "," + size;
			rs = stat.executeQuery(sql);
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("Name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				emp.setPhoto(rs.getString("photo"));
				Department dep = new Department();
				dep.setId(rs.getInt("d_id"));
				dep.setName(rs.getString("dName"));
				emp.setDep(dep);
				list.add(emp);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(conn, stat, rs);
		}

		return list;
	}
}
