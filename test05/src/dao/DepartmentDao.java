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

//对数据库增删改查的方法类
public class DepartmentDao extends BaseDao {

	// 上面的方法是存储方法，还需要一个读取的方法，再次运行程序的时候能看到上一次输入的数据，也就是输入流
	// 这里没有void 是有返回值的方法，List<Department>这是定义这个方法返回来的值的类型
	public List<Department> search() {
		List<Department> list = new ArrayList<Department>();
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
			// String sql = "select e.*,d.name as dName,dep_count from Department as e left
			// join department as"
			// + " d on e.d_id=d.id ";
			String sql = "select * from Department ";
			rs = stat.executeQuery(sql);
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("Name"));
				dep.setEmpCount(rs.getInt("emp_count"));
				list.add(dep);
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

	public List<Department> search(int begin, int size) {
		List<Department> list = new ArrayList<Department>();
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
			// String sql = "select e.*,d.name as dName,dep_count from Department as e left
			// join department as"
			// + " d on e.d_id=d.id ";
			String sql = "select * from Department limit " + begin + "," + size;
			rs = stat.executeQuery(sql);
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("Name"));
				dep.setEmpCount(rs.getInt("emp_count"));
				list.add(dep);
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
			// 查询Department表中的人数
			String sql = "select count(*) from Department ";
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
	public int searchCount(Department condition) {
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
			if (condition.getName()!=null&&!condition.getName().equals("")) {
				where += " and name='" + condition.getName() + "'";
			}
			if (condition.getEmpCount()!=-1) {
				where += " and ifnull(emp_count,0)=" + condition.getEmpCount() ;
			}
		

			
			// 查询Department表中的人数
			String sql = "select count(*) from Department "+where ;
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

	public boolean add(Department dep) {
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
			String sql = "insert into Department(name)values(?)";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dep.getName());
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

	public boolean update(Department dep) {
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
			// int rs = stat.executeUpdate("update Department set name=('hh')where
			// '"+dep.getName()+"'=name");

			String sql = "update Department set name=? where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dep.getName());
			pstat.setInt(2, dep.getId());
			// Department表中没有具体的部门，只有d_id
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
	public Department search(int id) {
		Department dep = new Department();
		Connection conn = null;
		Statement stat = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			// 4.建立statement sql语句执行器
			stat = conn.createStatement();
			// 5.执行sql语句并得到结果
			// 下面这个是查询,查出来的结果类型是ResultSet类型的，跟游标一样
			String sql = "select *from Department where id =" + id;
			rs = stat.executeQuery(sql);
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
			if (rs.next()) {
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("name"));
				dep.setEmpCount(rs.getInt("emp_count"));

			}

			// 7.关闭
			rs.close();
			stat.close();
			conn.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dep;
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
			// int rs = stat.executeUpdate("update department set name=('hh')where
			// '"+dep.getName()+"'=name");
			conn.setAutoCommit(false);
			// 用事务把它弄成一个整体
			String sql = "delete from department where id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			int rs = pstat.executeUpdate();
			pstat.close();
			// 删除部门的时候，把员工表里这个部门的人的d_id设为null
			sql = "update employee set d_id=null where d_id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			rs = pstat.executeUpdate();
			pstat.close();
			// 删除部门的时候，把员工表里这个部门的人的d_id设为null
			sql = "delete from r_dep_pro where d_id=?";
			pstat = conn.prepareStatement(sql);
			pstat.setInt(1, id);
			rs = pstat.executeUpdate();
			conn.commit();
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

	


	public List<Department> search(Department condition, int begin, int size) {
		List<Department> list = new ArrayList<Department>();
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
			//这里做了两个判断，当第一次打开界面的时候不传name，它就是null，打开后点搜索它就是空字符串
			//而EmpCount不用，因为它在servlet中就做了判断
			if (condition.getName()!=null&&!condition.getName().equals("")) {
				where += " and name='" + condition.getName() + "'";
			}
			//ifnull：如果属性为null的话可以赋一个值，如果不为null就是他自己，
			//这是因为在数据库中有的部门的emp_count为null，让它变成0,便于查找
			if (condition.getEmpCount()!=-1) {
				where += " and ifnull(emp_count,0)=" + condition.getEmpCount() ;
			}
			//注意分号周围加空格
			String sql = " select * from Department " + where + " limit " + begin + "," + size;
			rs = stat.executeQuery(sql);
			// 6.对结果集进行处理
			// .nest()是判断有没有吓一跳，有的话就返回true，没有就返回false
			while (rs.next()) {
				Department dep = new Department();
				dep.setId(rs.getInt("id"));
				dep.setName(rs.getString("Name"));
				dep.setEmpCount(rs.getInt("emp_Count"));
				// Department dep = new Department();
				// dep.setId(rs.getInt("d_id"));
				// dep.setName(rs.getString("dName"));
				// dep.setdepCount(rs.getInt("dep_count"));
				// dep.setDep(dep);
				list.add(dep);
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
